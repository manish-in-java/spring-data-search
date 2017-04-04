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

package org.springframework.data.search.catalog.product;

import org.springframework.data.search.Indexable;
import org.springframework.data.search.annotation.Indexed;

/**
 * Represents a product.
 */
public class Product implements Indexable
{
    @Indexed
    private String description;
    @Indexed
    private String id;
    @Indexed
    private String name;

    /**
     * Default constructor.
     */
    public Product()
    {
    }

    /**
     * Initializes all the fields.
     * 
     * @param id The unique product identifier.
     * @param name The product name.
     * @param description The product description.
     */
    public Product(final String id, final String name, final String description)
    {
        this.description = description;
        this.id = id;
        this.name = name;
    }

    /**
     * Gets the product description.
     * 
     * @return The product description.
     */
    public final String getDescription()
    {
        return this.description;
    }

    /**
     * Gets the unique product identifier.
     * 
     * @return The unique product identifier.
     */
    @Override
    public final String getId()
    {
        return this.id;
    }

    /**
     * Gets the product name.
     * 
     * @return The product name.
     */
    public final String getName()
    {
        return this.name;
    }

    /**
     * Sets the product description.
     * 
     * @param description The product description.
     */
    public final void setDescription(final String description)
    {
        this.description = description;
    }

    /**
     * Sets the unique product identifier.
     * 
     * @param id The unique product identifier.
     */
    public final void setId(final String id)
    {
        this.id = id;
    }

    /**
     * Sets the product name.
     * 
     * @param name The product name.
     */
    public final void setName(final String name)
    {
        this.name = name;
    }
}
