package com.example.demo.service.export;

import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.ClientDto;
import com.example.demo.service.ClientService;

@Service
public class ExportClientCSVService {
	
	@Autowired
	private ClientService clientService;
	
	public void exportAll(PrintWriter writer) {
        writer.println("Nom;Prénom;Age");

        List<ClientDto> allClients = clientService.findAllClients();
        for (ClientDto client : allClients) {
        	int age = LocalDate.now().compareTo(client.getDateNaissance());
            writer.println(client.getNom() + ";" + client.getPrenom() + ";" + age);
        }
    }

}
