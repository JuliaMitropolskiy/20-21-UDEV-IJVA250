package com.example.demo.service.export;

import static java.util.stream.Collectors.toList;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.ClientDto;
import com.example.demo.dto.FactureDto;
import com.example.demo.service.ClientService;
import com.example.demo.service.FactureService;

@Service
public class ExportFacturesExcelService {
	
	@Autowired
	private FactureService factureService;
	
	@Autowired
	private ClientService clientService;
	
	
	public void exportAll(OutputStream outputStream) throws IOException {
		List<FactureDto> factures = factureService.findAllFactures(); 
		
		List<Long> clientsIdUnique = factures
	                .stream()
	                .map(f -> f.getClient().getId())
	                .distinct()
	                .collect(toList());
	    
		Workbook workbook = new XSSFWorkbook();
		
		for (Long id : clientsIdUnique) {
			ClientDto c = clientService.findById(id);
			Sheet sheet = workbook.createSheet(c.getNom() + " " + c.getPrenom());
			
			String[] labels = {"Nom", "Prénom", "Date de naissance"};
			
			for (int i = 0; i<3; i++) {
			
				Row row = sheet.createRow(i);
		        Cell cellLabel = row.createCell(0);
		        cellLabel.setCellValue(labels[i]);
		        Cell cellData = row.createCell(1);
		        switch (i) {
			        case 0 : 
			        	cellData.setCellValue(c.getNom());
			        	break;
			        case 1 :
			        	cellData.setCellValue(c.getPrenom());
			        	break;
			        case 2 :
			        	cellData.setCellValue(c.getDateNaissance().toString());
			        	break;
		        }
			}
		}
		
		workbook.write(outputStream);
        workbook.close();
	}

}
