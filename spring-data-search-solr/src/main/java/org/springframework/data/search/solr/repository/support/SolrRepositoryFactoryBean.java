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

import org.springframework.data.repository.core.support.RepositoryFactoryBeanSupport;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;
import org.springframework.data.search.Indexable;
import org.springframework.data.search.solr.SolrOperations;
import org.springframework.data.search.solr.repository.SolrRepository;


/**
 * Instantiates a factory that can be used to create Solr repository bean
 * instances.
 * 
 * @author Manish Baxi
 */
public class SolrRepositoryFactoryBean<T extends SolrRepository<S>, S extends Indexable> extends RepositoryFactoryBeanSupport<T, S, String>
{
    private SolrOperations operations;

    /**
     * Sets the {@link SolrOperations} instance that will be used to perform
     * operations on the underlying Solr instances.
     * 
     * @param operations A {@link SolrOperations}.
     */
    public void setSolrOperations(final SolrOperations operations)
    {
        this.operations = operations;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected RepositoryFactorySupport createRepositoryFactory()
    {
        return new SolrRepositoryFactory(this.operations);
    }
}
