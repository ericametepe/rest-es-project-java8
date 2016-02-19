package com.gocpf.web.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;

import javax.inject.Inject;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.gocpf.entities.Activite;
import com.gocpf.repository.ActiviteRepository;
import com.google.common.collect.Lists;
import com.google.common.io.Files;
/**
 * http://www.rouen.cci.fr/outils/ape/Naf_Liste.asp?code=2573A
 * 
 * Table de passage code APE :http://travail-emploi.gouv.fr/dialogue-social/negociation-collective/conventions-collectives/article/table-de-passage-entre-secteur-d-activite-et-convention-collective
 * Nombre de lignes : 4846
 * @author kodjovi1
 *
 */
@RestController
@RequestMapping(value="/service")
public class ActiviteResource {
	
	private static final String FILE_CSV_APE = "/Users/kodjovi1/Desktop/CPF/branchespro/Table_de_passage_code_APE_IDCC_2012.csv";
	
	private static final String FILE_PDF_APE = "/Users/kodjovi1/Desktop/CPF/liste-code-APE.pdf";


	
	private static final String[] FILE_HEADER = { "codeAPE", "intitule", "effectifSalarie", "codeIDCC",
			"intituleCodeIDCC", "couvertureSalarie" };
	
	
	private static final Character delim = ';';


	
	@Inject
	private ActiviteRepository activiteRepository;
	
	@RequestMapping(value="/activites", method=RequestMethod.GET)
	public ResponseEntity<Iterable<Activite>> findAll() {
		return new ResponseEntity<Iterable<Activite>>(activiteRepository.findAll(), HttpStatus.OK);
	}
	
	
	@RequestMapping(value="/activites/search/{intitule}", method=RequestMethod.GET)
	public ResponseEntity<Iterable<Activite>> findByCode(@PathVariable String intitule){
		return new ResponseEntity<Iterable<Activite>>(activiteRepository.findByIntitule(intitule),HttpStatus.OK);
		
	}
	
	
	@RequestMapping(value="/activites/{code}", method=RequestMethod.GET)
	public ResponseEntity<Iterable<Activite>> findByIntitule(@PathVariable String code){
		return new ResponseEntity<Iterable<Activite>>(activiteRepository.findByCodeAPE(code),HttpStatus.OK);
		
	}
	
	
	@RequestMapping(value="/activites/populate", method=RequestMethod.POST)
	public ResponseEntity<Void> populate() throws URISyntaxException {
		

		CSVFormat csvFormat = CSVFormat.EXCEL.withHeader(FILE_HEADER).withDelimiter(delim);
		CSVParser csvFileParser = null;
		FileReader fileReader = null;
		List<CSVRecord> csvRecords = null;
		
		

		try {
			fileReader = new FileReader(new File(FILE_CSV_APE));
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
	
		Set<Activite> activites = new HashSet<Activite>();
		
		csvRecords.stream().forEach(c ->{
			if (c!=null){
		
		Activite activite = new Activite();
		activite.setId(String.valueOf(c.getRecordNumber()));
		activite.setCodeAPE(c.get("codeAPE"));
		activite.setIntitule(c.get("intitule"));
		activite.setCodeIDCC(c.get("codeIDCC"));
		activite.setIntituleCodeIDCC(c.get("intituleCodeIDCC"));
		activite.setCouvertureSalarie(c.get("couvertureSalarie"));
		activite.setEffectifSalarie(c.get("effectifSalarie"));
		
		activites.add(activite);}});
		
		activiteRepository.deleteAll();
		Iterable<Activite> acts=activiteRepository.save(activites);
		
		
		return ResponseEntity.created(new URI("/activites/" + Lists.newArrayList(acts).size())).build();
		
	}
	
	
	
	public ResponseEntity<Void> initAll() throws URISyntaxException {
		List<Activite> activites = new ArrayList<Activite>();
        int i=0;
        List<String> lines=null;
		try {
			lines = Files.readLines(new File(FILE_PDF_APE), Charset.defaultCharset());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		for (String line : lines) {
			java.util.regex.Pattern pattern = java.util.regex.Pattern.compile("[0-9]{4}[A-Z]{1}");
			
			Matcher matcher = pattern.matcher(line);
			if (matcher.find())
			{
				String code = matcher.group(0);
				Activite activite = new Activite();
				activite.setId(String.valueOf(++i));
				activite.setCodeAPE(code);
				activite.setIntitule(StringUtils.stripToNull(StringUtils.remove(line,code)));
				
				activites.add(activite);
			}
		}
		
		Iterable<Activite> acts=activiteRepository.save(activites);
		
		return ResponseEntity.created(new URI("/activites/" + Lists.newArrayList(acts).size())).build();
		
	}

}
