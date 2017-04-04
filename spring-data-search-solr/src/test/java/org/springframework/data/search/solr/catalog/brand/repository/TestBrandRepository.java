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

package org.springframework.data.search.solr.catalog.brand.repository;

import static org.junit.Assert.assertNotNull;

import java.util.UUID;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.search.catalog.brand.Brand;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Unit tests for {@link BrandRepository}.
 */
@ContextConfiguration(locations = "classpath:springContext.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class TestBrandRepository
{
    @Autowired
    BrandRepository repository;

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
     * Tests that all indexed records can be retrieved.
     */
    @Test
    public void testFindAll()
    {
        Brand bean = new Brand(UUID.randomUUID().toString(), "Garmin",
                "Innovative GPS technology across diverse markets, including aviation, marine, fitness, outdoor recreation, tracking and mobile apps.");
        this.repository.save(bean);

        bean = new Brand(UUID.randomUUID().toString(), "Acer",
                "Manufacturer and distributor of PC notebooks and desktops, smartphones, monitors, TVs and solutions for business, Government, Education and home users.");
        this.repository.save(bean);

        bean = new Brand(
                UUID.randomUUID().toString(),
                "Nestlé",
                "Nestlé is the world's leading Nutrition, Health and Wellness company. With headquarters in Switzerland, Nestlé has offices, factories and research and development facilities around the world.");
        this.repository.save(bean);

        bean = new Brand(UUID.randomUUID().toString(), "Boeing", "American builder and manufacturer of Commercial Airplanes and Integrated Defence Systems.");
        this.repository.save(bean);

        Iterable<Brand> beans = this.repository.findAll();

        assertNotNull(beans.iterator());
    }
}
