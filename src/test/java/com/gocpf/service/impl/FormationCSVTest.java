package com.gocpf.service.impl;

import static org.junit.Assert.assertTrue;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import javax.inject.Inject;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.gocpf.entities.Activite;
import com.gocpf.entities.Formation;
import com.gocpf.entities.Intitule;
import com.gocpf.repository.AbstractTest;
import com.gocpf.repository.ActiviteRepository;
import com.gocpf.repository.FormationRepository;

@RunWith(SpringJUnit4ClassRunner.class)
public class FormationCSVTest extends AbstractTest {

	@Autowired
	private FormationRepository formationRepository;
	
	@Inject
	
	private ActiviteRepository activiteRepository;
	
	private Character delim = ';';
	
	private static final String FILE_CSV = "/Users/kodjovi1/Desktop/CPF/ListingFormations/csv/Certif_publies_CPF_26-10-15.csv";
	
	private static final String FILE_CSV_APE = "/Users/kodjovi1/Desktop/CPF/liste-code-APE.csv";
	
	private static final String FILE_PDF_APE = "/Users/kodjovi1/Desktop/liste-code-APE.pdf";

	
	

	
	private static final String[] FILE_HEADER = { "organismeEditeur", "niveau", "intitule", "certificateur", "codeRNCP",
			"codeInventaire", "codeCertifInfo", "codeCPF", "public" };
	
	private static final String[] FILE_HEADER_APE = { "codeAPE", "intitule"};
	
	
	

	/*
	 * $$$$$$$CSVRecord [comment=null, mapping={organismeEditeur=0, niveau=1,
	 * certificateur=2, codeRNCP=3, codeInventaire=4, codeCertifInfo=5,
	 * codeCPF=6, public=7},
	 * 
	 */
//
//	@Before
//	public void initTest() {
//		elasticsearchTemplate.deleteIndex(Formation.class);
//		elasticsearchTemplate.createIndex(Formation.class);
//		elasticsearchTemplate.putMapping(Formation.class);
//		elasticsearchTemplate.refresh(Formation.class, true);
//
//	}
	

		
		
	
	
	@Test
	public void insertAPE(){
		CSVFormat csvFormat = CSVFormat.EXCEL.withHeader(FILE_HEADER_APE).withDelimiter(delim);
		CSVParser csvFileParser = null;
		FileReader fileReader = null;
		List<CSVRecord> csvRecords = null;

		try {
			fileReader = new FileReader(FILE_CSV_APE);
			try {
				csvFileParser = new CSVParser(fileReader, csvFormat);
				csvRecords = csvFileParser.getRecords();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	

		List<Activite> activites = new ArrayList<Activite>();
        int i=0;
		for (CSVRecord csvRecord : csvRecords) {
			if(csvRecord.getRecordNumber()>1){
				Activite activite = new Activite();
				activite.setId(String.valueOf(++i));
				activite.setCodeAPE(csvRecord.get("codeAPE"));
				activite.setIntitule(csvRecord.get("intitule"));
				activites.add(activite);
				
				
			}

		}
		
		activiteRepository.save(activites);

		
	}
	

	@Test
	public void testCSV() {

		CSVFormat csvFormat = CSVFormat.EXCEL.withHeader(FILE_HEADER).withDelimiter(delim);
		CSVParser csvFileParser = null;
		FileReader fileReader = null;
		List<CSVRecord> csvRecords = null;

		try {
			fileReader = new FileReader(FILE_CSV);
			try {
				csvFileParser = new CSVParser(fileReader, csvFormat);
				csvRecords = csvFileParser.getRecords();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		// for(int i=0;i<csvRecords.size();i++){
		// CSVRecord csvRecord = csvRecords.get(i);
		// System.out.println("==========" +csvRecord.toMap());
		// }

		List<Formation> formations = new ArrayList<Formation>();

		for (CSVRecord csvRecord : csvRecords) {
			if(csvRecord.getRecordNumber()>1){
				Formation formation = new Formation();
				formation.setId(String.valueOf(csvRecord.getRecordNumber()));
				formation.setIntitule(new Intitule(csvRecord.get("intitule"),csvRecord.get("intitule")));
				formation.setNiveau(csvRecord.get("niveau"));
				formation.setOrganismecertificateur(csvRecord.get("certificateur"));
				formation.setCodeRNCP(csvRecord.get("codeRNCP"));
				formation.setCodeInventaire(csvRecord.get("codeInventaire"));
				formation.setCodeCertifInfo(csvRecord.get("codeCertifInfo"));
				formation.setCodeCPF(csvRecord.get("codeCPF"));
				formation.setPublicAuditeur(csvRecord.get("public"));
				formation.setOrganismeEditeur(csvRecord.get("organismeEditeur"));
				
				formations.add(formation);
			}

		}
		
		formationRepository.save(formations);

	}
	
	@Test
	public void testFindByPublicAuditeurToutPublic(){
		String pub ="Tout Public";
		String pub1="DE";
		String pub2="Salari�";
		assertTrue(formationRepository.findByPublicAuditeur(pub).size()>1);
		assertTrue(formationRepository.findByPublicAuditeur(pub1).size()>1);
		assertTrue(formationRepository.findByPublicAuditeur(pub2).size()>1);
	}
	/**
	 * @param
	 */
	@Test
	public void testFindByPublicAuditeurPublicSal(){
		
		String pub2="Salari�";
		List<Formation> formationsSals = formationRepository.findByPublicAuditeur(pub2);
		
		for(Formation formation:formationsSals){
				formation.setPublicAuditeur("Salarie");
				formationRepository.save(formation);
			}
		}
		
	}



	
	

