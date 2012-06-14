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

package org.springframework.data.search.solr.repository.support;

import java.io.Serializable;

import org.springframework.data.repository.core.EntityInformation;
import org.springframework.data.repository.core.RepositoryMetadata;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;
import org.springframework.data.search.solr.SolrOperations;
import org.springframework.data.search.solr.repository.SimpleSolrRepository;
import org.springframework.util.Assert;


/**
 * Factory to create Solr repository instances.
 * 
 * @author Manish Baxi
 */
public class SolrRepositoryFactory extends RepositoryFactorySupport
{
    private final SolrOperations operations;

    /**
     * Sets the {@link SolrOperations} instance that will be used to interact
     * with the underlying Solr instances.
     * 
     * @param operations A {@link SolrOperations}.
     */
    public SolrRepositoryFactory(final SolrOperations operations)
    {
        Assert.notNull(operations);

        this.operations = operations;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Class<?> getRepositoryBaseClass(RepositoryMetadata metadata)
    {
        return SimpleSolrRepository.class;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T, ID extends Serializable> EntityInformation<T, ID> getEntityInformation(final Class<T> domainClass)
    {
        throw new UnsupportedOperationException("SolrRepositoryFactory does not support RepositoryFactorySupport.getEntityInformation().");
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    protected Object getTargetRepository(final RepositoryMetadata metadata)
    {
        return new SimpleSolrRepository(this.operations, new SimpleSolrEntityInformation(metadata.getDomainType()));
    }
}
