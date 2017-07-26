/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comiteetica.controller;

import com.comiteetica.hibernate.model.Registro;
import com.comiteetica.hibernate.service.RegistroService;
import com.comiteetica.json.JsonTransformer;
import com.comiteetica.persistencia.BussinessException;
import com.comiteetica.persistencia.BussinessMessage;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author rasec
 */
@Controller
@RequestMapping("/Registro")
public class RegistroController {
    
    @Autowired
    private JsonTransformer jsonTransformer;
    
    @Autowired
    private RegistroService registroService;
    
    
    @RequestMapping(value = "/RegistroListFindAll", method = RequestMethod.GET, produces = "application/json", consumes = "application/json")
    public void listFindAllRegistro(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        try {

            registroService.beginTransaction();
            String jsonSalida = "[]";
            List<Object> registros = registroService.getAllRegistroList();
            if (registros != null) {
                if (registros.size() > 0) {
                    if (registros.get(0) != null) {
                        jsonSalida = "[" + ((String) registros.get(0)) + "]";
                    }
                }
            }
            registroService.commit();
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
                registroService.rollback();
                System.out.println("2do try ");
            } catch (IOException ex1) {
                Logger.getLogger(RegistroController.class.getName()).log(Level.SEVERE, null, ex1);
                System.out.println("2do catch " + ex1.getMessage());
            } catch (Exception e) {

            }
            System.out.println("1er catch " + ex.getMessage());

        } catch (Exception ex) {
            httpServletResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            try {
                registroService.rollback();
            } catch (Exception e) {
            }
            System.out.println("3er catch " + ex.getMessage());
        } finally {
            try {
                registroService.close();
            } catch (Exception e) {

            }
        }
    }
    
}
