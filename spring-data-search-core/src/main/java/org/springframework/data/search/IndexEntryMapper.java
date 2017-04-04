/*
 * Copyright 2008-2012 the original author or authors.
 *
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
 * Contract for converting a search index entry into the object from which the
 * index entry originated.
 * 
 * @author Vincent Devillers
 * @author Manish Baxi
 */
public interface IndexEntryMapper<T>
{
    /**
     * Converts a search index entry into the object from which the index entry
     * originated.
     * 
     * @param document The search index entry to convert.
     * @return The converted entry.
     */
    T map(IndexEntry document);
}
