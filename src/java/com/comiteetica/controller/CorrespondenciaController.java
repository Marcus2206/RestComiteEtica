/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comiteetica.controller;

import com.comiteetica.hibernate.model.Correspondencia;
import com.comiteetica.hibernate.model.SerieCorrelativo;
import com.comiteetica.hibernate.service.CorrespondenciaService;
import com.comiteetica.hibernate.service.SerieCorrelativoService;
import com.comiteetica.json.JsonTransformer;
import com.comiteetica.persistencia.BussinessException;
import com.comiteetica.persistencia.BussinessMessage;
import java.io.IOException;
import java.time.Instant;
import java.util.Date;
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
@RequestMapping(value = "/Correspondencia")
public class CorrespondenciaController {

    @Autowired
    private JsonTransformer jsonTransformer;

    @Autowired
    private CorrespondenciaService correspondeciaService;

    @Autowired
    private SerieCorrelativoService serieCorrelativoService;

    @RequestMapping(value = "/CorrespondenciaRead/{idCorrespondencia}", method = RequestMethod.GET, produces = "application/json")
    public void readCorrespondencia(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @PathVariable("idCorrespondencia") String idCorrespondencia) {
        try {
            correspondeciaService.beginTransaction();
            Correspondencia correspondencia = correspondeciaService.read(idCorrespondencia);
            String jsonSalida = jsonTransformer.toJson(correspondencia);
            correspondeciaService.commit();
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
                correspondeciaService.rollback();
            } catch (IOException ex1) {
                Logger.getLogger(CorrespondenciaController.class.getName()).log(Level.SEVERE, null, ex1);
            } catch (Exception ee) {

            }

        } catch (Exception ex) {
            try {
                correspondeciaService.rollback();
            } catch (Exception ee) {

            }
            httpServletResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            System.out.println("catch " + ex.getMessage());
        } finally {
            try {
                correspondeciaService.close();
            } catch (Exception ee) {

            }
        }

    }

    @RequestMapping(value = "/CorrespondenciaInsert", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public void insertCorrespondencia(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @RequestBody String jsonEntrada) {
        try {
            correspondeciaService.beginTransaction();
            Correspondencia correspondencia = (Correspondencia) jsonTransformer.fromJson(jsonEntrada, Correspondencia.class);
            java.util.Date date = Date.from(Instant.now());
            SerieCorrelativo seriecorrelativo = serieCorrelativoService.readNextSerieCorrelativo("CRP", date);
            correspondencia.setIdCorrespondencia(seriecorrelativo.getId().getIdSerie() + seriecorrelativo.getUltimoUsado());
            correspondeciaService.create(correspondencia);
            seriecorrelativo.setFechaModificacion(date);
            serieCorrelativoService.update(seriecorrelativo);
            String jsonSalida = jsonTransformer.toJson(correspondencia);
            correspondeciaService.commit();
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
                correspondeciaService.rollback();
            } catch (IOException ex1) {
                Logger.getLogger(CorrespondenciaController.class.getName()).log(Level.SEVERE, null, ex1);
            } catch (Exception ee) {

            }
            System.out.println("catch 1" + ex.getMessage());

        } catch (Exception ex) {
            httpServletResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            httpServletResponse.setContentType("text/plain; charset=UTF-8");
            try {
                ex.printStackTrace(httpServletResponse.getWriter());
                correspondeciaService.rollback();
                System.out.println("try 3" + ex.getMessage());
            } catch (IOException ex1) {
                Logger.getLogger(CorrespondenciaController.class.getName()).log(Level.SEVERE, null, ex1);
                System.out.println("catch 4" + ex1.getMessage());
            } catch (Exception ee) {

            }
            System.out.println("catch 3" + ex.getMessage());
        } finally {
            try {
                correspondeciaService.close();
            } catch (Exception ee) {

            }
        }
    }

    @RequestMapping(value = "/CorrespondenciaUpdate", method = RequestMethod.PUT, consumes = "application/json", produces = "application/json")
    public void updateCorrespondencia(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @RequestBody String jsonEntrada) {
        try {
            correspondeciaService.beginTransaction();
            Correspondencia correspondencia = (Correspondencia) jsonTransformer.fromJson(jsonEntrada, Correspondencia.class);
            correspondeciaService.update(correspondencia);
            String jsonSalida = jsonTransformer.toJson(correspondencia);
            correspondeciaService.commit();
            httpServletResponse.setStatus(HttpServletResponse.SC_OK);
            httpServletResponse.setContentType("application/json; charset=UTF-8");
            httpServletResponse.getWriter().println(jsonSalida);
            
        } catch (BussinessException ex) {
            List<BussinessMessage> bussinessMessage=ex.getBussinessMessages();
            String jsonSalida = jsonTransformer.toJson(bussinessMessage);
            
            httpServletResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            httpServletResponse.setContentType("application/json; charset=UTF-8");
            try {
                httpServletResponse.getWriter().println(jsonSalida);
                correspondeciaService.rollback();
            } catch (IOException ex1) {
                Logger.getLogger(CorrespondenciaController.class.getName()).log(Level.SEVERE, null, ex1);
            } catch (Exception ee){
                
            }

        } catch (IOException ex) {
            httpServletResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            httpServletResponse.setContentType("text/plain; charset=UTF-8");
            try {
                ex.printStackTrace(httpServletResponse.getWriter());
                correspondeciaService.rollback();
            } catch (IOException ex1) {
                Logger.getLogger(CorrespondenciaController.class.getName()).log(Level.SEVERE, null, ex1);
            } catch (Exception ee){
                
            }
        }finally{
            try{
                correspondeciaService.close();
            }catch(Exception eee){
                
            }
        }
    }
    
    @RequestMapping(value = "/CorrespondenciaListFindAll", method = RequestMethod.GET, produces = "application/json", consumes = "application/json")
    public void listFindAllCorrespondencia(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        try {

            correspondeciaService.beginTransaction();
            String jsonSalida = "[]";
            List<Object> correspondencias = correspondeciaService.getAllCorrespondenciaList();
            if (correspondencias != null) {
                if (correspondencias.size() > 0) {
                    if (correspondencias.get(0) != null) {
                        jsonSalida = "[" + ((String) correspondencias.get(0)) + "]";
                    }
                }
            }
//            System.out.println(jsonSalida);
            correspondeciaService.commit();
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
                correspondeciaService.rollback();
                System.out.println("2do try ");
            } catch (IOException ex1) {
                Logger.getLogger(CorrespondenciaController.class.getName()).log(Level.SEVERE, null, ex1);
                System.out.println("2do catch " + ex1.getMessage());
            } catch (Exception e) {

            }
            System.out.println("1er catch " + ex.getMessage());

        } catch (Exception ex) {
            httpServletResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            try {
                correspondeciaService.rollback();
            } catch (Exception e) {
            }
            System.out.println("3er catch " + ex.getMessage());
        } finally {
            try {
                correspondeciaService.close();
            } catch (Exception e) {

            }
        }
    }

    @RequestMapping(value = "/CorrespondenciaDelete", method = RequestMethod.PUT, consumes = "application/json")
    public void deleteCorrespondencia(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @RequestBody String jsonEntrada) {
        try {
            
            correspondeciaService.beginTransaction();
            Correspondencia correspondencia = (Correspondencia) jsonTransformer.fromJson(jsonEntrada, Correspondencia.class);
            correspondeciaService.delete(correspondencia);
            correspondeciaService.commit();
            httpServletResponse.setStatus(HttpServletResponse.SC_OK);
            //httpServletResponse.setContentType("application/json; charset=UTF-8");
            //httpServletResponse.getWriter().println(jsonSalida);
            
        } catch (BussinessException ex) {
            List<BussinessMessage> bussinessMessage=ex.getBussinessMessages();
            String jsonSalida = jsonTransformer.toJson(bussinessMessage);
            
            httpServletResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            httpServletResponse.setContentType("application/json; charset=UTF-8");
            try {
                httpServletResponse.getWriter().println(jsonSalida);
                correspondeciaService.rollback();
            } catch (IOException ex1) {
                Logger.getLogger(CorrespondenciaController.class.getName()).log(Level.SEVERE, null, ex1);
            } catch(Exception eee){
                
            }
            System.out.println("1er catch " + ex.getMessage());
        } catch (Exception ex) {
            httpServletResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            httpServletResponse.setContentType("text/plain; charset=UTF-8");
            try {
                ex.printStackTrace(httpServletResponse.getWriter());
                correspondeciaService.rollback();
            } catch (IOException ex1) {
                Logger.getLogger(CorrespondenciaController.class.getName()).log(Level.SEVERE, null, ex1);
            } catch(Exception eee){
                
            }
            System.out.println("1er catch " + ex.getMessage());
        }finally{
            try{
                correspondeciaService.close();
            }catch(Exception ee){
                
            }
        }
    }
    
    @RequestMapping(value = "/CorrespondenciaFindAll", method = RequestMethod.GET, produces = "application/json")
    public void findAllCorrespondencia(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, String jsonEntrada) {
        try {
            correspondeciaService.beginTransaction();
            List<Correspondencia> correspondencias = correspondeciaService.getAllCorrespondencia();
            String jsonSalida = jsonTransformer.toJson(correspondencias);
            correspondeciaService.commit();
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
                correspondeciaService.rollback();
                System.out.println("2do try ");
            } catch (IOException ex1) {
                Logger.getLogger(CorrespondenciaController.class.getName()).log(Level.SEVERE, null, ex1);
                System.out.println("2do catch " + ex1.getMessage());
            } catch (Exception ee) {

            }

            System.out.println("1er catch " + ex.getMessage());

        } catch (Exception ex) {
            httpServletResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            try {
                correspondeciaService.rollback();
            } catch (Exception ee) {

            }

            System.out.println("3er catch " + ex.getMessage());
        } finally {
            try {
                correspondeciaService.close();
            } catch (Exception ee) {

            }
        }

    }
}
