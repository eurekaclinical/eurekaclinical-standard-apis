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
import java.util.List;
import javax.inject.Provider;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.SingularAttribute;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Andrew Post
 */
public final class DatabaseSupport {

    private static Logger LOGGER
            = LoggerFactory.getLogger(DatabaseSupport.class);

    /**
     * Comparators for constructing where clauses that set a threshold on a
     * numerical value.
     */
    public static enum SqlComparator {
        LESS_THAN_OR_EQUAL_TO,
        LESS_THAN,
        EQUAL_TO,
        NOT_EQUAL_TO,
        GREATER_THAN,
        GREATER_THAN_OR_EQUAL_TO
    }

    /**
     * Wraps an entity manager in an implementation of the {@link Provider}
     * interface.
     */
    private static class EntityManagerProvider
            implements Provider<EntityManager> {

        private final EntityManager entityManager;

        /**
         * Creates a provider of the given entity manager.
         *
         * @param inEntityManager the entity manager. Cannot be
         * <code>null</code>.
         */
        EntityManagerProvider(EntityManager inEntityManager) {
            assert inEntityManager != null : "inEntityManager cannot be null";
            this.entityManager = inEntityManager;
        }

        @Override
        public EntityManager get() {
            return this.entityManager;
        }
    }

    /**
     * The entity manager used in creating queries.
     */
    private final Provider<EntityManager> entityManagerProvider;

    /**
     * Creates a database support instance that uses the provided entity manager
     * to construct queries.
     *
     * @param entityManager the entity manager to use. Cannot be
     * <code>null</code>.
     */
    public DatabaseSupport(EntityManager entityManager) {
        if (entityManager == null) {
            throw new IllegalArgumentException("entityManager cannot be null");
        }
        this.entityManagerProvider = new EntityManagerProvider(entityManager);
    }

    /**
     * Creates a database support instance that uses the provided entity manager
     * provider to construct queries.
     *
     * @param entityManagerProvider the entity manager provider to use. Cannot
     * be <code>null</code>.
     */
    public DatabaseSupport(Provider<EntityManager> entityManagerProvider) {
        if (entityManagerProvider == null) {
            throw new IllegalArgumentException(
                    "entityManagerProvider cannot be null");
        }
        this.entityManagerProvider = entityManagerProvider;
    }

    /**
     * Gets every instance of the specified entity in the database.
     *
     * @param <T> the type of the entity.
     * @param entityCls the class of the specified entity. Cannot be
     * <code>null</code>.
     * @return the instances requested. Guaranteed not <code>null</code>.
     */
    public <T> List<T> getAll(Class<T> entityCls) {
        if (entityCls == null) {
            throw new IllegalArgumentException("entityCls cannot be null");
        }
        EntityManager entityManager = this.entityManagerProvider.get();
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> criteriaQuery
                = builder.createQuery(entityCls);
        criteriaQuery.from(entityCls);
        TypedQuery<T> typedQuery
                = entityManager.createQuery(criteriaQuery);
        List<T> jobs = typedQuery.getResultList();
        return jobs;
    }

    /**
     * Gets the entity that has the specified value of an
     * attribute. This method assumes that at most one instance of the given
     * entity will be a match. This typically is used with attributes with a
     * uniqueness constraint.
     *
     * @param <T> the type of the entity.
     * @param <Y> the type of the attribute.
     * @param entityCls the entity class. Cannot be <code>null</code>.
     * @param attribute the attribute. Cannot be <code>null</code>.
     * @param value the value. If there is more than one matching instance, 
     * only the first will be returned, and a warning will be logged.
     *
     * @return the matching instance, or <code>null</code> if there is none.
     */
    public <T, Y> T getUniqueByAttribute(Class<T> entityCls,
            SingularAttribute<T, Y> attribute, Y value) {
        if (entityCls == null) {
            throw new IllegalArgumentException("entityCls cannot be null");
        }
        if (attribute == null) {
            throw new IllegalArgumentException("attribute cannot be null");
        }
        TypedQuery<T> query = createTypedQuery(entityCls, attribute, value);
        T result = null;
        try {
            result = query.getSingleResult();
        } catch (NonUniqueResultException nure) {
            LOGGER.warn("Result not unique for {}: {} = {}", 
                    entityCls.getName(), attribute.getName(), value);
            result = query.getResultList().get(0);
        } catch (NoResultException nre) {
            LOGGER.debug("Result not existant for {}: {} = {}", 
                    entityCls.getName(), attribute.getName(), value);
        }
        return result;
    }

