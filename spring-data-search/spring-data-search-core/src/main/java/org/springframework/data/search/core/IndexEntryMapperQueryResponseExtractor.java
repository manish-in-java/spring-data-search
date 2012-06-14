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

package org.springframework.data.search.core;

import java.util.Collection;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.search.IndexEntry;
import org.springframework.data.search.IndexEntryMapper;
import org.springframework.data.search.QueryResponse;
import org.springframework.data.search.QueryResponseExtractor;
import org.springframework.util.Assert;

/**
 * Converts a search index entry into a strongly-typed indexable object usable
 * by the application.
 * 
 * @author Vincent Devillers
 * @author Manish Baxi
 */
public class IndexEntryMapperQueryResponseExtractor<T> implements QueryResponseExtractor<T>
{
    private final IndexEntryMapper<T> indexEntryMapper;

    /**
     * Sets the converter that will perform the task of converting index entries
     * into indexable objects.
     * 
     * @param indexEntryMapper The converter that will perform the task of
     *            converting index entries into indexable objects.
     */
    public IndexEntryMapperQueryResponseExtractor(final IndexEntryMapper<T> indexEntryMapper)
    {
        Assert.notNull(indexEntryMapper, "DocMapper is required");

        this.indexEntryMapper = indexEntryMapper;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Collection<T> extractData(final QueryResponse response)
    {
        final List<T> results = new ArrayList<T>(response.getMatchingEntries().size());
        for (IndexEntry doc : response.getMatchingEntries())
        {
            results.add(this.indexEntryMapper.map(doc));
        }

        return results;
    }
}
