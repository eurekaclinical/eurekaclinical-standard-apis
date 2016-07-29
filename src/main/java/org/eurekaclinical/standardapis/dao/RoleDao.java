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
import org.eurekaclinical.standardapis.entity.RoleEntity;

/**
 * A data access object interface for working with {@link RoleEntity} objects in
 * a data store.
 *
 * @author Andrew Post
 * @param <U> a user type.
 *
 */
public interface RoleDao<U extends RoleEntity> extends Dao<U, Long> {

    /**
     * Get a role, given the name of that role.
     *
     * @param name The name of the role to search for.
     * @return A {@link RoleEntity} object with a name matching the given name.
     */
    U getRoleByName(String name);
}