    /**
     * Executes a query for the entity with the given
     * attribute value. This method assumes that at most one instance of the
     * given entity will be a match. This typically is used with attributes with
     * a uniqueness constraint.
     *
     * @param <T> the type of the entity.
     * @param <Y> the type of the attribute.
     * @param entityCls the entity class. Cannot be <code>null</code>.
     * @param attributeName the name of the attribute. Cannot be
     * <code>null</code>.
     * @param value the value. If there is more than one matching instance, only
     * the first will be returned, and a warning will be logged.
     *
     * @return the matching instance, or <code>null</code> if there is none.
     */
    public <T, Y> T getUniqueByAttribute(Class<T> entityCls,
            String attributeName, Y value) {
        if (entityCls == null) {
            throw new IllegalArgumentException("entityCls cannot be null");
        }
        if (attributeName == null) {
            throw new IllegalArgumentException("attributeName cannot be null");
        }
        TypedQuery<T> query = createTypedQuery(entityCls, attributeName, value);
        T result = null;
        try {
            result = query.getSingleResult();
        } catch (NonUniqueResultException nure) {
            LOGGER.warn("Result not unique for {}: {} = {}", 
                    entityCls.getName(), attributeName, value);
            result = query.getResultList().get(0);
        } catch (NoResultException nre) {
            LOGGER.debug("Result not existant for {}: {} = {}", 
                    entityCls.getName(), attributeName, value);
        }
        return result;
    }

    /**
     * Executes a query for the entities that have the specified
     * value of the given attribute.
     *
     * @param <T> the type of the entity.
     * @param <Y> the type of the attribute.
     * @param entityCls the entity class. Cannot be <code>null</code>.
     * @param attribute the attribute. Cannot be <code>null</code>.
     * @param value the value.
     *
     * @return the matching entities. Guaranteed not <code>null</code>.
     */
    public <T, Y> List<T> getListByAttribute(
            Class<T> entityCls, SingularAttribute<T, Y> attribute, Y value) {
        if (entityCls == null) {
            throw new IllegalArgumentException("entityCls cannot be null");
        }
        if (attribute == null) {
            throw new IllegalArgumentException("attribute cannot be null");
        }
        TypedQuery<T> query = createTypedQuery(entityCls, attribute, value);
        return query.getResultList();
    }

    /**
     * Executes a query for the entities that have the specified
     * values of the given numerical attribute.
     *
     * @param <T> the type of the entity.
     * @param <Y> the type of the numerical attribute.
     * @param entityCls the entity class. Cannot be <code>null</code>.
     * @param attribute the attribute. Cannot be <code>null</code>.
     * @param comparator the comparator to use. Cannot be <code>null</code>.
     * @param value the value.
     *
     * @return the matching entities. Guaranteed not <code>null</code>.
     */
    public <T, Y extends Number> List<T> getListByAttribute(Class<T> entityCls,
            SingularAttribute<T, Y> attribute, SqlComparator comparator,
            Y value) {
        if (entityCls == null) {
            throw new IllegalArgumentException("entityCls cannot be null");
        }
        if (attribute == null) {
            throw new IllegalArgumentException("attribute cannot be null");
        }
        if (comparator == null) {
            throw new IllegalArgumentException("comparator cannot be null");
        }
        TypedQuery<T> query = createTypedQuery(entityCls, attribute, 
                comparator, value);
        return query.getResultList();
    }

    /**
     * Executes a query for entities that have any of the given attribute 
     * values.
     *
     * @param <T> the type of the entity.
     * @param <Y> the type of the attribute.
     * @param entityCls the entity class. Cannot be <code>null</code>.
     * @param attribute the attribute. Cannot be <code>null</code>.
     * @param values the values.
     *
     * @return the matching entities. Guaranteed not <code>null</code>.
     */
    public <T, Y> List<T> getListByAttributeIn(Class<T> entityCls,
            SingularAttribute<T, Y> attribute, List<Y> values) {
        if (entityCls == null) {
            throw new IllegalArgumentException("entityCls cannot be null");
        }
        if (attribute == null) {
            throw new IllegalArgumentException("attribute cannot be null");
        }
        TypedQuery<T> query
                = createTypedQueryIn(entityCls, attribute, values);
        return query.getResultList();
    }

