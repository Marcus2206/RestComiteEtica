/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comiteetica.controller;

import com.comiteetica.hibernate.model.InvestigacionCoordinador;
import com.comiteetica.hibernate.model.InvestigacionMonitor;
import com.comiteetica.hibernate.model.InvestigacionMonitorId;
import com.comiteetica.hibernate.model.InvestigacionSede;
import com.comiteetica.hibernate.model.InvestigacionSedeId;
import com.comiteetica.hibernate.service.InvestigacionSedeService;
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
public class InvestigacionSedeController {
    
    @Autowired
    private JsonTransformer jsonTransformer;
    
    @Autowired
    private InvestigacionSedeService investigacionSedeService;
       
    @RequestMapping(value = "/InvestigacionSedeInsert", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public void insertInvestigacionSede(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @RequestBody String jsonEntrada) {
        
        try {
            investigacionSedeService.beginTransaction();
            InvestigacionSede investigacionCoordinador = (InvestigacionSede) jsonTransformer.fromJson(jsonEntrada, InvestigacionSede.class);
            investigacionSedeService.create(investigacionCoordinador);
            
            String jsonSalida = jsonTransformer.toJson(investigacionCoordinador);
            investigacionSedeService.commit();
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
                investigacionSedeService.rollback();
            } catch (IOException ex1) {
                Logger.getLogger(InvestigacionCoordinadorController.class.getName()).log(Level.SEVERE, null, ex1);
            } catch(Exception e){
                
            }
            System.out.println("catch 1"+ex.getMessage());
            
        } catch (Exception ex) {
            httpServletResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            httpServletResponse.setContentType("text/plain; charset=UTF-8");
            try {
                ex.printStackTrace(httpServletResponse.getWriter());
                investigacionSedeService.rollback();
                System.out.println("try 3"+ex.getMessage());
            } catch (IOException ex1) {
                Logger.getLogger(InvestigacionCoordinadorController.class.getName()).log(Level.SEVERE, null, ex1);
                System.out.println("catch 4"+ex1.getMessage());
            } catch (Exception e){
                
            }
            System.out.println("catch 3"+ex.getMessage());
        }finally{
            try{
                investigacionSedeService.close();
            }catch (Exception e){
                
            }
        }
    }

    
    @RequestMapping(value = "/InvestigacionSedeDelete", method = RequestMethod.PUT, consumes = "application/json")
    public void deleteInvestigacionSede(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @RequestBody String jsonEntrada) {
        try {
            investigacionSedeService.beginTransaction();
            InvestigacionSede investigacionSede = (InvestigacionSede) jsonTransformer.fromJson(jsonEntrada, InvestigacionSede.class);
            investigacionSedeService.delete(investigacionSede);
            investigacionSedeService.commit();
            httpServletResponse.setStatus(HttpServletResponse.SC_OK);
            
        } catch (BussinessException ex) {
            List<BussinessMessage> bussinessMessage=ex.getBussinessMessages();
            String jsonSalida = jsonTransformer.toJson(bussinessMessage);
            
            httpServletResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            httpServletResponse.setContentType("application/json; charset=UTF-8");
            try {
                httpServletResponse.getWriter().println(jsonSalida);
                System.out.println(ex.getMessage());
                investigacionSedeService.rollback();
            } catch (IOException ex1) {
                Logger.getLogger(InvestigacionCoordinadorController.class.getName()).log(Level.SEVERE, null, ex1);
            } catch (Exception e){
                
            }

            
        } catch (Exception ex) {
            httpServletResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            httpServletResponse.setContentType("text/plain; charset=UTF-8");
            try {
                ex.printStackTrace(httpServletResponse.getWriter());
                System.out.println(ex.getMessage());
                investigacionSedeService.rollback();
            } catch (IOException ex1) {
                Logger.getLogger(InvestigacionCoordinadorController.class.getName()).log(Level.SEVERE, null, ex1);
            } catch(Exception e){
                
            }
        }finally{
            try{
                investigacionSedeService.close();
            }catch(Exception e){
                
            }
        }
    }
    
    @RequestMapping(value = "/InvestigacionSedeRead", method = RequestMethod.PATCH, produces = "application/json",consumes = "application/json")
    public void readInvestigacionSede(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,@RequestBody String jsonEntrada) {
        //Product prod=new Product();
        try {
            InvestigacionSedeId investigacionSedeId = (InvestigacionSedeId)jsonTransformer.fromJson(jsonEntrada, InvestigacionSedeId.class);
            InvestigacionSede investigacionSede = investigacionSedeService.read(investigacionSedeId);
            String jsonSalida = jsonTransformer.toJson(investigacionSede);
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
                System.out.println("2do try ");
            } catch (IOException ex1) {
                Logger.getLogger(InvestigacionCoordinadorController.class.getName()).log(Level.SEVERE, null, ex1);
                System.out.println("2do catch "+ex1.getMessage());
            }
            
            System.out.println("1er catch "+ex.getMessage());
            
        } catch (Exception ex) {
            httpServletResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            System.out.println("3er catch "+ex.getMessage());
        }

    }

    
    @RequestMapping(value = "/InvestigacionSedeByIdInvestigacionFind/{idInvestigacion}", method = RequestMethod.GET, produces = "application/json",consumes = "application/json")
    public void findInvestigacionSedeByIdInvestigacion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,@PathVariable("idInvestigacion") String idInvestigacion) {
        try {
            
            investigacionSedeService.beginTransaction();
            String jsonSalida="[]";
            List<Object> investigacionSedes = investigacionSedeService.getInvestigacionSedeByIdInvestigacion(idInvestigacion);
            if(investigacionSedes!=null){
                if(investigacionSedes.size()>0){
                    if(investigacionSedes.get(0)!=null)
                    jsonSalida = "["+((String)investigacionSedes.get(0))+"]";
                }
            }
            investigacionSedeService.commit();
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
                investigacionSedeService.rollback();
                System.out.println("2do try ");
            } catch (IOException ex1) {
                Logger.getLogger(InvestigacionCoordinadorController.class.getName()).log(Level.SEVERE, null, ex1);
                System.out.println("2do catch "+ex1.getMessage());
            } catch(Exception e){
                
            }
            
            System.out.println("1er catch "+ex.getMessage());
            
        } catch (Exception ex) {
            httpServletResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            try{
                investigacionSedeService.rollback();
            }catch(Exception e){
            }
            System.out.println("3er catch "+ex.getMessage());
        }finally{
            try{
                investigacionSedeService.close();
            }catch(Exception e){
                
            }
        }

    }
}
