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

package org.springframework.data.search;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.springframework.data.search.InvalidParamsException;
import org.springframework.data.search.core.QueryBuilder;

/**
 * Unit tests for {@link QueryBuilder}.
 * 
 * @author Vincent Devillers
 */
public class TestQueryBuilder
{
    @Test(expected = InvalidParamsException.class)
    public void searchWithoutEnoughParams()
    {
        QueryBuilder.resolveParams("id:{id} and name:{name}", new Object[] { 123 });
    }

    @Test(expected = InvalidParamsException.class)
    public void searchWithExcessParams()
    {
        QueryBuilder.resolveParams("id:{id}", new Object[] { 123, "toto" });
    }

    @Test
    public void searchWithOneParam()
    {
        String result = QueryBuilder.resolveParams("id:{id}", new Object[] { 123 });
        assertEquals("id:123", result);
    }

    @Test
    public void searchWithManyParams()
    {
        String result = QueryBuilder.resolveParams("id:{id} and name:{name} or else", new Object[] { 123, "toto" });
        assertEquals("id:123 and name:toto or else", result);
    }

    @Test
    public void jsonSearchWithManyParams()
    {
        String result = QueryBuilder.resolveParams("{\"query\" : {\"field\" : { \"id\" : \"{id}\"}}}", new Object[] { 123 });
        assertEquals("{\"query\" : {\"field\" : { \"id\" : \"123\"}}}", result);
    }
}
