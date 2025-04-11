package jdbc;

import com.facebook.airlift.configuration.Config;
import com.facebook.airlift.configuration.ConfigDescription;
import javax.validation.constraints.NotNull;
import java.util.Optional;
import com.facebook.presto.iceberg.rest.SessionType;

public class IcebergJdbcConfig
{
    private String url;
    private String user;
    private String password;
    private String driverClassName;
    private SessionType sessionType;
    private boolean nestedNamespaceEnabled = true;
    private String extraConnectionProperties;

    @NotNull
    public Optional<String> getUrl()
    {
        return Optional.ofNullable(url);
    }

    @Config("iceberg.jdbc.url")
    @ConfigDescription("The JDBC URL to connect to the remote database")
    public IcebergJdbcConfig setUrl(String url)
    {
        this.url = url;
        return this;
    }

    public Optional<String> getUser()
    {
        return Optional.ofNullable(user);
    }

    @Config("iceberg.jdbc.user")
    @ConfigDescription("The user name for JDBC connections")
    public IcebergJdbcConfig setUser(String user)
    {
        this.user = user;
        return this;
    }

    public Optional<String> getPassword()
    {
        return Optional.ofNullable(password);
    }

    @Config("iceberg.jdbc.password")
    @ConfigDescription("The password for JDBC connections")
    public IcebergJdbcConfig setPassword(String password)
    {
        this.password = password;
        return this;
    }

    @NotNull
    public Optional<String> getDriverClassName()
    {
        return Optional.ofNullable(driverClassName);
    }

    @Config("iceberg.jdbc.driver-class-name")
    @ConfigDescription("The fully-qualified JDBC driver class name")
    public IcebergJdbcConfig setDriverClassName(String driverClassName)
    {
        this.driverClassName = driverClassName;
        return this;
    }

    public Optional<SessionType> getSessionType()
    {
        return Optional.ofNullable(sessionType);
    }

    @Config("iceberg.jdbc.session.type")
    @ConfigDescription("The session type for JDBC catalog (NONE | USER)")
    public IcebergJdbcConfig setSessionType(SessionType sessionType)
    {
        this.sessionType = sessionType;
        return this;
    }

    public boolean isNestedNamespaceEnabled()
    {
        return nestedNamespaceEnabled;
    }

    @Config("iceberg.jdbc.nested-namespace-enabled")
    @ConfigDescription("Enable nested‑namespace support (default: true)")
    public IcebergJdbcConfig setNestedNamespaceEnabled(boolean nestedNamespaceEnabled)
    {
        this.nestedNamespaceEnabled = nestedNamespaceEnabled;
        return this;
    }

    public Optional<String> getExtraConnectionProperties()
    {
        return Optional.ofNullable(extraConnectionProperties);
    }

    @Config("iceberg.jdbc.extra-connection-properties")
    @ConfigDescription("Extra JDBC properties (key1=value1;key2=value2)")
    public IcebergJdbcConfig setExtraConnectionProperties(String extraConnectionProperties)
    {
        this.extraConnectionProperties = extraConnectionProperties;
        return this;
    }
}