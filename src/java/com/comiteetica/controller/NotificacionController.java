/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comiteetica.controller;

import com.comiteetica.hibernate.service.NotificacionService;
import com.comiteetica.json.JsonTransformer;
import com.comiteetica.persistencia.BussinessException;
import com.comiteetica.persistencia.BussinessMessage;
import java.io.IOException;
import java.time.Instant;
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
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author rasec
 */
@Controller
@RequestMapping("/Notificacion")
public class NotificacionController {

    @Autowired
    JsonTransformer jsonTransformer;

    @Autowired
    NotificacionService notificaiconService;

    @RequestMapping(value = "/NotificacionListFindAll/{usuario}", method = RequestMethod.GET, produces = "application/json", consumes = "application/json")
    public void findAllNotificacionList(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
            @PathVariable("usuario") String usuario) {
        try {

            notificaiconService.beginTransaction();
            String jsonSalida = "[]";
            List<Object> notificacions = notificaiconService.getAllNotificacionList(usuario);
            if (notificacions != null) {
                if (notificacions.size() > 0) {
                    if (notificacions.get(0) != null) {
                        jsonSalida = "[" + ((String) notificacions.get(0)) + "]";
                    }
                }
            }
            notificaiconService.commit();

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
                notificaiconService.rollback();
                System.out.println("2do try ");
            } catch (IOException ex1) {
                Logger.getLogger(NotificacionController.class.getName()).log(Level.SEVERE, null, ex1);
                System.out.println("2do catch " + ex1.getMessage());
            } catch (Exception e) {

            }
            System.out.println("1er catch " + ex.getMessage());

        } catch (Exception ex) {
            httpServletResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            try {
                notificaiconService.rollback();
            } catch (Exception e) {
            }
            System.out.println("3er catch " + ex.getMessage());
        } finally {
            try {
                notificaiconService.close();
            } catch (Exception e) {

            }
        }
    }

    @RequestMapping(value = "/SetLeidoUpdate", method = RequestMethod.PUT, consumes = "application/json", produces = "application/json")
    public void updateSetLeido(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
            @RequestParam("idNotificacion") int idNotificacion, @RequestParam("usuario") String usuario) {
        try {
            notificaiconService.beginTransaction();
            notificaiconService.updateSetLeido(idNotificacion, usuario);
            notificaiconService.commit();
            httpServletResponse.setStatus(HttpServletResponse.SC_OK);
            httpServletResponse.setContentType("application/json; charset=UTF-8");
            httpServletResponse.getWriter().println("1");

        } catch (BussinessException ex) {
            List<BussinessMessage> bussinessMessage = ex.getBussinessMessages();
            String jsonSalida = jsonTransformer.toJson(bussinessMessage);

            httpServletResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            httpServletResponse.setContentType("application/json; charset=UTF-8");
            try {
                httpServletResponse.getWriter().println(jsonSalida);
                notificaiconService.rollback();
            } catch (IOException ex1) {
                Logger.getLogger(NotificacionController.class.getName()).log(Level.SEVERE, null, ex1);
            } catch (Exception ee) {

            }

        } catch (IOException ex) {
            httpServletResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            httpServletResponse.setContentType("text/plain; charset=UTF-8");
            try {
                ex.printStackTrace(httpServletResponse.getWriter());
                notificaiconService.rollback();
            } catch (IOException ex1) {
                Logger.getLogger(NotificacionController.class.getName()).log(Level.SEVERE, null, ex1);
            } catch (Exception ee) {

            }
        } finally {
            try {
                notificaiconService.close();
            } catch (Exception eee) {

            }
        }
    }

    @RequestMapping(value = "/SetTodoLeidoUpdate", method = RequestMethod.PUT, consumes = "application/json", produces = "application/json")
    public void updateSetTodoLeido(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
            @RequestParam("usuario") String usuario) {
        try {
            notificaiconService.beginTransaction();
            notificaiconService.updateSetTodoLeido(usuario);
            notificaiconService.commit();
            httpServletResponse.setStatus(HttpServletResponse.SC_OK);
            httpServletResponse.setContentType("application/json; charset=UTF-8");
            httpServletResponse.getWriter().println("1");
        } catch (BussinessException ex) {
            List<BussinessMessage> bussinessMessage = ex.getBussinessMessages();
            String jsonSalida = jsonTransformer.toJson(bussinessMessage);
            httpServletResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            httpServletResponse.setContentType("application/json; charset=UTF-8");
            try {
                httpServletResponse.getWriter().println(jsonSalida);
                notificaiconService.rollback();
            } catch (IOException ex1) {
                Logger.getLogger(NotificacionController.class.getName()).log(Level.SEVERE, null, ex1);
            } catch (Exception ee) {

            }

        } catch (IOException ex) {
            httpServletResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            httpServletResponse.setContentType("text/plain; charset=UTF-8");
            try {
                ex.printStackTrace(httpServletResponse.getWriter());
                notificaiconService.rollback();
            } catch (IOException ex1) {
                Logger.getLogger(NotificacionController.class.getName()).log(Level.SEVERE, null, ex1);
            } catch (Exception ee) {

            }
            System.out.println("void entra updateSetTodoLeido"+ex.getMessage());
        } finally {
            try {
                notificaiconService.close();
            } catch (Exception eee) {

            }
        }
    }
}
