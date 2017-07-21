/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comiteetica.controller;

import com.comiteetica.hibernate.model.CorrespondenciaFile;
import com.comiteetica.hibernate.model.CorrespondenciaFileId;
import com.comiteetica.hibernate.service.CorrespondenciaFileService;
import com.comiteetica.json.JsonTransformer;
import com.comiteetica.persistencia.BussinessException;
import com.comiteetica.persistencia.BussinessMessage;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Path;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author rasec
 */
@Controller
@RequestMapping("/Correspondencia")
public class CorrespondenciaFileController {

    @Autowired
    private JsonTransformer jsonTransformer;

    @Autowired
    private CorrespondenciaFileService correspondenciaFileService;

    @RequestMapping(value = "/CorrespondenciaFileFindAllByIdCorrepondencia/{idCorrespondencia}", method = RequestMethod.GET, produces = "application/json", consumes = "application/json")
    public void findAllByIdCorrepondenciaCorrespondenciaFile(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @PathVariable String idCorrespondencia) {
        try {

            correspondenciaFileService.beginTransaction();
            List<CorrespondenciaFile> correspondenciaFiles = correspondenciaFileService.getAllCorrespondenciaFileByIdCorrepondencia(idCorrespondencia);
            String jsonSalida = jsonTransformer.toJson(correspondenciaFiles);
            correspondenciaFileService.commit();
            httpServletResponse.addHeader("Access-Control-Allow-Origin", "*");
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
                System.out.println("2do try ");
            } catch (IOException ex1) {
                Logger.getLogger(CorrespondenciaFileController.class.getName()).log(Level.SEVERE, null, ex1);
                System.out.println("2do catch " + ex1.getMessage());
            } catch (Exception e) {

            }
            System.out.println("1er catch " + ex.getMessage());

        } catch (Exception ex) {
            httpServletResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            try {
                correspondenciaFileService.rollback();
            } catch (Exception e) {
            }
            System.out.println("3er catch " + ex.getMessage());
        } finally {
            try {
                correspondenciaFileService.close();
            } catch (Exception e) {

            }
        }
    }

}
