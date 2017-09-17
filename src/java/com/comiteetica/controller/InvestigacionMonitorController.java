/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comiteetica.controller;

import com.comiteetica.hibernate.model.InvestigacionInvestigador;
import com.comiteetica.hibernate.model.InvestigacionInvestigadorId;
import com.comiteetica.hibernate.model.InvestigacionMonitor;
import com.comiteetica.hibernate.model.InvestigacionMonitorId;
import com.comiteetica.hibernate.service.InvestigacionMonitorService;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author rasec
 */
@Controller
public class InvestigacionMonitorController {
    @Autowired
    private JsonTransformer jsonTransformer;
    
    @Autowired
    private InvestigacionMonitorService investigacionMonitorService;
    
    @RequestMapping(value = "/InvestigacionMonitorRead", method = RequestMethod.PATCH, produces = "application/json",consumes = "application/json")
    public void readInvestigacionMonitor(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,@RequestBody String jsonEntrada) {
        try {
            InvestigacionMonitorId investigacionMonitorId = (InvestigacionMonitorId)jsonTransformer.fromJson(jsonEntrada, InvestigacionMonitorId.class);
            InvestigacionMonitor investigacionMonitor = investigacionMonitorService.read(investigacionMonitorId);
            String jsonSalida = jsonTransformer.toJson(investigacionMonitor);
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
    
}
