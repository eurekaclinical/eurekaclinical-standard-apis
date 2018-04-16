package org.eurekaclinical.standardapis.props;

/*-
 * #%L
 * Eureka! Clinical Standard APIs
 * %%
 * Copyright (C) 2016 Emory University
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.UriBuilder;
import java.util.List;

/**
 * Base class for implementing properties for Eureka! Clinical microservices.
 * 
 * @author hrathod
 */
public abstract class CasEurekaClinicalProperties extends EurekaClinicalProperties {

    /**
     * The class level logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(CasEurekaClinicalProperties.class);

    /**
     * Loads the application configuration.
     *
     * There are two potential sources of application configuration. The
     * fallback configuration should always be there. The default configuration
     * directory, provided as an argument to this constructor, may optionally
     * have an application.properties file within it that overrides the fallback
     * configuration for each configuration property that is specified. The
     * <code>eureka.config.dir</code> system property allows specifying an
     * alternative configuration directory.
     *
     * @param defaultConfigDir the default location of configuration file, based
     * on the operating system. Cannot be <code>null</code>.
     */
    protected CasEurekaClinicalProperties(String defaultConfigDir) {
        super(defaultConfigDir);
    }

    public abstract String getProxyCallbackServer();

    public String getCasUrl() {
        return this.getValue("cas.url", "https://localhost/cas-server");
    }

    public String getCasLoginUrl() {
        UriBuilder builder = UriBuilder.fromUri(getCasUrl());
        builder.path(this.getValue("cas.url.login", "/login"));
        return builder.build().toString();
    }

    public String getCasLogoutUrl() {
        UriBuilder builder = UriBuilder.fromUri(getCasUrl());
        builder.path(this.getValue("cas.url.logout", "/logout"));
        return builder.build().toString();
    }
    
    /**
     * Whether or not to use CAS renew mode for authentication. Renew mode
     * will always present the authentication screen if the user does not
     * already have an application session. Set to <code>false</code> by
     * default.
     * 
     * @return <code>true</code> to use renew mode, <code>false</code>
     * otherwise.
     */
    public boolean getCasLoginRenew() {
        return Boolean.parseBoolean(getValue("cas.login.renew", "false"));
    }

    /**
     * Whether or not to use CAS gateway mode for authentication. Set to
     * <code>false</code>. Subclasses may override this method to return
     * <code>true</code> instead.
     * 
     * @return <code>false</code>.
     */
    public boolean getCasLoginGateway() {
        return false;
    }
    
    /**
     * For API gateways, specifies the URLs of the web clients that may attempt
     * to login to the gateway. This default implementation always returns
     * <code>null</code>. Subclasses for an API gateway should override this
     * implementation to get the list of allowed webclients from an
     * application property that is specific to the gateway.
     * 
     * @return a list of URL strings, or an empty list or <code>null</code> if
     * none are specified.
     */
    public List<String> getAllowedWebClientUrls() {
        return null;
    }

    /**
     * Get the JWT passphrase used to encode tokens.
     *
     * @return The JWT passphrase
     */
    public String getJwtSecret() {
        return getValue("jwt.secret");
    }

    /**
     * Returns whether JWT should be enabled for the service.  Defaults to false
     * if no configuration value is provided.
     *
     * @return Whether JWT should be enabled
     */
    public boolean isJwtEnabled() {
        return Boolean.parseBoolean(getValue("jwt.enabled", "false"));
    }

    /**
     * Returns a list of addresses whitelisted for access.  The value must be defined as a comma separated
     * list in the configuration. Defaults to no hosts in the whitelist if no configuration value is
     * provided.
     *
     * @return A list of internet addresses
     */
    public String getJwtWhitelist() {
        return getValue("jwt.whitelist", "");
    }
}
