/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comiteetica.controller;

import com.comiteetica.hibernate.model.Correo;
import com.comiteetica.hibernate.service.CorreoService;
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
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author rasec
 */
@Controller
@RequestMapping("/Correo")
public class CorreoController {

    @Autowired
    JsonTransformer jsonTransformer;

    @Autowired
    CorreoService correoService;

    @RequestMapping(value = "/CorreoRead/{idCorreo}", method = RequestMethod.GET, produces = "application/json")
    public void readCorreo(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @PathVariable("idCorreo") int idCorreo) {
        try {
            correoService.beginTransaction();
            Correo correo = correoService.read(idCorreo);
            String jsonSalida = jsonTransformer.toJson(correo);
            correoService.commit();
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
                correoService.rollback();
            } catch (IOException ex1) {
                Logger.getLogger(CorreoController.class.getName()).log(Level.SEVERE, null, ex1);
            } catch (Exception ee) {

            }

        } catch (Exception ex) {
            try {
                correoService.rollback();
            } catch (Exception ee) {

            }
            httpServletResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            System.out.println("catch " + ex.getMessage());
        } finally {
            try {
                correoService.close();
            } catch (Exception ee) {

            }
        }

    }

    @RequestMapping(value = "/CorreoUpdate", method = RequestMethod.PUT, consumes = "application/json", produces = "application/json")
    public void updateCorreo(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @RequestBody String jsonEntrada) {
        try {
            correoService.beginTransaction();
            Correo correo = (Correo) jsonTransformer.fromJson(jsonEntrada, Correo.class);
            correoService.update(correo);
            String jsonSalida = jsonTransformer.toJson(correo);
            correoService.commit();
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
                correoService.rollback();
            } catch (IOException ex1) {
                Logger.getLogger(CorreoController.class.getName()).log(Level.SEVERE, null, ex1);
            } catch (Exception ee) {

            }

        } catch (IOException ex) {
            httpServletResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            httpServletResponse.setContentType("text/plain; charset=UTF-8");
            try {
                ex.printStackTrace(httpServletResponse.getWriter());
                correoService.rollback();
            } catch (IOException ex1) {
                Logger.getLogger(CorreoController.class.getName()).log(Level.SEVERE, null, ex1);
            } catch (Exception ee) {

            }
        } finally {
            try {
                correoService.close();
            } catch (Exception eee) {

            }
        }
    }

    @RequestMapping(value = "/CorreoInsert", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public void insertCorreo(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
            @RequestBody String jsonString) {
        try {
            correoService.beginTransaction();
            Correo correo = (Correo) jsonTransformer.fromJson(jsonString, Correo.class);
            correoService.create(correo);
            correoService.commit();
            correoService.beginTransaction();
            String jsonSalida = jsonTransformer.toJson(correo);
            correoService.commit();
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
                correoService.rollback();
            } catch (IOException ex1) {
                Logger.getLogger(CorreoController.class.getName()).log(Level.SEVERE, null, ex1);
            } catch (Exception ee) {

            }
            System.out.println("catch 1" + ex.getMessage());

        } catch (Exception ex) {
            httpServletResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            httpServletResponse.setContentType("text/plain; charset=UTF-8");
            try {
                ex.printStackTrace(httpServletResponse.getWriter());
                correoService.rollback();
                System.out.println("try 3" + ex.getMessage());
            } catch (IOException ex1) {
                Logger.getLogger(CorreoController.class.getName()).log(Level.SEVERE, null, ex1);
                System.out.println("catch 4" + ex1.getMessage());
            } catch (Exception ee) {

            }
            System.out.println("catch 3" + ex.getMessage());
        } finally {
            try {
                correoService.close();
            } catch (Exception ee) {

            }
        }
    }

    @RequestMapping(value = "/CorreoListFindAll", method = RequestMethod.GET, produces = "application/json", consumes = "application/json")
    public void findAllCorreoList(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        try {

            correoService.beginTransaction();
            String jsonSalida = "[]";
            List<Object> correos = correoService.getAllCorreoList();
            if (correos != null) {
                if (correos.size() > 0) {
                    if (correos.get(0) != null) {
                        jsonSalida = "[" + ((String) correos.get(0)) + "]";
                    }
                }
            }
            correoService.commit();
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
                correoService.rollback();
                System.out.println("2do try ");
            } catch (IOException ex1) {
                Logger.getLogger(CorreoController.class.getName()).log(Level.SEVERE, null, ex1);
                System.out.println("2do catch " + ex1.getMessage());
            } catch (Exception e) {

            }
            System.out.println("1er catch " + ex.getMessage());

        } catch (Exception ex) {
            httpServletResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            try {
                correoService.rollback();
            } catch (Exception e) {
            }
            System.out.println("3er catch " + ex.getMessage());
        } finally {
            try {
                correoService.close();
            } catch (Exception e) {

            }
        }
    }

    @RequestMapping(value = "/CorreoDelete", method = RequestMethod.PUT, consumes = "application/json")
    public void deleteCorreo(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @RequestParam("idUsuario") int idCorreo) {
        try {

            correoService.beginTransaction();
            Correo correo = correoService.read(idCorreo);
            correoService.delete(correo);
            correoService.commit();
            httpServletResponse.setStatus(HttpServletResponse.SC_OK);
            //httpServletResponse.setContentType("application/json; charset=UTF-8");
            //httpServletResponse.getWriter().println(jsonSalida);

        } catch (BussinessException ex) {
            List<BussinessMessage> bussinessMessage = ex.getBussinessMessages();
            String jsonSalida = jsonTransformer.toJson(bussinessMessage);

            httpServletResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            httpServletResponse.setContentType("application/json; charset=UTF-8");
            try {
                httpServletResponse.getWriter().println(jsonSalida);
                correoService.rollback();
            } catch (IOException ex1) {
                Logger.getLogger(CorreoController.class.getName()).log(Level.SEVERE, null, ex1);
            } catch (Exception eee) {

            }
            System.out.println("1er catch " + ex.getMessage());
        } catch (Exception ex) {
            httpServletResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            httpServletResponse.setContentType("text/plain; charset=UTF-8");
            try {
                ex.printStackTrace(httpServletResponse.getWriter());
                correoService.rollback();
            } catch (IOException ex1) {
                Logger.getLogger(CorreoController.class.getName()).log(Level.SEVERE, null, ex1);
            } catch (Exception eee) {

            }
            System.out.println("1er catch " + ex.getMessage());
        } finally {
            try {
                correoService.close();
            } catch (Exception ee) {

            }
        }
    }

}
