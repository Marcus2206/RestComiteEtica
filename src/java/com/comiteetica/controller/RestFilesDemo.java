/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comiteetica.controller;

import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import static jdk.nashorn.tools.ShellFunctions.input;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author rasec
 */
//@Controller
public class RestFilesDemo  {
//    @POST
//    @Path("/upload")
//    @Consumes("multipart/form-data")
//    public void uploadFile(MultipartFormDataInput input) throws IOException {
//    @RequestMapping(value = "/upload", method = RequestMethod.GET, produces = "application/json")
//    public void readCoordinador(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {     
//        Map<String, List<InputPart>> uploadForm = input.getFormDataMap();
// 
//        // Get file data to save
//        List<InputPart> inputParts = uploadForm.get("attachment");
// 
//        for (InputPart inputPart : inputParts) {
//            try {
// 
//                MultivaluedMap<String, String> header = inputPart.getHeaders();
//                String fileName = getFileName(header);
//   
//                // convert the uploaded file to inputstream
//                InputStream inputStream = inputPart.getBody(InputStream.class,
//                        null);
// 
//                byte[] bytes = IOUtils.toByteArray(inputStream);
//                // constructs upload file path
//                fileName = "/home/user/Downloads/" + fileName;
//                writeFile(bytes, fileName);
// 
//                  
//                return Response.status(200).entity("Uploaded file name : " + fileName)
//                        .build();
// 
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//        return null;
//    }
// 
//    private String getFileName(MultivaluedMap<String, String> header) {
// 
//        String[] contentDisposition = header.getFirst("Content-Disposition").split(";");
// 
//        for (String filename : contentDisposition) {
//            if ((filename.trim().startsWith("filename"))) {
// 
//                String[] name = filename.split("=");
// 
//                String finalFileName = name[1].trim().replaceAll("\"", "");
//                return finalFileName;
//            }
//        }
//        return "unknown";
//    }
// 
//    // Utility method
//    private void writeFile(byte[] content, String filename) throws IOException {
//        File file = new File(filename);
//        if (!file.exists()) {
//            System.out.println("not exist> " + file.getAbsolutePath());
//            file.createNewFile();
//        }
//        FileOutputStream fop = new FileOutputStream(file);
//        fop.write(content);
//        fop.flush();
//        fop.close();
//    }
}   