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

package org.springframework.data.search.util;

/**
 * Contains helper methods for working with filesystem paths. This class is not
 * thread-safe.
 * 
 * @author Manish Baxi
 */
public final class PathUtil
{
    private static final char SYSTEM_PATH_SEPARATOR = System.getProperty("line.separator").charAt(0);

    /**
     * Prevent instantiation.
     */
    private PathUtil()
    {
    }

    /**
     * Concatenates multiple paths, separating them by the character used to
     * delimit paths on the platform where the application is running.
     * 
     * @param paths Paths to be concatenated.
     * @return Concatenated path.
     */
    public static String concatenate(final String... paths)
    {
        return concatenate(SYSTEM_PATH_SEPARATOR, paths);
    }

    /**
     * Concatenates multiple paths, separating them by a specified character.
     * 
     * @param paths Paths to be concatenated.
     * @return Concatenated path.
     */
    public static String concatenate(final char delimiter, final String... paths)
    {
        final StringBuilder buffer = new StringBuilder();

        for (final String path : paths)
        {
            if ((path != null) && !"".equals(path.trim()))
            {
                if ((buffer.length() > 0) && (buffer.charAt(buffer.length() - 1) != delimiter))
                {
                    buffer.append(delimiter);
                }

                buffer.append(path);
            }
        }

        return buffer.toString();
    }
}
