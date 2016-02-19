package com.gocpf.repository;

import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.gocpf.entities.Activite;

public interface ActiviteRepository  extends ElasticsearchRepository<Activite, String> {
	
	Iterable<Activite> findByCodeAPE(String code);
//	@Query("{\"match\": {\"intitule\":\"?0\"}}")
	 @Query("{\"match\" : {\"intitule\" : {\"query\" : \"?0\",\"minimum_should_match\" : \"75%\"}}}")
	Iterable<Activite> findByIntitule(String intitule);
	

}

