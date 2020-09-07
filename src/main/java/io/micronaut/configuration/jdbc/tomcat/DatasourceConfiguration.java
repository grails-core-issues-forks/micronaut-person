/*
 * Copyright 2017-2018 original authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.micronaut.configuration.jdbc.tomcat;

import io.micronaut.context.annotation.EachProperty;
import io.micronaut.context.annotation.Parameter;
import io.micronaut.core.convert.format.MapFormat;
import io.micronaut.core.naming.conventions.StringConvention;
import io.micronaut.jdbc.BasicJdbcConfiguration;
import io.micronaut.jdbc.CalculatedSettings;
import org.apache.tomcat.jdbc.pool.PoolProperties;

import javax.annotation.PostConstruct;
import java.util.Properties;
import io.micronaut.core.annotation.Introspected;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Allows the configuration of Tomcat JDBC data sources. All properties on
 * {@link PoolProperties} are available to be configured.
 *
 * If the url, driver class, username, or password are missing, sensible defaults
 * will be provided when possible. If no configuration beyond the datasource name
 * is provided, an in memory datasource will be configured based on the available
 * drivers on the classpath.
 *
 * @author James Kleeh
 * @since 1.0
 */
@EachProperty(value = BasicJdbcConfiguration.PREFIX, primary = "default")
@Introspected
public class DatasourceConfiguration extends PoolProperties implements BasicJdbcConfiguration {
    private static final Logger LOG = LoggerFactory.getLogger(DatasourceConfiguration.class);
    private CalculatedSettings calculatedSettings;

    /**
     * Constructor.
     * @param name name that comes from properties
     */
    public DatasourceConfiguration(@Parameter String name) {
        super();
        this.setName(name);
        this.calculatedSettings = new CalculatedSettings(this);
        LOG.info("name {}, calculatedSettings {}", name, calculatedSettings);
    }

    /**
     * After construction of the bean, call the getters.
     */
    @PostConstruct
    void postConstruct() {
        getDriverClassName();
        getUrl();
        getUsername();
        getPassword();
        getValidationQuery();
    }

    @Override
    public void setDbProperties(@MapFormat(keyFormat = StringConvention.UNDER_SCORE_SEPARATED) Properties dbProperties) {
        LOG.info("dbProperties {}", dbProperties);
        super.setDbProperties(dbProperties);
    }

    /**
     * @return The calculated driver class name
     */
    @Override
    public String getDriverClassName() {
        LOG.info("calculatedSettings.getDriverClassName() {}", calculatedSettings.getDriverClassName());
        return calculatedSettings.getDriverClassName();
    }

    /**
     * @return The configured driver class name
     */
    @Override
    public String getConfiguredDriverClassName() {
        LOG.info("super.getDriverClassName() {}", super.getDriverClassName());
        return super.getDriverClassName();
    }

    /**
     * @return The calculated URL
     */
    @Override
    public String getUrl() {
        LOG.info("calculatedSettings.getUrl() {}", calculatedSettings.getUrl());
        return calculatedSettings.getUrl();
    }

    /**
     * @return The configured URL
     */
    @Override
    public String getConfiguredUrl() {
        LOG.info("super.getUrl() {}", super.getUrl());
        return super.getUrl();
    }

    /**
     * @return The calculated username
     */
    @Override
    public String getUsername() {
        LOG.info("calculatedSettings.getUsername() {}", calculatedSettings.getUsername());
        return calculatedSettings.getUsername();
    }

    /**
     * @return The configured username
     */
    @Override
    public String getConfiguredUsername() {
        LOG.info("super.getUsername() {}", super.getUsername());
        return super.getUsername();
    }

    /**
     * @return The calculated password
     */
    @Override
    public String getPassword() {
        return calculatedSettings.getPassword();
    }

    /**
     * @return The configured password
     */
    @Override
    public String getConfiguredPassword() {
        return super.getPassword();
    }

    /**
     * @return The calculated validation query
     */
    @Override
    public String getValidationQuery() {
        return calculatedSettings.getValidationQuery();
    }

    /**
     * @return The configured validation query
     */
    @Override
    public String getConfiguredValidationQuery() {
        return super.getValidationQuery();
    }

    /**
     * @return The JNDI name
     */
    public String getJndiName() {
        return getDataSourceJNDI();
    }

    /**
     * @param jndiName Set the JDNI name
     */
    public void setJndiName(String jndiName) {
        setDataSourceJNDI(jndiName);
    }
}