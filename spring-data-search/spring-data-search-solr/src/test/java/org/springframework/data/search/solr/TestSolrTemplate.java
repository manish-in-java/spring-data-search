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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.solr.client.solrj.SolrServerException;
import org.junit.Before;
import org.junit.Test;
import org.springframework.data.search.AnythingToBeIndexedBean;
import org.springframework.data.search.DummyBean;
import org.springframework.data.search.EmptyBean;
import org.springframework.data.search.IndexEntry;
import org.springframework.data.search.IndexEntryMapper;
import org.springframework.data.search.InvalidIndexEntryException;
import org.springframework.data.search.InvalidParamsException;
import org.springframework.data.search.InvalidQueryException;
import org.springframework.data.search.QueryResponse;
import org.springframework.data.search.SearchOperations;
import org.springframework.data.search.core.SimpleIndexEntry;
import org.xml.sax.SAXException;

/**
 * Unit tests for {@link SolrTemplate}.
 */
public class TestSolrTemplate
{
    private SearchOperations searchOperations;

    @Before
    public void beforeEachTest() throws SolrServerException, IOException, ParserConfigurationException, SAXException
    {
        searchOperations = new SolrTemplate("default", true, "solr/solr.xml");
        searchOperations.deleteByQuery("*:*");
    }

    @Test
    public void addDocumentAndCheckResult()
    {
        IndexEntry document = new SimpleIndexEntry();
        document.put("id", "123");
        document.put("name", "toto");

        searchOperations.add(document);

        QueryResponse response = searchOperations.query("id:123");
        assertNotNull(response);
        assertNotNull(response.getMatchingEntries());

        assertEquals(1, response.getMatchingEntries().size());
        assertEquals("toto", response.getMatchingEntries().iterator().next().get("name"));
    }

    @Test
    public void addDocumentsAndCheckResult()
    {

        IndexEntry document1 = new SimpleIndexEntry();
        document1.put("id", "123");
        document1.put("name", "toto");

        IndexEntry document2 = new SimpleIndexEntry();
        document2.put("id", "124");
        document2.put("name", "tata");

        searchOperations.add(document1, document2);

        QueryResponse response1 = searchOperations.query("id:123");
        assertNotNull(response1);
        assertNotNull(response1.getMatchingEntries());

        assertEquals(1, response1.getMatchingEntries().size());
        assertEquals("toto", response1.getMatchingEntries().iterator().next().get("name"));

        QueryResponse response2 = searchOperations.query("id:124");
        assertNotNull(response2);
        assertNotNull(response2.getMatchingEntries());

        assertEquals(1, response2.getMatchingEntries().size());
        assertEquals("tata", response2.getMatchingEntries().iterator().next().get("name"));
    }

    @Test
    public void addDummyBeanAndCheckResult()
    {

        Date today = new Date();
        DummyBean bean = new DummyBean("1234", today, "dummy name");

        searchOperations.index(bean);

        QueryResponse response = searchOperations.query("id:1234");
        assertNotNull(response);
        assertNotNull(response.getMatchingEntries());

        assertEquals(1, response.getMatchingEntries().size());
        assertEquals("dummy name", response.getMatchingEntries().iterator().next().get("name"));
        assertEquals("1234", response.getMatchingEntries().iterator().next().get("id"));
        assertEquals(today, response.getMatchingEntries().iterator().next().get("last_modified"));
    }

    @Test
    public void addDummyBeansAndCheckResult()
    {

        Date today = new Date();
        DummyBean bean1 = new DummyBean("1234", today, "dummy name");
        DummyBean bean2 = new DummyBean("2345", today, "dummy name 2");

        searchOperations.index(bean1, bean2);

        QueryResponse response = searchOperations.query("id:*");
        assertNotNull(response);
        assertNotNull(response.getMatchingEntries());

        assertEquals(2, response.getMatchingEntries().size());

        final Iterator<? extends IndexEntry> resultsIterator = response.getMatchingEntries().iterator();
        IndexEntry result = resultsIterator.next();
        assertEquals("dummy name", result.get("name"));
        assertEquals("1234", result.get("id"));
        assertEquals(today, result.get("last_modified"));

        result = resultsIterator.next();
        assertEquals("dummy name 2", result.get("name"));
        assertEquals("2345", result.get("id"));
        assertEquals(today, result.get("last_modified"));
    }

