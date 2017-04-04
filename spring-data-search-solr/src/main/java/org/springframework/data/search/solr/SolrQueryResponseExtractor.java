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

package org.springframework.data.search.solr;

import java.util.Collection;

import org.springframework.data.search.Indexable;
import org.springframework.data.search.QueryResponse;
import org.springframework.data.search.QueryResponseExtractor;

/**
 * Converts a Solr query response into objects from which the index entries in
 * the response originated.
 */
public abstract class SolrQueryResponseExtractor<T extends Indexable> implements QueryResponseExtractor<T>
{
    /**
     * Converts a query response into objects from which the index entries in
     * the response originated.
     * 
     * @param queryResponse A {@link QueryResponse}.
     * @return A {@link Collection} of objects from which the index entries in
     *         the response originated.
     */
    public Collection<T> extractData(final QueryResponse queryResponse)
    {
        if (queryResponse instanceof SolrQueryResponse)
        {
            return this.extractNativeData((org.apache.solr.client.solrj.response.QueryResponse) queryResponse.getNativeResponse());
        }

        return null;
    }

    /**
     * Converts a Solr query response into objects from which the index entries
     * in the response originated.
     * 
     * @param queryResponse A
     *            {@link org.apache.solr.client.solrj.response.QueryResponse.QueryResponse}
     *            .
     * @return A {@link Collection} of objects from which the index entries in
     *         the response originated.
     */
    public abstract Collection<T> extractNativeData(final org.apache.solr.client.solrj.response.QueryResponse nativeResponse);
}
