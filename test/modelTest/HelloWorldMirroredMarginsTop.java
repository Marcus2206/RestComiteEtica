/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelTest;

import com.itextpdf.text.Rectangle;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

//import sandbox.WrapToTest;
//@WrapToTest
class HelloWorldMirrorMarginsTop {

    public static final String DEST = "c:/Repositorio/rectangle_in_cell.pdf";

    public static void main(String[] args) throws IOException,
            DocumentException {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new HelloWorldMirrorMarginsTop().createPdf(DEST);
    }

    public void createPdf(String dest) throws IOException, DocumentException {
        Document document = new Document();
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(dest));
        document.open();
        document.add(new Paragraph("Option 1:"));
        PdfPTable table = new PdfPTable(3);

        table.addCell("A rectangle:");
        PdfTemplate template = writer.getDirectContent().createTemplate(120, 80);
        template.setColorFill(BaseColor.RED);
        template.rectangle(0, 0, 120, 80);
        template.fill();
        writer.releaseTemplate(template);
        table.addCell(Image.getInstance(template));
        table.addCell("The rectangle is scaled to fit inside the cell, you see a padding.");
        document.add(table);

        document.add(new Paragraph("Option 2:"));
        table = new PdfPTable(3);
        table.addCell("A rectangle:");
        PdfPCell cell = new PdfPCell(Image.getInstance(template));
        table.addCell(cell);
        table.addCell("The rectangle keeps its original size, but can overlap other cells in the same row.");
        document.add(table);

        document.add(new Paragraph("Option 3:"));
        table = new PdfPTable(3);
        table.addCell("A rectangle:");
        cell = new PdfPCell(Image.getInstance(template), true);
        table.addCell(cell);
        table.addCell("The rectangle is scaled to fit inside the cell, no padding.");
        document.add(table);
        PdfContentByte cb = writer.getDirectContent();
        cb.moveTo(228, 810);
        cb.lineTo(338, 810);
        cb.stroke();

        PdfContentByte cb1 = writer.getDirectContent();

        cb1.rectangle(document.getPageSize().getWidth() - 150f, 830f, 50f, 50f);
        cb1.stroke();

        Rectangle rect = new Rectangle(
                document.getPageSize().getWidth() - 150f, 830f,
                document.getPageSize().getWidth() - 40f, 880f);

        ColumnText ct = new ColumnText(cb1);
        ct.setSimpleColumn(rect);
        ct.addElement(new Paragraph("This is the text added in the rectangle"));
        ct.go();

        cb1 = writer.getDirectContent();
        cb1.rectangle(document.getPageSize().getWidth() - 250f, 830f, 50f, 50f);
        cb1.stroke();

        rect = new Rectangle(
                document.getPageSize().getWidth() - 250f, 830f,
                document.getPageSize().getWidth() - 40f, 880f);

        ct = new ColumnText(cb1);
        ct.setSimpleColumn(rect);
        ct.addElement(new Paragraph("222This is the text added in the rectangle222"));
        ct.go();

        document.close();
    }
}
