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

import javax.inject.Provider;
import org.eurekaclinical.standardapis.entity.RoleEntity;

import org.eurekaclinical.standardapis.entity.UserTemplateEntity;

/**
 * An implementation of the {@link UserDao} interface, backed by JPA entities
 * and queries.
 *
 * @author Andrew Post
 * @param <R> the role entity class.
 * @param <T> the user template entity class.
 */
public abstract class AbstractJpaUserTemplateDao<R extends RoleEntity, T extends UserTemplateEntity<R>> extends GenericDao<T, Long> implements UserTemplateDao<R, T> {

    /**
     * Create an object with the give entity manager.
     *
     * @param cls the user entity class.
     * @param inEMProvider The entity manager to be used for communication with
     * the data store.
     */
    public AbstractJpaUserTemplateDao(Class<T> cls, final Provider<EntityManager> inEMProvider) {
        super(cls, inEMProvider);
    }

    @Override
    public T getByName(String name) {
        return getUniqueByAttribute("name", name);
    }

}
