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

import java.util.Date;

import org.springframework.data.search.annotation.Indexed;

/**
 * A sample POJO for testing search classes and interfaces.
 * 
 * @author Vincent Devillers
 * @author Manish Baxi
 */
public class DummyBean implements Indexable
{
    @Indexed
    private String id;

    @Indexed(fieldName = "last_modified")
    private Date   modified;

    @Indexed
    private String name;

    public DummyBean()
    {
    }

    public DummyBean(String id, Date modified, String name)
    {
        this.id = id;
        this.modified = modified;
        this.name = name;
    }

    /**
     * {@inheritDoc}
     */
    public String getId()
    {
        return id;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getIndexName()
    {
        return null;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public Date getModified()
    {
        return modified;
    }

    public void setModified(Date modified)
    {
        this.modified = modified;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((modified == null) ? 0 : modified.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        DummyBean other = (DummyBean) obj;
        if (id == null)
        {
            if (other.id != null)
                return false;
        }
        else if (!id.equals(other.id))
            return false;
        if (modified == null)
        {
            if (other.modified != null)
                return false;
        }
        else if (!modified.equals(other.modified))
            return false;
        if (name == null)
        {
            if (other.name != null)
                return false;
        }
        else if (!name.equals(other.name))
            return false;
        return true;
    }

    @Override
    public String toString()
    {
        return "DummyBean [id=" + id + ", modified=" + modified + ", name=" + name + "]";
    }
}
