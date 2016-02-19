package com.gocpf.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.gocpf.entities.Niveau;

public interface NiveauRepository extends ElasticsearchRepository<Niveau, String> {

}
