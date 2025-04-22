/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package jdbc;

import com.facebook.presto.hive.NodeVersion;
import com.facebook.presto.hive.gcs.GcsConfigurationInitializer;
import com.facebook.presto.hive.s3.S3ConfigurationUpdater;
import com.facebook.presto.iceberg.IcebergCatalogName;
import com.facebook.presto.iceberg.IcebergConfig;
import com.facebook.presto.iceberg.IcebergNativeCatalogFactory;
import com.facebook.presto.iceberg.rest.IcebergJdbcConfig;
import com.facebook.presto.spi.ConnectorSession;
import com.facebook.presto.spi.PrestoException;
import com.google.common.collect.ImmutableMap;
import com.google.common.util.concurrent.UncheckedExecutionException;
import org.apache.iceberg.Catalog;
import org.apache.iceberg.catalog.CatalogProperties;
import org.apache.iceberg.jdbc.JdbcCatalog;

import javax.inject.Inject;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

import static com.google.common.base.Throwables.throwIfInstanceOf;
import static com.google.common.base.Throwables.throwIfUnchecked;
import static java.util.Objects.requireNonNull;

/**
 * Factory for creating Iceberg JDBC catalogs in Presto.
 */
public class IcebergJdbcCatalogFactory
        extends IcebergNativeCatalogFactory
{
    private final IcebergJdbcConfig catalogConfig;
    private final NodeVersion nodeVersion;
    private final String catalogName;
    private final boolean nestedNamespaceEnabled;

    @Inject
    public IcebergJdbcCatalogFactory(
            IcebergConfig config,
            IcebergJdbcConfig catalogConfig,
            IcebergCatalogName catalogName,
            S3ConfigurationUpdater s3ConfigurationUpdater,
            GcsConfigurationInitializer gcsConfigurationInitializer,
            NodeVersion nodeVersion)
    {
        super(config, catalogName, s3ConfigurationUpdater, gcsConfigurationInitializer);
        this.catalogConfig = requireNonNull(catalogConfig, "catalogConfig is null");
        this.nodeVersion = requireNonNull(nodeVersion, "nodeVersion is null");
        this.catalogName = requireNonNull(catalogName, "catalogName is null").getCatalogName();
        this.nestedNamespaceEnabled = catalogConfig.isNestedNamespaceEnabled();
    }

    @Override
    public Catalog getCatalog(ConnectorSession session)
    {
        try {
            return catalogCache.get(getCacheKey(session), () -> {
                JdbcCatalog catalog = new JdbcCatalog();
                catalog.initialize(catalogName, getCatalogProperties(session));
                return catalog;
            });
        }
        catch (ExecutionException | UncheckedExecutionException e) {
            throwIfInstanceOf(e.getCause(), PrestoException.class);
            throwIfUnchecked(e);
            throw new UncheckedExecutionException(e);
        }
    }

    @Override
    protected Optional<String> getCatalogCacheKey(ConnectorSession session)
    {
        // No per-user session caching by default; override if needed
        return Optional.empty();
    }

    @Override
    protected Map<String, String> getCatalogProperties(ConnectorSession session)
    {
        ImmutableMap.Builder<String, String> properties = ImmutableMap.builder();

        // JDBC URI is required
        properties.put(CatalogProperties.URI,
                catalogConfig.getUri().orElseThrow(
                        () -> new IllegalStateException("iceberg.jdbc.uri must be set for JDBC catalog")));

        // Optional credentials
        catalogConfig.getUsername().ifPresent(user -> properties.put("jdbc.user", user));
        catalogConfig.getPassword().ifPresent(pass -> properties.put("jdbc.password", pass));
        catalogConfig.getDriverClass().ifPresent(driver -> properties.put("jdbc.driver", driver));

        // Nested namespace support
        if (nestedNamespaceEnabled) {
            properties.put("write.namespace.enabled", "true");
        }

        return properties.build();
    }

    @Override
    public boolean isNestedNamespaceEnabled()
    {
        return this.nestedNamespaceEnabled;
    }
}
