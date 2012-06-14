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

import org.apache.lucene.queryParser.ParseException;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.SolrException;
import org.apache.solr.common.SolrException.ErrorCode;
import org.springframework.dao.DataAccessException;
import org.springframework.data.search.InvalidQueryException;
import org.springframework.data.search.SearchException;
import org.springframework.data.search.SearchServerException;
import org.springframework.data.search.UncategorizedSearchException;
import org.springframework.data.search.core.SearchExceptionTranslator;

/**
 * Wraps runtime Solr exceptions in {@link SearchException}.
 * 
 * @author Vincent Devillers
 */
public class SolrExceptionTranslator extends SearchExceptionTranslator
{
    /**
     * {@inheritDoc}
     */
    @Override
    public DataAccessException translateExceptionIfPossible(RuntimeException ex)
    {
        if (ex.getCause() instanceof SolrServerException)
        {
            SolrServerException solrServerException = (SolrServerException) ex.getCause();
            if (solrServerException.getCause() instanceof SolrException)
            {
                SolrException solrException = (SolrException) solrServerException.getCause();
                if (solrException.getCause() instanceof ParseException)
                {
                    return new InvalidQueryException(((ParseException) solrException.getCause()).getMessage(), solrException.getCause());
                }
                else
                {
                    ErrorCode errorCode = SolrException.ErrorCode.getErrorCode(solrException.code());
                    switch (errorCode)
                    {
                    case NOT_FOUND:
                    case FORBIDDEN:
                    case SERVICE_UNAVAILABLE:
                    case SERVER_ERROR:
                        return new SearchServerException(solrException.getMessage(), solrException);
                    case BAD_REQUEST:
                        return new InvalidQueryException(solrException.getMessage(), solrException);
                    case UNAUTHORIZED:
                    case UNKNOWN:
                        return new UncategorizedSearchException(solrException.getMessage(), solrException);
                    default:
                        break;
                    }
                }
            }
        }

        return super.translateExceptionIfPossible(ex);
    }
}
