package com.gocpf.service.impl;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gocpf.repository.FormationRepository;
import com.gocpf.service.DataConversionService;
@Component
public class DataConversionServiceImpl implements DataConversionService {
	
	@Autowired
	private FormationRepository formationRepository;
	private Character delim = ';';
	
	private static final String FILE_CSV = "/Users/kodjovi1/Desktop/CPF/ListingFormations/csv/Certif_publies_CPF_26-10-15.csv";
	
	private static final String[] FILE_HEADER = { "organismeEditeur", "niveau", "intitule", "certificateur", "codeRNCP",
			"codeInventaire", "codeCertifInfo", "codeCPF", "public" };
	
	private static final String[] FILE_HEADER_APE = { "code", "intitule"};

	@Override
	public List<CSVRecord> convertCSVFileToRecod (String file, String[] header) {
		
		
		CSVFormat csvFormat = CSVFormat.DEFAULT.withHeader(header).withDelimiter(delim);
		CSVParser csvFileParser = null;
		FileReader fileReader = null;
		List<CSVRecord> csvRecords = null;

		try {
			fileReader = new FileReader(file);
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
		return csvRecords;
		
		
	}
}

