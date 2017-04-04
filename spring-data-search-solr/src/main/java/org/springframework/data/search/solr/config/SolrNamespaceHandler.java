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

package org.springframework.data.search.solr.config;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;
import org.springframework.data.repository.config.RepositoryBeanDefinitionParser;
import org.springframework.data.repository.config.RepositoryConfigurationExtension;

/**
 * Provides support for parsing Spring configuration associated with the
 * <code>solr</code> XML namespace in Spring configuration files.
 */
public class SolrNamespaceHandler extends NamespaceHandlerSupport
{
    /**
     * @see org.springframework.beans.factory.xml.NamespaceHandler#init()
     */
    @Override
    public void init()
    {
        final RepositoryConfigurationExtension extension = new SolrRepositoryConfigurationExtension();
        final RepositoryBeanDefinitionParser repositoryBeanDefinitionParser = new RepositoryBeanDefinitionParser(extension);

        registerBeanDefinitionParser("repositories", repositoryBeanDefinitionParser);
    }
}
