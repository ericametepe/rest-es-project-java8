package com.gocpf.service;

import java.io.File;
import java.util.List;

import org.apache.commons.csv.CSVRecord;

public interface DataConversionService {
	
	
	 List<CSVRecord> convertCSVFileToRecod(File file, String[] header) ;
	
	

}
