
package com.gocpf.repository;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Optional;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.DefaultResourceLoader;

public class UtilityTest {
	
	private static final Logger LOG =LoggerFactory.getLogger(UtilityTest.class);
	
	private static final String DIR_CSV = "/Users/kodjovi1/Desktop/CPF/";
	
	private static final String FILE_CSV = "liste-cpf-2016.csv";
	
	private static final String REGION_JSON = "regions.json";
	
	
	@Test
	public void testOpt(){
		Optional<String> opt=Optional.of(REGION_JSON);
		
		System.out.println(opt.orElse("Null is not a value"));
		
	}

	
	
	@Test
	public void testM() throws IOException{
		ClassPathResource classPathResource = new ClassPathResource(REGION_JSON);

        InputStream inputStream = classPathResource.getInputStream();
        File somethingFile = File.createTempFile(FILE_CSV.split("\\.")[0], "."+FILE_CSV.split("\\.")[1]);
        try {
            FileUtils.copyInputStreamToFile(inputStream, somethingFile);
        } finally {
            IOUtils.closeQuietly(inputStream);
        }
        
        System.out.println(""+somethingFile.getName());
		assertTrue(somethingFile!=null);
	}

	
	
	@Test
	public void testLoad() throws IOException{
		System.out.println(FILE_CSV.split("\\.")[1]);
		final DefaultResourceLoader loader = new DefaultResourceLoader();
		File  src = loader.getResource(FILE_CSV).getFile();
		
	    System.out.println(src.exists());
	}
	
	
	@Test
	public void testEnum(){
		String test ="Niveau III (Bac+2)";
		LOG.info("" +StringUtils.split(test, "(")[0]);
	}
	
	
	
	
	
	@Test
	public void testTo(){
		String subjectString = "Certification Microsoft administration d'une base de données SQL Server 2012/2014";
		String resultString = subjectString.replaceAll("[^\\p{L}\\p{Nd}]+", " ");
		
		System.out.println("Sans special character :"+resultString);
		
		String text = "français";
		
		System.out.println("----->"+StringUtils.stripAccents(text));

		
		
		
//		 Iterable<String> it2=new ArrayList<String>();
//		 Iterables.concat(it1,it2);

		 
		 
//		String statut="DEMANDEUR";
//		
//		
//		
//		switch (statut) {
//		case "DEMANDEUR":
//			System.out.println("Yes DE");
//			break;
//			
//		case "LASTD":
//			System.out.println(""+"LAST");
//
//		default:
//			break;
//		}
//		List<String> ts = new ArrayList<String>();
//		List<String> tv=new ArrayList<>();
//		
//		ts.add(DIR_CSV);
//		Iterable<String> res =Iterables.concat(ts,tv);
//		 int i =0;
//		ts.forEach(t->{StringBuilder builder= new StringBuilder();t=t.concat(String.valueOf(i));System.out.println("====="+t);});
//		
//		assertTrue(!Lists.newArrayList(res).isEmpty());
	}
	
	
	
	


	
	@Test
	public void testString(){
//		String [] t = StringUtils.split("Ing�nieur dipl�m� de l'�cole nationale sup�rieure d'ing�nieurs en informatique");
//		System.out.println(t[2]);
		
		File dir = new File(DIR_CSV);
		assertTrue(dir.isDirectory());
		
		for(File file:dir.listFiles()){
			LOG.info(file.getName());
		}
		
//		LOG.info(org.apache.commons.lang3.StringUtils.stripAccents("Ingénieur dipl�m� de l'�cole nationale sup�rieure d'ing�nieurs en informatique"));
		
	}
	
	@Test
	public void testDate() throws ParseException{
		
		assertTrue(StringUtils.contains("copanef (comite paritaire interprofessionnel national pour l'emploi et la formation)", "copanef"));
		SimpleDateFormat dateFormat= new SimpleDateFormat("dd/MM/yyyy HH:mm");
		
		LOG.info(""+dateFormat.parse("01/01/2015".concat(StringUtils.SPACE).concat("00:00")));
		
	}

}
