/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comiteetica.controller;

import com.comiteetica.hibernate.model.Investigador;
import com.comiteetica.hibernate.model.SerieCorrelativo;
import com.comiteetica.hibernate.service.InvestigadorService;
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
public class InvestigadorController {
    @Autowired
    JsonTransformer jsonTransformer;
    
    @Autowired
    InvestigadorService investigadorService;
    
    @Autowired
    SerieCorrelativoService serieCorrelativoService;
    
    @RequestMapping(value = "/InvestigadorRead/{idInvestigador}", method = RequestMethod.GET, produces = "application/json")
    public void readInvestigador(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @PathVariable("idInvestigador") String idInvestigador) {
        try {
            investigadorService.beginTransaction();
            Investigador investigador = investigadorService.read(idInvestigador);
            String jsonSalida = jsonTransformer.toJson(investigador);   
            investigadorService.commit();
            httpServletResponse.setStatus(HttpServletResponse.SC_OK);
            httpServletResponse.setContentType("application/json; charset=UTF-8");
            httpServletResponse.getWriter().println(jsonSalida);
            
        } catch (BussinessException ex) {
            List<BussinessMessage> bussinessMessage=ex.getBussinessMessages();
            String jsonSalida = jsonTransformer.toJson(bussinessMessage);
            
            httpServletResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            httpServletResponse.setContentType("application/json; charset=UTF-8");
            try {
                investigadorService.rollback();
                httpServletResponse.getWriter().println(jsonSalida);
            } catch (IOException ex1) {
                Logger.getLogger(InvestigadorController.class.getName()).log(Level.SEVERE, null, ex1);
            } catch(Exception e){
                
            }
            
        } catch (Exception ex) {
            httpServletResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            System.out.println("catch "+ex.getMessage());
        }finally{
            try{
                investigadorService.close();
            }catch(Exception e){
                
            }
        }

    }
 
    @RequestMapping(value = "/InvestigadorInsert", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public void insertInvestigador(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @RequestBody String jsonEntrada) {
        
        try {
            investigadorService.beginTransaction();
            Investigador investigador = (Investigador) jsonTransformer.fromJson(jsonEntrada, Investigador.class);
            java.util.Date date = Date.from(Instant.now());
            SerieCorrelativo seriecorrelativo=serieCorrelativoService.readNextSerieCorrelativo("INT", date);
            investigador.setIdInvestigador(seriecorrelativo.getId().getIdSerie()+seriecorrelativo.getUltimoUsado());
            investigadorService.create(investigador);
            seriecorrelativo.setFechaModificacion(date);
            serieCorrelativoService.update(seriecorrelativo);
            String jsonSalida = jsonTransformer.toJson(investigador);
            investigadorService.commit();
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
                investigadorService.rollback();
            } catch (IOException ex1) {
                Logger.getLogger(InvestigadorController.class.getName()).log(Level.SEVERE, null, ex1);
            }catch(Exception ee){
                
            }
            System.out.println("catch 1"+ex.getMessage());
            
        } catch (Exception ex) {
            httpServletResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            httpServletResponse.setContentType("text/plain; charset=UTF-8");
            try {
                ex.printStackTrace(httpServletResponse.getWriter());
                investigadorService.rollback();
                System.out.println("try 3"+ex.getMessage());
            } catch (IOException ex1) {
                Logger.getLogger(InvestigadorController.class.getName()).log(Level.SEVERE, null, ex1);
                System.out.println("catch 4"+ex1.getMessage());
            } catch(Exception ee){
                
            }
            System.out.println("catch 3"+ex.getMessage());
        } finally {
            try{
                investigadorService.close();
            }
            catch(Exception ee){
                
            }
        }
    }

