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

package org.springframework.data.search.solr.catalog.product.repository;

import static org.junit.Assert.assertNotNull;

import java.util.UUID;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.search.catalog.product.Product;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Unit tests for {@link ProductRepository}.
 */
@ContextConfiguration(locations = "classpath:springContext.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class TestProductRepository
{
    @Autowired
    ProductRepository repository;

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
        Product bean = new Product(UUID.randomUUID().toString(), "Black and Decker Power Drive", "An electrical screwdriver.");
        this.repository.save(bean);

        bean = new Product(
                UUID.randomUUID().toString(),
                "Hoover WindTunnel",
                "Combining superior performance with a lightweight design at just 16.5 pounds, this bagless upright vacuum cleaner offers powerful cleaning throughout the home, from top to bottom. The unit's five-position carpet-height adjustment allows for effectively cleaning of all floor types. The patented WindTunnel technology features air passages that help maintain suction power and traps dirt and channels it into the dirt cup-- which means the dirt and debris stay in the machine, not scattered back out onto the floor.");
        this.repository.save(bean);

        bean = new Product(UUID.randomUUID().toString(), "Nerf Vortex Proton",
                "Offering long-range, high-powered disc-blasting technology, NERF VORTEX blasters hurl ultra-distance discs for the ultimate battle experience!");
        this.repository.save(bean);

        Iterable<Product> beans = this.repository.findAll();

        assertNotNull(beans.iterator());
    }
}
