package com.example.demo.service.export;

import java.io.PrintWriter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.ArticleDto;
import com.example.demo.service.ArticleService;

@Service
public class ExportArticleCSVService {

    @Autowired
    private ArticleService articleService;

    public void exportAll(PrintWriter writer) {
        //génération d'un fichier CSV exemples avec 2 colonnes et 4 lignes
        writer.println("Libelle;Prix");

        List<ArticleDto> allArticles = articleService.findAll();
        for (ArticleDto article : allArticles) {
            writer.println(article.getLibelle() + ";" + article.getPrix());
        }
    }

}
