package org.eurekaclinical.standardapis.entity;

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

import java.util.List;

/**
 * Interface for JPA user entities. The only fields that may be required for a
 * user entity are the username and roles. Everything else must be optional or
 * have a default value if left unspecified.
 * 
 * @author Andrew Post
 * @param <R> a role type.
 */
public interface UserEntity<R extends RoleEntity> extends Entity<Long> {

    /**
     * Get the user's unique identifier.
     *
     * @return A {@link Long} representing the user's unique identifier.
     */
    @Override
    Long getId();

    /**
     * Get all the roles assigned to the user.
     *
     * @return A list of roles assigned to the user.
     */
    List<R> getRoles();
    
    /**
     * Gets all the roles assigned to the user as strings.
     * 
     * @return An array of roles assigned to the user.
     */
    String[] getRoleNames();

    /**
     * Get the user's unique username.
     *
     * @return the username.
     */
    String getUsername();

    /**
     * Set the user's unique identifier.
     *
     * @param inId A {@link Long} representing the user's unique identifier.
     */
    @Override
    void setId(final Long inId);

    /**
     * Set the roles assigned to the current user.
     *
     * @param inRoles A list of roles to be assigned to the user.
     */
    void setRoles(final List<R> inRoles);
    
    void addRole(R role);
    
    void removeRole(R role);

    /**
     * Set the user's unique username.
     *
     * @param inUsername the usernames.
     */
    void setUsername(final String inUsername);
    
}
