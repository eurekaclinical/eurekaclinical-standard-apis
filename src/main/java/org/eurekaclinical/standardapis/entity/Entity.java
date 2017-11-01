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

/**
 * A generic entity interface. Eureka! Clinical entities that implement this
 * interface will have a primary key field called <code>id</code>, which is 
 * declared here.
 * 
 * @author Andrew Post
 * @param <PK> the primary key type, usually a <code>Long</code>.
 */
public interface Entity<PK> {
    
    /**
     * Gets the primary key.
     * 
     * @return the primary key.
     */
    PK getId();
    
    /**
     * Sets the primary key.
     * 
     * @param inId the primary key.
     */
    void setId(PK inId);
}
