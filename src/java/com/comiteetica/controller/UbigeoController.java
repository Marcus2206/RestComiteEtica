/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comiteetica.controller;

import com.comiteetica.hibernate.model.Departamento;
import com.comiteetica.hibernate.model.Distrito;
import com.comiteetica.hibernate.model.Provincia;
import com.comiteetica.hibernate.service.DepartamentoService;
import com.comiteetica.hibernate.service.DistritoService;
import com.comiteetica.hibernate.service.ProvinciaService;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author rasec
 */
@Controller
public class UbigeoController {
    @Autowired
    JsonTransformer jsonTransformer;
    
//    @Autowired
//    SerieCorrelativoService serieCorrelativoService;
    
    @Autowired
    DepartamentoService departamentoService;
    
    @Autowired
    ProvinciaService provinciaService;
    
    @Autowired
    DistritoService distritoService;
    
    @RequestMapping(value = "/DepartamentoFindAll", method = RequestMethod.GET, produces = "application/json")
    public void findAllDepartamento(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        try {
            departamentoService.beginTransaction();
            List<Departamento> departamentos = departamentoService.getAllDepartamento();
            
            String jsonSalida = jsonTransformer.toJson(departamentos);            
            httpServletResponse.setStatus(HttpServletResponse.SC_OK);
            httpServletResponse.setContentType("application/json; charset=UTF-8");
            departamentoService.commit();
            httpServletResponse.getWriter().println(jsonSalida);
        } catch (BussinessException ex) {
            List<BussinessMessage> bussinessMessage=ex.getBussinessMessages();
            String jsonSalida = jsonTransformer.toJson(bussinessMessage);
            
            httpServletResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            httpServletResponse.setContentType("application/json; charset=UTF-8");
            try {
                httpServletResponse.getWriter().println(jsonSalida);
                departamentoService.rollback();
            } catch (IOException ex1) {
                Logger.getLogger(UbigeoController.class.getName()).log(Level.SEVERE, null, ex1);
            } catch(Exception ee){
                
            }
            
        } catch (Exception ex) {
            try{
                departamentoService.rollback();
            }catch(Exception ee){
                
            }
            httpServletResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            System.out.println("catch "+ex.getMessage());
        } finally{
            try{
                departamentoService.close();
            }catch(Exception ee){
                
            }
        }

    }
      
    @RequestMapping(value = "/ProvinciaByDepartamentoFind/{idDepartamento}", method = RequestMethod.GET, produces = "application/json")
    public void findProvinciaByDepartamento(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @PathVariable String idDepartamento) {
        try {
            provinciaService.beginTransaction();
            List<Provincia> provincias = provinciaService.getAllProvinciaByDepartamento(idDepartamento);
            
            String jsonSalida = jsonTransformer.toJson(provincias);            
            httpServletResponse.setStatus(HttpServletResponse.SC_OK);
            httpServletResponse.setContentType("application/json; charset=UTF-8");
            departamentoService.commit();
            httpServletResponse.getWriter().println(jsonSalida);
        } catch (BussinessException ex) {
            List<BussinessMessage> bussinessMessage=ex.getBussinessMessages();
            String jsonSalida = jsonTransformer.toJson(bussinessMessage);
            
            httpServletResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            httpServletResponse.setContentType("application/json; charset=UTF-8");
            try {
                httpServletResponse.getWriter().println(jsonSalida);
                departamentoService.rollback();
            } catch (IOException ex1) {
                Logger.getLogger(UbigeoController.class.getName()).log(Level.SEVERE, null, ex1);
            } catch(Exception ee){
                
            }
            
        } catch (Exception ex) {
            try{
                departamentoService.rollback();
            }catch(Exception ee){
                
            }
            httpServletResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            System.out.println("catch "+ex.getMessage());
        } finally{
            try{
                departamentoService.close();
            }catch(Exception ee){
                
            }
        }

    }
    
    @RequestMapping(value = "/DistritoByDepartamentoProvinciaFind/{idDepartamento}/{idProvincia}", method = RequestMethod.GET, produces = "application/json")
    public void findDistritoByDepartamentoProvincia(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @PathVariable String idDepartamento, @PathVariable String idProvincia) {
        try {
            distritoService.beginTransaction();
            List<Distrito> distritos = distritoService.getAllDistritoByDepartamentoProvincia(idDepartamento,idProvincia);
            
            String jsonSalida = jsonTransformer.toJson(distritos);            
            httpServletResponse.setStatus(HttpServletResponse.SC_OK);
            httpServletResponse.setContentType("application/json; charset=UTF-8");
            departamentoService.commit();
            httpServletResponse.getWriter().println(jsonSalida);
        } catch (BussinessException ex) {
            List<BussinessMessage> bussinessMessage=ex.getBussinessMessages();
            String jsonSalida = jsonTransformer.toJson(bussinessMessage);
            
            httpServletResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            httpServletResponse.setContentType("application/json; charset=UTF-8");
            try {
                httpServletResponse.getWriter().println(jsonSalida);
                departamentoService.rollback();
            } catch (IOException ex1) {
                Logger.getLogger(UbigeoController.class.getName()).log(Level.SEVERE, null, ex1);
            } catch(Exception ee){
                
            }
            
        } catch (Exception ex) {
            try{
                departamentoService.rollback();
            }catch(Exception ee){
                
            }
            httpServletResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            System.out.println("catch "+ex.getMessage());
        } finally{
            try{
                departamentoService.close();
            }catch(Exception ee){
                
            }
        }

    }
}
