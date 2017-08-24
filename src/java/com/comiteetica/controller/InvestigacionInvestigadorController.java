/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comiteetica.controller;

import com.comiteetica.hibernate.model.InvestigacionInvestigador;
import com.comiteetica.hibernate.model.InvestigacionInvestigadorId;
import com.comiteetica.hibernate.service.InvestigacionInvestigadorService;
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
public class InvestigacionInvestigadorController{
    
    @Autowired
    private JsonTransformer jsonTransformer;
    
    @Autowired
    private InvestigacionInvestigadorService investigacionInvestigadorService;
       
    @RequestMapping(value = "/InvestigacionInvestigadorInsert", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public void insertInvestigacionInvestigador(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @RequestBody String jsonEntrada) {
        
        try {
            investigacionInvestigadorService.beginTransaction();
            InvestigacionInvestigador investigacionInvestigador = (InvestigacionInvestigador) jsonTransformer.fromJson(jsonEntrada, InvestigacionInvestigador.class);
            investigacionInvestigadorService.create(investigacionInvestigador);
            String jsonSalida = jsonTransformer.toJson(investigacionInvestigador);
            investigacionInvestigadorService.commit();
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
                investigacionInvestigadorService.rollback();
            } catch (IOException ex1) {
                Logger.getLogger(InvestigacionInvestigadorController.class.getName()).log(Level.SEVERE, null, ex1);
            } catch(Exception e){
                
            }
            System.out.println("catch 1"+ex.getMessage());
            
        } catch (Exception ex) {
            httpServletResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            httpServletResponse.setContentType("text/plain; charset=UTF-8");
            try {
                ex.printStackTrace(httpServletResponse.getWriter());
                investigacionInvestigadorService.rollback();
                System.out.println("try 3"+ex.getMessage());
            } catch (IOException ex1) {
                Logger.getLogger(InvestigacionInvestigadorController.class.getName()).log(Level.SEVERE, null, ex1);
                System.out.println("catch 4"+ex1.getMessage());
            } catch (Exception e){
                
            }
            System.out.println("catch 3"+ex.getMessage());
        }finally{
            try{
                investigacionInvestigadorService.close();
            }catch(Exception e){
                
            }
        }
    }

    @RequestMapping(value = "/InvestigacionInvestigadorByIdInvestigacionFind/{idInvestigacion}", method = RequestMethod.GET, produces = "application/json")
    public void findInvestigacionInvestigadorByIdInvestigacion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @PathVariable String idInvestigacion) {
        //Product prod=new Product();
        try {
            investigacionInvestigadorService.beginTransaction();
            List<Object> objects = investigacionInvestigadorService.getInvestigacionInvestigadorByIdInvestigacion(idInvestigacion);
            String jsonSalida = jsonTransformer.toJson(objects);
            investigacionInvestigadorService.commit();
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
                investigacionInvestigadorService.rollback();
                System.out.println("2do try ");
            } catch (IOException ex1) {
                Logger.getLogger(InvestigacionInvestigadorController.class.getName()).log(Level.SEVERE, null, ex1);
                System.out.println("2do catch "+ex1.getMessage());
            } catch (Exception e){
                
            }
            
            System.out.println("1er catch "+ex.getMessage());
            
        } catch (Exception ex) {
            httpServletResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            try{
                investigacionInvestigadorService.rollback();
            }catch(Exception e){
                
            }
            System.out.println("3er catch "+ex.getMessage());
        }finally{
            try{
                investigacionInvestigadorService.close();
            }catch(Exception e){
                
            }
        }

    }
    
    @RequestMapping(value = "/InvestigacionInvestigadorDelete", method = RequestMethod.PUT, consumes = "application/json")
    public void deleteInvestigacionInvestigador(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,@RequestBody String jsonEntrada  ) {
        try {
            investigacionInvestigadorService.beginTransaction();
            InvestigacionInvestigador investigacionInvestigador = (InvestigacionInvestigador) jsonTransformer.fromJson(jsonEntrada, InvestigacionInvestigador.class);
            investigacionInvestigadorService.delete(investigacionInvestigador);
            investigacionInvestigadorService.commit();
            httpServletResponse.setStatus(HttpServletResponse.SC_NO_CONTENT);
            
        } catch (BussinessException ex) {
            List<BussinessMessage> bussinessMessage=ex.getBussinessMessages();
            String jsonSalida = jsonTransformer.toJson(bussinessMessage);
            
            httpServletResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            httpServletResponse.setContentType("application/json; charset=UTF-8");
            try {
                httpServletResponse.getWriter().println(jsonSalida);
                investigacionInvestigadorService.rollback();
                System.out.println("try 2: "+ex.getMessage());
            } catch (IOException ex1) {
                Logger.getLogger(InvestigacionInvestigadorController.class.getName()).log(Level.SEVERE, null, ex1);
                System.out.println("catch 3: "+ex1.getMessage());
            } catch (Exception e){
                
            }
            System.out.println("catch 2: "+ex.getMessage());
            
        } catch (Exception ex) {
            httpServletResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            try{
                investigacionInvestigadorService.rollback();
            }catch (Exception e){
                
            }
            System.out.println("catch 1: "+ex.getMessage());
        }finally{
            try{
                investigacionInvestigadorService.close();
            }catch (Exception e){
                
            }
        }

    }    
}
