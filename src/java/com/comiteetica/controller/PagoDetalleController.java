/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comiteetica.controller;

import com.comiteetica.hibernate.model.CorrespondenciaServicio;
import com.comiteetica.hibernate.model.CorrespondenciaServicioId;
import com.comiteetica.hibernate.model.PagoDetalle;
import com.comiteetica.hibernate.model.PagoDetalleId;
import com.comiteetica.hibernate.service.CorrespondenciaServicioService;
import com.comiteetica.hibernate.service.PagoDetalleService;
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
@RequestMapping("/PagoDetalle")
public class PagoDetalleController {

    @Autowired
    private JsonTransformer jsonTransformer;

    @Autowired
    private PagoDetalleService pagoDetalleService;

    @Autowired
    private CorrespondenciaServicioService correspondenciaServicioService;

    @RequestMapping(value = "/PagoDetalleInsert", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public void insertPagoDetalle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @RequestBody String jsonEntrada) {
        try {
            pagoDetalleService.beginTransaction();
            PagoDetalle pagoDetalle = (PagoDetalle) jsonTransformer.fromJson(jsonEntrada, PagoDetalle.class);
            PagoDetalleId id = pagoDetalle.getId();
            id.setIdPagoDetalle(pagoDetalleService.getNextPagoDetalleByIdPago(id.getIdPago()));
            pagoDetalleService.create(pagoDetalle);
            String jsonSalida = jsonTransformer.toJson(pagoDetalle);

            CorrespondenciaServicioId idCS = new CorrespondenciaServicioId(pagoDetalle.getIdCorrespondencia(), pagoDetalle.getIdCorrespondenciaServicio());
            CorrespondenciaServicio correspondenciaServicio = correspondenciaServicioService.read(idCS);
            correspondenciaServicio.setTransferido(1);
            correspondenciaServicioService.update(correspondenciaServicio);
            pagoDetalleService.commit();
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
                pagoDetalleService.rollback();
            } catch (IOException ex1) {
                Logger.getLogger(PagoDetalleController.class.getName()).log(Level.SEVERE, null, ex1);
            } catch (Exception ee) {

            }
            System.out.println("catch 1" + ex.getMessage());

        } catch (Exception ex) {
            httpServletResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            httpServletResponse.setContentType("text/plain; charset=UTF-8");
            try {
                ex.printStackTrace(httpServletResponse.getWriter());
                pagoDetalleService.rollback();
                System.out.println("try 3" + ex.getMessage());
            } catch (IOException ex1) {
                Logger.getLogger(PagoDetalleController.class.getName()).log(Level.SEVERE, null, ex1);
                System.out.println("catch 4" + ex1.getMessage());
            } catch (Exception ee) {

            }
            System.out.println("catch 3" + ex.getMessage());
        } finally {
            try {
                pagoDetalleService.close();
            } catch (Exception ee) {

            }
        }
    }

    @RequestMapping(value = "/PagoDetalleFindByPagoList/{idPago}", method = RequestMethod.GET, produces = "application/json", consumes = "application/json")
    public void findByPagoListPagoDetalle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @PathVariable("idPago") String idPago) {
        try {

            pagoDetalleService.beginTransaction();
            String jsonSalida = "[]";
            List<Object> pagos = pagoDetalleService.getAllPagoDetalleByPagoList(idPago);
            if (pagos != null) {
                if (pagos.size() > 0) {
                    if (pagos.get(0) != null) {
                        jsonSalida = "[" + ((String) pagos.get(0)) + "]";
                    }
                }
            }
            pagoDetalleService.commit();
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
                pagoDetalleService.rollback();
                System.out.println("2do try ");
            } catch (IOException ex1) {
                Logger.getLogger(CorrespondenciaServicioController.class.getName()).log(Level.SEVERE, null, ex1);
                System.out.println("2do catch " + ex1.getMessage());
            } catch (Exception e) {

            }
            System.out.println("1er catch " + ex.getMessage());

        } catch (Exception ex) {
            httpServletResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            try {
                pagoDetalleService.rollback();
            } catch (Exception e) {
            }
            System.out.println("3er catch " + ex.getMessage());
        } finally {
            try {
                pagoDetalleService.close();
            } catch (Exception e) {

            }
        }
    }

    @RequestMapping(value = "/PagoDetalleDelete", method = RequestMethod.PUT, consumes = "application/json")
    public void deletePagoDetalle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
            @RequestParam("idPago") String idPago, @RequestParam("idPagoDetalle") int idPagoDetalle,
            @RequestParam("idCorrespondencia") String idCorrespondencia, @RequestParam("idCorrespondenciaServicio") int idCorrespondenciaServicio) {
        try {

            pagoDetalleService.beginTransaction();
            PagoDetalleId id = new PagoDetalleId(idPago, idPagoDetalle);
            PagoDetalle pagoDetalle = pagoDetalleService.read(id);
            pagoDetalleService.delete(pagoDetalle);
            CorrespondenciaServicioId idCS = new CorrespondenciaServicioId(idCorrespondencia, idCorrespondenciaServicio);
            CorrespondenciaServicio correspondenciaServicio = correspondenciaServicioService.read(idCS);
            correspondenciaServicio.setTransferido(0);
            correspondenciaServicioService.update(correspondenciaServicio);
            pagoDetalleService.commit();
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
                pagoDetalleService.rollback();
            } catch (IOException ex1) {
                Logger.getLogger(PagoDetalleController.class.getName()).log(Level.SEVERE, null, ex1);
            } catch (Exception eee) {

            }
            System.out.println("1er catch " + ex.getMessage());
        } catch (Exception ex) {
            httpServletResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            httpServletResponse.setContentType("text/plain; charset=UTF-8");
            try {
                ex.printStackTrace(httpServletResponse.getWriter());
                pagoDetalleService.rollback();
            } catch (IOException ex1) {
                Logger.getLogger(PagoDetalleController.class.getName()).log(Level.SEVERE, null, ex1);
            } catch (Exception eee) {

            }
            System.out.println("1er catch " + ex.getMessage());
        } finally {
            try {
                pagoDetalleService.close();
            } catch (Exception ee) {

            }
        }
    }
}
