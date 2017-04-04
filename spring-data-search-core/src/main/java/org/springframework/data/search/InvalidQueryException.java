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

/**
 * Raised when an invalid query is attempted.
 * 
 * @author Vincent Devillers
 */
public class InvalidQueryException extends SearchException
{
    private static final long serialVersionUID = -2333748296483420277L;

    /**
     * Sets the query that led to this exception as the exception message.
     * 
     * @param query The query that led to this exception.
     */
    public InvalidQueryException(String query)
    {
        super(query);
    }

    /**
     * Sets the query that led to this exception and the underlying exception
     * that led to this exception.
     * 
     * @param query The query that led to this exception.
     * @param cause The exception that led to this exception.
     */
    public InvalidQueryException(String query, Throwable cause)
    {
        super(query, cause);
    }

    /**
     * Gets the query that led to this exception.
     * 
     * @return The query that led to this exception.
     */
    public String getQuery()
    {
        return this.getMessage();
    }
}
