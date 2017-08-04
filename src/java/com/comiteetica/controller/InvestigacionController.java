/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comiteetica.controller;

import com.comiteetica.hibernate.model.Investigacion;
import com.comiteetica.hibernate.model.InvestigacionCoordinador;
import com.comiteetica.hibernate.model.SerieCorrelativo;
import com.comiteetica.hibernate.service.InvestigacionService;
import com.comiteetica.hibernate.service.SerieCorrelativoService;
import com.comiteetica.json.JsonTransformer;
import com.comiteetica.persistencia.BussinessException;
import com.comiteetica.persistencia.BussinessMessage;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
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
@RequestMapping("/Investigacion")
public class InvestigacionController {
    
    @Autowired 
    private JsonTransformer jsonTransformer;
    
    @Autowired
    private InvestigacionService investigacionService;
    
    @Autowired
    private SerieCorrelativoService serieCorrelativoService;
    
    @RequestMapping(value = "/InvestigacionRead/{idInvestigacion}", method = RequestMethod.GET, produces = "application/json")
    public void readInvestigacion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @PathVariable("idInvestigacion") String idInvestigacion) {
        try {            
            investigacionService.beginTransaction();
            Investigacion investigacion = investigacionService.read(idInvestigacion);
            
            String jsonSalida = jsonTransformer.toJson(investigacion);
            httpServletResponse.setStatus(HttpServletResponse.SC_OK);
            httpServletResponse.setContentType("application/json; charset=UTF-8");
            investigacionService.commit();
            httpServletResponse.getWriter().println(jsonSalida);
        } catch (BussinessException ex) {
            List<BussinessMessage> bussinessMessage=ex.getBussinessMessages();
            String jsonSalida = jsonTransformer.toJson(bussinessMessage);
            
            httpServletResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            httpServletResponse.setContentType("application/json; charset=UTF-8");
            try {
                httpServletResponse.getWriter().println(jsonSalida);
                investigacionService.rollback();
            } catch (IOException ex1) {
                Logger.getLogger(InvestigadorController.class.getName()).log(Level.SEVERE, null, ex1);
            } catch(Exception e){
                
            }
            
        } catch (Exception ex) {
            httpServletResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            try{
                investigacionService.rollback();
            }catch(Exception e){
                
            }
            System.out.println("catch "+ex.getMessage());
        }finally{
            try{
                investigacionService.close();
            }catch(Exception e){
                
            }
        }

    }
    
    @RequestMapping(value = "/InvestigacionInsert", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public void insertInvestigacion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @RequestBody String jsonEntrada) {
        
        try {
            investigacionService.beginTransaction();
            Investigacion investigacion = (Investigacion) jsonTransformer.fromJson(jsonEntrada, Investigacion.class);
            SerieCorrelativo seriecorrelativo=serieCorrelativoService.readNextSerieCorrelativo("INV",investigacion.getFechaIngreso());
            investigacion.setIdInvestigacion("INV"+seriecorrelativo.getUltimoUsado());
            investigacionService.create(investigacion);
            serieCorrelativoService.update(seriecorrelativo);
            String jsonSalida = jsonTransformer.toJson(investigacion);
            investigacionService.commit();
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
                investigacionService.rollback();
            } catch (IOException ex1) {
                Logger.getLogger(InvestigacionController.class.getName()).log(Level.SEVERE, null, ex1);
            } catch (Exception e){
                
            }
            System.out.println("catch 1"+ex.getMessage());
            
        } catch (Exception ex) {
            httpServletResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            httpServletResponse.setContentType("text/plain; charset=UTF-8");
            try {
                ex.printStackTrace(httpServletResponse.getWriter());
                investigacionService.rollback();
                System.out.println("try 3"+ex.getMessage());
            } catch (IOException ex1) {
                Logger.getLogger(InvestigacionController.class.getName()).log(Level.SEVERE, null, ex1);
                System.out.println("catch 4"+ex1.getMessage());
            } catch (Exception e){
                
            }
            System.out.println("catch 3"+ex.getMessage());
        }finally{
            try{
                investigacionService.close();
            }catch(Exception e){
                
            }
        }
    }

    @RequestMapping(value = "/InvestigacionFindAll", method = RequestMethod.GET, produces = "application/json")
    public void findAllInvestigacion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        try {
            investigacionService.beginTransaction();
            String jsonSalida = "[]";
            List<Object> investigacions = investigacionService.getAllInvestigacionList();
            if (investigacions != null) {
                if (investigacions.size() > 0) {
                    if (investigacions.get(0) != null) {
                        jsonSalida = "[" + ((String) investigacions.get(0)) + "]";
                    }
                }
            }
            investigacionService.commit();
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
                investigacionService.rollback();
                System.out.println("2do try ");
            } catch (IOException ex1) {
                Logger.getLogger(InvestigacionController.class.getName()).log(Level.SEVERE, null, ex1);
                System.out.println("2do catch "+ex1.getMessage());
            } catch (Exception e){
                
            }
            System.out.println("1er catch "+ex.getMessage());
            
        } catch (Exception ex) {
            httpServletResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            try{
                investigacionService.rollback();
            }catch (Exception e){
                
            }
            System.out.println("3er catch "+ex.getMessage());
        }finally{
            try{
                investigacionService.close();
            }catch (Exception e){
                
            }
        }
    }
    
    @RequestMapping(value = "/InvestigacionUpdate", method = RequestMethod.PUT, consumes = "application/json", produces = "application/json")
    public void updateInvestigacion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @RequestBody String jsonEntrada) {
        try {
            investigacionService.beginTransaction();
            Investigacion investigacion = (Investigacion) jsonTransformer.fromJson(jsonEntrada, Investigacion.class);
            investigacionService.update(investigacion);
            String jsonSalida = jsonTransformer.toJson(investigacion);       
            investigacionService.commit();
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
                investigacionService.rollback();
            } catch (IOException ex1) {
                Logger.getLogger(InvestigacionController.class.getName()).log(Level.SEVERE, null, ex1);
            } catch (Exception e){
                
            }

        } catch (IOException ex) {
            httpServletResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            httpServletResponse.setContentType("text/plain; charset=UTF-8");
            try {
                ex.printStackTrace(httpServletResponse.getWriter());
                investigacionService.rollback();
            } catch (IOException ex1) {
                Logger.getLogger(InvestigacionController.class.getName()).log(Level.SEVERE, null, ex1);
            } catch(Exception e){
                
            }
        }finally{
            try{
                investigacionService.close();
            }catch (Exception e){
                
            }
        }
    }
    
    @RequestMapping(value = "/InvestigacionDelete", method = RequestMethod.PUT, consumes = "application/json")
    public void deleteInvestigacion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @RequestBody String jsonEntrada) {
        try {
            investigacionService.beginTransaction();
            Investigacion investigacion = (Investigacion) jsonTransformer.fromJson(jsonEntrada, Investigacion.class);
            investigacionService.delete(investigacion);
            String jsonSalida = jsonTransformer.toJson(investigacion); 
            investigacionService.commit();
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
                investigacionService.rollback();
            } catch (IOException ex1) {
                Logger.getLogger(InvestigacionController.class.getName()).log(Level.SEVERE, null, ex1);
            } catch (Exception e){
                
            }

        } catch (Exception ex) {
            httpServletResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            httpServletResponse.setContentType("text/plain; charset=UTF-8");
            try {
                ex.printStackTrace(httpServletResponse.getWriter());
                investigacionService.rollback();
            } catch (IOException ex1) {
                Logger.getLogger(InvestigacionController.class.getName()).log(Level.SEVERE, null, ex1);
            } catch (Exception e){
                
            }
        }finally{
            try{
                investigacionService.close();
            }catch(Exception e){
                
            }
        }
    }
}


