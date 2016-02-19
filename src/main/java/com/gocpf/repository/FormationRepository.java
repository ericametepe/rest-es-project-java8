
package com.gocpf.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.gocpf.entities.Formation;

public interface FormationRepository extends ElasticsearchRepository<Formation, String>{
	
	List<Formation> findByPublicAuditeurAndIntituleLike(String pub, String intitule);
	
	List<Formation> findByPublicAuditeur(String pub);
	
	
	Iterable<Formation> findByPublicAuditeurAndCouvertureGeoAndIntituleAndCodeAPEsLike( String pub,  String region, String intitule, String codeAPE);
	
	
	Iterable<Formation> findByPublicAuditeurAndCouvertureGeoAndIntituleOriginalTextAndCodeAPEsLike( String pub,  String region, String intituleSansAccent, String codeAPE);


	
	List<Formation> findByIntituleLike(String intitule );
	
	
	List<Formation> findByIntituleContains(String ...words );
	
	
	List<Formation> findByCouvertureGeo(String couvGeo);
	
	Page<Formation> findAll(Pageable pageable);
	
	List<Formation> findByCouvertureGeoAndPublicAuditeur(String region,String pub, Pageable pageable);

	

//    @Query("{\"bool\" : {\"must\" : {\"field\" : {\"intitule\" : \"?0\"}}}}")
	@Query("{\"match\": {\"intitule\":\"?0\"}}")
	List<Formation> findByIntitule(String intitule, Pageable pageable );
	
	@Query("{\"bool\": {\"must\" : {\"match\":{\"publicAuditeur\":\"?0\"}},\"must\" :{\"match\" :{\"couverturegeo\":\"?1\"}},\"must\": {\"match\": {\"intitule\": \"?2\"}} }}")
	List<Formation> findByCustomProfile(String pub,  String region, String intitule );


//	@Query("{\"match\": {\"organismeEditeur\":\"?0\"}}")
	List<Formation> findByOrganismeEditeur(String editeur);

	Formation findByCodeCPF(String codeCPF);

	List<Formation> findByNiveau(String nom);
//	@Query("{\"bool\": {\"must\" : {\"terms\":{\"codeAPE\":[\"?0\"],\"minimum_should_match\":1}}}}")
	@Query("{\"match\" : {\"codeAPEs\" : \"?0\"}}")
	Iterable<Formation> findByCodeAPEsIn(String codeAPE);
	
	Iterable<Formation> findByCodeAPEsLike(String codeAPE);

	Iterable<Formation> findByPublicAuditeurAndCouvertureGeoAndIntituleOriginalTextSansAccent(String allPub, String allRegion,
			String intitule);

	Iterable<Formation> findByPublicAuditeurAndIntitule(String id, String intitule);
	
	
	
	
	

	

}
