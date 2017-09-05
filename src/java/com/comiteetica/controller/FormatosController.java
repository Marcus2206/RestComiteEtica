/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comiteetica.controller;

import word.api.interfaces.IDocument;
import word.w2004.Document2004;
import word.w2004.Document2004.Encoding;
import word.w2004.elements.BreakLine;
import word.w2004.elements.Paragraph;
import word.w2004.elements.ParagraphPiece;
import word.w2004.style.ParagraphPieceStyle;

/**
 *
 * @author rasec
 */
public class FormatosController {

    public void GenerarAprobacion() {
        IDocument myDoc = new Document2004();
        // myDoc.setPageOrientationLandscape();
        // default is Portrait be can be changed.
        myDoc.encoding(Document2004.Encoding.UTF_8); //or ISO8859-1. Default is UTF-8

        ParagraphPiece paragraphPiece = ParagraphPiece.with("asdasd");
//        if(Style="bold")
        paragraphPiece.withStyle().bold().italic();
        
    }
}
