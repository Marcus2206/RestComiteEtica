/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comiteetica.controller;

import com.comiteetica.hibernate.model.PatrocinadorCro;
import com.comiteetica.hibernate.service.PatrocinadorCroService;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author rasec
 */
@Controller
@RequestMapping("/PatrocinadorCro")
public class PatrocinadorCroController {

    @Autowired
    JsonTransformer jsonTransformer;

    @Autowired
    PatrocinadorCroService patrocinadorCroService;

    @RequestMapping(value = "/PatrocinadorCroInsert", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public void insertPatrocinadorCro(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @RequestBody String jsonEntrada) {

        try {
            patrocinadorCroService.beginTransaction();
            PatrocinadorCro patrocinadorCro = (PatrocinadorCro) jsonTransformer.fromJson(jsonEntrada, PatrocinadorCro.class);
            patrocinadorCroService.create(patrocinadorCro);
            String jsonSalida = jsonTransformer.toJson(patrocinadorCro);
            patrocinadorCroService.commit();
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
                patrocinadorCroService.rollback();
            } catch (IOException ex1) {
                Logger.getLogger(PatrocinadorCroController.class.getName()).log(Level.SEVERE, null, ex1);
            } catch (Exception e) {

            }
            System.out.println("catch 1" + ex.getMessage());

        } catch (Exception ex) {
            httpServletResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            httpServletResponse.setContentType("text/plain; charset=UTF-8");
            try {
                ex.printStackTrace(httpServletResponse.getWriter());
                patrocinadorCroService.rollback();
                System.out.println("try 3" + ex.getMessage());
            } catch (IOException ex1) {
                Logger.getLogger(PatrocinadorCroController.class.getName()).log(Level.SEVERE, null, ex1);
                System.out.println("catch 4" + ex1.getMessage());
            } catch (Exception e) {

            }
            System.out.println("catch 3" + ex.getMessage());
        } finally {
            try {
                patrocinadorCroService.close();
            } catch (Exception e) {

            }
        }
    }

    @RequestMapping(value = "/PatrocinadorByIdCroFind/{idCro}", method = RequestMethod.GET, produces = "application/json")
    public void findPatrocinadorByIdCro(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @PathVariable("idCro") String idCro) {
        //Product prod=new Product();
        try {
            patrocinadorCroService.beginTransaction();
            List<Object> objects = patrocinadorCroService.getPatrocinadorByIdCro(idCro);
            String jsonSalida = jsonTransformer.toJson(objects);
            patrocinadorCroService.commit();
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
                patrocinadorCroService.rollback();
                System.out.println("2do try ");
            } catch (IOException ex1) {
                Logger.getLogger(PatrocinadorCroController.class.getName()).log(Level.SEVERE, null, ex1);
                System.out.println("2do catch " + ex1.getMessage());
            } catch (Exception e) {

            }

            System.out.println("1er catch " + ex.getMessage());

        } catch (Exception ex) {
            httpServletResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            try {
                patrocinadorCroService.rollback();
            } catch (Exception e) {

            }
            System.out.println("3er catch " + ex.getMessage());
        } finally {
            try {
                patrocinadorCroService.close();
            } catch (Exception e) {

            }
        }
    }
    
    @RequestMapping(value = "/CroByIdPatrocinadorFind/{idPatrocinador}", method = RequestMethod.GET, produces = "application/json")
    public void findCroByIdPatrocinador(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @PathVariable("idPatrocinador") String idPatrocinador) {
        //Product prod=new Product();
        try {
            patrocinadorCroService.beginTransaction();
            List<Object> objects = patrocinadorCroService.getCroByIdPatrocinador(idPatrocinador);
            String jsonSalida = jsonTransformer.toJson(objects);
            patrocinadorCroService.commit();
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
                patrocinadorCroService.rollback();
                System.out.println("2do try ");
            } catch (IOException ex1) {
                Logger.getLogger(PatrocinadorCroController.class.getName()).log(Level.SEVERE, null, ex1);
                System.out.println("2do catch " + ex1.getMessage());
            } catch (Exception e) {

            }

            System.out.println("1er catch " + ex.getMessage());

        } catch (Exception ex) {
            httpServletResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            try {
                patrocinadorCroService.rollback();
            } catch (Exception e) {

            }
            System.out.println("3er catch " + ex.getMessage());
        } finally {
            try {
                patrocinadorCroService.close();
            } catch (Exception e) {

            }
        }
    }

    @RequestMapping(value = "/PatrocinadorCroDelete", method = RequestMethod.PUT, consumes = "application/json")
    public void deletePatrocinadorCro(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @RequestBody String jsonEntrada) {
        try {
            patrocinadorCroService.beginTransaction();
            PatrocinadorCro patrocinadorCro = (PatrocinadorCro) jsonTransformer.fromJson(jsonEntrada, PatrocinadorCro.class);
            patrocinadorCroService.delete(patrocinadorCro);
            patrocinadorCroService.commit();
            httpServletResponse.setStatus(HttpServletResponse.SC_NO_CONTENT);

        } catch (BussinessException ex) {
            List<BussinessMessage> bussinessMessage = ex.getBussinessMessages();
            String jsonSalida = jsonTransformer.toJson(bussinessMessage);

            httpServletResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            httpServletResponse.setContentType("application/json; charset=UTF-8");
            try {
                httpServletResponse.getWriter().println(jsonSalida);
                patrocinadorCroService.rollback();
                System.out.println("try 2: " + ex.getMessage());
            } catch (IOException ex1) {
                Logger.getLogger(PatrocinadorCroController.class.getName()).log(Level.SEVERE, null, ex1);
                System.out.println("catch 3: " + ex1.getMessage());
            } catch (Exception e) {

            }
            System.out.println("catch 2: " + ex.getMessage());

        } catch (Exception ex) {
            httpServletResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            try {
                patrocinadorCroService.rollback();
            } catch (Exception e) {

            }
            System.out.println("catch 1: " + ex.getMessage());
        } finally {
            try {
                patrocinadorCroService.close();
            } catch (Exception e) {

            }
        }

    }

}
