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

import java.util.Arrays;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.ArrayUtils;
import org.springframework.data.search.InvalidParamsException;

/**
 * Builds a search query using a tokenized string query and dynamic query
 * parameters.
 * 
 * @author Vincent Devillers
 * @author Manish Baxi
 */
public abstract class QueryBuilder
{
    private static final Pattern NAMES_PATTERN = Pattern.compile("\\{\\w+\\}");

    /**
     * Builds a query dynamically using a tokenized string query and dynamic
     * query parameters.
     * 
     * @param query The tokenized query.
     * @param params Query parameters.
     * @return A valid query that can be executed on a search engine.
     */
    public static String resolveParams(final String query, final Object[] params)
    {
        final StringBuffer buffer = new StringBuffer(query.length());

        if (!ArrayUtils.isEmpty(params))
        {
            Iterator<Object> iterator = Arrays.asList(params).iterator();
            Matcher matcher = NAMES_PATTERN.matcher(query);

            int i = 0;
            while (matcher.find())
            {
                i++;
                if (params.length < i)
                {
                    throw new InvalidParamsException("Some parameters are missing:" + Arrays.toString(params));
                }
                Object variableValue = iterator.next();
                String variableValueString = getVariableValueAsString(variableValue);
                String replacement = Matcher.quoteReplacement(variableValueString);
                matcher.appendReplacement(buffer, replacement);
            }
            matcher.appendTail(buffer);

            if (params.length > i)
            {
                throw new InvalidParamsException("Too much parameters for this query!" + Arrays.toString(params));
            }
        }
        else
        {
            buffer.append(query);
        }

        return buffer.toString();
    }

    /**
     * Gets a null-safe string value for a field.
     * 
     * @param value An object to be converted into a string.
     * @return The string representation of <code>value</code> if
     *         <code>value</code> is not <code>null</code>, the empty string (
     *         <code>"</code>) otherwise.
     */
    private static String getVariableValueAsString(Object value)
    {
        return value != null ? value.toString() : "";
    }
}
