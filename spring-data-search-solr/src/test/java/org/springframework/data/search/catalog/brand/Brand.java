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

package org.springframework.data.search.catalog.brand;

import org.springframework.data.search.Indexable;
import org.springframework.data.search.annotation.Indexed;

/**
 * Represents a brand of products.
 */
public class Brand implements Indexable
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
    public Brand()
    {
    }

    /**
     * Initializes all the fields.
     * 
     * @param id The unique brand identifier.
     * @param name The brand name.
     * @param description The brand description.
     */
    public Brand(final String id, final String name, final String description)
    {
        this.description = description;
        this.id = id;
        this.name = name;
    }

    /**
     * Gets the brand description.
     * 
     * @return The brand description.
     */
    public final String getDescription()
    {
        return this.description;
    }

    /**
     * Gets the unique brand identifier.
     * 
     * @return The unique brand identifier.
     */
    @Override
    public final String getId()
    {
        return this.id;
    }

    /**
     * Gets the brand name.
     * 
     * @return The brand name.
     */
    public final String getName()
    {
        return this.name;
    }

    /**
     * Sets the brand description.
     * 
     * @param description The brand description.
     */
    public final void setDescription(final String description)
    {
        this.description = description;
    }

    /**
     * Sets the unique brand identifier.
     * 
     * @param id The unique brand identifier.
     */
    public final void setId(final String id)
    {
        this.id = id;
    }

    /**
     * Sets the brand name.
     * 
     * @param name The brand name.
     */
    public final void setName(final String name)
    {
        this.name = name;
    }
}
