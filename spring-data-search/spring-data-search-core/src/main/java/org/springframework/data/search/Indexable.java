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

/**
 * Contract for objects that can be indexed.
 * 
 * @author Manish Baxi
 */
public interface Indexable
{
    /**
     * Gets the unique identifier with which the object will be referred to in
     * search indices.
     * 
     * @return The unique identifier with which the object will be referred to
     *         in search indices.
     */
    String getId();

    /**
     * Gets an optional index name to which the objects must be saved.
     * 
     * @return An optional index name to which the objects must be saved.
     */
    String getIndexName();
}