    /**
     * Executes a query for entities that match the given path value. The path 
     * may traverse one or more entity relationships, and is followed through 
     * to get the resulting attribute. That attribute's value is compared to 
     * the given target value.
     *
     * @param <T> the type of the entity class.
     * @param <Y> the type of the target value and resulting attribute/column
     * value.
     * @param entityCls the entity class. Cannot be <code>null</code>.
     * @param provider provides the path from the entity to the target
     * attribute/column. Cannot be <code>null</code>.
     * @param value the target value to compare with the resulting attribute
     * value.
     * @return the matching entities. Guaranteed not <code>null</code>.
     */
    public <T, Y> List<T> getListByAttribute(Class<T> entityCls,
            QueryPathProvider<T, Y> provider, Y value) {
        if (entityCls == null) {
            throw new IllegalArgumentException("entityCls cannot be null");
        }
        if (provider == null) {
            throw new IllegalArgumentException("provider cannot be null");
        }
        TypedQuery<T> typedQuery = 
                createTypedQuery(entityCls, provider, value);
        return typedQuery.getResultList();
    }
    
    /**
     * Executes a query for entities that match the given path value. The path 
     * may traverse one or more entity relationships, and is followed through 
     * to get the resulting attribute. That attribute's value is compared to 
     * the given target value.
     *
     * @param <T> the type of the entity class.
     * @param <Y> the type of the target value and resulting attribute/column
     * value.
     * @param entityCls the entity class. Cannot be <code>null</code>.
     * @param provider provides the path from the entity to the target
     * attribute/column. Cannot be <code>null</code>.
     * @param values the target value to compare with the resulting attribute
     * value.
     * @return the matching entities. Guaranteed not <code>null</code>.
     */
    public <T, Y> List<T> getListByAttributeIn(Class<T> entityCls,
            QueryPathProvider<T, Y> provider, List<Y> values) {
        if (entityCls == null) {
            throw new IllegalArgumentException("entityCls cannot be null");
        }
        if (provider == null) {
            throw new IllegalArgumentException("provider cannot be null");
        }
        TypedQuery<T> typedQuery = 
                createTypedQueryIn(entityCls, provider, values);
        return typedQuery.getResultList();
    }
    
    /**
     * Creates a typed query for entities that match any of the specified 
     * values of the resulting attribute.
     *
     * @param <T> the type of the entity class.
     * @param <Y> the type of the target value and resulting attribute/column
     * value.
     * @param entityCls the entity class. Cannot be <code>null</code>.
     * @param provider provides the path from the entity to the target
     * attribute/column. Cannot be <code>null</code>.
     * @param values the target value to compare with the resulting attribute
     * value.
     * @return
     */
    private <T, Y> TypedQuery<T> createTypedQueryIn(Class<T> entityCls,
            QueryPathProvider<T, Y> provider, List<Y> values) {
        EntityManager entityManager = this.entityManagerProvider.get();
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> criteriaQuery = builder.createQuery(entityCls);
        Root<T> root = criteriaQuery.from(entityCls);
        Path<Y> path = provider.getPath(root, builder);
        CriteriaBuilder.In<Y> in = builder.in(path);
        if (values != null) {
            for (Y val : values) {
                in.value(val);
            }
        }
        return entityManager.createQuery(criteriaQuery.where(in));
    }
    
    /**
     * Creates a typed query for entities that have the resulting attribute 
     * value.
     *
     * @param <T> the type of the entity class.
     * @param <Y> the type of the target attribute and target value.
     * @param entityCls the entity class. Cannot be <code>null</code>.
     * @param provider the attribute to compare.
     * @param value the target value for the given attribute.
     * @return a typed query that contains the given criteria.
     */
    private <T, Y> TypedQuery<T> createTypedQuery(Class<T> entityCls,
            QueryPathProvider<T, Y> provider, Y value) {
        EntityManager entityManager = this.entityManagerProvider.get();
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> criteriaQuery = builder.createQuery(entityCls);
        Root<T> root = criteriaQuery.from(entityCls);
        Path<Y> path = provider.getPath(root, builder);
        return entityManager.createQuery(criteriaQuery.where(
                builder.equal(path, value)));
    }

