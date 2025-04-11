package jdbc;

import com.facebook.airlift.configuration.AbstractConfigurationAwareModule;
import com.facebook.airlift.configuration.ConfigBinder;
import com.facebook.presto.iceberg.IcebergMetadataFactory;
import com.facebook.presto.iceberg.IcebergNativeCatalogFactory;
import com.facebook.presto.iceberg.IcebergNativeMetadataFactory;
import com.google.inject.Binder;
import com.google.inject.Scopes;

public class IcebergJdbcCatalogModule
        extends AbstractConfigurationAwareModule
{
    @Override
    public void setup(Binder binder)
    {
        // Bind our new JDBC config class
        ConfigBinder.configBinder(binder).bindConfig(IcebergJdbcConfig.class);

        // Bind the native catalog factory to your JDBC implementation
        binder.bind(IcebergNativeCatalogFactory.class)
                .to(IcebergJdbcCatalogFactory.class)
                .in(Scopes.SINGLETON);

        // Metadata factory remains the same as other Iceberg connectors
        binder.bind(IcebergMetadataFactory.class)
                .to(IcebergNativeMetadataFactory.class)
                .in(Scopes.SINGLETON);
    }
}
