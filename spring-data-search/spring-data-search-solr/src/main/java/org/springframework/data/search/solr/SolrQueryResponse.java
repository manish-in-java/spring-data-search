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

import org.apache.solr.client.solrj.response.QueryResponse;
import org.springframework.data.search.core.AbstractQueryResponse;

/**
 * Primary implementation for a response to a Solr search query.
 * 
 * @author Vincent Devillers
 */
public class SolrQueryResponse extends AbstractQueryResponse
{
    private QueryResponse nativeResponse;

    /**
     * Gets the native Solr response to the search query.
     * 
     * @return A {@link QueryResponse}.
     */
    public QueryResponse getNativeResponse()
    {
        return nativeResponse;
    }

    /**
     * Sets the native Solr response to the search query.
     * 
     * @param nativeResponse A {@link QueryResponse}.
     */
    public void setNativeResponse(final QueryResponse nativeResponse)
    {
        this.nativeResponse = nativeResponse;
    }
}
