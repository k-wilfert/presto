package com.facebook.presto.iceberg.rest;

import com.facebook.presto.hive.NodeVersion;
import com.facebook.presto.hive.gcs.GcsConfigurationInitializer;
import com.facebook.presto.hive.s3.S3ConfigurationUpdater;
import com.facebook.presto.iceberg.IcebergCatalogName;
import com.facebook.presto.iceberg.IcebergConfig;
import com.facebook.presto.iceberg.IcebergNativeCatalogFactory;
import com.facebook.presto.spi.ConnectorSession;
import com.facebook.presto.spi.PrestoException;
import com.facebook.presto.spi.security.ConnectorIdentity;
import com.google.common.collect.ImmutableMap;
import com.google.common.util.concurrent.UncheckedExecutionException;
import io.jsonwebtoken.Jwts;
import org.apache.iceberg.CatalogProperties;
import org.apache.iceberg.catalog.Catalog;
import org.apache.iceberg.catalog.SessionCatalog.SessionContext;
import org.apache.iceberg.rest.HTTPClient;
import org.apache.iceberg.rest.RESTCatalog;

import javax.inject.Inject;

import java.util.Date;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

import static com.facebook.presto.iceberg.rest.AuthenticationType.OAUTH2;
import static com.facebook.presto.iceberg.rest.SessionType.USER;
import static com.google.common.base.Throwables.throwIfInstanceOf;
import static com.google.common.base.Throwables.throwIfUnchecked;
import static java.lang.String.format;
import static java.util.Objects.requireNonNull;
import static java.util.UUID.randomUUID;
import static org.apache.iceberg.CatalogProperties.URI;
import static org.apache.iceberg.CatalogUtil.configureHadoopConf;
import static org.apache.iceberg.rest.auth.OAuth2Properties.CREDENTIAL;
import static org.apache.iceberg.rest.auth.OAuth2Properties.JWT_TOKEN_TYPE;
import static org.apache.iceberg.rest.auth.OAuth2Properties.OAUTH2_SERVER_URI;
import static org.apache.iceberg.rest.auth.OAuth2Properties.SCOPE;
import static org.apache.iceberg.rest.auth.OAuth2Properties.TOKEN;


public class IceBergJdbcCatalogFactory {

    //insert actual code here!
}
