/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comiteetica.controller;

import com.comiteetica.hibernate.model.SerieCorrelativo;
import com.comiteetica.hibernate.model.SerieCorrelativoId;
import com.comiteetica.hibernate.service.SerieCorrelativoService;
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
public class SerieCorrelativoController {
    @Autowired
    JsonTransformer jsonTransformer;
    
    @Autowired
    SerieCorrelativoService serieCorrelativoService;
    
    @RequestMapping(value = "/SerieCorrelativo/{idSerie}/{idSerieCorrelativo}", method = RequestMethod.GET, produces = "application/json")
    public void read(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @PathVariable("idSerie") String idSerie, @PathVariable("idSerieCorrelativo") String idSerieCorrelativo) {
        try {
            SerieCorrelativoId serieCorrelativoId=new SerieCorrelativoId(idSerie,idSerieCorrelativo);
            SerieCorrelativo serieCorrelativo = serieCorrelativoService.read(serieCorrelativoId);
            String jsonSalida = jsonTransformer.toJson(serieCorrelativo);
            
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
            } catch (IOException ex1) {
                Logger.getLogger(InvestigadorController.class.getName()).log(Level.SEVERE, null, ex1);
            }
            
        } catch (Exception ex) {
            httpServletResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            System.out.println("catch "+ex.getMessage());
        }

    }
}
