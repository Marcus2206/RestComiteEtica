/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comiteetica.controller;

import com.comiteetica.hibernate.dao.CorrespondenciaDao;
import com.comiteetica.hibernate.model.Correspondencia;
import com.comiteetica.hibernate.model.CorrespondenciaFile;
import com.comiteetica.hibernate.model.CorrespondenciaFileId;
import com.comiteetica.hibernate.model.FechaSesion;
import com.comiteetica.hibernate.model.SerieCorrelativo;
import com.comiteetica.hibernate.service.CorrespondenciaFileService;
import com.comiteetica.hibernate.service.CorrespondenciaService;
import com.comiteetica.hibernate.service.FechaSesionService;
import com.comiteetica.hibernate.service.FormatoLineaService;
import com.comiteetica.hibernate.service.RegistroService;
import com.comiteetica.hibernate.service.SerieCorrelativoService;
import com.comiteetica.json.JsonTransformer;
import com.comiteetica.persistencia.BussinessException;
import com.comiteetica.persistencia.BussinessMessage;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.File;
import org.springframework.util.FileCopyUtils;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

//@Controller
@Controller
@RequestMapping("/File")
public class FileResourceController {

    private final String APPLICATION_ContentType = "application/unefined";

    @RequestMapping(value = "/SubirArchivo/{idCorrespondencia}", method = RequestMethod.POST)
    public void handleUpload(@RequestParam("file") MultipartFile file, HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse, @PathVariable("idCorrespondencia") String idCorrespondencia)
            throws IOException {
        if (!file.isEmpty()) {
            /*Directorio único por correspondencia.*/
            File directorio = new File("c:/Repositorio/Correspondencia/" + idCorrespondencia);
            ServletContext context = httpServletRequest.getServletContext();
            String appPath = context.getRealPath("");
            /*Se valida si existe, si no existe, se crea.*/
            if (!directorio.exists()) {
                try {
                    directorio.mkdir();
                } catch (SecurityException se) {
                }
            }

            /*Se obtiene el nombre del archivo*/
            String nombreFile = file.getOriginalFilename();
            /*Se convierte el nombre del archivo a formato UTF-8, para que acepte tíldes*/
 /*From ISO-8859-1 to UTF-8 */
            String nombreUTF8 = new String(nombreFile.getBytes("ISO-8859-1"), "UTF-8");
            /*Se crea el directiorio con el nombre de archivo dentro del directorio*/
            String fileLocation = directorio.getCanonicalPath() + "\\" + nombreUTF8;
            /*Se procede a crear la entrada de escritura.*/
            InputStream is = new BufferedInputStream(file.getInputStream());

            /*Validando archivos*/
            File validaFile = new File(fileLocation);
            String extensionFile = FilenameUtils.getExtension(fileLocation);
            String nameFile = FilenameUtils.removeExtension(FilenameUtils.getName(fileLocation));
            int conta = 0;

            try {

                /*Si existe el archivo con el mismo nombre, se suma y no reemplaza.*/
                while (validaFile.exists()) {
                    conta++;
                    fileLocation = directorio.getCanonicalPath() + "\\" + nameFile + "_" + conta + "." + extensionFile;
                    validaFile = new File(fileLocation);
                }

                /*Escribiendo archivo.*/
                saveFile(is, fileLocation);
                is.close();
                nameFile = FilenameUtils.getName(fileLocation);
                httpServletResponse.setStatus(HttpServletResponse.SC_OK);
                httpServletResponse.setContentType("application/undefined; charset=UTF-8");
                fileLocation = fileLocation.replace("\\", "\\\\");
                String jsonSalida = "{\n"
                        + "    \"nombreArchivo\":\"" + nameFile + "\",\n"
                        + "    \"direccion\":\"" + fileLocation + "\"\n"
                        + "}";
                httpServletResponse.getWriter().println(jsonSalida);
            } catch (Exception e) {
                httpServletResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
        } else {
            httpServletResponse.setStatus(HttpServletResponse.SC_OK);
        }
    }

    private void saveFile(InputStream is, String fileLocation) throws IOException {
        OutputStream os = new FileOutputStream(new File(fileLocation));
        byte[] buffer = new byte[256];
        int bytes = 0;
        while ((bytes = is.read(buffer)) != -1) {
            os.write(buffer, 0, bytes);
        }
        os.close();
    }

    @RequestMapping(value = "/BajarArchivo",
            method = RequestMethod.GET,
            produces = "application/unefined")
    public @ResponseBody
    void downloadA(HttpServletResponse response,
            @RequestParam("direccion") String direccion) throws IOException {
        File file = getFile(direccion);
        InputStream in = new FileInputStream(file);
        response.setContentType(APPLICATION_ContentType + "; charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment; filename=" + file.getName());
        response.setHeader("Content-Length", String.valueOf(file.length()));
        FileCopyUtils.copy(in, response.getOutputStream());
    }

    private File getFile(String nombre) throws FileNotFoundException, UnsupportedEncodingException {
        /* From ISO-8859-1 to UTF-8 */
        String nombreUTF8 = new String(nombre.getBytes("ISO-8859-1"), "UTF-8");

        File file = new File(nombreUTF8);
//        File file = new File("d:/prueba/dasdsadeeee/imagen.png");
        if (!file.exists()) {
            throw new FileNotFoundException("file with path: " + nombreUTF8 + " was not found.");
        } else {
        }
        return file;
    }

    @RequestMapping(value = "/BorrarArchivo", method = RequestMethod.DELETE)
    public void borrarFile(HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse,
            @RequestParam("direccion") String direccion)
            throws IOException {

        /*Nombre a UTF-8*/
        String nombreUTF8 = new String(direccion.getBytes("ISO-8859-1"), "UTF-8");
        try {
            deleteFile(nombreUTF8);
            httpServletResponse.setStatus(HttpServletResponse.SC_OK);
            httpServletResponse.setContentType("application/undefined; charset=UTF-8");
            httpServletResponse.getWriter().println("");
        } catch (Exception e) {
            httpServletResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            httpServletResponse.setContentType("application/undefined; charset=UTF-8");
            httpServletResponse.getWriter().println(e.getMessage());
        }

    }

    private void deleteFile(String fileLocation) throws IOException {
        File file = new File(fileLocation);
        file.delete();

    }

    @RequestMapping(value = "/BorrarTodoArchivo", method = RequestMethod.DELETE)
    public void borrarTodoFile(HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse,
            @RequestParam("carpeta") String carpeta)
            throws IOException {

        /*Nombre a UTF-8*/
        String nombreUTF8 = new String(carpeta.getBytes("ISO-8859-1"), "UTF-8");
        try {
            deleteFolder(new File("C:/Repositorio/Correspondencia/" + nombreUTF8));
            httpServletResponse.setStatus(HttpServletResponse.SC_OK);
            httpServletResponse.setContentType("application/undefined; charset=UTF-8");
            httpServletResponse.getWriter().println("");
        } catch (Exception e) {
            httpServletResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            httpServletResponse.setContentType("application/undefined; charset=UTF-8");
            httpServletResponse.getWriter().println(e.getMessage());
        }
    }

    private void deleteFolder(File fileDel) throws IOException {
        if (fileDel.isDirectory()) {
            if (fileDel.list().length == 0) {
                fileDel.delete();
            } else {
                for (String temp : fileDel.list()) {
                    File fileDelete = new File(fileDel, temp);
                    //recursive delete
                    deleteFolder(fileDelete);
                }
                //check the directory again, if empty then delete it
                if (fileDel.list().length == 0) {
                    fileDel.delete();
                }
            }
        } else {
            //if file, then delete it
            fileDel.delete();
        }
    }

    String ruta = "C:/Repositorio/Correspondencia/";
    String rutaActa = "C:/Repositorio/Actas";
    String rutaActaCierre = "C:/Repositorio/ActasCierre";
    String rutaActaInspeccion = "C:/Repositorio/ActasInspeccion";
    @Autowired
    CorrespondenciaFileService correspondenciaFileService;

    @Autowired
    FormatoLineaService formatoLineaService;
    
    @Autowired
    RegistroService registroService;

    @Autowired
    CorrespondenciaService correspondenciaService;

    @Autowired
    private JsonTransformer jsonTransformer;

    @Autowired
    private SerieCorrelativoService serieCorrelativoService;

    @Autowired
    private FechaSesionService fechaSesionService;

    private Font BOLD_UNDERLINED = new Font(Font.FontFamily.TIMES_ROMAN, 9, Font.BOLD | Font.UNDERLINE);

    private Font BOLD = new Font(Font.FontFamily.TIMES_ROMAN, 9, Font.BOLD);

    private Font NORMAL = new Font(Font.FontFamily.TIMES_ROMAN, 9);

    @RequestMapping(value = "/HojaRuta", method = RequestMethod.POST)
    public void createPdf(HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse, @RequestParam("idCorrespondencia") String idCorrespondencia) throws DocumentException, IOException {
        try {

            correspondenciaFileService.beginTransaction();
            String dirHojaRuta;
            dirHojaRuta = ruta + idCorrespondencia;
            File directorio = new File(dirHojaRuta);
            ServletContext context = httpServletRequest.getServletContext();
            String appPath = context.getRealPath("");
            /*Se valida si existe, si no existe, se crea.*/
            if (!directorio.exists()) {
                try {
                    directorio.mkdir();
                } catch (SecurityException se) {
                }
            }

            java.util.Date date = Date.from(Instant.now());
            SerieCorrelativo serieCorrelativo = serieCorrelativoService.readNextSerieCorrelativo("HRT", date);

            String nombreArchivo = serieCorrelativo.getId().getIdSerie() + serieCorrelativo.getUltimoUsado() + ".pdf";
            dirHojaRuta = dirHojaRuta + "/" + nombreArchivo;
// step 1
            Document document = new Document();
            document.setPageSize(PageSize.A6);
            // step 2
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(dirHojaRuta));
            // step 3
            document.open();
            // step 4
//            document.add(new Paragraph("Hoja de Ruta"));
            // step 5
            List<Object> list = correspondenciaService.getDatosHojaRuta(idCorrespondencia);
            list.stream().forEach((lista) -> {

                ArrayList temp = (ArrayList) lista;
                try {
                    generarHojaRuta(document, writer, temp);
//                    document.add(new Paragraph(temp.get(0).toString()));
//                    document.add(new Paragraph(temp.get(1).toString()));
//                    document.add(new Paragraph(temp.get(2).toString()));
//                    document.add(new Paragraph(temp.get(3).toString()));
//                    document.add(new Paragraph(temp.get(4).toString()));
                } catch (Exception e) {

                }
            });

            document.close();

            CorrespondenciaFile correspondenciaFile = new CorrespondenciaFile();
            CorrespondenciaFileId id = new CorrespondenciaFileId();
            id.setIdCorrespondencia(idCorrespondencia);
            id.setFileDetalle(correspondenciaFileService.getNextFileDetalleByIdCorrespondencia(idCorrespondencia));
            correspondenciaFile.setId(id);
            correspondenciaFile.setDireccion(dirHojaRuta);
            correspondenciaFile.setNombreArchivo(nombreArchivo);

            correspondenciaFileService.create(correspondenciaFile);
            String jsonSalida = jsonTransformer.toJson(correspondenciaFile);
            serieCorrelativo.setFechaModificacion(date);
            serieCorrelativoService.update(serieCorrelativo);
            correspondenciaFileService.commit();
            httpServletResponse.setStatus(HttpServletResponse.SC_OK);
            httpServletResponse.setContentType("application/json; charset=UTF-8");
            httpServletResponse.getWriter().println(jsonSalida);
        } catch (BussinessException ex) {
            List<BussinessMessage> bussinessMessage = ex.getBussinessMessages();
            String jsonSalida = jsonTransformer.toJson(bussinessMessage);
            httpServletResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            httpServletResponse.setContentType("application/json; charset=UTF-8");
            try {
                httpServletResponse.getWriter().println(jsonSalida);
                correspondenciaFileService.rollback();
            } catch (IOException ex1) {
                Logger.getLogger(FileResourceController.class.getName()).log(Level.SEVERE, null, ex1);
            } catch (Exception ee) {
            }
            System.out.println("catch 1" + ex.getMessage());
        } catch (Exception ex) {
            httpServletResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            httpServletResponse.setContentType("text/plain; charset=UTF-8");
            try {
                ex.printStackTrace(httpServletResponse.getWriter());
                correspondenciaFileService.rollback();
                System.out.println("try 3" + ex.getMessage());
            } catch (IOException ex1) {
                Logger.getLogger(FileResourceController.class.getName()).log(Level.SEVERE, null, ex1);
                System.out.println("catch 4" + ex1.getMessage());
            } catch (Exception ee) {

            }
            System.out.println("catch 3" + ex.getMessage());
        } finally {
            try {
                correspondenciaFileService.close();
            } catch (Exception ee) {

            }
        }
    }

