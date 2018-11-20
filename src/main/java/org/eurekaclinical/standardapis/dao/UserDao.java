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

import java.security.Principal;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.eurekaclinical.standardapis.entity.RoleEntity;
import org.eurekaclinical.standardapis.entity.UserEntity;

/**
 * A data access object interface for working with {@link UserEntity} objects
 * in a data store.
 *
 * @author Andrew Post
 * @param <R> a role type.
 * @param <U> a user type.
 *
 */
public interface UserDao<R extends RoleEntity, U extends UserEntity<R>> extends DaoWithUniqueName<U, Long> {

    U getByHttpServletRequest(HttpServletRequest request);
    
    U getByPrincipal(Principal principal);
    
    /**
     * Creates a new user entity instance with all-default values. It does not
     * persist it.
     * 
     * @return a newly created entity.
     */
    U newUser();
    
    /**
     * Creates a user record with the given username, grants the user the given 
     * roles, and persists the user.
     * This method calls {@link #newUser() } to create the user instance.
     * 
     * @param username the username.
     * @param roles zero or more roles.
     */
    void createUser(String username, List<R> roles);

}
