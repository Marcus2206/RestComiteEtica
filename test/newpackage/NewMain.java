/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package newpackage;

import com.comiteetica.controller.FormatosController;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import word.api.interfaces.IDocument;
import word.utils.TestUtils;
import word.utils.Utils;
import word.w2004.Document2004;
import word.w2004.Document2004.Encoding;
import word.w2004.elements.BreakLine;
import word.w2004.elements.Heading1;
import word.w2004.elements.Heading2;
import word.w2004.elements.Heading3;
import word.w2004.elements.HyperLink;
import word.w2004.elements.Image;
import word.w2004.elements.PageBreak;
import word.w2004.elements.Paragraph;
import word.w2004.elements.ParagraphPiece;
import word.w2004.elements.Table;
import word.w2004.elements.tableElements.TableEle;
import word.w2004.style.Font;
import word.w2004.style.HeadingStyle.Align;
import word.w2004.style.ParagraphStyle;

/**
 *
 * @author rasec
 */
public class NewMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        FormatosController controller = new FormatosController();
        String[][] detalleFormato = new String[100][4];

        detalleFormato[0][0] = "Lima,  {Date}";
            detalleFormato[0][1] = "";
            detalleFormato[0][2] = "der";
            detalleFormato[0][3] = "";
            detalleFormato[1][0] = "CE{Correlativo}";
            detalleFormato[1][1] = "";
            detalleFormato[1][2] = "der";
            detalleFormato[1][3] = "";
            detalleFormato[2][3] = "BreakLine";
            detalleFormato[3][3] = "BreakLine";
            detalleFormato[4][3] = "BreakLine";
            detalleFormato[5][3] = "BreakLine";
            detalleFormato[6][0] = "Doctor";
            detalleFormato[6][1] = "";
            detalleFormato[6][2] = "izq";
            detalleFormato[6][3] = "";
            detalleFormato[7][0] = "{investigadorPrincipal}";
            detalleFormato[7][1] = "n";
            detalleFormato[7][2] = "izq";
            detalleFormato[7][3] = "";
            detalleFormato[8][0] = "Investigador Principal";
            detalleFormato[8][1] = "";
            detalleFormato[8][2] = "izq";
            detalleFormato[8][3] = "";
            detalleFormato[9][0] = "{CentroInvestigacion}";
            detalleFormato[9][1] = "";
            detalleFormato[9][2] = "izq";
            detalleFormato[9][3] = "";
            detalleFormato[10][0] = "Clínica Javier Prado";
            detalleFormato[10][1] = "";
            detalleFormato[10][2] = "izq";
            detalleFormato[10][3] = "BreakLine";
            detalleFormato[11][0] = "Presente.-";
            detalleFormato[11][1] = "s";
            detalleFormato[11][2] = "izq";
            detalleFormato[11][3] = "";
            detalleFormato[12][3] = "BreakLine";
            detalleFormato[13][0] = "Ref: {idProtocolo}  \"{tituloProtocolo}\"";
            detalleFormato[13][1] = "n";
            detalleFormato[13][2] = "izq";
            detalleFormato[13][3] = "";
            detalleFormato[14][3] = "BreakLine";
            detalleFormato[15][0] = "De nuestra consideración:";
            detalleFormato[15][1] = "";
            detalleFormato[15][2] = "izq";
            detalleFormato[15][3] = "";
            detalleFormato[16][3] = "BreakLine";
            detalleFormato[17][0] = "Es grato dirigirnos a usted a fin de hacer de su conocimiento que el Comité de Ética ha revisado y evaluado el consentimiento informado, en el cual se han encontrado las siguientes observaciones que deberán ser subsanadas para su posterior aprobación:";
            detalleFormato[17][1] = "";
            detalleFormato[17][2] = "izq";
            detalleFormato[17][3] = "";
            detalleFormato[18][0] = "Se hace las siguientes observaciones a los FCI ";
            detalleFormato[18][1] = "n-s";
            detalleFormato[18][2] = "izq";
            detalleFormato[18][3] = "";
            detalleFormato[19][0] = "- ";
            detalleFormato[19][1] = "n";
            detalleFormato[19][2] = "izq";
            detalleFormato[19][3] = "";
            detalleFormato[20][0] = "- ";
            detalleFormato[20][1] = "n";
            detalleFormato[20][2] = "izq";
            detalleFormato[20][3] = "";
            detalleFormato[21][3] = "BreakLine";
            detalleFormato[22][0] = "Sin otro particulpar, nos despedimos de usted. ";
            detalleFormato[22][1] = "";
            detalleFormato[22][2] = "izq";
            detalleFormato[22][3] = "";
            detalleFormato[23][3] = "BreakLine";
            detalleFormato[24][0] = "Atentamente,";
            detalleFormato[24][1] = "";
            detalleFormato[24][2] = "der";
            detalleFormato[24][3] = "";
            detalleFormato[25][3] = "BreakLine";
            detalleFormato[26][3] = "BreakLine";
            detalleFormato[27][3] = "BreakLine";
            detalleFormato[28][3] = "BreakLine";
            detalleFormato[29][0] = "Salomón Zavala Sarrio";
            detalleFormato[29][1] = "";
            detalleFormato[29][2] = "der";
            detalleFormato[29][3] = "";
            detalleFormato[30][0] = "Presidente";
            detalleFormato[30][1] = "";
            detalleFormato[30][2] = "der";
            detalleFormato[30][3] = "";
            detalleFormato[31][0] = "Comité de Ética";
            detalleFormato[31][1] = "";
            detalleFormato[31][2] = "der";
            detalleFormato[31][3] = "";
        
            crearAprobacion(detalleFormato,"C:\\Users\\Felix\\OneDrive\\Documents\\prueba.doc");
            
