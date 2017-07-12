/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comiteetica.controller;

import com.comiteetica.hibernate.model.Patrocinador;
import com.comiteetica.hibernate.model.SerieCorrelativo;
import com.comiteetica.hibernate.service.PatrocinadorService;
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
public class PatrocinadorController {
    @Autowired
    JsonTransformer jsonTransformer;
    
    @Autowired
    PatrocinadorService patrocinadorService;
    
    @Autowired
    SerieCorrelativoService serieCorrelativoService;
    
    
    @RequestMapping(value = "/PatrocinadorRead/{idPatrocinador}", method = RequestMethod.GET, produces = "application/json")
    public void readPatrocinador(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @PathVariable("idPatrocinador") String idPatrocinador) {
        try {
            patrocinadorService.beginTransaction();
            Patrocinador patrocinador = patrocinadorService.read(idPatrocinador);
            String jsonSalida = jsonTransformer.toJson(patrocinador); 
            patrocinadorService.commit();
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
                patrocinadorService.rollback();
            } catch (IOException ex1) {
                Logger.getLogger(PatrocinadorController.class.getName()).log(Level.SEVERE, null, ex1);
            } catch(Exception ee){
                
            }
            
        } catch (Exception ex) {
            try{
                patrocinadorService.rollback();
            }catch(Exception ee){
                
            }
            httpServletResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            System.out.println("catch "+ex.getMessage());
        } finally{
            try{
                patrocinadorService.close();
            }catch(Exception ee){
                
            }
        }

    }
    
    
    @RequestMapping(value = "/PatrocinadorUpdate", method = RequestMethod.PUT, consumes = "application/json", produces = "application/json")
    public void updatePatrocinador(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @RequestBody String jsonEntrada) {
        try {
            patrocinadorService.beginTransaction();
            Patrocinador patrocinador = (Patrocinador) jsonTransformer.fromJson(jsonEntrada, Patrocinador.class);
            patrocinadorService.update(patrocinador);
            String jsonSalida = jsonTransformer.toJson(patrocinador);
            patrocinadorService.commit();
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
                patrocinadorService.rollback();
            } catch (IOException ex1) {
                Logger.getLogger(PatrocinadorController.class.getName()).log(Level.SEVERE, null, ex1);
            } catch (Exception ee){
                
            }

        } catch (IOException ex) {
            httpServletResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            httpServletResponse.setContentType("text/plain; charset=UTF-8");
            try {
                ex.printStackTrace(httpServletResponse.getWriter());
                patrocinadorService.rollback();
            } catch (IOException ex1) {
                Logger.getLogger(PatrocinadorController.class.getName()).log(Level.SEVERE, null, ex1);
            } catch (Exception ee){
                
            }
        }finally{
            try{
                patrocinadorService.close();
            }catch(Exception eee){
                
            }
        }
    }
    
    @RequestMapping(value = "/PatrocinadorDelete", method = RequestMethod.PUT, consumes = "application/json")
    public void deletePatrocinador(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @RequestBody String jsonEntrada) {
        try {
            patrocinadorService.beginTransaction();
            Patrocinador patrocinador = (Patrocinador) jsonTransformer.fromJson(jsonEntrada, Patrocinador.class);
            patrocinadorService.delete(patrocinador);
            patrocinadorService.commit();
            
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
                patrocinadorService.rollback();
            } catch (IOException ex1) {
                Logger.getLogger(PatrocinadorController.class.getName()).log(Level.SEVERE, null, ex1);
            } catch(Exception eee){
                
            }
        } catch (Exception ex) {
            httpServletResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            httpServletResponse.setContentType("text/plain; charset=UTF-8");
            try {
                ex.printStackTrace(httpServletResponse.getWriter());
                patrocinadorService.rollback();
            } catch (IOException ex1) {
                Logger.getLogger(PatrocinadorController.class.getName()).log(Level.SEVERE, null, ex1);
            } catch(Exception eee){
                
            }
        }finally{
            try{
                patrocinadorService.close();
            }catch(Exception ee){
                
            }
        }
    }
    
    @RequestMapping(value = "/PatrocinadorFindAll", method = RequestMethod.GET, produces = "application/json")
    public void findAllPatrocinador(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, String jsonEntrada) {
        try {
            patrocinadorService.beginTransaction();
            List<Patrocinador> coordinadors = patrocinadorService.getAllPatrocinador();
            String jsonSalida = jsonTransformer.toJson(coordinadors);
            patrocinadorService.commit();
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
                patrocinadorService.rollback();
                System.out.println("2do try ");
            } catch (IOException ex1) {
                Logger.getLogger(PatrocinadorController.class.getName()).log(Level.SEVERE, null, ex1);
                System.out.println("2do catch "+ex1.getMessage());
            } catch(Exception ee){
                
            }
            
            System.out.println("1er catch "+ex.getMessage());
            
        } catch (Exception ex) {
            httpServletResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            try{
                patrocinadorService.rollback();
            }catch(Exception ee){
                
            }
            
            System.out.println("3er catch "+ex.getMessage());
        } finally{
            try{
                patrocinadorService.close();
            }catch(Exception ee){
                
            }
        }

    }

    @RequestMapping(value = "/PatrocinadorInsert", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public void insertPatrocinador(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @RequestBody String jsonEntrada) {
        
        try {
            patrocinadorService.beginTransaction();
            Patrocinador patrocinador = (Patrocinador) jsonTransformer.fromJson(jsonEntrada, Patrocinador.class);
            java.util.Date date = Date.from(Instant.now());
            SerieCorrelativo seriecorrelativo=serieCorrelativoService.readNextSerieCorrelativo("PTR", date);
            patrocinador.setIdPatrocinador(seriecorrelativo.getId().getIdSerie()+seriecorrelativo.getUltimoUsado());
            patrocinadorService.create(patrocinador);
            seriecorrelativo.setFechaModificacion(date);
            serieCorrelativoService.update(seriecorrelativo);
            String jsonSalida = jsonTransformer.toJson(patrocinador);
            patrocinadorService.commit();
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
                patrocinadorService.rollback();
            } catch (IOException ex1) {
                Logger.getLogger(PatrocinadorController.class.getName()).log(Level.SEVERE, null, ex1);
            }catch(Exception ee){
                
            }
            System.out.println("catch 1"+ex.getMessage());
            
        } catch (Exception ex) {
            httpServletResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            httpServletResponse.setContentType("text/plain; charset=UTF-8");
            try {
                ex.printStackTrace(httpServletResponse.getWriter());
                patrocinadorService.rollback();
                System.out.println("try 3"+ex.getMessage());
            } catch (IOException ex1) {
                Logger.getLogger(PatrocinadorController.class.getName()).log(Level.SEVERE, null, ex1);
                System.out.println("catch 4"+ex1.getMessage());
            } catch(Exception ee){
                
            }
            System.out.println("catch 3"+ex.getMessage());
        } finally {
            try{
                patrocinadorService.close();
            }
            catch(Exception ee){
                
            }
        }
    }
    
}
