/*
 * Copyright 2008-2012 the original author or authors.
 *
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

package org.springframework.data.search;

import java.util.Collection;

/**
 * Contract specifying a basic set of search operations.
 * 
 * @author Vincent Devillers
 * @author Manish Baxi
 * @see SearchTemplate
 */
public interface SearchOperations
{
    /**
     * Adds a single entry to a search index.
     * 
     * @param entry The entry to add.
     * @return The unique identifier of the search index entry.
     */
    String add(IndexEntry entry);

    /**
     * Adds multiple index entries to a search index.
     * 
     * @param entries The entries to add.
     * @return The unique identifiers of the search index entries.
     */
    Collection<String> add(Collection<IndexEntry> entries);

    /**
     * Adds multiple index entries to a search index.
     * 
     * @param entries The entries to add.
     * @return The unique identifiers of the search index entries.
     */
    Collection<String> add(IndexEntry... entries);

    /**
     * Adds an object to a search index.
     * 
     * @param bean The object to add.
     * @return The unique identifier of the search index entry for the indexed
     *         object.
     */
    <T> String index(T bean);

    /**
     * Adds multiple indexable objects to a search index.
     * 
     * @param beans The objects to index.
     * @return The unique identifiers of the search index entries for the
     *         indexed objects.
     */
    <T> Collection<String> index(Collection<T> beans);

    /**
     * Adds multiple indexable objects to a search index.
     * 
     * @param beans The objects to index.
     * @return The unique identifiers of the search index entries for the
     *         indexed objects.
     */
    <T> Collection<String> index(T... beans);

    /**
     * Performs a query on the underlying search engine.
     * 
     * @param query The query to be performed.
     * @return A {@link QueryResponse} holding all the search index entries
     *         matching the query.
     */
    QueryResponse query(String query);

    /**
     * Performs a query on the underlying search engine.
     * 
     * @param query The query to be performed.
     * @param qre An {@link IndexEntryMapper}.
     * @return A {@link Collection} of objects.
     */
    <T> Collection<T> query(String query, IndexEntryMapper<T> qre);

    /**
     * Performs a query on the underlying search engine, along with dynamic
     * parameter values for the query.
     * 
     * @param query The query to be performed.
     * @param qre A {@link QueryResponseExtractor}.
     * @return A {@link Collection} of objects.
     */
    <T> Collection<T> query(String query, QueryResponseExtractor<T> qre);

    /**
     * Performs a query on the underlying search engine.
     * 
     * @param query The query to be performed.
     * @param clazz The {@link Class} for the search results.
     * @return A {@link Collection} of objects.
     */
    <T> Collection<T> query(String query, Class<T> clazz);

    /**
     * Performs a query on the underlying search engine, along with dynamic
     * parameter values for the query.
     * 
     * @param query The query to be performed.
     * @param params The query parameters.
     * @return A {@link QueryResponse} holding all the search index entries
     *         matching the query.
     */
    QueryResponse query(String query, Object[] params);

    /**
     * Performs a query on the underlying search engine, along with dynamic
     * parameter values for the query.
     * 
     * @param query The query to be performed.
     * @param params The query parameters.
     * @param qre A {@link QueryResponseExtractor}.
     * @return A {@link Collection} of objects.
     */
    <T> Collection<T> query(String query, Object[] params, QueryResponseExtractor<T> qre);

    /**
     * Performs a query on the underlying search engine, along with dynamic
     * parameter values for the query.
     * 
     * @param query The query to be performed.
     * @param params The query parameters.
     * @param qre An {@link IndexEntryMapper}.
     * @return A {@link Collection} of objects.
     */
    <T> Collection<T> query(String query, Object[] params, IndexEntryMapper<T> qre);

    /**
     * Performs a query on the underlying search engine, along with dynamic
     * parameter values for the query.
     * 
     * @param query The query to be performed.
     * @param params The query parameters.
     * @param clazz The {@link Class} for the search results.
     * @return A {@link Collection} of objects.
     */
    <T> Collection<T> query(String query, Object[] params, Class<T> clazz);

    /**
     * Deletes a single index entry specified by its unique identifier.
     * 
     * @param id The unique identifier of the index entry to delete.
     */
    void delete(String id);

    /**
     * Deletes multiple index entries specified by their unique identifiers.
     * 
     * @param ids The unique identifiers of the entries to delete.
     */
    void delete(String... ids);

    /**
     * Deletes multiple index entries specified by their unique identifiers.
     * 
     * @param ids The unique identifiers of the entries to delete.
     */
    void delete(Collection<String> ids);

    /**
     * Deletes all index entries from an index.
     */
    void deleteAll();

    /**
     * Deletes multiple index entries based on a query.
     * 
     * @param query The query for the entries to delete.
     */
    void deleteByQuery(String query);

    /**
     * Issues a ping request to check if the search server is available.
     */
    boolean isAlive();

    /**
     * Refreshes or optimizes the indexes.
     */
    void refresh();

    /**
     * Performs an update on a search index based on a query.
     * 
     * @param query The query for the update operation.
     */
    void update(String query);

    /**
     * Performs a batch update on a search index based on a query.
     * 
     * @param query The query for the update operation.
     */
    void updateInBatch(String query);
}
