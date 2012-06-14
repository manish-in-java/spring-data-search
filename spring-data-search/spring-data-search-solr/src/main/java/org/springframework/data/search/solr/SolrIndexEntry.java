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
import java.util.Map;
import java.util.Set;

import org.apache.solr.common.SolrDocument;
import org.springframework.data.search.IndexEntry;

/**
 * Represents an entry in a Solr search index. A thin wrapper around a
 * {@link SolrDocument}.
 * 
 * @author Vincent Devillers
 * @author Manish Baxi
 */
public class SolrIndexEntry implements IndexEntry, Map<String, Object>
{
    private final org.apache.solr.common.SolrDocument solrDocument;

    private Float                                     score;

    /**
     * Sets the Solr document that this instance wraps.
     */
    public SolrIndexEntry(final SolrDocument solrDocument)
    {
        this.solrDocument = solrDocument;
    }

    /**
     * Instantiates a new Solr document using the keys and values in a map as
     * the fields for the document.
     * 
     * @param fields A {@link Map} representing the fields to be added to the
     *            Solr document.
     */
    public SolrIndexEntry(final Map<String, Object> fields)
    {
        final SolrDocument nativeDocument = new SolrDocument();
        nativeDocument.putAll(fields);

        this.solrDocument = nativeDocument;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void clear()
    {
        this.solrDocument.clear();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean containsKey(final Object key)
    {
        return this.solrDocument.containsKey(key);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean containsValue(final Object value)
    {
        return this.solrDocument.containsValue(value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<Entry<String, Object>> entrySet()
    {
        return this.solrDocument.entrySet();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object get(final Object key)
    {
        return this.solrDocument.get(key);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Float getScore()
    {
        return score;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isEmpty()
    {
        return this.solrDocument.isEmpty();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<String> keySet()
    {
        return this.solrDocument.keySet();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object put(String key, Object value)
    {
        return this.solrDocument.put(key, value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void putAll(Map<? extends String, ? extends Object> m)
    {
        solrDocument.putAll(m);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object remove(Object key)
    {
        return solrDocument.remove(key);
    }

    /**
     * {@inheritDoc}
     */
    public void setScore(Float score)
    {
        this.score = score;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int size()
    {
        return this.solrDocument.size();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Collection<Object> values()
    {
        return this.solrDocument.values();
    }
}
