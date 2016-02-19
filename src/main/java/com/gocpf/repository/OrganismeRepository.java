package com.gocpf.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.gocpf.entities.Organisme;

public interface OrganismeRepository extends ElasticsearchRepository<Organisme, String> {
	

}
