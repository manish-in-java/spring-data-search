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

import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.data.repository.config.AbstractRepositoryConfigDefinitionParser;
import org.w3c.dom.Element;

/**
 * Scans the classpath for Solr repository interfaces and creates
 * implementations for them.
 */
public class SolrRepositoryConfigParser extends AbstractRepositoryConfigDefinitionParser<SolrRepositoryConfiguration, SolrRepositoryConfigInformation>
{
    /**
     * {@inheritDoc}
     */
    @Override
    protected SolrRepositoryConfiguration getGlobalRepositoryConfigInformation(final Element element)
    {
        return new SolrRepositoryConfiguration(element);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void postProcessBeanDefinition(final SolrRepositoryConfigInformation context, final BeanDefinitionBuilder builder, final BeanDefinitionRegistry registry,
            final Object beanSource)
    {
        builder.addPropertyReference("solrOperations", context.getSolrTemplateRef());
    }
}
