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

/**
 * Base class for implementing properties for a Eureka! Clinical service.
 * 
 * @author hrathod
 */
public abstract class CasJerseyEurekaClinicalProperties extends CasEurekaClinicalProperties {

    /**
     * The class level logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(CasJerseyEurekaClinicalProperties.class);

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
    protected CasJerseyEurekaClinicalProperties(String defaultConfigDir) {
        super(defaultConfigDir);
    }

    /**
     * Whether or not to use CAS gateway mode for authentication. Set to
     * <code>true</code> for Eureka! Clinical services because we never want
     * the login screen.
     * 
     * @return <code>true</code> always.
     */
    @Override
    public boolean getCasLoginGateway() {
        return true;
    }
    
}
