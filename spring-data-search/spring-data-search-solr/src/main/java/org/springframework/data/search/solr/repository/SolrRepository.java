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

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.search.Indexable;
import org.springframework.data.search.repository.SearchRepository;

/**
 * Apache Solr implementation of {@link SearchRepository}.
 * 
 * @author Manish Baxi
 */
@NoRepositoryBean
public interface SolrRepository<T extends Indexable> extends SearchRepository<T>
{
    /**
     * Commits any pending changes to the underlying search indices.
     */
    void commit();

    /**
     * Finds all records matching a specified query.
     * 
     * @param query The query to use for searching records.
     * @return An {@link Iterable} of {@link Indexable}s.
     */
    Iterable<T> findAll(String query);
}
