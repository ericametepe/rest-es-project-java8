package com.gocpf.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.gocpf.entities.CouvertureGeo;

public interface CouvertureGeoRepository extends ElasticsearchRepository<CouvertureGeo, String> {
	
	CouvertureGeo findByNom(String name);
	

}
