/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comiteetica.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
//import com.sun.jersey.core.header.FormDataContentDisposition;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
//import org.glassfish.jersey.media.multipart.FormDataParam;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

//@Controller
@Controller
public class FileResource {
//	@POST
//	@Path("/CargaArchivo") 
//	@Consumes(MediaType.MULTIPART_FORM_DATA)
//	public Response uploadFile(@FormDataParam("upload") InputStream is, 
//	                    @FormDataParam("upload") FormDataContentDisposition formData) {
//                System.out.println("sdasdasdasd");
//		String fileLocation = "c:/temp/" + formData.getFileName();
//		try {
//			saveFile(is, fileLocation);
//			String result = "Successfully File Uploaded on the path "+fileLocation;
//			return Response.status(Status.OK).entity(result).build();
//		} catch (IOException e) {
//			e.printStackTrace();
//                        System.out.println("sdasdasdsssssssssssssdasd");
//			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
//		}
//                
//                
//                
//	}

    @RequestMapping(value = "/VerificaArchivo", method = RequestMethod.POST)
    public @ResponseBody
    void handleUpload(@RequestParam("file") MultipartFile file) throws IOException {
        System.out.print("handleUpload");
        if (!file.isEmpty()) {
            System.out.print("file.getOriginalFilename(): "+file.getOriginalFilename());
            System.out.print("file.getName(): "+file.getName());
            byte[] bytes = file.getBytes(); // alternatively, file.getInputStream();
            String fileLocation = "d:/prueba/"+file.getOriginalFilename();
//            InputStream stream = new FileInputStream(file);
            InputStream is =  new BufferedInputStream(file.getInputStream());
            try {
                saveFile(is, fileLocation);
                String result = "Successfully File Uploaded on the path " + fileLocation;
//                return Response.status(Status.OK).entity(result).build();
                is.close();
                System.out.print(result);
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("sdasdasdsssssssssssssdasd");
//                return Response.status(Status.INTERNAL_SERVER_ERROR).build();
                System.out.print("error"+e.getMessage());
            }

            
            // application logic
        } else {
            System.out.print("vacío");
        }
    }

//    @Consumes(MediaType.MULTIPART_FORM_DATA)
//    @RequestMapping(value = "/CargaArchivo", method = RequestMethod.POST)
//    public Response readCoordinador(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @FormDataParam("upload") InputStream is,
//            @FormDataParam("upload") FormDataContentDisposition formData) {
//        System.out.println("sdasdasdasd");
//        String fileLocation = "c:/temp/" + formData.getFileName();
//        try {
//            saveFile(is, fileLocation);
//            String result = "Successfully File Uploaded on the path " + fileLocation;
//            return Response.status(Status.OK).entity(result).build();
//        } catch (IOException e) {
//            e.printStackTrace();
//            System.out.println("sdasdasdsssssssssssssdasd");
//            return Response.status(Status.INTERNAL_SERVER_ERROR).build();
//        }
//
//    }

    private void saveFile(InputStream is, String fileLocation) throws IOException {
        OutputStream os = new FileOutputStream(new File(fileLocation));
        byte[] buffer = new byte[256];
        int bytes = 0;
        while ((bytes = is.read(buffer)) != -1) {
            os.write(buffer, 0, bytes);
        }
        os.close();
    }
}
