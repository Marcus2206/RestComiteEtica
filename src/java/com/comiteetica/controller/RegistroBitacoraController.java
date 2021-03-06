/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comiteetica.controller;

import com.comiteetica.hibernate.model.Correspondencia;
import com.comiteetica.hibernate.model.RegistroBitacora;
import com.comiteetica.hibernate.model.RegistroBitacoraId;
import com.comiteetica.hibernate.service.RegistroBitacoraService;
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
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author rasec
 */
@Controller
@RequestMapping("/RegistroBitacora")
public class RegistroBitacoraController {

    @Autowired
    private JsonTransformer jsonTransformer;

    @Autowired
    private RegistroBitacoraService registroBitacoraService;

    @RequestMapping(value = "/RegistroBitacoraInsert", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public void insertRegistroBitacora(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @RequestBody String jsonEntrada) {
        try {
            registroBitacoraService.beginTransaction();
            RegistroBitacora registroBitacora = (RegistroBitacora) jsonTransformer.fromJson(jsonEntrada, RegistroBitacora.class);
            RegistroBitacoraId id = registroBitacora.getId();
            id.setIdBitacora(registroBitacoraService.getNextBitacoraByIdRegistro(id.getIdRegistro()));
            registroBitacoraService.create(registroBitacora);
            String jsonSalida = jsonTransformer.toJson(registroBitacora);
            registroBitacoraService.commit();
            httpServletResponse.setStatus(HttpServletResponse.SC_OK);
            httpServletResponse.setContentType("application/json; charset=UTF-8");
            httpServletResponse.getWriter().println(jsonSalida);
        } catch (BussinessException ex) {
            List<BussinessMessage> bussinessMessage = ex.getBussinessMessages();
            String jsonSalida = jsonTransformer.toJson(bussinessMessage);
            httpServletResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            httpServletResponse.setContentType("application/json; charset=UTF-8");
            try {
                httpServletResponse.getWriter().println(jsonSalida);
                registroBitacoraService.rollback();
            } catch (IOException ex1) {
                Logger.getLogger(RegistroBitacoraController.class.getName()).log(Level.SEVERE, null, ex1);
            } catch (Exception ee) {

            }
            System.out.println("catch 1" + ex.getMessage());

        } catch (Exception ex) {
            httpServletResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            httpServletResponse.setContentType("text/plain; charset=UTF-8");
            try {
                ex.printStackTrace(httpServletResponse.getWriter());
                registroBitacoraService.rollback();
                System.out.println("try 3" + ex.getMessage());
            } catch (IOException ex1) {
                Logger.getLogger(RegistroBitacoraController.class.getName()).log(Level.SEVERE, null, ex1);
                System.out.println("catch 4" + ex1.getMessage());
            } catch (Exception ee) {

            }
            System.out.println("catch 3" + ex.getMessage());
        } finally {
            try {
                registroBitacoraService.close();
            } catch (Exception ee) {

            }
        }
    }

    @RequestMapping(value = "/RegistroBitacoraDelete", method = RequestMethod.PUT, consumes = "application/json")
    public void deleteRegistroBitacora(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
            @RequestParam("idRegistro") String idRegistro, @RequestParam("idBitacora") int idBitacora) {
        try {

            registroBitacoraService.beginTransaction();
            RegistroBitacoraId id = new RegistroBitacoraId(idRegistro, idBitacora);
            RegistroBitacora registroBitacora = registroBitacoraService.read(id);
            registroBitacoraService.delete(registroBitacora);
            registroBitacoraService.commit();

            httpServletResponse.setStatus(HttpServletResponse.SC_OK);
            //httpServletResponse.setContentType("application/json; charset=UTF-8");
            //httpServletResponse.getWriter().println(jsonSalida);

        } catch (BussinessException ex) {
            List<BussinessMessage> bussinessMessage = ex.getBussinessMessages();
            String jsonSalida = jsonTransformer.toJson(bussinessMessage);

            httpServletResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            httpServletResponse.setContentType("application/json; charset=UTF-8");
            try {
                httpServletResponse.getWriter().println(jsonSalida);
                registroBitacoraService.rollback();
            } catch (IOException ex1) {
                Logger.getLogger(RegistroBitacoraController.class.getName()).log(Level.SEVERE, null, ex1);
            } catch (Exception eee) {

            }
            System.out.println("1er catch " + ex.getMessage());
        } catch (Exception ex) {
            httpServletResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            httpServletResponse.setContentType("text/plain; charset=UTF-8");
            try {
                ex.printStackTrace(httpServletResponse.getWriter());
                registroBitacoraService.rollback();
            } catch (IOException ex1) {
                Logger.getLogger(RegistroBitacoraController.class.getName()).log(Level.SEVERE, null, ex1);
            } catch (Exception eee) {

            }
            System.out.println("1er catch " + ex.getMessage());
        } finally {
            try {
                registroBitacoraService.close();
            } catch (Exception ee) {

            }
        }
    }

    @RequestMapping(value = "/RegistroBitacoraFindAllByIdRegistro/{idRegistro}", method = RequestMethod.GET, produces = "application/json", consumes = "application/json")
    public void findAllByIdCorrepondenciaCorrespondenciaFile(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
            @PathVariable String idRegistro) {
        try {
            registroBitacoraService.beginTransaction();//getAllBitacoraByIdRegistroList
            String jsonSalida = "[]";
            List<Object> registroBitacoras = registroBitacoraService.getAllBitacoraByIdRegistroList(idRegistro);
            if (registroBitacoras != null) {
                if (registroBitacoras.size() > 0) {
                    if (registroBitacoras.get(0) != null) {
                        jsonSalida = "[" + ((String) registroBitacoras.get(0)) + "]";
                    }
                }
            }
            registroBitacoraService.commit();
            httpServletResponse.addHeader("Access-Control-Allow-Origin", "*");
            httpServletResponse.setStatus(HttpServletResponse.SC_OK);
            httpServletResponse.setContentType("application/json; charset=UTF-8");
            httpServletResponse.getWriter().println(jsonSalida);

        } catch (BussinessException ex) {
            List<BussinessMessage> bussinessMessage = ex.getBussinessMessages();
            String jsonSalida = jsonTransformer.toJson(bussinessMessage);

            httpServletResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            httpServletResponse.setContentType("application/json; charset=UTF-8");
            try {
                httpServletResponse.getWriter().println(jsonSalida);
                registroBitacoraService.rollback();
                System.out.println("2do try ");
            } catch (IOException ex1) {
                Logger.getLogger(RegistroBitacoraController.class.getName()).log(Level.SEVERE, null, ex1);
                System.out.println("2do catch " + ex1.getMessage());
            } catch (Exception e) {

            }
            System.out.println("1er catch " + ex.getMessage());

        } catch (Exception ex) {
            httpServletResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            try {
                registroBitacoraService.rollback();
            } catch (Exception e) {
            }
            System.out.println("3er catch " + ex.getMessage());
        } finally {
            try {
                registroBitacoraService.close();
            } catch (Exception e) {

            }
        }
    }

}
