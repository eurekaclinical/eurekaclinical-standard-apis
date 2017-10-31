package org.eurekaclinical.standardapis.entity;

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

import java.util.Date;

/**
 * Interface for entities for which the database stores a record of the current
 * and previous historical states of its instances. The time period in
 * which each state was or is valid is denoted by an effective date and an 
 * expiration date. Updating an entity instance will expire the current
 * record in the database and create a new record. Deleting an entity instance
 * will expire the current record in the database but will not physically
 * delete it. Data access objects for historical entities only return instances 
 * that are valid at the time of the query unless otherwise specified.
 *
 * @author Andrew Post
 * 
 * @param <E> the entity type.
 */
public interface HistoricalEntity<E> {

    /**
     * Returns the timestamp at which this state became effective.
     * 
     * @return a timestamp.
     */
    Date getEffectiveAt();

    /**
     * Sets the timestamp at which this state became effective.
     * 
     * @param effectiveAt a timestamp.
     */
    void setEffectiveAt(Date effectiveAt);

    /**
     * Returns the timestamp at which this state expired.
     * 
     * @return a timestamp.
     */
    Date getExpiredAt();

    /**
     * Sets the timestamp at which this state expired.
     * 
     * @param expiredAt a timestamp.
     */
    void setExpiredAt(Date expiredAt);
    
    E copySelf();
}
