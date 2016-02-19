package com.gocpf.repository;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gocpf.entities.CouvertureGeo;
import com.google.common.collect.Lists;
@RunWith(SpringJUnit4ClassRunner.class)
public class CouvertureGeoRepositoryTest extends AbstractTest {
	private static final String REGION_FILE = "/Users/kodjovi1/Documents/workspace/cpf-yo-project/regions.json";
	
	@Autowired
	private CouvertureGeoRepository couvertureGeoRepository ;
	
	@Autowired
	private ElasticsearchTemplate elasticsearchTemplate ;
	
//	@Before
	public void initTest(){
	
	}

	@Before
	public void testSaveIterableOfS() {
		
		elasticsearchTemplate.deleteIndex(CouvertureGeo.class);
		elasticsearchTemplate.createIndex(CouvertureGeo.class);
		elasticsearchTemplate.putMapping(CouvertureGeo.class);
		elasticsearchTemplate.refresh(CouvertureGeo.class, true);
		
		List<CouvertureGeo>  entities =  new ArrayList<CouvertureGeo>();
		
		ObjectMapper mapper = new ObjectMapper();
		
		try {
			entities=mapper.readValue(new File(REGION_FILE), new TypeReference<List<CouvertureGeo>>(){
			});
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		List<CouvertureGeo> geos =(List<CouvertureGeo>) couvertureGeoRepository.save(entities);
		
		assertTrue("must not be empty",geos.size()>0);
		
	}



	@Test
	public void testFindOne() {
		String id="8";
		
		CouvertureGeo couvertureGeo = couvertureGeoRepository.findOne(id);
		System.out.println("couvertureGeoRepository.findOne(id) :"+couvertureGeo);
		assertNotNull("", couvertureGeo);
	}

	@Test
	public void testFindAll() {
		List<CouvertureGeo> couvs = Lists.newArrayList(couvertureGeoRepository.findAll());
		assertTrue(couvs.size()>0);
	}
	
	
	@Test
	public void updateComite(){
		List<CouvertureGeo> couvs = Lists.newArrayList(couvertureGeoRepository.findAll());
		List<CouvertureGeo> couvsRes= new ArrayList<CouvertureGeo>();
		
		for (CouvertureGeo couvertureGeo:couvs){
			if (StringUtils.equals("National", couvertureGeo.getNom())){
				couvertureGeo.setComite("COPANEF");
				
			}
			else {
				couvertureGeo.setComite("COPAREF ".concat(" ").concat(couvertureGeo.getNom()));
			}
			
			couvsRes.add(couvertureGeo);
		}
		couvertureGeoRepository.save(couvsRes);

		
	}

	@Test
	public void testCount() {
		assertTrue(couvertureGeoRepository.count()==23);
	}

}
