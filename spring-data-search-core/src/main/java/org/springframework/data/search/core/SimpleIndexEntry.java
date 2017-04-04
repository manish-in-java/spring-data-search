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

package org.springframework.data.search.core;

import java.util.HashMap;

import org.springframework.data.search.IndexEntry;

/**
 * Basic implementation of {@link IndexEntry}.
 * 
 * @author Vincent Devillers
 */
public class SimpleIndexEntry extends HashMap<String, Object> implements IndexEntry
{
    private static final long serialVersionUID = 4287290907113314399L;

    /**
     * Throws {@link IllegalArgumentException}.
     */
    @Override
    public Float getScore()
    {
        throw new IllegalArgumentException("Instance does not support a score.");
    }
}
