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
 * Raised when communication with the server search fails.
 * 
 * @author Vincent Devillers
 */
public class SearchServerException extends SearchException
{
    private static final long serialVersionUID = -4350812455697611819L;

    /**
     * Sets the message associated with the exception.
     * 
     * @param message The message associated with the exception.
     */
    public SearchServerException(String message)
    {
        super(message);
    }

    /**
     * Sets the message associated with the exception and the exception that led
     * to this exception.
     * 
     * @param message The message associated with the exception.
     * @param cause The exception that led to this exception.
     */
    public SearchServerException(String message, Throwable cause)
    {
        super(message, cause);
    }
}