    /**
     * Creates a typed query for entities that match any of the specified 
     * values of the given attribute.
     *
     * @param <T> the type of the entity class.
     * @param <Y>
     * @param entityCls
     * @param attribute
     * @param values
     * @return
     */
    private <T, Y> TypedQuery<T> createTypedQueryIn(Class<T> entityCls,
            SingularAttribute<T, Y> attribute, List<Y> values) {
        EntityManager entityManager = this.entityManagerProvider.get();
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> criteriaQuery = builder.createQuery(entityCls);
        Root<T> root = criteriaQuery.from(entityCls);
        Path<Y> path = root.get(attribute);
        CriteriaBuilder.In<Y> in = builder.in(path);
        if (values != null) {
            for (Y val : values) {
                in.value(val);
            }
        }
        return entityManager.createQuery(criteriaQuery.where(in));
    }
    
    /**
     * Creates a typed query for entities that have the given attribute value.
     *
     * @param <T> the type of the entity class.
     * @param <Y> the type of the target attribute and target value.
     * @param attribute the attribute to compare.
     * @param value the target value for the given attribute.
     * @return a typed query that contains the given criteria.
     */
    private <T, Y> TypedQuery<T> createTypedQuery(Class<T> entityCls,
            SingularAttribute<T, Y> attribute, Y value) {
        EntityManager entityManager = this.entityManagerProvider.get();
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> criteriaQuery = builder.createQuery(entityCls);
        Root<T> root = criteriaQuery.from(entityCls);
        Path<Y> path = root.get(attribute);
        return entityManager.createQuery(criteriaQuery.where(
                builder.equal(path, value)));
    }

    /**
     * Creates a typed query for entities that have the given attribute value.
     *
     * @param <T> the type of the entity to return.
     * @param <Y> the type of the attribute.
     * @param attribute the attribute.
     * @param value the target value for the given attribute.
     * @return a typed query that contains the given criteria.
     */
    private <T, Y> TypedQuery<T> createTypedQuery(Class<T> entityCls,
            String attributeName, Y value) {
        EntityManager entityManager = this.entityManagerProvider.get();
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> criteriaQuery = builder.createQuery(entityCls);
        Root<T> root = criteriaQuery.from(entityCls);
        Path<Y> path = root.get(attributeName);
        return entityManager.createQuery(criteriaQuery.where(
                builder.equal(path, value)));
    }

    /**
     * Creates a typed query for entities with the given numerical attribute 
     * value.
     *
     * @param <T> the type of the entity to return.
     * @param <Y> the type of the attribute.
     * @param entityCls the entity's class.
     * @param attribute the attribute.
     * @param comparator the comparator.
     * @param value the value or value threshold.
     * @return a typed query with the given criteria.
     */
    private <T, Y extends Number> TypedQuery<T> createTypedQuery(
            Class<T> entityCls, SingularAttribute<T, Y> attribute,
            SqlComparator comparator, Y value) {
        EntityManager entityManager = this.entityManagerProvider.get();
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> criteriaQuery = builder.createQuery(entityCls);
        Root<T> root = criteriaQuery.from(entityCls);
        Path<Y> path = root.get(attribute);
        Predicate pred;
        switch (comparator) {
            case LESS_THAN:
                pred = builder.lt(path, value);
                break;
            case LESS_THAN_OR_EQUAL_TO:
                pred = builder.le(path, value);
                break;
            case EQUAL_TO:
                pred = builder.equal(path, value);
                break;
            case NOT_EQUAL_TO:
                pred = builder.notEqual(path, value);
                break;
            case GREATER_THAN_OR_EQUAL_TO:
                pred = builder.ge(path, value);
                break;
            case GREATER_THAN:
                pred = builder.gt(path, value);
                break;
            default:
                throw new AssertionError("Invalid SQLComparator: "
                        + comparator);
        }
        return entityManager.createQuery(criteriaQuery.where(pred));
    }
}
