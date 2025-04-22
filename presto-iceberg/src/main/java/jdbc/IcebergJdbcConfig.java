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

import com.facebook.airlift.configuration.Config;
import com.facebook.airlift.configuration.ConfigDescription;
import javax.validation.constraints.NotNull;
import java.util.Optional;
import com.facebook.presto.iceberg.rest.SessionType;

public class IcebergJdbcConfig
{

    private String uri;
    private String username;
    private String password;
    private String driver;
    private boolean nestedNamespaceEnabled = true;

    @NotNull
    public String getUri()
    {
        return uri;
    }

    @Config("iceberg.jdbc.uri")
    @ConfigDescription("JDBC connection string for Iceberg catalog (e.g. jdbc:postgresql://host:port/db)")
    public IcebergJdbcConfig setUri(String uri)
    {
        this.uri = uri;
        return this;
    }

    public String getUsername()
    {
        return username;
    }

    @Config("iceberg.jdbc.username")
    @ConfigDescription("JDBC user name")
    public IcebergJdbcConfig setUsername(String username)
    {
        this.username = username;
        return this;
    }

    public String getPassword()
    {
        return password;
    }

    @Config("iceberg.jdbc.password")
    @ConfigDescription("JDBC password")
    public IcebergJdbcConfig setPassword(String password)
    {
        this.password = password;
        return this;
    }

    public String getDriver()
    {
        return driver;
    }

    @Config("iceberg.jdbc.driver")
    @ConfigDescription("JDBC driver class name (e.g. org.postgresql.Driver)")
    public IcebergJdbcConfig setDriver(String driver)
    {
        this.driver = driver;
        return this;
    }

    public boolean isNestedNamespaceEnabled()
    {
        return nestedNamespaceEnabled;
    }

    @Config("iceberg.jdbc.nested-namespace-enabled")
    @ConfigDescription("Allow nested namespaces in this catalog")
    public IcebergJdbcConfig setNestedNamespaceEnabled(boolean nestedNamespaceEnabled)
    {
        this.nestedNamespaceEnabled = nestedNamespaceEnabled;
        return this;
    }
}