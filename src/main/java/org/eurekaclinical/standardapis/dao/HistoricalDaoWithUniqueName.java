package org.eurekaclinical.standardapis.dao;

/*-
 * #%L
 * Eureka! Clinical Standard APIs
 * %%
 * Copyright (C) 2016 - 2017 Emory University
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

import org.eurekaclinical.standardapis.entity.HistoricalEntity;

/**
 * Interface for data access objects for managing an entity that stores rather
 * than overwrites its previous states. The entity must implement the
 * {@link HistoricalEntity} interface.
 * 
 * @author Andrew Post
 * @param <T> an entity that stores rather than overwrites its previous states.
 */
public interface HistoricalDaoWithUniqueName <T extends HistoricalEntity<T>> extends HistoricalDao<T> {
    
    /**
     * Gets the current entity with the given name, for entities that store
     * historical state about themselves. 
     * 
     * @param name the entity's unique name.
     * @return the current entity.
     */
    T getCurrentByName(String name);
}
