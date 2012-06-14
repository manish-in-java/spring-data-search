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

package org.springframework.data.search.solr.repository;

import java.util.UUID;
import java.util.Date;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.search.DummyBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

/**
 *
 */
@ContextConfiguration(locations = "classpath:springContext.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class TestSolrRepository
{
    @Autowired
    DummyBeanRepository repository;

    /**
     * Clears the index after each test.
     */
    @After
    public void afterEachTest()
    {
        this.repository.deleteAll();
        this.repository.commit();
    }

    /**
     * Tests that repository instances are created automatically through
     * classpath scanning, provided appropriate Spring bean configuration has
     * been created.
     */
    @Test
    public void testAutoConfig()
    {
        assertNotNull(this.repository);
    }

    /**
     * Tests that objects indexed previously can be loaded using the repository
     * interfaces.
     */
    @Test
    public void testFind()
    {
        final DummyBean bean = new DummyBean(UUID.randomUUID().toString(), new Date(), "Searchable data");

        this.repository.save(bean);

        final DummyBean indexedBean = this.repository.findOne(bean.getId());

        assertNotNull(indexedBean);
        assertEquals(bean.getId(), indexedBean.getId());
        assertEquals(bean.getModified(), indexedBean.getModified());
        assertEquals(bean.getName(), indexedBean.getName());
    }

    /**
     * Tests that all indexed records can be
     */
    @Test
    public void testFindAll()
    {
        DummyBean bean = new DummyBean(UUID.randomUUID().toString(), new Date(), "Searchable data");

        this.repository.save(bean);

        bean = new DummyBean(UUID.randomUUID().toString(), new Date(), "Searchable data");

        this.repository.save(bean);

        bean = new DummyBean(UUID.randomUUID().toString(), new Date(), "Searchable data");

        this.repository.save(bean);

        Iterable<DummyBean> beans = this.repository.findAll();

        assertNotNull(beans.iterator());
    }

    /**
     * Tests that objects can be indexed using the repository interfaces.
     */
    @Test
    public void testSave()
    {
        final DummyBean bean = new DummyBean(UUID.randomUUID().toString(), new Date(), "Searchable data");

        final DummyBean indexedBean = this.repository.save(bean);

        assertNotNull(indexedBean);
        assertEquals(bean.getId(), indexedBean.getId());
        assertEquals(bean.getModified(), indexedBean.getModified());
        assertEquals(bean.getName(), indexedBean.getName());
    }
}