    void generarHojaRuta(Document d, PdfWriter writer, ArrayList al) {
        try {

            Paragraph parrafo = new Paragraph();
            /*Agregando título en negrita y centrado*/
            parrafo.add(new Chunk("COMITÉ DE ÉTICA", BOLD_UNDERLINED));
            parrafo.setAlignment(Element.ALIGN_CENTER);
            d.add(parrafo);

            /*Agregando Etiqueta Fecha*/
            parrafo = new Paragraph();
            parrafo.add(new Chunk("Fecha", NORMAL));
            parrafo.setAlignment(Element.ALIGN_LEFT);
            addNewRectangulo(writer, d, 15f, 350f, 60f, 10f, parrafo);

            /*Agregando Etiqueta Correlativo Interno*/
            parrafo = new Paragraph();
            parrafo.add(new Chunk("No.", NORMAL));
            parrafo.setAlignment(Element.ALIGN_RIGHT);
            addNewRectangulo(writer, d, 260f, 350f, 15f, 10f, parrafo);

            /*Agregando Fecha valor*/
            parrafo = new Paragraph();
            parrafo.add(new Chunk(al.get(0).toString(), NORMAL));
            parrafo.setAlignment(Element.ALIGN_LEFT);
            addNewRectangulo(writer, d, 15f, 340f, 80f, 10f, parrafo);

            /*Agregando correlativo ruta valor*/
            parrafo = new Paragraph();
            parrafo.add(new Chunk(al.get(6).toString(), NORMAL));
            parrafo.setAlignment(Element.ALIGN_RIGHT);
            addNewRectangulo(writer, d, 260f, 340f, 80f, 10f, parrafo);

            /*Agregando investigador principal*/
            parrafo = new Paragraph();
            parrafo.add(new Chunk("Investigador Principal: " + al.get(1).toString(), NORMAL));
            parrafo.setAlignment(Element.ALIGN_LEFT);
            addNewRectangulo(writer, d, 15f, 325f, 260f, 10f, parrafo);

            /*Agregando Protocolo*/
            parrafo = new Paragraph();
            parrafo.add(new Chunk("Protocolo: " + al.get(2).toString(), NORMAL));
            parrafo.setAlignment(Element.ALIGN_LEFT);
            addNewRectangulo(writer, d, 15f, 310f, 260f, 10f, parrafo);

            /*Agregando correlativo Investigacion( REG) interno*/
            parrafo = new Paragraph();
            parrafo.add(new Chunk("Correlativo: " + al.get(3).toString(), NORMAL));
            parrafo.setAlignment(Element.ALIGN_LEFT);
            addNewRectangulo(writer, d, 15f, 295f, 260f, 10f, parrafo);

            /*Agregando observación*/
            parrafo = new Paragraph();
            parrafo.add(new Chunk("Ref.: " + al.get(5).toString(), NORMAL));
            parrafo.setAlignment(Element.ALIGN_JUSTIFIED);
            addNewRectangulo(writer, d, 15f, 280f, 260f, 60f, parrafo);

            /*Revisado por: */
            parrafo = new Paragraph();
            parrafo.add(new Chunk("Revisado por:                        Fecha", BOLD));
            parrafo.setAlignment(Element.ALIGN_RIGHT);
            addNewRectangulo(writer, d, 15f, 220f, 260f, 10f, parrafo);

            /*Drs.*/
            parrafo = new Paragraph();
            parrafo.add(new Chunk("Dr. Salomón Zavala:", NORMAL));
            parrafo.setAlignment(Element.ALIGN_LEFT);
            addNewRectangulo(writer, d, 15f, 200f, 260f, 10f, parrafo);

            /*Línea 1: */
            parrafo = new Paragraph();
            parrafo.add(new Chunk("           .................................              ...................", BOLD));
            parrafo.setAlignment(Element.ALIGN_RIGHT);
            addNewRectangulo(writer, d, 45f, 200f, 260f, 10f, parrafo);

            parrafo = new Paragraph();
            parrafo.add(new Chunk("Dra. Marilú Chiang", NORMAL));
            parrafo.setAlignment(Element.ALIGN_LEFT);
            addNewRectangulo(writer, d, 15f, 180f, 260f, 10f, parrafo);

            /*Línea 2: */
            parrafo = new Paragraph();
            parrafo.add(new Chunk("           .................................              ...................", BOLD));
            parrafo.setAlignment(Element.ALIGN_RIGHT);
            addNewRectangulo(writer, d, 45f, 180f, 260f, 10f, parrafo);

//            /*Agregando Distribución*/
//            parrafo = new Paragraph();
//            parrafo.add(new Chunk("Acciones a Tomar: " + al.get(4).toString(), NORMAL));
//            parrafo.setAlignment(Element.ALIGN_LEFT);
//            addNewRectangulo(writer, d, 15f, 150f, 260f, 10f, parrafo);

            /*Agregando Distribución*/
            parrafo = new Paragraph();
            parrafo.add(new Chunk("Acciones a Tomar: ", BOLD));
            parrafo.setAlignment(Element.ALIGN_CENTER);
            addNewRectangulo(writer, d, 15f, 150f, 260f, 10f, parrafo);

            parrafo = new Paragraph();
            Font zapfdingbats = new Font(Font.FontFamily.ZAPFDINGBATS, 14);
            Chunk chunk = new Chunk("o", zapfdingbats);
            parrafo.add(chunk);
            parrafo.add(new Chunk("Fotocopiar para reunión de Comité", NORMAL));
            parrafo.setAlignment(Element.ALIGN_LEFT);
            addNewRectangulo(writer, d, 15f, 130f, 260f, 10f, parrafo);

            parrafo = new Paragraph();
            zapfdingbats = new Font(Font.FontFamily.ZAPFDINGBATS, 14);
            chunk = new Chunk("o", zapfdingbats);
            parrafo.add(chunk);
            parrafo.add(new Chunk("Archivar", NORMAL));
            parrafo.setAlignment(Element.ALIGN_RIGHT);
            addNewRectangulo(writer, d, 200f, 130f, 100f, 10f, parrafo);

            parrafo = new Paragraph();
            zapfdingbats = new Font(Font.FontFamily.ZAPFDINGBATS, 14);
            chunk = new Chunk("o", zapfdingbats);
            parrafo.add(chunk);
            parrafo.add(new Chunk("Contestar", NORMAL));
            parrafo.setAlignment(Element.ALIGN_LEFT);
            addNewRectangulo(writer, d, 15f, 110f, 100f, 10f, parrafo);

            parrafo = new Paragraph();
            zapfdingbats = new Font(Font.FontFamily.ZAPFDINGBATS, 14);
            chunk = new Chunk("o", zapfdingbats);
            parrafo.add(chunk);
            parrafo.add(new Chunk("Revisar en próxima reunion del Comité", NORMAL));
            parrafo.setAlignment(Element.ALIGN_RIGHT);
            addNewRectangulo(writer, d, 50f, 110f, 260f, 10f, parrafo);

            parrafo = new Paragraph();
            parrafo.add(new Chunk(".....................................................................................................................", NORMAL));
            parrafo.setAlignment(Element.ALIGN_JUSTIFIED);
            addNewRectangulo(writer, d, 15f, 90f, 280f, 10f, parrafo);

            parrafo = new Paragraph();
            parrafo.add(new Chunk(".....................................................................................................................", NORMAL));
            parrafo.setAlignment(Element.ALIGN_JUSTIFIED);
            addNewRectangulo(writer, d, 15f, 70f, 280f, 10f, parrafo);

            parrafo = new Paragraph();
            parrafo.add(new Chunk(".....................................................................................................................", NORMAL));
            parrafo.setAlignment(Element.ALIGN_JUSTIFIED);
            addNewRectangulo(writer, d, 15f, 50f, 280f, 10f, parrafo);

            parrafo = new Paragraph();
            parrafo.add(new Chunk(".....................................................................................................................", NORMAL));
            parrafo.setAlignment(Element.ALIGN_JUSTIFIED);
            addNewRectangulo(writer, d, 15f, 30f, 280f, 10f, parrafo);

            parrafo = new Paragraph();
            parrafo.add(new Chunk(".....................................................................................................................", NORMAL));
            parrafo.setAlignment(Element.ALIGN_JUSTIFIED);
            addNewRectangulo(writer, d, 15f, 10f, 280f, 10f, parrafo);

        } catch (Exception e) {

        }
    }

