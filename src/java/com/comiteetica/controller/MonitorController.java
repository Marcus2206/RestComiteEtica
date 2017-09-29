/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comiteetica.controller;

import com.comiteetica.hibernate.model.Monitor;
import com.comiteetica.hibernate.model.SerieCorrelativo;
import com.comiteetica.hibernate.service.MonitorService;
import com.comiteetica.hibernate.service.SerieCorrelativoService;
import com.comiteetica.json.JsonTransformer;
import com.comiteetica.persistencia.BussinessException;
import com.comiteetica.persistencia.BussinessMessage;
import java.io.IOException;
import java.sql.Date;
import java.time.Instant;
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
public class MonitorController {
    @Autowired
    JsonTransformer jsonTransformer;
    
    @Autowired
    MonitorService monitorService;
    
    @Autowired
    SerieCorrelativoService serieCorrelativoService;
    
    @RequestMapping(value = "/MonitorRead/{idMonitor}", method = RequestMethod.GET, produces = "application/json")
    public void readMonitor(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @PathVariable("idMonitor") String idMonitor) {
        try {
            monitorService.beginTransaction();
            Monitor monitor = monitorService.read(idMonitor);
            String jsonSalida = jsonTransformer.toJson(monitor); 
            monitorService.commit();
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
                monitorService.rollback();
            } catch (IOException ex1) {
                Logger.getLogger(MonitorController.class.getName()).log(Level.SEVERE, null, ex1);
            } catch(Exception ee){
                
            }
            
        } catch (Exception ex) {
            try{
                monitorService.rollback();
            }catch(Exception ee){
                
            }
            httpServletResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            System.out.println("catch "+ex.getMessage());
        } finally{
            try{
                monitorService.close();
            }catch(Exception ee){
                
            }
        }

    }

    
    @RequestMapping(value = "/MonitorInsert", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public void insertMonitor(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @RequestBody String jsonEntrada) {
        
        try {
            monitorService.beginTransaction();
            Monitor monitor = (Monitor) jsonTransformer.fromJson(jsonEntrada, Monitor.class);
            java.util.Date date = Date.from(Instant.now());
            SerieCorrelativo seriecorrelativo=serieCorrelativoService.readNextSerieCorrelativo("MON", date);
            monitor.setIdMonitor(seriecorrelativo.getId().getIdSerie()+seriecorrelativo.getUltimoUsado());
            monitorService.create(monitor);
            seriecorrelativo.setFechaModificacion(date);
            serieCorrelativoService.update(seriecorrelativo);
            
            String jsonSalida = jsonTransformer.toJson(monitor);
            monitorService.commit();
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
                monitorService.rollback();
            } catch (IOException ex1) {
                Logger.getLogger(CoordinadorController.class.getName()).log(Level.SEVERE, null, ex1);
            }catch(Exception ee){
                
            }
            System.out.println("catch 1"+ex.getMessage());
            
        } catch (Exception ex) {
            httpServletResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            httpServletResponse.setContentType("text/plain; charset=UTF-8");
            try {
                ex.printStackTrace(httpServletResponse.getWriter());
                monitorService.rollback();
                System.out.println("try 3"+ex.getMessage());
            } catch (IOException ex1) {
                Logger.getLogger(CoordinadorController.class.getName()).log(Level.SEVERE, null, ex1);
                System.out.println("catch 4"+ex1.getMessage());
            } catch(Exception ee){
                
            }
            System.out.println("catch 3"+ex.getMessage());
        } finally {
            try{
                monitorService.close();
            }
            catch(Exception ee){
                
            }
        }
    }

    @RequestMapping(value = "/MonitorFindAll", method = RequestMethod.GET, produces = "application/json")
    public void findAllMonitor(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, String jsonEntrada) {
        try {
            monitorService.beginTransaction();
            List<Monitor> coordinadors = monitorService.getAllMonitor();
            String jsonSalida = jsonTransformer.toJson(coordinadors);
            monitorService.commit();
            httpServletResponse.addHeader("Access-Control-Allow-Origin", "*");
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
                monitorService.rollback();
                System.out.println("2do try ");
            } catch (IOException ex1) {
                Logger.getLogger(CoordinadorController.class.getName()).log(Level.SEVERE, null, ex1);
                System.out.println("2do catch "+ex1.getMessage());
            } catch(Exception ee){
                
            }
            
            System.out.println("1er catch "+ex.getMessage());
            
        } catch (Exception ex) {
            httpServletResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            try{
                monitorService.rollback();
            }catch(Exception ee){
                
            }
            
            System.out.println("3er catch "+ex.getMessage());
        } finally{
            try{
                monitorService.close();
            }catch(Exception ee){
                
            }
        }

    }
    
    @RequestMapping(value = "/MonitorUpdate", method = RequestMethod.PUT, consumes = "application/json", produces = "application/json")
    public void updateMonitor(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @RequestBody String jsonEntrada) {
        try {
            monitorService.beginTransaction();
            Monitor monitor = (Monitor) jsonTransformer.fromJson(jsonEntrada, Monitor.class);
            monitorService.update(monitor);
            String jsonSalida = jsonTransformer.toJson(monitor);
            monitorService.commit();
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
                monitorService.rollback();
            } catch (IOException ex1) {
                Logger.getLogger(MonitorController.class.getName()).log(Level.SEVERE, null, ex1);
            } catch (Exception ee){
                
            }

        } catch (IOException ex) {
            httpServletResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            httpServletResponse.setContentType("text/plain; charset=UTF-8");
            try {
                ex.printStackTrace(httpServletResponse.getWriter());
                monitorService.rollback();
            } catch (IOException ex1) {
                Logger.getLogger(MonitorController.class.getName()).log(Level.SEVERE, null, ex1);
            } catch (Exception ee){
                
            }
        }finally{
            try{
                monitorService.close();
            }catch(Exception eee){
                
            }
        }
    }
    
    @RequestMapping(value = "/MonitorDelete", method = RequestMethod.PUT, consumes = "application/json")
    public void deleteMonitor(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @RequestParam("idMonitor") String idMonitor) {
        try {
            monitorService.beginTransaction();
            Monitor monitor = monitorService.read(idMonitor);
            monitorService.delete(monitor);
            monitorService.commit();
            
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
                monitorService.rollback();
            } catch (IOException ex1) {
                Logger.getLogger(MonitorController.class.getName()).log(Level.SEVERE, null, ex1);
            } catch(Exception eee){
                
            }
        } catch (Exception ex) {
            httpServletResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            httpServletResponse.setContentType("text/plain; charset=UTF-8");
            try {
                ex.printStackTrace(httpServletResponse.getWriter());
                monitorService.rollback();
            } catch (IOException ex1) {
                Logger.getLogger(MonitorController.class.getName()).log(Level.SEVERE, null, ex1);
            } catch(Exception eee){
                
            }
        }finally{
            try{
                monitorService.close();
            }catch(Exception ee){
                
            }
        }
    }
    
}
