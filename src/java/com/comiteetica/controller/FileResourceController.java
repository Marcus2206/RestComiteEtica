/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comiteetica.controller;

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
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.io.FilenameUtils;
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
    public void handleUpload(@RequestParam("file") MultipartFile file, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @PathVariable("idCorrespondencia") String idCorrespondencia) throws IOException {
        if (!file.isEmpty()) {
            /*Directorio único por correspondencia.*/
            System.out.println("dasdasdasdads: "+idCorrespondencia);
            File directorio = new File("d:/Repositorio/Correspondencia/" + idCorrespondencia);
            ServletContext context = httpServletRequest.getServletContext();
            String appPath = context.getRealPath("");

            System.out.println("appPath = " + appPath);
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

            try {
                /*Validando archivos*/
                File validaFile = new File(fileLocation);
                String extensionFile = FilenameUtils.getExtension(fileLocation);
                String nameFile = nombreUTF8.replace(extensionFile, "");
                int conta = 0;

                /*Si existe el archivo con el mismo nombre, se suma y no reemplaza.*/
                while (validaFile.exists()) {
                    conta++;
                    fileLocation = directorio.getCanonicalPath() + "\\" + nameFile + "_" + conta + extensionFile;
                    validaFile = new File(fileLocation);
                }

                /*Escribiendo archivo.*/
                saveFile(is, fileLocation);
                is.close();
                httpServletResponse.setStatus(HttpServletResponse.SC_OK);
                httpServletResponse.setContentType("application/unefined; charset=UTF-8");
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

    @RequestMapping(value = "/BajarArchivo/{nombre:.+}",
            method = RequestMethod.GET,
            produces = "application/unefined")
    public @ResponseBody
    void downloadA(HttpServletResponse response,
            @PathVariable String nombre) throws IOException {
        File file = getFile(nombre);
        InputStream in = new FileInputStream(file);
        response.setContentType(APPLICATION_ContentType + "; charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment; filename=" + file.getName());
        response.setHeader("Content-Length", String.valueOf(file.length()));
        System.out.println("file.getAbsolutePath():" + file.getAbsolutePath());
        FileCopyUtils.copy(in, response.getOutputStream());
    }

    private File getFile(String nombre) throws FileNotFoundException, UnsupportedEncodingException {
        /* From ISO-8859-1 to UTF-8 */
        String nombreUTF8 = new String(nombre.getBytes("ISO-8859-1"), "UTF-8");

        File file = new File("d:/prueba/dasdsadeeee/" + nombreUTF8);
//        File file = new File("d:/prueba/dasdsadeeee/imagen.png");
        if (!file.exists()) {
            throw new FileNotFoundException("file with path: " + "d:/prueba/dasdsadeeee/" + nombreUTF8 + " was not found.");
        } else {
            System.out.println("d:/prueba/dasdsadeeee/" + nombreUTF8);
        }
        return file;
    }

}
