/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comiteetica.controller;

import com.comiteetica.hibernate.model.CorrespondenciaServicio;
import com.comiteetica.hibernate.model.CorrespondenciaServicioId;
import com.comiteetica.hibernate.model.Pago;
import com.comiteetica.hibernate.model.PagoDetalleId;
import com.comiteetica.hibernate.model.SerieCorrelativo;
import com.comiteetica.hibernate.service.CorrespondenciaServicioService;
import com.comiteetica.hibernate.service.PagoDetalleService;
import com.comiteetica.hibernate.service.PagoService;
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
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author rasec
 */
@Controller
@RequestMapping("/Pago")
public class PagoController {

    @Autowired
    private JsonTransformer jsonTransformer;

    @Autowired
    private PagoService pagoService;

    @Autowired
    private PagoDetalleService pagoDetalleService;

    @Autowired
    private CorrespondenciaServicioService correspondenciaServicioService;

    @Autowired
    private SerieCorrelativoService serieCorrelativoService;

    @RequestMapping(value = "/PagoRead/{idPago}", method = RequestMethod.GET, produces = "application/json")
    public void readPago(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @PathVariable("idPago") String idPago) {
        try {
            pagoService.beginTransaction();
            Pago pago = pagoService.read(idPago);
            String jsonSalida = jsonTransformer.toJson(pago);
            pagoService.commit();
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
                pagoService.rollback();
            } catch (IOException ex1) {
                Logger.getLogger(PagoController.class.getName()).log(Level.SEVERE, null, ex1);
            } catch (Exception ee) {

            }

        } catch (Exception ex) {
            try {
                pagoService.rollback();
            } catch (Exception ee) {

            }
            httpServletResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            System.out.println("catch " + ex.getMessage());
        } finally {
            try {
                pagoService.close();
            } catch (Exception ee) {

            }
        }

    }

    @RequestMapping(value = "/PagoInsert", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public void insertPago(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @RequestBody String jsonEntrada) {
        try {
            pagoService.beginTransaction();
            System.out.println(jsonEntrada);
            Pago pago = (Pago) jsonTransformer.fromJson(jsonEntrada, Pago.class);
            java.util.Date date = Date.from(Instant.now());
            SerieCorrelativo seriecorrelativo = serieCorrelativoService.readNextSerieCorrelativo("PGO", date);
            pago.setIdPago(seriecorrelativo.getId().getIdSerie() + seriecorrelativo.getUltimoUsado());
            pagoService.create(pago);
            seriecorrelativo.setFechaModificacion(date);
            serieCorrelativoService.update(seriecorrelativo);
            System.out.println(pago.getPagoDetalles().size());

            pago.getPagoDetalles().forEach((pagoDetalle) -> {
                try {
                    PagoDetalleId id = pagoDetalle.getId();
                    id.setIdPago(pago.getIdPago());
                    id.setIdPagoDetalle(pagoDetalleService.getNextPagoDetalleByIdPago(id.getIdPago()));
                    pagoDetalleService.create(pagoDetalle);
//                    String jsonSalida = jsonTransformer.toJson(pagoDetalle);

                    CorrespondenciaServicioId idCS = new CorrespondenciaServicioId(pagoDetalle.getIdCorrespondencia(), pagoDetalle.getIdCorrespondenciaServicio());
                    CorrespondenciaServicio correspondenciaServicio = correspondenciaServicioService.read(idCS);
                    correspondenciaServicio.setTransferido(1);
                    correspondenciaServicioService.update(correspondenciaServicio);
                } catch (Exception e) {
                }
            });

            String jsonSalida = jsonTransformer.toJson(pago);
            pagoService.commit();
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
                pagoService.rollback();
            } catch (IOException ex1) {
                Logger.getLogger(PagoController.class.getName()).log(Level.SEVERE, null, ex1);
            } catch (Exception ee) {

            }
            System.out.println("catch 1" + ex.getMessage());

        } catch (Exception ex) {
            httpServletResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            httpServletResponse.setContentType("text/plain; charset=UTF-8");
            try {
                ex.printStackTrace(httpServletResponse.getWriter());
                pagoService.rollback();
                System.out.println("try 3" + ex.getMessage());
            } catch (IOException ex1) {
                Logger.getLogger(PagoController.class.getName()).log(Level.SEVERE, null, ex1);
                System.out.println("catch 4" + ex1.getMessage());
            } catch (Exception ee) {

            }
            System.out.println("catch 3" + ex.getMessage());
        } finally {
            try {
                pagoService.close();
            } catch (Exception ee) {

            }
        }
    }

    @RequestMapping(value = "/PagoListFindAll", method = RequestMethod.GET, produces = "application/json", consumes = "application/json")
    public void findAllPagoList(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        try {

            pagoService.beginTransaction();
            String jsonSalida = "[]";
            List<Object> pagos = pagoService.getAllPagoList();
            if (pagos != null) {
                if (pagos.size() > 0) {
                    if (pagos.get(0) != null) {
                        jsonSalida = "[" + ((String) pagos.get(0)) + "]";
                    }
                }
            }
            pagoService.commit();
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
                pagoService.rollback();
                System.out.println("2do try ");
            } catch (IOException ex1) {
                Logger.getLogger(PagoController.class.getName()).log(Level.SEVERE, null, ex1);
                System.out.println("2do catch " + ex1.getMessage());
            } catch (Exception e) {

            }
            System.out.println("1er catch " + ex.getMessage());

        } catch (Exception ex) {
            httpServletResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            try {
                pagoService.rollback();
            } catch (Exception e) {
            }
            System.out.println("3er catch " + ex.getMessage());
        } finally {
            try {
                pagoService.close();
            } catch (Exception e) {

            }
        }
    }

    @RequestMapping(value = "/PagoUpdate", method = RequestMethod.PUT, consumes = "application/json", produces = "application/json")
    public void updatePago(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @RequestBody String jsonEntrada) {
        try {
            pagoService.beginTransaction();
            Pago pago = (Pago) jsonTransformer.fromJson(jsonEntrada, Pago.class);
            pagoService.update(pago);
            String jsonSalida = jsonTransformer.toJson(pago);
            pagoService.commit();

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
                pagoService.rollback();
            } catch (IOException ex1) {
                Logger.getLogger(PagoController.class.getName()).log(Level.SEVERE, null, ex1);
            } catch (Exception ee) {

            }

        } catch (IOException ex) {
            httpServletResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            httpServletResponse.setContentType("text/plain; charset=UTF-8");
            try {
                ex.printStackTrace(httpServletResponse.getWriter());
                pagoService.rollback();
            } catch (IOException ex1) {
                Logger.getLogger(PagoController.class.getName()).log(Level.SEVERE, null, ex1);
            } catch (Exception ee) {

            }
        } finally {
            try {
                pagoService.close();
            } catch (Exception eee) {

            }
        }
    }

    @RequestMapping(value = "/PagoDelete", method = RequestMethod.PUT, consumes = "application/json")
    public void deletePago(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
            @RequestParam("idPago") String idPago) {
        try {
            pagoService.beginTransaction();
            Pago pago = pagoService.read(idPago);
            pagoService.delete(pago);
            pagoService.commit();
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
                pagoService.rollback();
            } catch (IOException ex1) {
                Logger.getLogger(PagoController.class.getName()).log(Level.SEVERE, null, ex1);
            } catch (Exception eee) {

            }
            System.out.println("1er catch " + ex.getMessage());
        } catch (Exception ex) {
            httpServletResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            httpServletResponse.setContentType("text/plain; charset=UTF-8");
            try {
                ex.printStackTrace(httpServletResponse.getWriter());
                pagoService.rollback();
            } catch (IOException ex1) {
                Logger.getLogger(PagoController.class.getName()).log(Level.SEVERE, null, ex1);
            } catch (Exception eee) {

            }
            System.out.println("1er catch " + ex.getMessage());
        } finally {
            try {
                pagoService.close();
            } catch (Exception ee) {

            }
        }
    }

    @RequestMapping(value = "/MailSendCopia", method = RequestMethod.PUT, consumes = "application/json", produces = "application/json")
    public void sendMailCopia(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @RequestParam("idPago") String idPago, @RequestParam("copiaCorreo") String copiaCorreo) {
        try {
            pagoService.beginTransaction();
            int flag = pagoService.sendMail(idPago, copiaCorreo);
            pagoService.commit();
            httpServletResponse.setStatus(HttpServletResponse.SC_OK);
            httpServletResponse.setContentType("application/json; charset=UTF-8");
            httpServletResponse.getWriter().println("" + flag + "");

        } catch (BussinessException ex) {
            List<BussinessMessage> bussinessMessage = ex.getBussinessMessages();
            String jsonSalida = jsonTransformer.toJson(bussinessMessage);

            httpServletResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            httpServletResponse.setContentType("application/json; charset=UTF-8");
            try {
                httpServletResponse.getWriter().println(jsonSalida);
                pagoService.rollback();
            } catch (IOException ex1) {
                Logger.getLogger(PagoController.class.getName()).log(Level.SEVERE, null, ex1);
            } catch (Exception ee) {

            }

        } catch (IOException ex) {
            httpServletResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            httpServletResponse.setContentType("text/plain; charset=UTF-8");
            try {
                ex.printStackTrace(httpServletResponse.getWriter());
                pagoService.rollback();
            } catch (IOException ex1) {
                Logger.getLogger(PagoController.class.getName()).log(Level.SEVERE, null, ex1);
            } catch (Exception ee) {

            }
        } finally {
            try {
                pagoService.close();
            } catch (Exception eee) {

            }
        }
    }

    @RequestMapping(value = "/MailSendConta", method = RequestMethod.PUT, consumes = "application/json", produces = "application/json")
    public void sendMailConta(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @RequestParam("idPago") String idPago) {
        try {
            pagoService.beginTransaction();
            int flag = pagoService.sendMail(idPago, "");
            Pago pago = pagoService.read(idPago);
            pago.setContador(pago.getContador() + 1);
            pagoService.update(pago);
            pagoService.commit();

            httpServletResponse.setStatus(HttpServletResponse.SC_OK);
            httpServletResponse.setContentType("application/json; charset=UTF-8");
            httpServletResponse.getWriter().println("" + flag + "");

        } catch (BussinessException ex) {
            List<BussinessMessage> bussinessMessage = ex.getBussinessMessages();
            String jsonSalida = jsonTransformer.toJson(bussinessMessage);

            httpServletResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            httpServletResponse.setContentType("application/json; charset=UTF-8");
            try {
                httpServletResponse.getWriter().println(jsonSalida);
                pagoService.rollback();
            } catch (IOException ex1) {
                Logger.getLogger(PagoController.class.getName()).log(Level.SEVERE, null, ex1);
            } catch (Exception ee) {

            }

        } catch (IOException ex) {
            httpServletResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            httpServletResponse.setContentType("text/plain; charset=UTF-8");
            try {
                ex.printStackTrace(httpServletResponse.getWriter());
                pagoService.rollback();
            } catch (IOException ex1) {
                Logger.getLogger(PagoController.class.getName()).log(Level.SEVERE, null, ex1);
            } catch (Exception ee) {

            }
        } finally {
            try {
                pagoService.close();
            } catch (Exception eee) {

            }
        }
    }
}
