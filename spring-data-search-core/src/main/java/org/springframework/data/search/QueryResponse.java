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
 * Contract for a response to a search query.
 * 
 * @author Vincent Devillers
 * @author Manish Baxi
 */
public interface QueryResponse
{
    /**
     * Gets the total time it took to execute the query.
     * 
     * @return The total time it took to execute the query.
     */
    long getElapsedTime();

    /**
     * Gets the index entries matching the query.
     * 
     * @return A {@link Collection} of {@link IndexEntry}s.
     */
    Collection<? extends IndexEntry> getMatchingEntries();

    /**
     * Gets the query response native to the underlying search technology.
     * 
     * @return The query response native to the underlying search technology.
     */
    Object getNativeResponse();
}
