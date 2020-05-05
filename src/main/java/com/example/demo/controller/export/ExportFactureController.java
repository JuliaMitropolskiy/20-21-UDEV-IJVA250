package com.example.demo.controller.export;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.service.export.ExportFacturesExcelService;

@Controller
@RequestMapping("export")
public class ExportFactureController {
	
	@Autowired
	private ExportFacturesExcelService exportFacturesExcelService;
	
	@GetMapping("/factures/xlsx")
    public void facturesXlsx(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "attachment; filename=\"export-factures.xlsx\"");
        OutputStream outputStream = response.getOutputStream();
        exportFacturesExcelService.exportAll(outputStream);
    }

}
