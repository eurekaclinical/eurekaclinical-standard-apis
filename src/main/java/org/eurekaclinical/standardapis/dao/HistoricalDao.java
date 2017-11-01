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

import java.util.List;
import org.eurekaclinical.standardapis.entity.HistoricalEntity;

/**
 * A data access object that manages an entity that maintains its historical
 * state. Such entities implement the {@link HistoricalEntity} interface, which
 * defines <code>effectiveAt</code> and <code>expiredAt</code>. Real-world
 * objects will have one or more entity instances, each representing the 
 * object's state between the effective and expired datetimes.
 * 
 * @author Andrew Post
 * @param <E> the entity type.
 */
public interface HistoricalDao<E extends HistoricalEntity> extends Dao<E, Long> {
    List<E> getCurrent();
    
    /**
     * Updates the given entity. The id of the entity must match that of an
     * entity that is already in the database. This method will get the 
     * existing entity instance, set its <code>expiredAt</code> field to the 
     * current datetime, and create a new record reflecting the given entity
     * instance. It will set the new entity instance's <code>effectiveAt</code> 
     * field to the current datetime. It also will set the 
     * <code>expiredAt</code> field to <code>null</code>. The given entity 
     * instance will have an updated <code>id</code> after the method returns, 
     * and the updated entity also will be returned from the method.
     * 
     * @param entity the entity to update.
     * 
     * @return the updated entity.
     */
    E updateCurrent(E entity);
    
}
