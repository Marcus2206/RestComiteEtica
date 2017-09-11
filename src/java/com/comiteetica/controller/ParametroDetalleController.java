/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comiteetica.controller;

import com.comiteetica.hibernate.model.ParametroDetalle;
import com.comiteetica.hibernate.model.ParametroDetalleId;
import com.comiteetica.hibernate.service.ParametroDetalleService;
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
public class ParametroDetalleController {

    @Autowired
    private JsonTransformer jsonTransformer;

    @Autowired
    private ParametroDetalleService parametroDetalleService;

    @RequestMapping(value = "/Parametro/{idParametro}/{idParametroDetalle}", method = RequestMethod.GET, produces = "application/json")
    public void read(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @PathVariable("idParametro") String idParametro, @PathVariable("idParametroDetalle") String idParametroDetalle) {
        try {
            ParametroDetalleId parametroDetalleId = new ParametroDetalleId(idParametro, idParametroDetalle);
            ParametroDetalle parametroDetalle = parametroDetalleService.read(parametroDetalleId);
            String jsonSalida = jsonTransformer.toJson(parametroDetalle);

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
            } catch (IOException ex1) {
                Logger.getLogger(InvestigadorController.class.getName()).log(Level.SEVERE, null, ex1);
            }

        } catch (Exception ex) {
            httpServletResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            System.out.println("catch " + ex.getMessage());
        }

    }

    @RequestMapping(value = "/ParametroDetalleUpdate", method = RequestMethod.PUT, consumes = "application/json", produces = "application/json")
    public void updateParametroDetalle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @RequestBody String jsonEntrada) {
        try {
            parametroDetalleService.beginTransaction();
            ParametroDetalle parametroDetalle = (ParametroDetalle) jsonTransformer.fromJson(jsonEntrada, ParametroDetalle.class);
            parametroDetalleService.update(parametroDetalle);
            String jsonSalida = jsonTransformer.toJson(parametroDetalle);
            parametroDetalleService.commit();
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
                parametroDetalleService.rollback();
            } catch (IOException ex1) {
                Logger.getLogger(ParametroDetalleController.class.getName()).log(Level.SEVERE, null, ex1);
            } catch (Exception ee) {

            }

        } catch (IOException ex) {
            httpServletResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            httpServletResponse.setContentType("text/plain; charset=UTF-8");
            try {
                ex.printStackTrace(httpServletResponse.getWriter());
                parametroDetalleService.rollback();
            } catch (IOException ex1) {
                Logger.getLogger(ParametroDetalleController.class.getName()).log(Level.SEVERE, null, ex1);
            } catch (Exception ee) {

            }
        } finally {
            try {
                parametroDetalleService.close();
            } catch (Exception eee) {

            }
        }
    }

    @RequestMapping(value = "/ParametroDetalleInsert", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public void insertParametroDetalle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
            @RequestBody String jsonString) {
        try {
            parametroDetalleService.beginTransaction();
            ParametroDetalle parametroDetalle = (ParametroDetalle) jsonTransformer.fromJson(jsonString, ParametroDetalle.class);
            parametroDetalle.getId().setIdParametroDetalle(parametroDetalleService.getNextParametroDetalleByIdParametro(parametroDetalle.getId().getIdParametro()));
            parametroDetalleService.create(parametroDetalle);
            String jsonSalida = jsonTransformer.toJson(parametroDetalle);
            parametroDetalleService.commit();
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
                parametroDetalleService.rollback();
            } catch (IOException ex1) {
                Logger.getLogger(ParametroDetalleController.class.getName()).log(Level.SEVERE, null, ex1);
            } catch (Exception ee) {

            }
            System.out.println("catch 1" + ex.getMessage());

        } catch (Exception ex) {
            httpServletResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            httpServletResponse.setContentType("text/plain; charset=UTF-8");
            try {
                ex.printStackTrace(httpServletResponse.getWriter());
                parametroDetalleService.rollback();
                System.out.println("try 3" + ex.getMessage());
            } catch (IOException ex1) {
                Logger.getLogger(ParametroDetalleController.class.getName()).log(Level.SEVERE, null, ex1);
                System.out.println("catch 4" + ex1.getMessage());
            } catch (Exception ee) {

            }
            System.out.println("catch 3" + ex.getMessage());
        } finally {
            try {
                parametroDetalleService.close();
            } catch (Exception ee) {

            }
        }
    }
    
    
    @RequestMapping(value = "/ParametroDetalleDelete", method = RequestMethod.PUT, consumes = "application/json")
    public void deleteParametroDetalle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
            @RequestParam("idParametro") String idParametro,
            @RequestParam("idParametroDetalle") String idParametroDetalle) {
        try {

            parametroDetalleService.beginTransaction();
            ParametroDetalleId id=new ParametroDetalleId(idParametro, idParametroDetalle);
            ParametroDetalle parametroDetalle = parametroDetalleService.read(id);
            parametroDetalleService.delete(parametroDetalle);
            parametroDetalleService.commit();
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
                parametroDetalleService.rollback();
            } catch (IOException ex1) {
                Logger.getLogger(ParametroDetalleController.class.getName()).log(Level.SEVERE, null, ex1);
            } catch (Exception eee) {

            }
            System.out.println("1er catch " + ex.getMessage());
        } catch (Exception ex) {
            httpServletResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            httpServletResponse.setContentType("text/plain; charset=UTF-8");
            try {
                ex.printStackTrace(httpServletResponse.getWriter());
                parametroDetalleService.rollback();
            } catch (IOException ex1) {
                Logger.getLogger(ParametroDetalleController.class.getName()).log(Level.SEVERE, null, ex1);
            } catch (Exception eee) {

            }
            System.out.println("1er catch " + ex.getMessage());
        } finally {
            try {
                parametroDetalleService.close();
            } catch (Exception ee) {

            }
        }
    }
}
