/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comiteetica.controller;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author rasec
 */
@Controller
@RequestMapping("/PDF")
public class PdfExportController {

//    String ruta="C:/Repositorio/Temp/";
//    
//    @RequestMapping(value = "/HojaRuta/{idCorrespondencia}", method = RequestMethod.POST)
//    public void createPdf(HttpServletRequest httpServletRequest,
//            HttpServletResponse httpServletResponse, @PathVariable("idCorrespondencia") String idCorrespondencia) throws DocumentException, IOException {
//        // step 1
//        Document document = new Document();
//        // step 2
//        PdfWriter.getInstance(document, new FileOutputStream(idCorrespondencia+".pdf"));
//        // step 3
//        document.open();
//        // step 4
//        document.add(new Paragraph("Hello World!"));
//        // step 5
//        document.close();
//    }

}
