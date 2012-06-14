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

import org.springframework.data.repository.config.AutomaticRepositoryConfigInformation;
import org.springframework.data.repository.config.ManualRepositoryConfigInformation;
import org.springframework.data.repository.config.RepositoryConfig;
import org.springframework.data.repository.core.support.RepositoryFactoryBeanSupport;
import org.springframework.data.search.solr.SolrTemplate;
import org.springframework.data.search.solr.repository.support.SolrRepositoryFactoryBean;
import org.springframework.util.StringUtils;
import org.w3c.dom.Element;

/**
 * Parses <code>solr:repositories</code> section in Spring configuration files
 * to create Spring repository implementations automatically.
 */
public class SolrRepositoryConfiguration extends RepositoryConfig<SolrRepositoryConfigInformation, SolrRepositoryConfiguration>
{
    private static final String DEFAULT_SOLR_TEMPLATE_REF   = "solrTemplate";
    private static final String SOLR_TEMPLATE_REF_ATTRIBUTE = "solr-template-ref";

    /**
     * Sets a {@link RepositoryFactoryBeanSupport} that will be used to
     * instantiate the factory, which in turn will create Solr repository
     * instances by scanning the classpath.
     * 
     * @param repositoriesElement The {@link Element} corresponding to the
     *            <code>solr:repositories</code> section in the Spring
     *            configuration file.
     */
    public SolrRepositoryConfiguration(final Element repositoriesElement)
    {
        super(repositoriesElement, SolrRepositoryFactoryBean.class.getName());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected SolrRepositoryConfigInformation createSingleRepositoryConfigInformationFor(final Element element)
    {
        return new ManualSolrRepositoryConfigInformation(element, this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SolrRepositoryConfigInformation getAutoconfigRepositoryInformation(final String interfaceName)
    {
        return new AutomaticSolrRepositoryConfigInformation(interfaceName, this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getNamedQueriesLocation()
    {
        return "classpath*:META-INF/solr-queries.properties";
    }

    /**
     * Returns the reference name of the {@link SolrTemplate} Spring bean to be
     * referenced by Solr repositories for performing search operations on Solr
     * instances.
     * 
     * @return The reference name of a {@link SolrTemplate} Spring bean.
     */
    public String getSolrTemplateRef()
    {
        String templateRef = this.getSource().getAttribute(SOLR_TEMPLATE_REF_ATTRIBUTE);

        return StringUtils.hasText(templateRef) ? templateRef : DEFAULT_SOLR_TEMPLATE_REF;
    }

    /**
     * Implements lookup of additional attributes required to create Solr
     * repository instances automatically.
     * 
     * @author Manish Baxi
     */
    private static class AutomaticSolrRepositoryConfigInformation extends AutomaticRepositoryConfigInformation<SolrRepositoryConfiguration> implements
            SolrRepositoryConfigInformation
    {
        /**
         * Creates a new {@link ManualSolrRepositoryConfigInformation} for a
         * given interface name and parent.
         * 
         * @param interfaceName The interface name.
         * @param parent A {@link SolrRepositoryConfiguration}.
         */
        public AutomaticSolrRepositoryConfigInformation(final String interfaceName, final SolrRepositoryConfiguration parent)
        {
            super(interfaceName, parent);
        }

        /**
         * {@inheritDoc}
         */
        public String getSolrTemplateRef()
        {

            return this.getParent().getSolrTemplateRef();
        }
    }

    /**
     * Implements lookup of additional attributes required to create Solr
     * repository instances manually.
     * 
     * @author Manish Baxi
     */
    private static class ManualSolrRepositoryConfigInformation extends ManualRepositoryConfigInformation<SolrRepositoryConfiguration> implements SolrRepositoryConfigInformation
    {
        /**
         * Creates a new {@link ManualSolrRepositoryConfigInformation} for a
         * given {@link Element} and parent.
         * 
         * @param element The {@link Element} containing repository attributes
         *            to parse for creating Solr repository instances.
         * @param parent A {@link SolrRepositoryConfiguration}.
         */
        public ManualSolrRepositoryConfigInformation(final Element element, final SolrRepositoryConfiguration parent)
        {

            super(element, parent);
        }

        /**
         * {@inheritDoc}
         */
        public String getSolrTemplateRef()
        {

            return this.getParent().getSolrTemplateRef();
        }
    }
}
