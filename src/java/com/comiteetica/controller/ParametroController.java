/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comiteetica.controller;

import com.comiteetica.hibernate.model.Parametro;
import com.comiteetica.hibernate.service.ParametroService;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author rasec
 */
@Controller
public class ParametroController {

    @Autowired
    private ParametroService parametroService;

    @Autowired
    private JsonTransformer jsonTransformer;

    @RequestMapping(value = "/ParametroRead/{idParametro}", method = RequestMethod.GET, produces = "application/json")
    public void readParametro(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @PathVariable("idParametro") String idParametro) {
        try {
            parametroService.beginTransaction();
            System.out.println("antes de cargar");
            Parametro parametro = parametroService.read(idParametro);
            System.out.println("cargó parametro");

            String jsonSalida = jsonTransformer.toJson(parametro);
            System.out.println(jsonSalida);
            parametroService.commit();
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
                parametroService.rollback();
            } catch (IOException ex1) {
                Logger.getLogger(ParametroController.class.getName()).log(Level.SEVERE, null, ex1);
            } catch (Exception e) {

            }

        } catch (Exception ex) {
            httpServletResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            System.out.println("catch " + ex.getMessage());
            try {
                parametroService.rollback();
            } catch (Exception e) {

            }
        } finally {
            try {
                parametroService.close();
            } catch (Exception e) {

            }
        }

    }

    @RequestMapping(value = "/ParametroFindAll", method = RequestMethod.GET, produces = "application/json")
    public void findAllParametro(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        try {
            parametroService.beginTransaction();
            List<Parametro> parametros = parametroService.getAllParametro();
            String jsonSalida = jsonTransformer.toJson(parametros);
            parametroService.commit();
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
                parametroService.rollback();
                System.out.println("2do try ");
            } catch (IOException ex1) {
                Logger.getLogger(ParametroController.class.getName()).log(Level.SEVERE, null, ex1);
                System.out.println("2do catch " + ex1.getMessage());
            } catch (Exception e) {

            }

            System.out.println("1er catch " + ex.getMessage());

        } catch (Exception ex) {
            httpServletResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            try {
                parametroService.rollback();
            } catch (Exception e) {

            }
            System.out.println("3er catch " + ex.getMessage());
        } finally {
            try {
                parametroService.close();
            } catch (Exception e) {

            }
        }

    }

}
