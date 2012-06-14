package org.springframework.data.search.solr.repository;

import org.springframework.data.repository.Repository;
import org.springframework.data.search.DummyBean;

/**
 * A Spring Data {@link Repository} for {@link DummyBean}.
 */
public interface DummyBeanRepository extends SolrRepository<DummyBean>
{
}