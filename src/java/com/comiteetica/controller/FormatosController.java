/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comiteetica.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.StringTokenizer;
import word.api.interfaces.IDocument;
import word.w2004.Document2004;
import word.w2004.Document2004.Encoding;
import word.w2004.elements.BreakLine;
import word.w2004.elements.Paragraph;
import word.w2004.elements.ParagraphPiece;
import word.w2004.style.Font;
import word.w2004.style.ParagraphPieceStyle;
import word.w2004.style.ParagraphStyle;

/**
 *
 * @author rasec
 */
public class FormatosController {

    public void GenerarAprobacion(String[][] detalleFormato) {
        IDocument myDoc = new Document2004();
        // myDoc.setPageOrientationLandscape();
        // default is Portrait be can be changed.
        myDoc.encoding(Document2004.Encoding.UTF_8); //or ISO8859-1. Default is UTF-8

        for (String[] detalleFormato1 : detalleFormato) {
            if (null != detalleFormato1[2]) {
                switch (detalleFormato1[3]) {
                    case "BreakLine": {
                        myDoc.addEle(BreakLine.times(1).create());
                    }
                    break;
                    case "List": {

                    }
                    break;
                    default: {
                        ParagraphPiece lineaParrafo = ParagraphPiece.with(detalleFormato1[0]);
                        lineaParrafo.withStyle().font(Font.CALIBRI);
                        lineaParrafo.withStyle().fontSize("11");
                        if (null != detalleFormato1[1]) {
                            StringTokenizer stilo = new StringTokenizer(detalleFormato1[1], "-");
                            while (stilo.hasMoreElements()) {
                                switch (stilo.nextToken()) {
                                    case "n":
                                        lineaParrafo.withStyle().bold();
                                        break;
                                    case "c":
                                        lineaParrafo.withStyle().italic();
                                        break;
                                    case "s":
                                        lineaParrafo.withStyle().underline();
                                        break;
                                }
                            }
                        }
                        Paragraph parrafo = new Paragraph(lineaParrafo);
                        if (null != detalleFormato1[2]) {
                            switch (detalleFormato1[2]) {
                                case "izq":
                                    parrafo.withStyle().align(ParagraphStyle.Align.LEFT);
                                    break;
                                case "cen":
                                    parrafo.withStyle().align(ParagraphStyle.Align.CENTER);
                                    break;
                                case "der":
                                    parrafo.withStyle().align(ParagraphStyle.Align.RIGHT);
                                    break;
                                case "jus":
                                    parrafo.withStyle().align(ParagraphStyle.Align.JUSTIFIED);
                                    break;

                                default:
                                    break;
                            }
                        }
                        myDoc.addEle(parrafo.create());
                    }
                    break;
                }
            }
        }
        String myWord = myDoc.getContent();
//        System.out.println("asdasdasdasd: " + myDoc.getContent() + "kokokokokok");

        File fileObj = new File("d:/Repositorio/prueba.doc");

        PrintWriter writer = null;
        try {
            writer = new PrintWriter(fileObj);
        } catch (FileNotFoundException e) {
        }

        writer.println(myWord);
        writer.close();

    }
}
