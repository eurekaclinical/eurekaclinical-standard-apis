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

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;

/**
 * Provides an interface for the subclasses to easily perform queries
 * without having to deal with a lot of boiler-plate code. The subclasses
 * can simply provide the path to a value and the target value using this
 * interface, and have this superclass perform the query.
 *
 * @param <E> The entity type.
 * @param <P> The target value and target column type.
 */
public interface QueryPathProvider<E, P> {

    /**
     * Provides a path from the entity to the target attribute.
     *
     * @param root The query root, used to build the path.
     * @param builder The criteria builder for the query.
     * @return The path from the entity to the target attribute.
     */
    Path<P> getPath(Root<E> root, CriteriaBuilder builder);
    
}