    void addNewRectangulo(PdfWriter writer, Document d, float x, float y, float xx, float yy, Paragraph parrafo) {
        try {
            PdfContentByte cb = writer.getDirectContent();
            cb.rectangle(x, y, xx, yy);
//            cb.stroke();

            Rectangle rect = new Rectangle(x, y + 18f, xx, yy);
            ColumnText ct = new ColumnText(cb);
            ct.setSimpleColumn(rect);
            ct.addElement(parrafo);
            ct.go();
        } catch (Exception e) {
        }
    }

    @RequestMapping(value = "/CartaAprobacion", method = RequestMethod.POST)
    public void createCarta(HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse, @RequestParam("idCorrespondencia") String idCorrespondencia) throws DocumentException, IOException {
        try {

            correspondenciaFileService.beginTransaction();
            String dirHojaRuta;
            dirHojaRuta = ruta + idCorrespondencia;
            File directorio = new File(dirHojaRuta);
            ServletContext context = httpServletRequest.getServletContext();
            String appPath = context.getRealPath("");
            /*Se valida si existe, si no existe, se crea.*/
            if (!directorio.exists()) {
                try {
                    directorio.mkdir();
                } catch (SecurityException se) {
                }
            }

            java.util.Date date = Date.from(Instant.now());
            SerieCorrelativo serieCorrelativo = serieCorrelativoService.readNextSerieCorrelativo("CE0", date);

            String nombreArchivo = serieCorrelativo.getId().getIdSerie() + serieCorrelativo.getUltimoUsado() + "(Borrador).doc";
            dirHojaRuta = dirHojaRuta + "/" + nombreArchivo;

            List<Object> list = correspondenciaService.getDatosCarta(idCorrespondencia);

            ArrayList item = (ArrayList) list.get(0);
            String investigador = item.get(1) != null ? item.get(1).toString() : "";
            String protocolo = item.get(2) != null ? item.get(2).toString() : "";
            String titulo = item.get(3) != null ? item.get(3).toString() : "";
            String sede = item.get(4) != null ? item.get(4).toString() : "";

            FormatosController controller = new FormatosController();

            Date date1 = new Date();

            String dia = "" + date1.getDate();
            String mes = "";

            switch (date1.getMonth()) {
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

            String anio = "" + (date1.getYear() + 1900);

            List<Object> formatoLinea = formatoLineaService.getLineaFormatoByIdFormato("1");

            for (int x = 0; x < formatoLinea.size(); x++) {
                ArrayList linea = (ArrayList) formatoLinea.get(x);
                if (linea.get(0) != null) {
                    String nuevalinea = linea.get(0).toString();
                    if (nuevalinea.contains("{dia}")) {
                        nuevalinea = nuevalinea.replace("{dia}", dia);
                        linea.remove(0);
                        linea.add(0, nuevalinea);
                    }
                    if (nuevalinea.contains("{mes}")) {
                        nuevalinea = nuevalinea.replace("{mes}", mes);
                        linea.remove(0);
                        linea.add(0, nuevalinea);
                    }
                    if (nuevalinea.contains("{anio}")) {
                        nuevalinea = nuevalinea.replace("{anio}", anio);
                        linea.remove(0);
                        linea.add(0, nuevalinea);
                    }
                    if (nuevalinea.contains("{Correlativo}")) {
                        nuevalinea = nuevalinea.replace("{Correlativo}", serieCorrelativo.getUltimoUsado());
                        linea.remove(0);
                        linea.add(0, nuevalinea);
                    }
                    if (nuevalinea.contains("{investigadorPrincipal}")) {
                        nuevalinea = nuevalinea.replace("{investigadorPrincipal}", investigador);
                        linea.remove(0);
                        linea.add(0, nuevalinea);
                    }
                    if (nuevalinea.contains("{CentroInvestigacion}")) {
                        nuevalinea = nuevalinea.replace("{CentroInvestigacion}", sede);
                        linea.remove(0);
                        linea.add(0, nuevalinea);
                    }
                    if (nuevalinea.contains("{Protocolo}")) {
                        nuevalinea = linea.get(0).toString().replace("{Protocolo}", protocolo);
                        linea.remove(0);
                        linea.add(0, nuevalinea);
                    }
                    if (nuevalinea.contains("{tituloProtocolo}")) {
                        nuevalinea = nuevalinea.replace("{tituloProtocolo}", titulo);
                        linea.remove(0);
                        linea.add(0, nuevalinea);
                    }
                    formatoLinea.remove(x);
                    formatoLinea.add(x, linea);
                }
            }

            controller.GenerarAprobacion(formatoLinea, dirHojaRuta);

            CorrespondenciaFile correspondenciaFile = new CorrespondenciaFile();
            CorrespondenciaFileId id = new CorrespondenciaFileId();
            id.setIdCorrespondencia(idCorrespondencia);
            id.setFileDetalle(correspondenciaFileService.getNextFileDetalleByIdCorrespondencia(idCorrespondencia));
            correspondenciaFile.setId(id);
            correspondenciaFile.setDireccion(dirHojaRuta);
            correspondenciaFile.setNombreArchivo(nombreArchivo);

            correspondenciaFileService.create(correspondenciaFile);
            String jsonSalida = jsonTransformer.toJson(correspondenciaFile);
            serieCorrelativo.setFechaModificacion(date);
            serieCorrelativoService.update(serieCorrelativo);
            correspondenciaFileService.commit();
            httpServletResponse.setStatus(HttpServletResponse.SC_OK);
            httpServletResponse.setContentType("application/json; charset=UTF-8");
            httpServletResponse.getWriter().println(jsonSalida);
        } catch (BussinessException ex) {
            List<BussinessMessage> bussinessMessage = ex.getBussinessMessages();
            String jsonSalida = jsonTransformer.toJson(bussinessMessage);
            httpServletResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            httpServletResponse.setContentType("application/json; charset=UTF-8");
            try {
                httpServletResponse.getWriter().println(jsonSalida);
                correspondenciaFileService.rollback();
            } catch (IOException ex1) {
                Logger.getLogger(FileResourceController.class.getName()).log(Level.SEVERE, null, ex1);
            } catch (Exception ee) {
            }
            System.out.println("catch 1" + ex.getMessage());
        } catch (Exception ex) {
            httpServletResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            httpServletResponse.setContentType("text/plain; charset=UTF-8");
            try {
                ex.printStackTrace(httpServletResponse.getWriter());
                correspondenciaFileService.rollback();
                System.out.println("try 3" + ex.getMessage());
            } catch (IOException ex1) {
                Logger.getLogger(FileResourceController.class.getName()).log(Level.SEVERE, null, ex1);
                System.out.println("catch 4" + ex1.getMessage());
            } catch (Exception ee) {

            }
            System.out.println("catch 3" + ex.getMessage());
        } finally {
            try {
                correspondenciaFileService.close();
            } catch (Exception ee) {

            }
        }
    }

    @RequestMapping(value = "/ActaCierre", method = RequestMethod.POST)
    public void createActaCierre(HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse, @RequestParam("idRegistro") String idRegistro) throws DocumentException, IOException {

        try {
            System.out.println("ActaCierre: "+idRegistro);
            serieCorrelativoService.beginTransaction();

            String dirHojaRuta;
            dirHojaRuta = rutaActaCierre;
            File directorio = new File(dirHojaRuta);
            dirHojaRuta = rutaActaCierre+ "/PruebaCierre.doc";
            ServletContext context = httpServletRequest.getServletContext();
            String appPath = context.getRealPath("");
            /*Se valida si existe, si no existe, se crea.*/
            if (!directorio.exists()) {
                try {
                    directorio.mkdir();
                } catch (SecurityException se) {
                }
            }
            
            FormatosController controller = new FormatosController();
            
            java.util.Date date = Date.from(Instant.now());
            SerieCorrelativo serieCorrelativo = serieCorrelativoService.readNextSerieCorrelativo("CEI", date);
            
            List<Object> formatoLinea = formatoLineaService.getLineaFormatoByIdFormato("4");
            System.out.println("Tam form linea: "+formatoLinea.size());
            List<Object> datosCierre = registroService.getDatosCierre(idRegistro);
            System.out.println("Tam form cierre: "+datosCierre.size());
            ArrayList item = (ArrayList) datosCierre.get(0);
            String registro = item.get(0) != null ? item.get(0).toString():"";
            String titulo = item.get(1) != null ? item.get(1).toString():"";
            String protocolo = item.get(2) != null ? item.get(2).toString():"";
            String investigador = item.get(3) != null ? item.get(3).toString():"";
            String patrocinador = item.get(4) != null ? item.get(4).toString():"";
            String fase = item.get(5) != null ? item.get(5).toString():"";
            String sede = item.get(6) != null ? item.get(6).toString():"";
            System.out.println("Tam form linea: "+formatoLinea.size());
            for(int x=0;x<formatoLinea.size();x++){
                ArrayList linea = (ArrayList) formatoLinea.get(x);
                if (linea.get(0) != null) {
                    String nuevalinea = linea.get(0).toString();
                    if (nuevalinea.contains("{titulo}")) {
                        nuevalinea = nuevalinea.replace("{titulo}", titulo);
                        linea.remove(0);
                        linea.add(0, nuevalinea);
                    }
                    if (nuevalinea.contains("{protocolo}")) {
                        nuevalinea = nuevalinea.replace("{protocolo}", protocolo);
                        linea.remove(0);
                        linea.add(0, nuevalinea);
                    }
                    if (nuevalinea.contains("{investigador}")) {
                        nuevalinea = nuevalinea.replace("{investigador}", investigador);
                        linea.remove(0);
                        linea.add(0, nuevalinea);
                    }
                    if (nuevalinea.contains("{patrocinador}")) {
                        nuevalinea = nuevalinea.replace("{patrocinador}", patrocinador);
                        linea.remove(0);
                        linea.add(0, nuevalinea);
                    }
                    if (nuevalinea.contains("{fase}")) {
                        nuevalinea = nuevalinea.replace("{fase}", fase);
                        linea.remove(0);
                        linea.add(0, nuevalinea);
                    }
                    if (nuevalinea.contains("{centro_investigaciòn}")) {
                        nuevalinea = nuevalinea.replace("{centro_investigaciòn}", sede);
                        linea.remove(0);
                        linea.add(0, nuevalinea);
                    }
                    
                    formatoLinea.remove(x);
                    formatoLinea.add(x, linea);
                }
            };
            
            controller.GenerarCierre(formatoLinea, dirHojaRuta);

            String jsonSalida = jsonTransformer.toJson(dirHojaRuta);
            serieCorrelativo.setFechaModificacion(date);
            serieCorrelativoService.update(serieCorrelativo);
            correspondenciaFileService.commit();
            httpServletResponse.setStatus(HttpServletResponse.SC_OK);
            httpServletResponse.setContentType("application/json; charset=UTF-8");
            httpServletResponse.getWriter().println(jsonSalida);
            
        } catch (BussinessException ex) {
            List<BussinessMessage> bussinessMessage = ex.getBussinessMessages();
            String jsonSalida = jsonTransformer.toJson(bussinessMessage);
            httpServletResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            httpServletResponse.setContentType("application/json; charset=UTF-8");
            try {
                httpServletResponse.getWriter().println(jsonSalida);
                correspondenciaFileService.rollback();
            } catch (IOException ex1) {
                Logger.getLogger(FileResourceController.class.getName()).log(Level.SEVERE, null, ex1);
            } catch (Exception ee) {
            }
            System.out.println("catch 1" + ex.getMessage());
        } catch (Exception ex) {
            httpServletResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            httpServletResponse.setContentType("text/plain; charset=UTF-8");
            try {
                ex.printStackTrace(httpServletResponse.getWriter());
                correspondenciaFileService.rollback();
                System.out.println("try 3" + ex.getMessage());
            } catch (IOException ex1) {
                Logger.getLogger(FileResourceController.class.getName()).log(Level.SEVERE, null, ex1);
                System.out.println("catch 4" + ex1.getMessage());
            } catch (Exception ee) {

            }
            System.out.println("catch 3" + ex.getMessage());
        } finally {
            try {
                correspondenciaFileService.close();
            } catch (Exception ee) {

            }
        }
    }

    @RequestMapping(value = "/ActaInspeccion", method = RequestMethod.POST)
    public void createActaInspeccion(HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse, @RequestParam("idRegistro") String idRegistro) throws DocumentException, IOException {
        
        try {
            serieCorrelativoService.beginTransaction();

            String dirHojaRuta;
            dirHojaRuta = rutaActaInspeccion;
            File directorio = new File(dirHojaRuta);
            dirHojaRuta = rutaActaInspeccion+ "/PruebaVisita.doc";
            ServletContext context = httpServletRequest.getServletContext();
            String appPath = context.getRealPath("");
            /*Se valida si existe, si no existe, se crea.*/
            if (!directorio.exists()) {
                try {
                    directorio.mkdir();
                } catch (SecurityException se) {
                }
            }
            
            FormatosController controller = new FormatosController();
            
            java.util.Date date = Date.from(Instant.now());
            SerieCorrelativo serieCorrelativo = serieCorrelativoService.readNextSerieCorrelativo("CEI", date);
            
            List<Object> formatoLinea = formatoLineaService.getLineaFormatoByIdFormato("5");
            
            List<Object> datosVisita = registroService.getDatosVisita(idRegistro);
            List<Object> documentos = registroService.getDocumentosRegistro(idRegistro);
            
            ArrayList item = (ArrayList) datosVisita.get(0);
            String registro = item.get(0) != null ? item.get(0).toString():"";
            String titulo = item.get(1) != null ? item.get(1).toString():"";
            String protocolo = item.get(2) != null ? item.get(2).toString():"";
            String investigador = item.get(3) != null ? item.get(3).toString():"";
            String patrocinador = item.get(4) != null ? item.get(4).toString():"";
            String fase = item.get(5) != null ? item.get(5).toString():"";
            String sede = item.get(6) != null ? item.get(6).toString():"";
            String farmaco = item.get(7) != null ? item.get(7).toString():"";
            String cro = item.get(8) != null ? item.get(8).toString():"";
            
            
            for(int x=0;x<formatoLinea.size();x++){
                ArrayList linea = (ArrayList) formatoLinea.get(x);
                if (linea.get(0) != null) {
                    String nuevalinea = linea.get(0).toString();
                    if (nuevalinea.contains("{{titulo_investigacion}}")) {
                        nuevalinea = nuevalinea.replace("{{titulo_investigacion}}", titulo);
                        linea.remove(0);
                        linea.add(0, nuevalinea);
                    }
                    if (nuevalinea.contains("{{protocolo}}")) {
                        nuevalinea = nuevalinea.replace("{{protocolo}}", protocolo);
                        linea.remove(0);
                        linea.add(0, nuevalinea);
                    }
                    if (nuevalinea.contains("{{investigador}}")) {
                        nuevalinea = nuevalinea.replace("{{investigador}}", investigador);
                        linea.remove(0);
                        linea.add(0, nuevalinea);
                    }
                    if (nuevalinea.contains("{{patrocinador}}")) {
                        nuevalinea = nuevalinea.replace("{{patrocinador}}", patrocinador);
                        linea.remove(0);
                        linea.add(0, nuevalinea);
                    }
                    if (nuevalinea.contains("{{fase}}")) {
                        nuevalinea = nuevalinea.replace("{{fase}}", fase);
                        linea.remove(0);
                        linea.add(0, nuevalinea);
                    }
                    if (nuevalinea.contains("{{centro_investigacion}}")) {
                        nuevalinea = nuevalinea.replace("{{centro_investigacion}}", sede);
                        linea.remove(0);
                        linea.add(0, nuevalinea);
                    }
                    
                    if (nuevalinea.contains("{{centro_investigaciòn}}")) {
                        nuevalinea = nuevalinea.replace("{{centro_investigaciòn}}", sede);
                        linea.remove(0);
                        linea.add(0, nuevalinea);
                    }
                    
                    if (nuevalinea.contains("{{farmaco}}")) {
                        nuevalinea = nuevalinea.replace("{{farmaco}}", farmaco);
                        linea.remove(0);
                        linea.add(0, nuevalinea);
                    }
                    
                    if (nuevalinea.contains("{{cro}}")) {
                        nuevalinea = nuevalinea.replace("{{cro}}", cro);
                        linea.remove(0);
                        linea.add(0, nuevalinea);
                    }
                    
                    formatoLinea.remove(x);
                    formatoLinea.add(x, linea);
                }
            };
            
            formatoLinea.forEach(linea ->{
                System.out.println(linea);
            });
            
            
            controller.GenerarActaInspeccion(formatoLinea, dirHojaRuta,documentos);

            String jsonSalida = jsonTransformer.toJson(dirHojaRuta);
            serieCorrelativo.setFechaModificacion(date);
            serieCorrelativoService.update(serieCorrelativo);
            correspondenciaFileService.commit();
            httpServletResponse.setStatus(HttpServletResponse.SC_OK);
            httpServletResponse.setContentType("application/json; charset=UTF-8");
            httpServletResponse.getWriter().println(jsonSalida);
            
        } catch (BussinessException ex) {
            List<BussinessMessage> bussinessMessage = ex.getBussinessMessages();
            String jsonSalida = jsonTransformer.toJson(bussinessMessage);
            httpServletResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            httpServletResponse.setContentType("application/json; charset=UTF-8");
            try {
                httpServletResponse.getWriter().println(jsonSalida);
                correspondenciaFileService.rollback();
            } catch (IOException ex1) {
                Logger.getLogger(FileResourceController.class.getName()).log(Level.SEVERE, null, ex1);
            } catch (Exception ee) {
            }
            System.out.println("catch 1" + ex.getMessage());
        } catch (Exception ex) {
            httpServletResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            httpServletResponse.setContentType("text/plain; charset=UTF-8");
            try {
                ex.printStackTrace(httpServletResponse.getWriter());
                correspondenciaFileService.rollback();
                System.out.println("try 3" + ex.getMessage());
            } catch (IOException ex1) {
                Logger.getLogger(FileResourceController.class.getName()).log(Level.SEVERE, null, ex1);
                System.out.println("catch 4" + ex1.getMessage());
            } catch (Exception ee) {

            }
            System.out.println("catch 3" + ex.getMessage());
        } finally {
            try {
                correspondenciaFileService.close();
            } catch (Exception ee) {

            }
        }
        
    }
    
    @RequestMapping(value = "/CartaObservacion", method = RequestMethod.POST)
    public void createCartaObservacion(HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse, @RequestParam("idCorrespondencia") String idCorrespondencia) throws DocumentException, IOException {
        try {

            correspondenciaFileService.beginTransaction();
            String dirHojaRuta;
            dirHojaRuta = ruta + idCorrespondencia;
            File directorio = new File(dirHojaRuta);
            ServletContext context = httpServletRequest.getServletContext();
            String appPath = context.getRealPath("");
            /*Se valida si existe, si no existe, se crea.*/
            if (!directorio.exists()) {
                try {
                    directorio.mkdir();
                } catch (SecurityException se) {
                }
            }

            java.util.Date date = Date.from(Instant.now());
            SerieCorrelativo serieCorrelativo = serieCorrelativoService.readNextSerieCorrelativo("CE0", date);

            String nombreArchivo = serieCorrelativo.getId().getIdSerie() + serieCorrelativo.getUltimoUsado() + "(Borrador).doc";
            dirHojaRuta = dirHojaRuta + "/" + nombreArchivo;

            List<Object> list = correspondenciaService.getDatosCarta(idCorrespondencia);

            ArrayList item = (ArrayList) list.get(0);
            String investigador = item.get(1) != null ? item.get(1).toString() : "";
            String protocolo = item.get(2) != null ? item.get(2).toString() : "";
            String titulo = item.get(3) != null ? item.get(3).toString() : "";
            String sede = item.get(4) != null ? item.get(4).toString() : "";

            FormatosController controller = new FormatosController();

            Date date1 = new Date();

            String dia = "" + date1.getDate();
            String mes = "";

            switch (date1.getMonth()) {
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

            String anio = "" + (date1.getYear() + 1900);

            List<Object> formatoLinea = formatoLineaService.getLineaFormatoByIdFormato("2");

            for (int x = 0; x < formatoLinea.size(); x++) {
                ArrayList linea = (ArrayList) formatoLinea.get(x);
                if (linea.get(0) != null) {
                    String nuevalinea = linea.get(0).toString();
                    if (nuevalinea.contains("{dia}")) {
                        nuevalinea = nuevalinea.replace("{dia}", dia);
                        linea.remove(0);
                        linea.add(0, nuevalinea);
                    }
                    if (nuevalinea.contains("{mes}")) {
                        nuevalinea = nuevalinea.replace("{mes}", mes);
                        linea.remove(0);
                        linea.add(0, nuevalinea);
                    }
                    if (nuevalinea.contains("{anio}")) {
                        nuevalinea = nuevalinea.replace("{anio}", anio);
                        linea.remove(0);
                        linea.add(0, nuevalinea);
                    }
                    if (nuevalinea.contains("{Correlativo}")) {
                        nuevalinea = nuevalinea.replace("{Correlativo}", serieCorrelativo.getUltimoUsado());
                        linea.remove(0);
                        linea.add(0, nuevalinea);
                    }
                    if (nuevalinea.contains("{investigadorPrincipal}")) {
                        nuevalinea = nuevalinea.replace("{investigadorPrincipal}", investigador);
                        linea.remove(0);
                        linea.add(0, nuevalinea);
                    }
                    if (nuevalinea.contains("{CentroInvestigacion}")) {
                        nuevalinea = nuevalinea.replace("{CentroInvestigacion}", sede);
                        linea.remove(0);
                        linea.add(0, nuevalinea);
                    }
                    if (nuevalinea.contains("{Protocolo}")) {
                        nuevalinea = linea.get(0).toString().replace("{Protocolo}", protocolo);
                        linea.remove(0);
                        linea.add(0, nuevalinea);
                    }
                    if (nuevalinea.contains("{tituloProtocolo}")) {
                        nuevalinea = nuevalinea.replace("{tituloProtocolo}", titulo);
                        linea.remove(0);
                        linea.add(0, nuevalinea);
                    }
                    formatoLinea.remove(x);
                    formatoLinea.add(x, linea);
                }
            }

            controller.GenerarAprobacion(formatoLinea, dirHojaRuta);

            CorrespondenciaFile correspondenciaFile = new CorrespondenciaFile();
            CorrespondenciaFileId id = new CorrespondenciaFileId();
            id.setIdCorrespondencia(idCorrespondencia);
            id.setFileDetalle(correspondenciaFileService.getNextFileDetalleByIdCorrespondencia(idCorrespondencia));
            correspondenciaFile.setId(id);
            correspondenciaFile.setDireccion(dirHojaRuta);
            correspondenciaFile.setNombreArchivo(nombreArchivo);

            correspondenciaFileService.create(correspondenciaFile);
            String jsonSalida = jsonTransformer.toJson(correspondenciaFile);
            serieCorrelativo.setFechaModificacion(date);
            serieCorrelativoService.update(serieCorrelativo);
            correspondenciaFileService.commit();
            httpServletResponse.setStatus(HttpServletResponse.SC_OK);
            httpServletResponse.setContentType("application/json; charset=UTF-8");
            httpServletResponse.getWriter().println(jsonSalida);
        } catch (BussinessException ex) {
            List<BussinessMessage> bussinessMessage = ex.getBussinessMessages();
            String jsonSalida = jsonTransformer.toJson(bussinessMessage);
            httpServletResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            httpServletResponse.setContentType("application/json; charset=UTF-8");
            try {
                httpServletResponse.getWriter().println(jsonSalida);
                correspondenciaFileService.rollback();
            } catch (IOException ex1) {
                Logger.getLogger(FileResourceController.class.getName()).log(Level.SEVERE, null, ex1);
            } catch (Exception ee) {
            }
            System.out.println("catch 1" + ex.getMessage());
        } catch (Exception ex) {
            httpServletResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            httpServletResponse.setContentType("text/plain; charset=UTF-8");
            try {
                ex.printStackTrace(httpServletResponse.getWriter());
                correspondenciaFileService.rollback();
                System.out.println("try 3" + ex.getMessage());
            } catch (IOException ex1) {
                Logger.getLogger(FileResourceController.class.getName()).log(Level.SEVERE, null, ex1);
                System.out.println("catch 4" + ex1.getMessage());
            } catch (Exception ee) {

            }
            System.out.println("catch 3" + ex.getMessage());
        } finally {
            try {
                correspondenciaFileService.close();
            } catch (Exception ee) {

            }
        }
    }

    @RequestMapping(value = "/ActaSesion", method = RequestMethod.POST)
    public void createActaSesion(HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse, @RequestParam("idSesion") int idSesion) throws DocumentException, IOException {
        try {

            serieCorrelativoService.beginTransaction();

            String dirHojaRuta;
            dirHojaRuta = rutaActa;
            File directorio = new File(dirHojaRuta);
            ServletContext context = httpServletRequest.getServletContext();
            String appPath = context.getRealPath("");
            /*Se valida si existe, si no existe, se crea.*/
            if (!directorio.exists()) {
                try {
                    directorio.mkdir();
                } catch (SecurityException se) {
                }
            }

            List<FechaSesion> fechaSesions = fechaSesionService.getAllFechaSesion();
            Date ffss = new Date();

            for (FechaSesion fechaSesion : fechaSesions) {
                if (fechaSesion.getIdFechaSesion() == idSesion) {
                    ffss = fechaSesion.getFechaSesion();
                }
            }

            java.util.Date date = Date.from(Instant.now());
            SerieCorrelativo serieCorrelativo = serieCorrelativoService.readNextSerieCorrelativo("ACT", date);

            String nombreArchivo = serieCorrelativo.getId().getIdSerie() + "(Borrador).doc";
            dirHojaRuta = dirHojaRuta + "/" + nombreArchivo;

            List<Correspondencia> list = correspondenciaService.readByFechaSesion(ffss);

//            List<Object> list = correspondenciaService.getDatosCarta(idSesion);
//            ArrayList item = (ArrayList) list.get(0);
//            String investigador = item.get(1) != null ? item.get(1).toString() : "";
//            String protocolo = item.get(2) != null ? item.get(2).toString() : "";
//            String titulo = item.get(3) != null ? item.get(3).toString() : "";
//            String sede = item.get(4) != null ? item.get(4).toString() : "";
            FormatosController controller = new FormatosController();

            Date date1 = new Date();

            int diasemana = date1.getDay();

            String dia = "" + date1.getDate();
            String mes = "";
            String nombredia = "";

            switch (diasemana) {
                case 0:
                    nombredia = "Domingo";
                    break;
                case 1:
                    nombredia = "Lunes";
                    break;
                case 2:
                    nombredia = "Martes";
                    break;
                case 3:
                    nombredia = "Miercoles";
                    break;
                case 4:
                    nombredia = "Jueves";
                    break;
                case 5:
                    nombredia = "Viernes";
                    break;
                case 6:
                    nombredia = "Sabado";
                    break;
            }

            switch (date1.getMonth()) {
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

            String anio = "" + (date1.getYear() + 1900);

            List<Object> formatoLinea = formatoLineaService.getLineaFormatoByIdFormato("3");

            for (int x = 0; x < formatoLinea.size(); x++) {
                ArrayList linea = (ArrayList) formatoLinea.get(x);
                if (linea.get(0) != null) {
                    String nuevalinea = linea.get(0).toString();

                    if (nuevalinea.contains("{nombredia}")) {
                        nuevalinea = nuevalinea.replace("{nombredia}", nombredia);
                        linea.remove(0);
                        linea.add(0, nuevalinea);
                    }

                    if (nuevalinea.contains("{dia}")) {
                        nuevalinea = nuevalinea.replace("{dia}", dia);
                        linea.remove(0);
                        linea.add(0, nuevalinea);
                    }
                    if (nuevalinea.contains("{mes}")) {
                        nuevalinea = nuevalinea.replace("{mes}", mes);
                        linea.remove(0);
                        linea.add(0, nuevalinea);
                    }
                    if (nuevalinea.contains("{anio}")) {
                        nuevalinea = nuevalinea.replace("{anio}", anio);
                        linea.remove(0);
                        linea.add(0, nuevalinea);
                    }
                    if (nuevalinea.contains("{Correlativo}")) {
                        nuevalinea = nuevalinea.replace("{Correlativo}", serieCorrelativo.getUltimoUsado());
                        linea.remove(0);
                        linea.add(0, nuevalinea);
                    }
//                    if (nuevalinea.contains("{investigadorPrincipal}")) {
//                        nuevalinea = nuevalinea.replace("{investigadorPrincipal}", investigador);
//                        linea.remove(0);
//                        linea.add(0, nuevalinea);
//                    }
//                    if (nuevalinea.contains("{CentroInvestigacion}")) {
//                        nuevalinea = nuevalinea.replace("{CentroInvestigacion}", sede);
//                        linea.remove(0);
//                        linea.add(0, nuevalinea);
//                    }
//                    if (nuevalinea.contains("{Protocolo}")) {
//                        nuevalinea = linea.get(0).toString().replace("{Protocolo}", protocolo);
//                        linea.remove(0);
//                        linea.add(0, nuevalinea);
//                    }
//                    if (nuevalinea.contains("{tituloProtocolo}")) {
//                        nuevalinea = nuevalinea.replace("{tituloProtocolo}", titulo);
//                        linea.remove(0);
//                        linea.add(0, nuevalinea);
//                    }
                    formatoLinea.remove(x);
                    formatoLinea.add(x, linea);
                }
            }

            controller.GenerarActa(formatoLinea, dirHojaRuta, list);

            String jsonSalida = jsonTransformer.toJson(dirHojaRuta);
            serieCorrelativo.setFechaModificacion(date);
            serieCorrelativoService.update(serieCorrelativo);
            correspondenciaFileService.commit();
            httpServletResponse.setStatus(HttpServletResponse.SC_OK);
            httpServletResponse.setContentType("application/json; charset=UTF-8");
            httpServletResponse.getWriter().println(jsonSalida);
        } catch (BussinessException ex) {
            List<BussinessMessage> bussinessMessage = ex.getBussinessMessages();
            String jsonSalida = jsonTransformer.toJson(bussinessMessage);
            httpServletResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            httpServletResponse.setContentType("application/json; charset=UTF-8");
            try {
                httpServletResponse.getWriter().println(jsonSalida);
                correspondenciaFileService.rollback();
            } catch (IOException ex1) {
                Logger.getLogger(FileResourceController.class.getName()).log(Level.SEVERE, null, ex1);
            } catch (Exception ee) {
            }
            System.out.println("catch 1" + ex.getMessage());
        } catch (Exception ex) {
            httpServletResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            httpServletResponse.setContentType("text/plain; charset=UTF-8");
            try {
                ex.printStackTrace(httpServletResponse.getWriter());
                correspondenciaFileService.rollback();
                System.out.println("try 3" + ex.getMessage());
            } catch (IOException ex1) {
                Logger.getLogger(FileResourceController.class.getName()).log(Level.SEVERE, null, ex1);
                System.out.println("catch 4" + ex1.getMessage());
            } catch (Exception ee) {

            }
            System.out.println("catch 3" + ex.getMessage());
        } finally {
            try {
                correspondenciaFileService.close();
            } catch (Exception ee) {

            }
        }
    }

}
