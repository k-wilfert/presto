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
import com.facebook.presto.iceberg.IcebergConfig;
import com.facebook.presto.iceberg.IcebergNativeCatalogFactory;
import com.facebook.presto.iceberg.IcebergCatalogName;
import com.facebook.presto.spi.ConnectorSession;
import com.facebook.presto.spi.PrestoException;
import com.facebook.presto.spi.StandardErrorCode;
import com.google.common.collect.ImmutableMap;
import org.apache.iceberg.Catalog;
import org.apache.iceberg.CatalogProperties;
import org.apache.iceberg.jdbc.JdbcCatalog;

import javax.inject.Inject;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import static java.util.Objects.requireNonNull;

public class IcebergJdbcCatalogFactory
        extends IcebergNativeCatalogFactory
{
    private final IcebergJdbcConfig jdbcConfig;
    private final String catalogName;
    private final boolean nestedNamespaceEnabled;

    @Inject
    public IcebergJdbcCatalogFactory(
            IcebergConfig config,
            IcebergJdbcConfig jdbcConfig,
            IcebergCatalogName catalogName)
    {
        super(config, catalogName, null, null);
        this.jdbcConfig = requireNonNull(jdbcConfig, "jdbcConfig is null");
        this.catalogName = requireNonNull(catalogName, "catalogName is null").getCatalogName();
        this.nestedNamespaceEnabled = jdbcConfig.isNestedNamespaceEnabled();
    }

    @Override
    public Catalog getCatalog(ConnectorSession session)
    {
        try {
            return catalogCache.get(catalogName, () -> {
                JdbcCatalog catalog = new JdbcCatalog();
                catalog.initialize(catalogName, getCatalogProperties(session));
                return catalog;
            });
        }
        catch (ExecutionException e) {
            throw new PrestoException(
                    StandardErrorCode.GENERIC_INTERNAL_ERROR,
                    "Failed to create JDBC catalog " + catalogName,
                    e);
        }
    }

    @Override
    protected Map<String, String> getCatalogProperties(ConnectorSession session)
    {
        ImmutableMap.Builder<String, String> props = ImmutableMap.builder();
        props.put(CatalogProperties.URI, jdbcConfig.getUri());
        if (jdbcConfig.getUsername() != null) {
            props.put("jdbc.user", jdbcConfig.getUsername());
        }
        if (jdbcConfig.getPassword() != null) {
            props.put("jdbc.password", jdbcConfig.getPassword());
        }
        if (jdbcConfig.getDriver() != null) {
            props.put("jdbc.driver", jdbcConfig.getDriver());
        }
        return props.build();
    }

    @Override
    public boolean isNestedNamespaceEnabled()
    {
        return nestedNamespaceEnabled;
    }
}
