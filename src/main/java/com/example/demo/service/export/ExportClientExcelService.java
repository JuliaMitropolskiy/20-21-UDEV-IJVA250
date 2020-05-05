package com.example.demo.service.export;

import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDate;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.ClientDto;
import com.example.demo.service.ClientService;

@Service
public class ExportClientExcelService {
	
	@Autowired
	private ClientService clientService;

    public void exportAll(OutputStream outputStream) throws IOException {
        List<ClientDto> allClients = clientService.findAllClients();

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Clients");

        Row headerRow = sheet.createRow(0);
        String[] headerLabel = {"Nom", "Prenom", "Age"};
        for (int i = 0; i < 3; i++) {
        	Cell cell = headerRow.createCell(i);
            cell.setCellValue(headerLabel[i]);
        }
        
       
        for (int i = 0; i < allClients.size(); i++) {
        	
        	Row row = sheet.createRow(i+1);
        	
        	ClientDto c = allClients.get(i);
        	int age = LocalDate.now().compareTo(c.getDateNaissance());
        	String[] donneesClient = {c.getNom(), c.getPrenom(), ""+age+" ans"};
        	
        	for (int j = 0; j < donneesClient.length; j++) {
            	Cell cell = row.createCell(j);
                cell.setCellValue(donneesClient[j]);
            }
        	
        }
        

        workbook.write(outputStream);
        workbook.close();
    }

}
