/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comiteetica.controller;

import com.comiteetica.hibernate.model.Coordinador;
import com.comiteetica.hibernate.model.SerieCorrelativo;
import com.comiteetica.hibernate.service.CoordinadorService;
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

/**
 *
 * @author rasec
 */
@Controller
public class CoordinadorController {
    @Autowired
    JsonTransformer jsonTransformer;
    
    @Autowired
    SerieCorrelativoService serieCorrelativoService;
    
    @Autowired
    CoordinadorService coordinadorService;
    
    @RequestMapping(value = "/CoordinadorRead/{idCoordinador}", method = RequestMethod.GET, produces = "application/json")
    public void readCoordinador(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @PathVariable("idCoordinador") String idCoordinador) {
        try {
            coordinadorService.beginTransaction();
            Coordinador coordinador = coordinadorService.read(idCoordinador);
            coordinadorService.commit();
            String jsonSalida = jsonTransformer.toJson(coordinador);            
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
                coordinadorService.rollback();
            } catch (IOException ex1) {
                Logger.getLogger(CoordinadorController.class.getName()).log(Level.SEVERE, null, ex1);
            } catch(Exception ee){
                
            }
            
        } catch (Exception ex) {
            try{
                coordinadorService.rollback();
            }catch(Exception ee){
                
            }
            httpServletResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            System.out.println("catch "+ex.getMessage());
        } finally{
            try{
                coordinadorService.close();
            }catch(Exception ee){
                
            }
        }

    }
      
    @RequestMapping(value = "/CoordinadorInsert", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public void insertCoordinador(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @RequestBody String jsonEntrada) {
        try {
            coordinadorService.beginTransaction();
            Coordinador coordinador = (Coordinador) jsonTransformer.fromJson(jsonEntrada, Coordinador.class);
            java.util.Date date = Date.from(Instant.now());
            SerieCorrelativo seriecorrelativo=serieCorrelativoService.readNextSerieCorrelativo("COD", date);
            coordinador.setIdCoordinador(seriecorrelativo.getId().getIdSerie()+seriecorrelativo.getUltimoUsado());
            coordinadorService.create(coordinador);
            seriecorrelativo.setFechaModificacion(date);
            serieCorrelativoService.update(seriecorrelativo);
            String jsonSalida = jsonTransformer.toJson(coordinador);
            coordinadorService.commit();
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
                coordinadorService.rollback();
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
                coordinadorService.rollback();
                System.out.println("try 3"+ex.getMessage());
            } catch (IOException ex1) {
                Logger.getLogger(CoordinadorController.class.getName()).log(Level.SEVERE, null, ex1);
                System.out.println("catch 4"+ex1.getMessage());
            } catch(Exception ee){
                
            }
            System.out.println("catch 3"+ex.getMessage());
        } finally {
            try{
                coordinadorService.close();
            }
            catch(Exception ee){
                
            }
        }
    }

    @RequestMapping(value = "/CoordinadorFindAll", method = RequestMethod.GET, produces = "application/json")
    public void findAllCoordinador(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, String jsonEntrada) {
        try {
            coordinadorService.beginTransaction();
            List<Coordinador> coordinadors = coordinadorService.getAllCoordinador();
            coordinadorService.commit();
            String jsonSalida = jsonTransformer.toJson(coordinadors);
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
                coordinadorService.rollback();
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
                coordinadorService.rollback();
            }catch(Exception ee){
                
            }
            
            System.out.println("3er catch "+ex.getMessage());
        } finally{
            try{
                coordinadorService.close();
            }catch(Exception ee){
                
            }
        }

    }

    @RequestMapping(value = "/CoordinadorUpdate", method = RequestMethod.PUT, consumes = "application/json", produces = "application/json")
    public void updateCoordinador(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @RequestBody String jsonEntrada) {
        try {
            coordinadorService.beginTransaction();
            Coordinador coordinador = (Coordinador) jsonTransformer.fromJson(jsonEntrada, Coordinador.class);
            coordinadorService.update(coordinador);
            coordinadorService.commit();
            String jsonSalida = jsonTransformer.toJson(coordinador);
            
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
                coordinadorService.rollback();
            } catch (IOException ex1) {
                Logger.getLogger(CoordinadorController.class.getName()).log(Level.SEVERE, null, ex1);
            } catch (Exception ee){
                
            }

        } catch (IOException ex) {
            httpServletResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            httpServletResponse.setContentType("text/plain; charset=UTF-8");
            try {
                ex.printStackTrace(httpServletResponse.getWriter());
                coordinadorService.rollback();
            } catch (IOException ex1) {
                Logger.getLogger(CoordinadorController.class.getName()).log(Level.SEVERE, null, ex1);
            } catch (Exception ee){
                
            }
        }finally{
            try{
                coordinadorService.close();
            }catch(Exception eee){
                
            }
        }
    }
    
    @RequestMapping(value = "/CoordinadorDelete", method = RequestMethod.PUT, consumes = "application/json")
    public void deleteCoordinador(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @RequestBody String jsonEntrada) {
        try {
            coordinadorService.beginTransaction();
            Coordinador coordinador = (Coordinador) jsonTransformer.fromJson(jsonEntrada, Coordinador.class);
            coordinadorService.delete(coordinador);
            coordinadorService.commit();
            
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
                coordinadorService.rollback();
            } catch (IOException ex1) {
                Logger.getLogger(CoordinadorController.class.getName()).log(Level.SEVERE, null, ex1);
            } catch(Exception eee){
                
            }
        } catch (Exception ex) {
            httpServletResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            httpServletResponse.setContentType("text/plain; charset=UTF-8");
            try {
                ex.printStackTrace(httpServletResponse.getWriter());
                coordinadorService.rollback();
            } catch (IOException ex1) {
                Logger.getLogger(CoordinadorController.class.getName()).log(Level.SEVERE, null, ex1);
            } catch(Exception eee){
                
            }
        }finally{
            try{
                coordinadorService.close();
            }catch(Exception ee){
                
            }
        }
    }
    
        
    @RequestMapping(value = "/CoordinadorSinIdInvestigacionFind/{idInvestigacion}", method = RequestMethod.GET, produces = "application/json")
    public void findCoordinadorSinIdInvestigacion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @PathVariable("idInvestigacion") String idInvestigacion) {
        try {
            coordinadorService.beginTransaction();
            List<Coordinador> coordinadors = coordinadorService.getCoordinadorSinIdInvestigacion(idInvestigacion);
            coordinadorService.commit();
            httpServletResponse.addHeader("Access-Control-Allow-Origin", "*");
            String jsonSalida = jsonTransformer.toJson(coordinadors);
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
                coordinadorService.rollback();
                System.out.println("2do try ");
            } catch (IOException ex1) {
                Logger.getLogger(CoordinadorController.class.getName()).log(Level.SEVERE, null, ex1);
                System.out.println("2do catch "+ex1.getMessage());
            } catch(Exception e){
                
            }
            
            System.out.println("1er catch "+ex.getMessage());
            
        } catch (Exception ex) {
            httpServletResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            try{
                coordinadorService.rollback();
            }catch(Exception e){
                
            }
            System.out.println("3er catch "+ex.getMessage());
        }finally{
            try{
                coordinadorService.close();
            }catch(Exception e){
                
            }
        }

    }
    
}
