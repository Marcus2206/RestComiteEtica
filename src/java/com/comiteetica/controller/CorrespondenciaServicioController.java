/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comiteetica.controller;

import com.comiteetica.hibernate.model.CorrespondenciaServicio;
import com.comiteetica.hibernate.model.CorrespondenciaServicioId;
import com.comiteetica.hibernate.service.CorrespondenciaServicioService;
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
@RequestMapping("/CorrespondenciaServicio")
public class CorrespondenciaServicioController {

    @Autowired
    private JsonTransformer jsonTransformer;

    @Autowired
    private CorrespondenciaServicioService correspondenciaServicioService;

    @RequestMapping(value = "/CorrespondenciaServicioInsert", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public void insertCorrespondenciaServicio(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @RequestBody String jsonEntrada) {
        try {
            correspondenciaServicioService.beginTransaction();
            CorrespondenciaServicio correspondenciaServicio = (CorrespondenciaServicio) jsonTransformer.fromJson(jsonEntrada, CorrespondenciaServicio.class);
            CorrespondenciaServicioId id = correspondenciaServicio.getId();
            id.setIdCorrespondenciaServicio(correspondenciaServicioService.getNextServicioDetalleByIdCorrespondencia(id.getIdCorrespondencia()));
            correspondenciaServicioService.create(correspondenciaServicio);
            String jsonSalida = jsonTransformer.toJson(correspondenciaServicio);
            correspondenciaServicioService.commit();
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
                correspondenciaServicioService.rollback();
            } catch (IOException ex1) {
                Logger.getLogger(CorrespondenciaServicioController.class.getName()).log(Level.SEVERE, null, ex1);
            } catch (Exception ee) {

            }
            System.out.println("catch 1" + ex.getMessage());

        } catch (Exception ex) {
            httpServletResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            httpServletResponse.setContentType("text/plain; charset=UTF-8");
            try {
                ex.printStackTrace(httpServletResponse.getWriter());
                correspondenciaServicioService.rollback();
                System.out.println("try 3" + ex.getMessage());
            } catch (IOException ex1) {
                Logger.getLogger(CorrespondenciaServicioController.class.getName()).log(Level.SEVERE, null, ex1);
                System.out.println("catch 4" + ex1.getMessage());
            } catch (Exception ee) {

            }
            System.out.println("catch 3" + ex.getMessage());
        } finally {
            try {
                correspondenciaServicioService.close();
            } catch (Exception ee) {

            }
        }
    }

    @RequestMapping(value = "/CorrespondenciaServicioSinPagoListFindAll", method = RequestMethod.GET, produces = "application/json", consumes = "application/json")
    public void listFindAllCorrespondencia(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        try {

            correspondenciaServicioService.beginTransaction();
            String jsonSalida = "[]";
            List<Object> correspondenciaServicios = correspondenciaServicioService.getAllCorrespondenciaServicioSinPagoList();
            if (correspondenciaServicios != null) {
                if (correspondenciaServicios.size() > 0) {
                    if (correspondenciaServicios.get(0) != null) {
                        jsonSalida = "[" + ((String) correspondenciaServicios.get(0)) + "]";
                    }
                }
            }
            correspondenciaServicioService.commit();
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
                correspondenciaServicioService.rollback();
                System.out.println("2do try ");
            } catch (IOException ex1) {
                Logger.getLogger(CorrespondenciaServicioController.class.getName()).log(Level.SEVERE, null, ex1);
                System.out.println("2do catch " + ex1.getMessage());
            } catch (Exception e) {

            }
            System.out.println("1er catch " + ex.getMessage());

        } catch (Exception ex) {
            httpServletResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            try {
                correspondenciaServicioService.rollback();
            } catch (Exception e) {
            }
            System.out.println("3er catch " + ex.getMessage());
        } finally {
            try {
                correspondenciaServicioService.close();
            } catch (Exception e) {

            }
        }
    }

    @RequestMapping(value = "/CorrespondenciaServicioDelete/{idCorrespondencia}/{idCorrespondenciaServicio}", method = RequestMethod.PUT, consumes = "application/json")
    public void deleteCorrespondenciaFile(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @PathVariable String idCorrespondencia, @PathVariable int idCorrespondenciaServicio) {
        try {

            correspondenciaServicioService.beginTransaction();
            CorrespondenciaServicioId id = new CorrespondenciaServicioId(idCorrespondencia, idCorrespondenciaServicio);
            CorrespondenciaServicio correspondencia = correspondenciaServicioService.read(id);
            String jsonSalida="1";
            if (correspondencia.getTransferido() == 0) {
                correspondenciaServicioService.delete(correspondencia);
                jsonSalida="1";
            }else{
                jsonSalida="0";
            }

            correspondenciaServicioService.commit();

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
                correspondenciaServicioService.rollback();
            } catch (IOException ex1) {
                Logger.getLogger(CorrespondenciaServicioController.class.getName()).log(Level.SEVERE, null, ex1);
            } catch (Exception eee) {

            }
            System.out.println("1er catch " + ex.getMessage());
        } catch (Exception ex) {
            httpServletResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            httpServletResponse.setContentType("text/plain; charset=UTF-8");
            try {
                ex.printStackTrace(httpServletResponse.getWriter());
                correspondenciaServicioService.rollback();
            } catch (IOException ex1) {
                Logger.getLogger(CorrespondenciaServicioController.class.getName()).log(Level.SEVERE, null, ex1);
            } catch (Exception eee) {

            }
            System.out.println("1er catch " + ex.getMessage());
        } finally {
            try {
                correspondenciaServicioService.close();
            } catch (Exception ee) {

            }
        }
    }

    @RequestMapping(value = "/CorrespondenciaServicioByCorrespondenciaFindAll/{idCorrespondencia}", method = RequestMethod.GET, produces = "application/json", consumes = "application/json")
    public void findAllCorrespondenciaServicioByCorrespondencia(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @PathVariable String idCorrespondencia) {
        try {

            correspondenciaServicioService.beginTransaction();
            String jsonSalida = "[]";
            List<Object> correspondenciaServicios = correspondenciaServicioService.getAllCorrespondenciaServicioByCorrespondencia(idCorrespondencia);
            if (correspondenciaServicios != null) {
                if (correspondenciaServicios.size() > 0) {
                    if (correspondenciaServicios.get(0) != null) {
                        jsonSalida = "[" + ((String) correspondenciaServicios.get(0)) + "]";
                    }
                }
            }
            correspondenciaServicioService.commit();
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
                correspondenciaServicioService.rollback();
                System.out.println("2do try ");
            } catch (IOException ex1) {
                Logger.getLogger(CorrespondenciaServicioController.class.getName()).log(Level.SEVERE, null, ex1);
                System.out.println("2do catch " + ex1.getMessage());
            } catch (Exception e) {

            }
            System.out.println("1er catch " + ex.getMessage());

        } catch (Exception ex) {
            httpServletResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            try {
                correspondenciaServicioService.rollback();
            } catch (Exception e) {
            }
            System.out.println("3er catch " + ex.getMessage());
        } finally {
            try {
                correspondenciaServicioService.close();
            } catch (Exception e) {

            }
        }
    }
}
