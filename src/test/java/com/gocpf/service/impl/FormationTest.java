package com.gocpf.service.impl;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gocpf.entities.Formation;
import com.gocpf.repository.AbstractTest;
import com.gocpf.repository.FormationRepository;


/**
 * Created by kodjovi1 on 22/11/2015.
 */

@RunWith(SpringJUnit4ClassRunner.class)
public class FormationTest extends AbstractTest {
	
	
	
	@Autowired
	private FormationRepository formationRepository;
	
	 @Autowired
	    private ElasticsearchTemplate elasticsearchTemplate;
	
//	@Before
//	public void init(){
//		elasticsearchTemplate.deleteIndex(Formation.class);
//		elasticsearchTemplate.createIndex(Formation.class);
//		elasticsearchTemplate.putMapping(Formation.class);
//		elasticsearchTemplate.refresh(Formation.class, true);
//	}
	 @Test
	 public void testfindByPublicAuditeurAndIntituleLike(){
		 
		 String intitule="info*";
		String pub="Tout Public";
		assertTrue(formationRepository.findByPublicAuditeurAndIntituleLike(pub, intitule).size()>0);
		 
	 }

	@Ignore 
    @Test
    public void testFormationAlsaceDemandeurEmploi(){
        ObjectMapper objectMapper = new ObjectMapper();
        List<Formation> formations = new ArrayList<Formation>();
        try {
            formations=objectMapper.readValue(new File("/Users/kodjovi1/Downloads/spring-boot-master/spring-boot-samples/spring-boot-sample-data-elasticsearch/src/main/resources/COPAREF_Alsace_Demandeur_Emploi_20151109.json")
                    , new TypeReference<List<Formation>>(){});
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<Formation> formationsRes = new ArrayList<Formation>();

Long i=0L;

        for (Formation formation :formations){
            formation.setId(String.valueOf(++i));
            formation.setCouvertureGeo(String.valueOf(1));
            formationsRes.add(formation);

        }
        
        
        formationRepository.save(formationsRes);
        
        


        try {
            objectMapper.writeValue(new File("/Users/kodjovi1/Downloads/spring-boot-master/spring-boot-samples/spring-boot-sample-data-elasticsearch/src/main/resources/COPAREF_Alsace_Demandeur_Emploi_Final.json"),formationsRes);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

     @Test
    public void testFormationNationalToutPublic(){
        ObjectMapper objectMapper = new ObjectMapper();
        List<Formation> formations = new ArrayList<Formation>();
        try {
            formations=objectMapper.readValue(new File("/Users/kodjovi1/Downloads/spring-boot-master/spring-boot-samples/spring-boot-sample-data-elasticsearch/src/main/resources/COPANEF_Comite_paritaire_interprofessionnel_national_pour_l_emploi_et_la_formation_Tout_public_20151116.json")
                    , new TypeReference<List<Formation>>(){});
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<Formation> formationsRes = new ArrayList<Formation>();

        Long i=707L;

        for (Formation formation :formations){
            formation.setId(String.valueOf(++i));
            formation.setCouvertureGeo(String.valueOf(23));

            formationsRes.add(formation);

        }


        try {
            objectMapper.writeValue(new File("/Users/kodjovi1/Downloads/spring-boot-master/spring-boot-samples/spring-boot-sample-data-elasticsearch/src/main/resources/COPANEF_Comite_paritaire_interprofessionnel_national_pour_l_emploi_et_la_formation_Tout_public_Final.json"),
                    formationsRes);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
