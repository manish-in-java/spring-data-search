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

import java.lang.reflect.Field;
import java.lang.reflect.Constructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.data.search.IndexEntryMapper;
import org.springframework.data.search.IndexEntry;
import org.springframework.data.search.IndexEntryMappingException;
import org.springframework.data.search.annotation.Indexed;
import org.springframework.util.ClassUtils;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;

/**
 * Converts a search index entry into a object of a specific type.
 * 
 * @author Vincent Devillers
 * @author Manish Baxi
 */
public class IndexedFieldIndexEntryMapper<T> implements IndexEntryMapper<T>
{
    private static final Logger LOGGER = LoggerFactory.getLogger(IndexedFieldIndexEntryMapper.class);

    private Class<T>            requiredType;

    /**
     * Sets the type of objects into which this instance should convert search
     * index entries.
     * 
     * @param requiredType The type of objects into which this instance should
     *            convert search index entries.
     */
    public IndexedFieldIndexEntryMapper(final Class<T> requiredType)
    {
        this.requiredType = requiredType;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T map(final IndexEntry entry)
    {
        final T bean = BeanUtils.instantiate(requiredType);

        final Field[] fields = requiredType.getDeclaredFields();

        for (Field field : fields)
        {
            field.setAccessible(true);
            Indexed annotation = field.getAnnotation(Indexed.class);
            if (annotation != null)
            {
                String fieldName = annotation.fieldName();
                if (!StringUtils.hasText(fieldName))
                {
                    fieldName = field.getName();
                }
                try
                {
                    ReflectionUtils.setField(field, bean, this.getValue(entry.get(fieldName), field.getType()));
                }
                catch (IllegalArgumentException e)
                {
                    Class<?> clazz = entry.get(fieldName).getClass();
                    if (LOGGER.isWarnEnabled())
                    {
                        LOGGER.warn("Unable to set the field " + field.getName() + " of type " + field.getType() + " with value " + entry.get(fieldName) + " of type " + clazz);
                    }
                    try
                    {
                        Constructor<?> constructor;
                        Class<?> primitiveClass = ClassUtils.resolvePrimitiveClassName(clazz.getSimpleName().toLowerCase());
                        if (primitiveClass != null)
                        {
                            constructor = field.getType().getConstructor(primitiveClass);
                        }
                        else
                        {
                            constructor = field.getType().getConstructor(clazz);
                        }

                        ReflectionUtils.setField(field, bean, constructor.newInstance(entry.get(fieldName)));
                    }
                    catch (Exception e1)
                    {
                        throw new IndexEntryMappingException("Unable to set the field " + field.getName() + " of type " + field.getType() + " with value " + entry.get(fieldName)
                                + " of type " + clazz, e1);
                    }
                }
            }
        }

        return bean;
    }

    /**
     * Gets the value for a specific field in a bean.
     * 
     * @param bean An object from which the value should be extracted.
     * @param requiredType The data type for the field.
     * @return The value of the specified field.
     */
    private Object getValue(final Object bean, final Class<?> requiredType)
    {
        return bean;
    }
}
