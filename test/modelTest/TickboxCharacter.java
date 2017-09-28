///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package modelTest;
//
///**
// *
// * @author rasec
// */
//import com.itextpdf.text.Chunk;
//import com.itextpdf.text.Document;
//import com.itextpdf.text.DocumentException;
//import com.itextpdf.text.Font;
//import com.itextpdf.text.Paragraph;
//import com.itextpdf.text.pdf.PdfWriter;
//
//import java.io.File;
//import java.io.FileOutputStream;
//import java.io.IOException;
////import sandbox.WrapToTest;
//
///**
// *
// * @author Bruno Lowagie (iText Software)
// */
////@WrapToTest
//public class TickboxCharacter {
//
//    public static final String DEST = "C:/Repositorio/tickbox_character.pdf";
//
//    public static void main(String[] args) throws IOException, DocumentException {
//        File file = new File(DEST);
//        file.getParentFile().mkdirs();
//        new TickboxCharacter().createPdf(DEST);
//    }
//
//    public void createPdf(String dest) throws IOException, DocumentException {
//        Document document = new Document();
//        PdfWriter.getInstance(document, new FileOutputStream(dest));
//        document.open();
//        Paragraph p = new Paragraph("This is a tick box character: ");
//        Font zapfdingbats = new Font(Font.FontFamily.ZAPFDINGBATS, 14);
//        Chunk chunk = new Chunk("o", zapfdingbats);
//        p.add(chunk);
//        document.add(p);
//        document.close();
//
//    }
//}
