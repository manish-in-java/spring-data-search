/*
 *
 * Copyright 2008-2012 the original author or authors.
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
 */

package org.springframework.data.search.solr.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.repository.core.EntityInformation;
import org.springframework.data.search.Indexable;
import org.springframework.data.search.solr.SolrOperations;
import org.springframework.util.Assert;

/**
 * Basic implementation for a Solr repository.
 * 
 * @author Manish Baxi
 */
public class SimpleSolrRepository<T extends Indexable> implements SolrRepository<T>
{
    private final EntityInformation<T, String> entityInformation;
    private final SolrOperations               operations;

    /**
     * Sets the {@link SolrOperations} instance that will be used to interact
     * with the underlying Solr instances.
     * 
     * @param operations A {@link SolrOperations}.
     */
    public SimpleSolrRepository(final SolrOperations operations, final EntityInformation<T, String> entityInformation)
    {
        Assert.notNull(entityInformation);
        Assert.notNull(operations);

        this.entityInformation = entityInformation;
        this.operations = operations;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void commit()
    {
        this.operations.commit();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public long count()
    {
        return 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(final String id)
    {
        this.operations.delete(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(final T entity)
    {
        this.delete(entity.getId());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(final Iterable<? extends T> entities)
    {
        for (T entity : entities)
        {
            this.delete(entity);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteAll()
    {
        this.operations.deleteAll();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean exists(final String id)
    {
        return this.findOne(id) != null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Iterable<T> findAll()
    {
        return this.operations.query("*:*", this.entityInformation.getJavaType());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Iterable<T> findAll(String query)
    {
        return this.operations.query(query, this.entityInformation.getJavaType());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Iterable<T> findAll(final Iterable<String> ids)
    {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T findOne(final String id)
    {
        return this.operations.query(String.format("id:%s", id), this.entityInformation.getJavaType()).iterator().next();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <S extends T> S save(S entity)
    {
        Assert.notNull(entity, "Entity must not be null!");

        this.operations.index(entity);

        return entity;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <S extends T> Iterable<S> save(Iterable<S> entities)
    {
        Assert.notNull(entities, "Iterable of entities must not be null!");

        final List<S> result = new ArrayList<S>();

        for (S entity : entities)
        {
            result.add(this.save(entity));
        }

        return result;
    }
}
