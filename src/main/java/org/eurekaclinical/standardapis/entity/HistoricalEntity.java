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
 * Interface for entities that maintain their historical state. Corresponding 
 * real-world objects will have one or more entity instances, each representing 
 * the object's state between the effective and expired datetimes. Updates will 
 * expire the object's most recent record in the database and create a new 
 * record. Deletes will expire the current record in the database but will not 
 * physically delete it. Data access objects for historical entities only 
 * return instances that are valid at the time of the query unless otherwise 
 * specified.
 *
 * @author Andrew Post
 * 
 */
public interface HistoricalEntity<PK> extends Entity<PK> {

    /**
     * Returns the effective datetime of this entity instance.
     * 
     * @return a datetime.
     */
    Date getEffectiveAt();

    /**
     * Sets the effective datetime of this entity instance.
     * 
     * @param effectiveAt a datetime.
     */
    void setEffectiveAt(Date effectiveAt);

    /**
     * Returns the datetime at which this entity instance expired.
     * 
     * @return a datetime.
     */
    Date getExpiredAt();

    /**
     * Sets the timestamp at which this entity instance expired.
     * 
     * @param expiredAt a datetime.
     */
    void setExpiredAt(Date expiredAt);
    
    /**
     * Gets the datetime at which the first entity instance corresponding to
     * this real-world object was created.
     * 
     * @return a datetime
     */
    Date getCreatedAt();
    
    /**
     * Sets the datetime at which the first entity instance corresponding to
     * this real-world object was created.
     * 
     * @param createdAt a datetime.
     */
    void setCreatedAt(Date createdAt);
    
}
