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

package org.springframework.data.search.core;

import java.util.Collection;
import org.springframework.data.search.IndexEntry;
import org.springframework.data.search.QueryResponse;

/**
 * Basic implementation for a response to a search query.
 * 
 * @author Vincent Devillers
 */
public abstract class AbstractQueryResponse implements QueryResponse
{
    private long                             elapsedTime;
    private Collection<? extends IndexEntry> matchingEntries;

    /**
     * {@inheritDoc}
     */
    @Override
    public long getElapsedTime()
    {
        return this.elapsedTime;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Collection<? extends IndexEntry> getMatchingEntries()
    {
        return this.matchingEntries;
    }

    /**
     * Sets the total time it took to execute the query.
     * 
     * @param elapsedTime The total time it took to execute the query.
     */
    public void setElapsedTime(final long elapsedTime)
    {
        this.elapsedTime = elapsedTime;
    }

    /**
     * Sets the index entries matching the query.
     * 
     * @param entries A {@link Collection} of {@link IndexEntry}s.
     */
    public void setMatchingEntries(final Collection<? extends IndexEntry> entries)
    {
        this.matchingEntries = entries;
    }
}
