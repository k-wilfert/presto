package jdbc;

import com.facebook.presto.spi.Plugin;
import com.google.inject.Module;
import java.util.List;

/**
 * Presto plugin entry point for the Iceberg JDBC catalog.
 */
public class IcebergJdbcPlugin implements Plugin
{
    @Override
    public List<Module> getModules()
    {
        return List.of(
                new IcebergJdbcCatalogModule()
        );
    }
}