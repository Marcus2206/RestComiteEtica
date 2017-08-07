/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comiteetica.controller;

import com.comiteetica.hibernate.model.FechaSesion;
import com.comiteetica.hibernate.service.FechaSesionService;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author rasec
 */
@Controller
@RequestMapping("/FechaSesion")
public class FechaSesionController {

    @Autowired
    JsonTransformer jsonTransformer;

    @Autowired
    FechaSesionService fechaSesionService;

    @RequestMapping(value = "/FechaSesionFindAll", method = RequestMethod.GET, produces = "application/json")
    public void findAllFechaSesion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, String jsonEntrada) {
        try {
            fechaSesionService.beginTransaction();
            List<FechaSesion> fechasesion = fechaSesionService.getAllFechaSesion();
            String jsonSalida = jsonTransformer.toJson(fechasesion);
            fechaSesionService.commit();
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
                fechaSesionService.rollback();
                System.out.println("2do try ");
            } catch (IOException ex1) {
                Logger.getLogger(FechaSesionController.class.getName()).log(Level.SEVERE, null, ex1);
                System.out.println("2do catch " + ex1.getMessage());
            } catch (Exception ee) {

            }

            System.out.println("1er catch " + ex.getMessage());

        } catch (Exception ex) {
            httpServletResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            try {
                fechaSesionService.rollback();
            } catch (Exception ee) {

            }

            System.out.println("3er catch " + ex.getMessage());
        } finally {
            try {
                fechaSesionService.close();
            } catch (Exception ee) {

            }
        }

    }

    @RequestMapping(value = "/FechaSesionProxFind", method = RequestMethod.GET, produces = "application/json")
    public void findFechaSesionProx(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, String jsonEntrada) {
        try {
            fechaSesionService.beginTransaction();
            String jsonSalida = "[]";
            List<Object> fechaSesion = fechaSesionService.getAllFechaSesionProx();
            if (fechaSesion != null) {
                if (fechaSesion.size() > 0) {
                    if (fechaSesion.get(0) != null) {
                        jsonSalida = "[" + ((String) fechaSesion.get(0)) + "]";
                    }
                }
            }
            fechaSesionService.commit();
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
                fechaSesionService.rollback();
                System.out.println("2do try ");
            } catch (IOException ex1) {
                Logger.getLogger(FechaSesionController.class.getName()).log(Level.SEVERE, null, ex1);
                System.out.println("2do catch " + ex1.getMessage());
            } catch (Exception ee) {

            }

            System.out.println("1er catch " + ex.getMessage());

        } catch (Exception ex) {
            httpServletResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            try {
                fechaSesionService.rollback();
            } catch (Exception ee) {

            }

            System.out.println("3er catch " + ex.getMessage());
        } finally {
            try {
                fechaSesionService.close();
            } catch (Exception ee) {

            }
        }

    }
}