    @Test(expected = InvalidIndexEntryException.class)
    public void addEmptyBeanAndCheckResult()
    {
        searchOperations.index(new EmptyBean());
    }

    @Test(expected = InvalidIndexEntryException.class)
    public void addEmptyBeansAndCheckResult()
    {
        searchOperations.index(new EmptyBean(), new EmptyBean());
    }

    @Test(expected = InvalidIndexEntryException.class)
    public void addAnythingToBeIndexedBeanAndCheckResult()
    {
        searchOperations.index(new AnythingToBeIndexedBean());
    }

    @Test(expected = InvalidIndexEntryException.class)
    public void addAnythingToBeIndexedBeansAndCheckResult()
    {
        searchOperations.index(new AnythingToBeIndexedBean(), new AnythingToBeIndexedBean());
    }

    @Test
    public void findAndMapADocumentWithAMapper()
    {
        Date today = new Date();
        DummyBean bean1 = new DummyBean("1234", today, "dummy name");
        DummyBean bean2 = new DummyBean("2345", today, "dummy name 2");

        searchOperations.index(bean1, bean2);

        Collection<DummyBean> beans = searchOperations.query("id:1234", new IndexEntryMapper<DummyBean>()
        {
            @Override
            public DummyBean map(IndexEntry doc)
            {
                return new DummyBean((String) doc.get("id"), (Date) doc.get("last_modified"), (String) doc.get("name"));
            }
        });

        assertNotNull(beans);
        assertEquals(1, beans.size());
        assertEquals(bean1, beans.iterator().next());
    }

    @Test
    public void findAndMapADocumentWithAClass()
    {
        Date today = new Date();
        DummyBean bean1 = new DummyBean("1234", today, "dummy name");
        DummyBean bean2 = new DummyBean("2345", today, "dummy name 2");

        searchOperations.index(bean1, bean2);

        Collection<DummyBean> beans = (Collection<DummyBean>) searchOperations.query("id:1234", DummyBean.class);

        assertNotNull(beans);
        assertEquals(1, beans.size());
        assertEquals(bean1, beans.iterator().next());
    }

    @Test
    public void searchWithManyParams()
    {
        IndexEntry document = new SimpleIndexEntry();
        document.put("id", "123");
        document.put("name", "toto");

        searchOperations.add(document);

        QueryResponse response = searchOperations.query("id:{id} and name:{name}", new Object[] { 123, "toto" });
        assertNotNull(response);
        assertNotNull(response.getMatchingEntries());

        assertEquals(1, response.getMatchingEntries().size());
        assertEquals("toto", response.getMatchingEntries().iterator().next().get("name"));
    }

    @Test
    public void searchWithOneParam()
    {
        IndexEntry document = new SimpleIndexEntry();
        document.put("id", "123");
        document.put("name", "toto");

        searchOperations.add(document);

        QueryResponse response = searchOperations.query("id:{id}", new Object[] { 123 });
        assertNotNull(response);
        assertNotNull(response.getMatchingEntries());

        assertEquals(1, response.getMatchingEntries().size());
        assertEquals("toto", response.getMatchingEntries().iterator().next().get("name"));
    }

    @Test(expected = InvalidQueryException.class)
    public void searchWithInvalidQuery()
    {
        searchOperations.query("<$'(-/*");
    }

    @Test(expected = InvalidParamsException.class)
    public void searchWithNoEnoughParams()
    {
        searchOperations.query("id:{id} and name:{name}", new Object[] { 123 });
    }

    @Test(expected = InvalidParamsException.class)
    public void searchWithToMuchParams()
    {
        searchOperations.query("id:{id}", new Object[] { 123, "toto" });
    }
}
