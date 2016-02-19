package com.gocpf.repository;

import java.util.Optional;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.gocpf.entities.Auditeur;

public interface AuditeurRepository extends ElasticsearchRepository<Auditeur, String> {
	
	Optional<Auditeur> findByNom(String nom);

}
