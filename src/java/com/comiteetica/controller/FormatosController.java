/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comiteetica.controller;

import com.comiteetica.hibernate.model.Correspondencia;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import word.api.interfaces.IDocument;
import word.w2004.Document2004;
import word.w2004.elements.BreakLine;
import word.w2004.elements.Paragraph;
import word.w2004.elements.ParagraphPiece;
import word.w2004.style.Font;
import word.w2004.style.ParagraphStyle;

/**
 *
 * @author rasec
 */
public class FormatosController {

    public void GenerarAprobacion(List<Object> detalleFormato, String Nombre) throws UnsupportedEncodingException {
        IDocument myDoc = new Document2004();
        // myDoc.setPageOrientationLandscape();
        // default is Portrait be can be changed.
        myDoc.encoding(Document2004.Encoding.ISO8859_1); //or ISO8859-1. Default is UTF-8
        for (int x = 0; x < detalleFormato.size(); x++) {
            ArrayList linea = (ArrayList) detalleFormato.get(x);
            if (null != linea.get(3)) {
                switch (linea.get(3).toString()) {
                    case "BreakLine": {
                        myDoc.addEle(BreakLine.times(1).create());
                    }
                    break;
                    case "List": {

                    }
                    break;
                    case "linea": {
                        ParagraphPiece lineaParrafo = ParagraphPiece.with(linea.get(0).toString());
                        lineaParrafo.withStyle().font(Font.CALIBRI);
                        lineaParrafo.withStyle().fontSize("11");
                        if (null != linea.get(1)) {
                            StringTokenizer stilo = new StringTokenizer(linea.get(1).toString(), "-");
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
                        if (null != linea.get(2)) {
                            switch (linea.get(2).toString()) {
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
        File fileObj = new File(Nombre);

        PrintWriter writer = null;
        try {
            writer = new PrintWriter(fileObj);
        } catch (FileNotFoundException e) {
        }

        writer.println(myWord);
        writer.close();

    }

    public void GenerarActa(List<Object> detalleFormato, String Nombre, List<Correspondencia> list) throws UnsupportedEncodingException {
        IDocument myDoc = new Document2004();
        // myDoc.setPageOrientationLandscape();
        // default is Portrait be can be changed.
        myDoc.encoding(Document2004.Encoding.ISO8859_1); //or ISO8859-1. Default is UTF-8
        for (int x = 0; x < detalleFormato.size(); x++) {
            ArrayList linea = (ArrayList) detalleFormato.get(x);
            if (null != linea.get(3)) {
                switch (linea.get(3).toString()) {
                    case "BreakLine": {
                        myDoc.addEle(BreakLine.times(1).create());
                    }
                    break;
                    case "List": {

                    }
                    break;
                    case "linea": {
                        ParagraphPiece lineaParrafo = ParagraphPiece.with(linea.get(0).toString());
                        lineaParrafo.withStyle().font(Font.CALIBRI);
                        lineaParrafo.withStyle().fontSize("11");
                        if (null != linea.get(1)) {
                            StringTokenizer stilo = new StringTokenizer(linea.get(1).toString(), "-");
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
                        if (null != linea.get(2)) {
                            switch (linea.get(2).toString()) {
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

        for (int x = 0; x < list.size(); x++) {
            Correspondencia corre = list.get(x);
            String mes = "";

            switch (corre.getFechaCorrespondencia().getMonth()) {
                case 0:
                    mes = "enero";
                    break;
                case 1:
                    mes = "febrero";
                    break;
                case 2:
                    mes = "marzo";
                    break;
                case 3:
                    mes = "abril";
                    break;
                case 4:
                    mes = "mayo";
                    break;
                case 5:
                    mes = "junio";
                    break;
                case 6:
                    mes = "julio";
                    break;
                case 7:
                    mes = "agosto";
                    break;
                case 8:
                    mes = "setiembre";
                    break;
                case 9:
                    mes = "octubre";
                    break;
                case 10:
                    mes = "noviembre";
                    break;
                case 11:
                    mes = "diciembre";
                    break;
            }

            String cadFecha = corre.getFechaCorrespondencia().getDate() + " de " + mes;
            ParagraphPiece lineaParrafo
                    = ParagraphPiece.with((x + 1) + ".- "
                            + (corre.getRegistro().getInvestigador().getNombres() == null ? "":corre.getRegistro().getInvestigador().getNombres()) + ", carta recibida el  "
                            + cadFecha + ", nos hace llegar el informe de "
                            + (corre.getOtro() == null ? "":corre.getOtro()) + " "
                            + corre.getProtocolo() + "."
                    );

            lineaParrafo.withStyle().font(Font.CALIBRI);
            lineaParrafo.withStyle().fontSize("11");
            Paragraph parrafo = new Paragraph(lineaParrafo);
            ParagraphPiece lineaParrafo2 = ParagraphPiece.with("   " + corre.getParamDistribucion());
            myDoc.addEle(parrafo.create());
            myDoc.addEle(BreakLine.times(1).create());

        }

        String myWord = myDoc.getContent();
        File fileObj = new File(Nombre);

        PrintWriter writer = null;
        try {
            writer = new PrintWriter(fileObj);
        } catch (FileNotFoundException e) {
        }

        writer.println(myWord);
        writer.close();

    }

    public void GenerarActaCierre(List<Object> detalleFormato, String Nombre, List<Correspondencia> list) throws UnsupportedEncodingException {
        IDocument myDoc = new Document2004();
        // myDoc.setPageOrientationLandscape();
        // default is Portrait be can be changed.
        myDoc.encoding(Document2004.Encoding.ISO8859_1); //or ISO8859-1. Default is UTF-8
        for (int x = 0; x < detalleFormato.size(); x++) {
            ArrayList linea = (ArrayList) detalleFormato.get(x);
            if (null != linea.get(3)) {
                switch (linea.get(3).toString()) {
                    case "BreakLine": {
                        myDoc.addEle(BreakLine.times(1).create());
                    }
                    break;
                    case "List": {

                    }
                    break;
                    case "linea": {
                        ParagraphPiece lineaParrafo = ParagraphPiece.with(linea.get(0).toString());
                        lineaParrafo.withStyle().font(Font.CALIBRI);
                        lineaParrafo.withStyle().fontSize("11");
                        if (null != linea.get(1)) {
                            StringTokenizer stilo = new StringTokenizer(linea.get(1).toString(), "-");
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
                        if (null != linea.get(2)) {
                            switch (linea.get(2).toString()) {
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
    }

}
