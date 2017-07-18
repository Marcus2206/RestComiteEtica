/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comiteetica.controller;

import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import java.io.File;
import org.springframework.util.FileCopyUtils;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
//import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
//import com.sun.jersey.core.header.FormDataContentDisposition;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.text.NumberFormat;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Response.ResponseBuilder;
import org.hibernate.validator.internal.util.logging.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.IOUtils;

//@Controller
@Controller
public class FileResource {

    private final String APPLICATION_ContentType = "application/unefined";
    
    @RequestMapping(value = "/SubirArchivo", method = RequestMethod.POST)
    public void handleUpload(@RequestParam("file") MultipartFile file, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException {
        if (!file.isEmpty()) {
//            byte[] bytes = file.getBytes(); // alternatively, file.getInputStream();
            File theDir = new File("d:/prueba/dasdsadeeee");
            ServletContext context = httpServletRequest.getServletContext();
            String appPath = context.getRealPath("");
            System.out.println("appPath = " + appPath);
            // if the directory does not exist, create it
            if (!theDir.exists()) {
                boolean result = false;
                try {
                    theDir.mkdir();
                    result = true;
                } catch (SecurityException se) {
                    //handle it
                }
                if (result) {
                }
            }
            String nombreFile = file.getOriginalFilename();
            /* From ISO-8859-1 to UTF-8 */
            String nombreUTF8 = new String(nombreFile.getBytes("ISO-8859-1"), "UTF-8");
            String fileLocation = theDir.getCanonicalPath() + "\\" + nombreUTF8;
            InputStream is = new BufferedInputStream(file.getInputStream());

            try {
                saveFile(is, fileLocation);
                is.close();
                httpServletResponse.setStatus(HttpServletResponse.SC_OK);
                httpServletResponse.setContentType("application/unefined; charset=UTF-8");
            } catch (Exception e) {
                e.printStackTrace();
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
        response.setContentType(APPLICATION_ContentType+"; charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment; filename=" + file.getName());
        response.setHeader("Content-Length", String.valueOf(file.length()));
        System.out.println("file.getAbsolutePath():"+file.getAbsolutePath());
        FileCopyUtils.copy(in, response.getOutputStream());
    }
    
    private File getFile(String nombre) throws FileNotFoundException, UnsupportedEncodingException {
        /* From ISO-8859-1 to UTF-8 */
        String nombreUTF8 = new String(nombre.getBytes("ISO-8859-1"), "UTF-8");
        
        File file = new File("d:/prueba/dasdsadeeee/"+nombreUTF8);
//        File file = new File("d:/prueba/dasdsadeeee/imagen.png");
        if (!file.exists()){
            throw new FileNotFoundException("file with path: " + "d:/prueba/dasdsadeeee/"+nombreUTF8 + " was not found.");
        }else{
            System.out.println("d:/prueba/dasdsadeeee/"+nombreUTF8);
        }
        return file;
    }

}
