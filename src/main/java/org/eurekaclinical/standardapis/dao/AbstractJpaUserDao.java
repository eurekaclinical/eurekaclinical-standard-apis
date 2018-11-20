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
import java.util.List;
import javax.inject.Provider;

import javax.servlet.http.HttpServletRequest;
import org.eurekaclinical.standardapis.entity.RoleEntity;
import org.eurekaclinical.standardapis.entity.UserEntity;

/**
 * An implementation of the {@link UserDao} interface, backed by JPA entities
 * and queries.
 *
 * @author Andrew Post
 * @param <R> a role type.
 * @param <U> a user type.
 */
public abstract class AbstractJpaUserDao<R extends RoleEntity, U extends UserEntity<R>> extends GenericDao<U, Long> implements UserDao<R, U> {

    /**
     * Create an object with the give entity manager.
     *
     * @param cls the user entity class.
     * @param inEMProvider The entity manager to be used for communication with
     * the data store.
     */
    public AbstractJpaUserDao(Class<U> cls, final Provider<EntityManager> inEMProvider) {
        super(cls, inEMProvider);
    }

    @Override
    public U getByHttpServletRequest(HttpServletRequest request) {
        return getByPrincipal(request.getUserPrincipal());
    }

    @Override
    public U getByPrincipal(Principal principal) {
        return getByName(principal.getName());
    }

    @Override
    public U getByName(String username) {
        return getUniqueByAttribute("username", username);
    }

    @Override
    public void createUser(String username, List<R> roles) {
        U user = newUser();
        user.setUsername(username);
        for (R role : roles) {
            user.addRole(role);
        }
        create(user);
    }

}
