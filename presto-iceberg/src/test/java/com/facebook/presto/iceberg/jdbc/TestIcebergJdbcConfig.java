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
package com.facebook.presto.iceberg.jdbc;

import com.google.common.collect.ImmutableMap;
import org.junit.jupiter.api.Test;

import static com.facebook.airlift.configuration.testing.ConfigAssertions.*;

import java.util.Map;

public class TestIcebergJdbcConfig
{
    @Test
    public void testDefaults()
    {
        assertRecordedDefaults(
                recordDefaults(IcebergJdbcConfig.class)
                        .setUri(null)
                        .setUsername(null)
                        .setPassword(null)
                        .setDriver(null)
                        .setNestedNamespaceEnabled(true)
        );
    }

    @Test
    public void testExplicitPropertyMappings()
    {
        Map<String, String> properties = ImmutableMap.<String, String>builder()
                .put("iceberg.jdbc.uri", "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1")
                .put("iceberg.jdbc.username", "sa")
                .put("iceberg.jdbc.password", "secret")
                .put("iceberg.jdbc.driver", "org.h2.Driver")
                .put("iceberg.jdbc.nested-namespace-enabled", "false")
                .build();

        IcebergJdbcConfig expected = new IcebergJdbcConfig()
                .setUri("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1")
                .setUsername("sa")
                .setPassword("secret")
                .setDriver("org.h2.Driver")
                .setNestedNamespaceEnabled(false);

        assertFullMapping(properties, expected);
    }
}
