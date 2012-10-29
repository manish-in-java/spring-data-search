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
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.data.config.ParsingUtils;
import org.springframework.data.repository.config.AnnotationRepositoryConfigurationSource;
import org.springframework.data.repository.config.RepositoryConfigurationExtensionSupport;
import org.springframework.data.repository.config.XmlRepositoryConfigurationSource;
import org.springframework.data.search.solr.repository.support.SolrRepositoryFactoryBean;
import org.w3c.dom.Element;

/**
 * Parses <code>solr:repositories</code> section in Spring configuration files
 * to create Spring repository implementations automatically.
 */
public class SolrRepositoryConfigurationExtension extends RepositoryConfigurationExtensionSupport
{
    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.data.repository.config.
     * RepositoryConfigurationExtensionSupport#getModulePrefix()
     */
    @Override
    protected String getModulePrefix()
    {
        return "solr";
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.springframework.data.repository.config.RepositoryConfigurationExtension
     * #getRepositoryFactoryClassName()
     */
    public String getRepositoryFactoryClassName()
    {
        return SolrRepositoryFactoryBean.class.getName();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.data.repository.config.
     * RepositoryConfigurationExtensionSupport
     * #postProcess(org.springframework.beans
     * .factory.support.BeanDefinitionBuilder,
     * org.springframework.data.repository
     * .config.XmlRepositoryConfigurationSource)
     */
    @Override
    public void postProcess(BeanDefinitionBuilder builder, XmlRepositoryConfigurationSource config)
    {
        Element element = config.getElement();

        ParsingUtils.setPropertyReference(builder, element, "solr-template-ref", "solrOperations");
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.data.repository.config.
     * RepositoryConfigurationExtensionSupport
     * #postProcess(org.springframework.beans
     * .factory.support.BeanDefinitionBuilder,
     * org.springframework.data.repository
     * .config.AnnotationRepositoryConfigurationSource)
     */
    @Override
    public void postProcess(BeanDefinitionBuilder builder, AnnotationRepositoryConfigurationSource config)
    {
        AnnotationAttributes attributes = config.getAttributes();

        builder.addPropertyReference("solrOperations", attributes.getString("solrTemplateRef"));
    }
}
