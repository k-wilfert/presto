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

import com.facebook.airlift.configuration.AbstractConfigurationAwareModule;
import com.facebook.airlift.configuration.ConfigBinder;
import com.facebook.presto.iceberg.IcebergMetadataFactory;
import com.facebook.presto.iceberg.IcebergNativeCatalogFactory;
import com.facebook.presto.iceberg.IcebergNativeMetadataFactory;
import com.google.inject.Binder;
import com.google.inject.Scopes;

import com.facebook.airlift.configuration.AbstractConfigurationAwareModule;
import com.facebook.presto.iceberg.IcebergMetadataFactory;
import com.facebook.presto.iceberg.IcebergNativeCatalogFactory;
import com.facebook.presto.iceberg.IcebergNativeMetadataFactory;
import com.google.inject.Binder;
import com.google.inject.Scopes;

import static com.facebook.airlift.configuration.ConfigBinder.configBinder;

public class IcebergJdbcCatalogModule
        extends AbstractConfigurationAwareModule
{
    @Override
    public void setup(Binder binder)
    {
        // Bind the JDBC config bean
        configBinder(binder).bindConfig(IcebergJdbcConfig.class);

        // Bind the native catalog factory to the JDBC implementation
        binder.bind(IcebergNativeCatalogFactory.class)
                .to(IcebergJdbcCatalogFactory.class)
                .in(Scopes.SINGLETON);

        // Use the standard Iceberg metadata factory
        binder.bind(IcebergMetadataFactory.class)
                .to(IcebergNativeMetadataFactory.class)
                .in(Scopes.SINGLETON);
    }
}
