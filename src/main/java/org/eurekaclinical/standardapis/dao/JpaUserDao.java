package org.eurekaclinical.standardapis.dao;

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

import javax.persistence.EntityManager;

import java.security.Principal;
import javax.inject.Provider;

import javax.servlet.http.HttpServletRequest;
import org.eurekaclinical.standardapis.entity.UserEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * An implementation of the {@link UserDao} interface, backed by JPA entities
 * and queries.
 *
 * @author Andrew Post
 * @param <U> the user entity class.
 */
public abstract class JpaUserDao<U extends UserEntity> extends GenericDao<U, Long> implements UserDao<U> {

    /**
     * The class level logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(
            JpaUserDao.class);

    /**
     * Create an object with the give entity manager.
     *
     * @param cls the user entity class.
     * @param inEMProvider The entity manager to be used for communication with
     * the data store.
     */
    public JpaUserDao(Class<U> cls, final Provider<EntityManager> inEMProvider) {
        super(cls, inEMProvider);
    }

    @Override
    public U getByHttpServletRequest(HttpServletRequest request) {
        return getByPrincipal(request.getUserPrincipal());
    }

    @Override
    public U getByPrincipal(Principal principal) {
        return getByUsername(principal.getName());
    }

    @Override
    public abstract U getByUsername(String username);

}