    @RequestMapping(value = "/InvestigadorSinIdInvestigacionFind/{idInvestigacion}", method = RequestMethod.GET, produces = "application/json")
    public void findInvestigadorSinIdInvestigacion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @PathVariable String idInvestigacion) {
        try {
            investigadorService.beginTransaction();
            List<Investigador> investigadors = investigadorService.getInvestigadorSinIdInvestigacion(idInvestigacion);
            String jsonSalida = jsonTransformer.toJson(investigadors);
            investigadorService.commit();
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
                investigadorService.rollback();
                System.out.println("2do try ");
            } catch (IOException ex1) {
                Logger.getLogger(InvestigadorController.class.getName()).log(Level.SEVERE, null, ex1);
                System.out.println("2do catch "+ex1.getMessage());
            } catch (Exception e){
                
            }
            
            System.out.println("1er catch "+ex.getMessage());
            
        } catch (Exception ex) {
            httpServletResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            try{
                investigadorService.rollback();
            }catch(Exception e){
                
            }
            System.out.println("3er catch "+ex.getMessage());
        }finally{
            try{
                investigadorService.close();
            }catch(Exception e){
                
            }
        }

    }
    
    
    @RequestMapping(value = "/InvestigadorFindAll", method = RequestMethod.GET, produces = "application/json")
    public void findAllInvestigador(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, String jsonEntrada) {
        try {
            investigadorService.beginTransaction();
            List<Investigador> investigadors = investigadorService.getAllInvestigador();
            String jsonSalida = jsonTransformer.toJson(investigadors);
            investigadorService.commit();
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
                investigadorService.rollback();
                System.out.println("2do try ");
            } catch (IOException ex1) {
                Logger.getLogger(InvestigadorController.class.getName()).log(Level.SEVERE, null, ex1);
                System.out.println("2do catch "+ex1.getMessage());
            } catch(Exception ee){
                
            }
            
            System.out.println("1er catch "+ex.getMessage());
            
        } catch (Exception ex) {
            httpServletResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            try{
                investigadorService.rollback();
            }catch(Exception ee){
                
            }
            
            System.out.println("3er catch "+ex.getMessage());
        } finally{
            try{
                investigadorService.close();
            }catch(Exception ee){
                
            }
        }

    }

    @RequestMapping(value = "/InvestigadorUpdate", method = RequestMethod.PUT, consumes = "application/json", produces = "application/json")
    public void updateInvestigador(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @RequestBody String jsonEntrada) {
        try {
            investigadorService.beginTransaction();
            Investigador investigador = (Investigador) jsonTransformer.fromJson(jsonEntrada, Investigador.class);
            investigadorService.update(investigador);
            String jsonSalida = jsonTransformer.toJson(investigador);
            investigadorService.commit();            
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
                investigadorService.rollback();
            } catch (IOException ex1) {
                Logger.getLogger(InvestigadorController.class.getName()).log(Level.SEVERE, null, ex1);
            } catch (Exception ee){
                
            }

        } catch (IOException ex) {
            httpServletResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            httpServletResponse.setContentType("text/plain; charset=UTF-8");
            try {
                ex.printStackTrace(httpServletResponse.getWriter());
                investigadorService.rollback();
            } catch (IOException ex1) {
                Logger.getLogger(InvestigadorController.class.getName()).log(Level.SEVERE, null, ex1);
            } catch (Exception ee){
                
            }
        }finally{
            try{
                investigadorService.close();
            }catch(Exception eee){
                
            }
        }
    }
    
    @RequestMapping(value = "/InvestigadorDelete", method = RequestMethod.PUT, consumes = "application/json")
    public void deleteInvestigador(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @RequestBody String jsonEntrada) {
        try {
            investigadorService.beginTransaction();
            Investigador investigador = (Investigador) jsonTransformer.fromJson(jsonEntrada, Investigador.class);
            investigadorService.delete(investigador);
            investigadorService.commit();
            
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
                investigadorService.rollback();
            } catch (IOException ex1) {
                Logger.getLogger(InvestigadorController.class.getName()).log(Level.SEVERE, null, ex1);
            } catch(Exception eee){
                
            }
        } catch (Exception ex) {
            httpServletResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            httpServletResponse.setContentType("text/plain; charset=UTF-8");
            try {
                ex.printStackTrace(httpServletResponse.getWriter());
                investigadorService.rollback();
            } catch (IOException ex1) {
                Logger.getLogger(InvestigadorController.class.getName()).log(Level.SEVERE, null, ex1);
            } catch(Exception eee){
                
            }
        }finally{
            try{
                investigadorService.close();
            }catch(Exception ee){
                
            }
        }
    }
    
}
