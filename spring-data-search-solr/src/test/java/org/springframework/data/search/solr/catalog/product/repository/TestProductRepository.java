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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.search.catalog.brand.Brand;
import org.springframework.data.search.catalog.product.Product;
import org.springframework.data.search.solr.catalog.brand.repository.BrandRepository;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Unit tests for {@link ProductRepository}.
 */
@ContextConfiguration(locations = "classpath:springContext.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class TestProductRepository
{
    private static final Log LOGGER = LogFactory.getLog(TestProductRepository.class);

    @Autowired
    BrandRepository          brandRepository;
    @Autowired
    ProductRepository        productRepository;

    /**
     * Clears the index after each test.
     */
    @After
    public void afterEachTest()
    {
        this.brandRepository.deleteAll();
        this.brandRepository.commit();

        this.productRepository.deleteAll();
        this.productRepository.commit();
    }

    /**
     * Tests that repository instances are created automatically through
     * classpath scanning, provided appropriate Spring bean configuration has
     * been created.
     */
    @Test
    public void testAutoConfig()
    {
        assertNotNull(this.brandRepository);
        assertNotNull(this.productRepository);
    }

    /**
     * Tests that all indexed records can be retrieved.
     */
    @Test
    public void testFindAll()
    {
        Brand brand = new Brand("1", "Garmin",
                "Innovative GPS technology across diverse markets, including aviation, marine, fitness, outdoor recreation, tracking and mobile apps.");
        this.brandRepository.save(brand);

        brand = new Brand("2", "Acer",
                "Manufacturer and distributor of PC notebooks and desktops, smartphones, monitors, TVs and solutions for business, Government, Education and home users.");
        this.brandRepository.save(brand);

        brand = new Brand(
                "3",
                "Nestlé",
                "Nestlé is the world's leading Nutrition, Health and Wellness company. With headquarters in Switzerland, Nestlé has offices, factories and research and development facilities around the world.");
        this.brandRepository.save(brand);

        brand = new Brand("4", "Boeing", "American builder and manufacturer of Commercial Airplanes and Integrated Defence Systems.");
        this.brandRepository.save(brand);

        Product product = new Product("1", "Black and Decker Power Drive", "An electrical screwdriver.");
        this.productRepository.save(product);

        product = new Product(
                "2",
                "Hoover WindTunnel",
                "Combining superior performance with a lightweight design at just 16.5 pounds, this bagless upright vacuum cleaner offers powerful cleaning throughout the home, from top to bottom. The unit's five-position carpet-height adjustment allows for effectively cleaning of all floor types. The patented WindTunnel technology features air passages that help maintain suction power and traps dirt and channels it into the dirt cup-- which means the dirt and debris stay in the machine, not scattered back out onto the floor.");
        this.productRepository.save(product);

        product = new Product("3", "Nerf Vortex Proton",
                "Offering long-range, high-powered disc-blasting technology, NERF VORTEX blasters hurl ultra-distance discs for the ultimate battle experience!");
        this.productRepository.save(product);

        final Iterable<Brand> brands = this.brandRepository.findAll();
        assertNotNull(brands.iterator());

        for (Brand bean : brands)
        {
            LOGGER.info(String.format("%s: %s", bean.getId(), bean.getName()));
        }

        final Iterable<Product> products = this.productRepository.findAll();
        assertNotNull(products.iterator());

        for (Product bean : products)
        {
            LOGGER.info(String.format("%s: %s", bean.getId(), bean.getName()));
        }
    }
}
