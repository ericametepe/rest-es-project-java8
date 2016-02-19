package com.gocpf.service;

import java.util.List;

import org.apache.commons.csv.CSVRecord;

public interface DataConversionService {
	
	
	 List<CSVRecord> convertCSVFileToRecod(String file, String[] header) ;
	
	

}
