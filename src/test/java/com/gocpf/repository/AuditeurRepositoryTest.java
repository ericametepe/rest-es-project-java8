package com.gocpf.repository;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;

import com.gocpf.entities.Auditeur;
import com.gocpf.repository.AuditeurRepository;

public class AuditeurRepositoryTest extends AbstractTest {
	
	@Autowired
	private AuditeurRepository auditeurRepository ;
	@Autowired
	private ElasticsearchTemplate elasticsearchTemplate ;

	@Before
	public void init() {
		elasticsearchTemplate.deleteType("gocpf", "auditeur");;
		
	}

	@Test
	public void testSaveS() {
	Auditeur audi = new Auditeur("1", "Tout Public", "description") ;
	Auditeur auditeurS = auditeurRepository.save(audi );
	assertTrue(auditeurS.getId().equals("1"));
	}

	@Test
	public void testSaveIterableOfS() {
	}

	@Test
	public void testFindOne() {
	}

}
