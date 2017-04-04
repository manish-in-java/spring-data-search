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

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.data.search.annotation.Indexed;
import org.springframework.data.search.core.IndexEntryMapperQueryResponseExtractor;
import org.springframework.data.search.core.IndexedFieldIndexEntryMapper;
import org.springframework.data.search.core.QueryBuilder;
import org.springframework.data.search.core.SearchExceptionTranslator;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

/**
 * Primary implementation of common routines in {@link SearchOperations}.
 * 
 * @author Vincent Devillers
 * @author Manish Baxi
 */
public abstract class SearchTemplate implements SearchOperations, InitializingBean
{
    private static final Logger       LOGGER              = LoggerFactory.getLogger(SearchTemplate.class);

    private SearchExceptionTranslator exceptionTranslator = new SearchExceptionTranslator();

    /**
     * Builds an index entry for an indexable object.
     * 
     * @return An {@link IndexEntry}.
     */
    protected abstract IndexEntry buildIndexEntry();

    /**
     * {@inheritDoc}
     */
    @Override
    public Collection<String> add(final IndexEntry... entries)
    {
        return this.add(Arrays.asList(entries));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Collection<String> add(Collection<IndexEntry> entries)
    {
        final List<String> ids = new ArrayList<String>(entries.size());
        for (IndexEntry entry : entries)
        {
            ids.add(this.add(entry));
        }

        return ids;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(final String... ids)
    {
        this.delete(Arrays.asList(ids));
    }

    /**
     * Ensures that an exception translator is properly set.
     */
    @Override
    public void afterPropertiesSet() throws Exception
    {
        Assert.notNull(this.getExceptionTranslator(), "Exception translator cannot be null!");
    }

    /**
     * Gets the exception translator for this instance.
     * 
     * @return The exception translator for this instance.
     */
    public final SearchExceptionTranslator getExceptionTranslator()
    {
        return this.exceptionTranslator;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T> String index(final T bean)
    {
        return this.add(this.createIndexEntry(bean));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T> Collection<String> index(final Collection<T> beans)
    {
        final List<IndexEntry> entries = new ArrayList<IndexEntry>(beans.size());

        for (T bean : beans)
        {
            entries.add(this.createIndexEntry(bean));
        }

        return this.add(entries);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T> Collection<String> index(T... beans)
    {
        return this.index(Arrays.asList(beans));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public QueryResponse query(final String query, final Object[] params)
    {
        return this.query(QueryBuilder.resolveParams(query, params));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T> Collection<T> query(final String query, final QueryResponseExtractor<T> qre)
    {
        return this.query(query, null, qre);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T> Collection<T> query(final String query, final IndexEntryMapper<T> qre)
    {
        return this.query(query, null, new IndexEntryMapperQueryResponseExtractor<T>(qre));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T> Collection<T> query(final String query, Class<T> clazz)
    {
        return this.query(query, new IndexedFieldIndexEntryMapper<T>(clazz));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T> Collection<T> query(final String query, final Object[] params, final QueryResponseExtractor<T> qre)
    {
        Assert.notNull(query, "Query must not be null");
        Assert.notNull(qre, "QueryResponseExtractor must not be null");
        if (LOGGER.isDebugEnabled())
        {
            LOGGER.debug("Executing query [" + query + "]");
        }

        return qre.extractData(this.query(query, params));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T> Collection<T> query(final String query, final Object[] params, final IndexEntryMapper<T> qre)
    {
        return query(query, params, new IndexEntryMapperQueryResponseExtractor<T>(qre));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T> Collection<T> query(String query, Object[] params, Class<T> clazz)
    {
        return this.query(query, params, new IndexedFieldIndexEntryMapper<T>(clazz));
    }

    /**
     * Sets the exception translator for this instance.
     * 
     * @param exceptionTranslator The exception translator for this instance.
     */
    public final void setExceptionTranslator(final SearchExceptionTranslator exceptionTranslator)
    {
        this.exceptionTranslator = exceptionTranslator;
    }

    /**
     * Finds indexable fields in an object and create a search index entry using
     * the fields.
     * 
     * @param bean The object to index.
     * @return An {@link IndexEntry}.
     */
    protected <T> IndexEntry createIndexEntry(final T bean)
    {
        // TODO: Find out indexable fields for each class only once per JVM run.
        final Map<String, Object> results = new HashMap<String, Object>();
        final Field[] fields = bean.getClass().getDeclaredFields();
        for (Field field : fields)
        {
            field.setAccessible(true);
            Indexed annotation = field.getAnnotation(Indexed.class);
            if (annotation != null)
            {
                try
                {
                    String fieldName = annotation.fieldName();
                    if (!StringUtils.hasText(fieldName))
                    {
                        fieldName = field.getName();
                    }
                    results.put(fieldName, field.get(bean));
                }
                catch (Exception e)
                {
                    // TODO: Decide whether to raise an exception here.
                }
            }
        }

        if (results.isEmpty())
        {
            throw new InvalidIndexEntryException("The document has no indexed field!");
        }

        IndexEntry document = this.buildIndexEntry();
        document.putAll(results);

        return document;
    }

    protected RuntimeException potentiallyConvertCheckedException(final RuntimeException ex)
    {
        final RuntimeException resolved = this.getExceptionTranslator().translateExceptionIfPossible(ex);

        return resolved == null ? ex : resolved;
    }
}