//
//        IDocument myDoc = new Document2004();
//        // myDoc.setPageOrientationLandscape();
//        // default is Portrait be can be changed.
//        myDoc.encoding(Encoding.UTF_8); //or ISO8859-1. Default is UTF-8
//
//        myDoc.addEle(BreakLine.times(1).create()); // this is one breakline
//
//        // Headings
//        myDoc.addEle(Heading1.with("===== Java2word ======").create());
//        myDoc.addEle(Heading2.with("===== Headings ======").create());
//
//        myDoc.addEle(Paragraph
//                .with("This doc has been generated by the unit test testJava2wordAllInOne() in the class DocumentTest2004Test.java.")
//                .create());
//        myDoc.addEle(BreakLine.times(1).create());
//
//        myDoc.addEle(Paragraph
//                .with("I will try to use a little bit of everything in the API Java2word. "
//                        + "I realised that is very dificult to keep the doucmentation updated "
//                        + "so this is where I will demostrate how to do some cool things with Java2word!")
//                .create());
//
//        myDoc.addEle(Heading1.with("Heading01 without styling").create());
//        myDoc.addEle(Heading2.with("Heading02 with style Center").withStyle()
//                .align(Align.CENTER).italic().create());
//        myDoc.addEle(Heading3.with("Heading03 aligned Right").withStyle().bold()
//                .align(Align.RIGHT).create());
//
//        // Paragraph and ParagrapPiece
//        myDoc.addEle(Heading2.with("===== Paragraph and ParagrapPiece ======")
//                .create());
//        myDoc.addEle(Paragraph.with("I am a very simple paragraph.").create());
//
//        myDoc.addEle(BreakLine.times(1).create());
//        ParagraphPiece myParPiece01 = ParagraphPiece
//                .with("If you use the class 'Paragraph', you will have limited style. Maybe only paragraph Aligment or BgColor.");
//        ParagraphPiece myParPiece02 = ParagraphPiece
//                .with("In order to use more advanced style, you have to use ParagraphPiece");
//        ParagraphPiece myParPiece03 = ParagraphPiece
//                .with("One example of this is when you want to make ONLY one word BOLD or ITALIC. the way to to this is create many pieces, format them separetely and put all together in a Paragraph object. Example:");
//
//        myDoc.addEle(Paragraph.withPieces(myParPiece01, myParPiece02,
//                myParPiece03).create());
//
//        ParagraphPiece myParPieceJava = ParagraphPiece.with("I like Java and ")
//                .withStyle().font(Font.COURIER).create();
//        ParagraphPiece myParPieceRuby = ParagraphPiece.with("Ruby!!! ")
//                .withStyle().bold().italic().create();
//        ParagraphPiece myParPieceAgile = ParagraphPiece
//                .with("I actually like Java, Ruby Agile, BDD, Cucumber... ")
//                .withStyle().textColor("008000").create();
//
//        myDoc.addEle(Paragraph.withPieces(myParPieceJava, myParPieceRuby,
//                myParPieceAgile).create());
//
//        myDoc.addEle(BreakLine.times(2).create());
//        myDoc.addEle(Paragraph.withPieces(
//                ParagraphPiece.with("This is a manual 'bold' and 'italic'")
//                        .withStyle().font(Font.COURIER).bold().italic()
//                        .create()).create());
//        myDoc.addEle(Paragraph
//                .withPieces(
//                        ParagraphPiece
//                                .with("This is the SAME as the above line but with 'Smart' Bold/Italic ")
//                                .withStyle().font(Font.COURIER_BOLD_ITALIC)
//                                .create()).create());
//        myDoc.addEle(BreakLine.times(1).create());
//
//        // font size
//        myDoc.addEle(Paragraph.withPieces(ParagraphPiece.with("No size")
//                .create(), ParagraphPiece.with(" but I am size 24.").withStyle()
//                        .fontSize("24").create()));
//
//        //ParagraphPiece and other format/styles
//        myDoc.addEle(BreakLine.times(1).create());
//        myDoc.addEle(Paragraph.withPieces(ParagraphPiece.with("New ParagraphPiece styles have been implemented. Here they are:").withStyle().fontSize("14").create()));
//
//        myDoc.addEle(Paragraph.withPieces(ParagraphPiece.with("The Style is: ").create(), ParagraphPiece.with("Subscript").withStyle().subscript().create()));
//        myDoc.addEle(Paragraph.withPieces(ParagraphPiece.with("The Style is: ").create(), ParagraphPiece.with("Superscript").withStyle().superscript().create()));
//        myDoc.addEle(Paragraph.withPieces(ParagraphPiece.with("The Style is: ").create(), ParagraphPiece.with("Strike").withStyle().strike().create()));
//        myDoc.addEle(Paragraph.withPieces(ParagraphPiece.with("The Style is: ").create(), ParagraphPiece.with("Caps").withStyle().caps().create()));
//        myDoc.addEle(Paragraph.withPieces(ParagraphPiece.with("The Style is: ").create(), ParagraphPiece.with("SmallCaps").withStyle().smallCaps().create()));
//        myDoc.addEle(Paragraph.withPieces(ParagraphPiece.with("The Style is: ").create(), ParagraphPiece.with("DoubleStrike").withStyle().doubleStrike().create()));
//        myDoc.addEle(Paragraph.withPieces(ParagraphPiece.with("The Style is: ").create(), ParagraphPiece.with("Emboss").withStyle().emboss().create()));
//        myDoc.addEle(Paragraph.withPieces(ParagraphPiece.with("The Style is: ").create(), ParagraphPiece.with("Imprint").withStyle().imprint().create()));
//        myDoc.addEle(Paragraph.withPieces(ParagraphPiece.with("The Style is: ").create(), ParagraphPiece.with("Outline").withStyle().outline().create()));
//        myDoc.addEle(Paragraph.withPieces(ParagraphPiece.with("The Style is: ").create(), ParagraphPiece.with("Shadow").withStyle().shadow().create()));
//        myDoc.addEle(BreakLine.times(2).create());
//
//        myDoc.addEle(Paragraph.with("Remember to scape special characters like empsersand").create());
//
//
//        // Document Header and Footer
//        myDoc.addEle(BreakLine.times(2).create());
//        myDoc.addEle(Heading2.with("===== Document Header and Footer ======")
//                .create());
//        myDoc.addEle(Paragraph
//                .with("By default everything is added to the Body when you do 'myDoc.addEle(...)'."
//                        + " But you can add elements to the Header and/or Footer. Other cool thing is show page number or not.")
//                .create());
//
//        myDoc.addEle(BreakLine.times(2).create());
//        myDoc.addEle(Paragraph
//                .with("Page number is displayed by default but you can disable: 'myDoc.getFooter().showPageNumber(false)' ")
//                .create());
//
//        myDoc.addEle(BreakLine.times(2).create());
//        myDoc.addEle(Paragraph
//                .with("you can also hide Header and Footer in the first Page. This is useful for when you have a cover page.: 'myDoc.getHeader().setHideHeaderAndFooterFirstPage(true)' ")
//                .create());
//
//
//        myDoc.getHeader().addEle(
//                Paragraph.withPieces(
//                        ParagraphPiece.with("I am in the"),
//                        ParagraphPiece.with(" Header ").withStyle().bold()
//                                .create(), ParagraphPiece.with("of all pages"))
//                        .create());
//
//        myDoc.getFooter().addEle(
//                Paragraph.with("I am in the Footer of all pages").create());
//
//        // Images
//        myDoc.addEle(BreakLine.times(1).create());
//        myDoc.addEle(Heading2.with("===== Images ======").create());
//        myDoc.addEle(Paragraph
//                .with("Images can be created from diferent locations. It can be from your local machine, from web URL or classpath.")
//                .create());
//
//
//        myDoc.addEle(Paragraph.with(
//                "This one is coming from WEB, google web site: ").create());
//        myDoc.addEle(Image.from_WEB_URL("http://www.google.com/images/logos/ps_logo2.png"));
//
//        myDoc.addEle(BreakLine.times(2).create());
//        myDoc.addEle(Paragraph.with("You can change the image dimensions:.")
//                .create());
//        myDoc.addEle(Image.from_WEB_URL("http://www.google.com/images/logos/ps_logo2.png")
//                .setHeight("40").setWidth("80").create());
//
//        myDoc.addEle(BreakLine.times(2).create());
//        myDoc.addEle(Paragraph
//                .with("You can always be creative and mix up images inside other IElements. Eg.: Paragraphs, Tables, etc.")
//                .create());
//
//
//        myDoc.addEle(Paragraph
//                .with("This document inside the paragraph, coming from '/src/test/resources/dtpick.gif': "
//                        + Image.from_FULL_LOCAL_PATHL(
//                                Utils.getAppRoot()
//                                + "/src/test/resources/dtpick.gif")
//                                .getContent()));
//
//        myDoc.addEle(BreakLine.times(1).create());
//
//        myDoc.addEle(PageBreak.create());
//        // Table
//        myDoc.addEle(Heading2.with("===== Table ======").create());
//        myDoc.addEle(Paragraph
//                .with("Table of The Best Soccer Players Ever and Their Number of Gols:")
//                .create());
//        myDoc.addEle(BreakLine.times(1).create());
//
//        
//        Table tbl = new Table() {
//        };
//        tbl.addTableEle(TableEle.TH, "Name", "Number of gols", "Country");
//        tbl.setRepeatTableHeaderOnEveryPage();
//
//        tbl.addTableEle(TableEle.TD, "* Arthur Friedenreich", "1329", "Brazil");
//        tbl.addTableEle(TableEle.TD, "Pele", "1281", "Brazil");
//        tbl.addTableEle(TableEle.TD, "Romario", "1002", "Brazil");
//        tbl.addTableEle(TableEle.TD, "Tulio Maravilha", "956", "Brazil");
//        tbl.addTableEle(TableEle.TD, "** Zico", "815", "Brazil");
//        tbl.addTableEle(TableEle.TD, "Roberto Dinamite", "748", "Brazil");
//        tbl.addTableEle(TableEle.TD, "Di Stéfano", "715", "Argentina");
//        tbl.addTableEle(TableEle.TD, "Puskas", "689", "Hungary");
//        tbl.addTableEle(TableEle.TD, "Flávio", "591", "Brazil");
//        tbl.addTableEle(TableEle.TD, "James McGory", "550", "Scotland");
//        tbl.addTableEle(TableEle.TD, "*** Leonardo Correa", "299", "Brazil/Australia");
//        tbl.addTableEle(TableEle.TD, "Maradona", "258", "Argentina");
//
//        tbl.addTableEle(TableEle.TF, "Total", "1,100,000.00", " ");
//
//        myDoc.addEle(tbl);
//
//        myDoc.addEle(BreakLine.times(1).create());
//
//        myDoc.addEle(Paragraph
//                .withPieces(
//                        ParagraphPiece
//                                .with("* Arthur Friedenreich - unofficial stats")
//                                .withStyle().italic().create()).create());
//        myDoc.addEle(Paragraph
//                .withPieces(
//                        ParagraphPiece
//                                .with("** Zico was a mid-fieldfer and managed to score all those goals!")
//                                .withStyle().italic().create()).create());
//        myDoc.addEle(Paragraph
//                .withPieces(
//                        ParagraphPiece
//                                .with("*** Leonardo Correa's goals (me) including futsal, soccer, friendly games, training games, so on... (but not playstation)")
//                                .withStyle().italic().create()).create());
//
//        // HyperLink
//        myDoc.addEle(BreakLine.times(1).create());
//
//        myDoc.addEle(HyperLink.with("http://google.com", "Hyperlink to google.com").create());
//
//        // PageBreaks
//        myDoc.addEle(Heading2.with("===== PageBreak ======").create());
//        myDoc.addEle(Paragraph.with("There is a PAGE BREAK after this line:")
//                .create());
//        myDoc.addEle(PageBreak.create());
//        myDoc.addEle(Paragraph.with("There is a PAGE BREAK before this line:")
//                .create());
//         
//        String myWord = myDoc.getContent();
////        System.out.println("asdasdasdasd: " + myDoc.getContent() + "kokokokokok");
//
//        File fileObj = new File("d:/Repositorio/Java2word_allInOne.doc");
//
//        PrintWriter writer = null;
//        try {
//            writer = new PrintWriter(fileObj);
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//
//        writer.println(myWord);
//        writer.close();
//
////TestUtils.createLocalDoc(myWord);
////        TestUtils.createLocalDoc(+myDoc.getContent());
    }
    
    public static void crearAprobacion(String[][] detalleFormato,String Nombre){
        IDocument myDoc = new Document2004();
        // myDoc.setPageOrientationLandscape();
        // default is Portrait be can be changed.
        myDoc.encoding(Document2004.Encoding.ISO8859_1); //or ISO8859-1. Default is UTF-8
        for (String[] linea : detalleFormato) {
            if (null != linea[3]) {
                switch (linea[3]) {
                    case "BreakLine": {
                        myDoc.addEle(BreakLine.times(1).create());
                    }
                    break;
                    case "List": {

                    }
                    break;
                    case "linea": {
                        ParagraphPiece lineaParrafo = ParagraphPiece.with(linea[0]);
                        lineaParrafo.withStyle().font(Font.CALIBRI);
                        lineaParrafo.withStyle().fontSize("11");
                        if (null != linea[1]) {
                            StringTokenizer stilo = new StringTokenizer(linea[1], "-");
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
                        if (null != linea[2]) {
                            switch (linea[2]) {
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

}
