package com.example.demo.service;

import com.example.demo.dto.ArticleDto;
import com.example.demo.dto.ClientDto;
import com.example.demo.dto.FactureDto;
import com.example.demo.dto.LigneFactureDto;
import com.example.demo.entity.Article;
import com.example.demo.entity.Client;
import com.example.demo.entity.Facture;
import com.example.demo.entity.LigneFacture;
import com.example.demo.repository.FactureRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * Service contenant les actions métiers liées aux factures.
 */
@Service
@Transactional
public class FactureService {

    private FactureRepository factureRepository;

    public FactureService(FactureRepository factureRepository) {
        this.factureRepository = factureRepository;
    }

    public List<FactureDto> findAllFactures() {
        // Transformation d'une liste de Facture en FactureDto
        return factureRepository
                .findAll()
                .stream()
                .map(f -> factureDto(clientDto(f.getClient()), f))
                .collect(toList());
    }
    

    public FactureDto findById(Long id) {
        // Transformation d'une Facture en FactureDto
        Facture facture = factureRepository.getOne(id);
        return factureDto(clientDto(facture.getClient()), facture);
    }


    private ClientDto clientDto(Client client) {
        return new ClientDto(client.getId(), client.getNom(), client.getPrenom(), client.getDateNaissance());
    }

    private FactureDto factureDto(ClientDto clientDto, Facture f) {
        List<LigneFactureDto> ligneFactureDtos = f.getLigneFactures()
                .stream()
                .map(lf -> ligneFactureDto(lf))
                .collect(toList());
        return new FactureDto(f.getId(), clientDto, ligneFactureDtos);
    }

    private LigneFactureDto ligneFactureDto(LigneFacture lf) {
        Article article = lf.getArticle();
        return new LigneFactureDto(new ArticleDto(article.getId(), article.getLibelle(), article.getPrix()), lf.getQuantite());
    }
}
