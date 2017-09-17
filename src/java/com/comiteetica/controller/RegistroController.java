/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comiteetica.controller;

import com.comiteetica.hibernate.model.InvestigacionInvestigador;
import com.comiteetica.hibernate.model.Registro;
import com.comiteetica.hibernate.model.SerieCorrelativo;
import com.comiteetica.hibernate.service.RegistroService;
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
@RequestMapping("/Registro")
public class RegistroController {

    @Autowired
    private JsonTransformer jsonTransformer;

    @Autowired
    private RegistroService registroService;

    @Autowired
    private SerieCorrelativoService serieCorrelativoService;

    @RequestMapping(value = "/RegistroRead/{idRegistro}", method = RequestMethod.GET, produces = "application/json")
    public void readRegistro(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @PathVariable("idRegistro") String idRegistro) {
        try {
            registroService.beginTransaction();
            Registro registro = registroService.read(idRegistro);
            String jsonSalida = jsonTransformer.toJson(registro);
            registroService.commit();
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
                registroService.rollback();
            } catch (IOException ex1) {
                Logger.getLogger(RegistroController.class.getName()).log(Level.SEVERE, null, ex1);
            } catch (Exception ee) {

            }

        } catch (Exception ex) {
            try {
                registroService.rollback();
            } catch (Exception ee) {

            }
            httpServletResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            System.out.println("catch " + ex.getMessage());
        } finally {
            try {
                registroService.close();
            } catch (Exception ee) {

            }
        }

    }

    @RequestMapping(value = "/RegistroUpdate", method = RequestMethod.PUT, consumes = "application/json", produces = "application/json")
    public void updateRegistro(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @RequestBody String jsonEntrada) {
        try {
            registroService.beginTransaction();
            Registro registro = (Registro) jsonTransformer.fromJson(jsonEntrada, Registro.class);
            registroService.update(registro);
            String jsonSalida = jsonTransformer.toJson(registro);
            registroService.commit();
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
                registroService.rollback();
            } catch (IOException ex1) {
                Logger.getLogger(RegistroController.class.getName()).log(Level.SEVERE, null, ex1);
            } catch (Exception ee) {

            }

        } catch (IOException ex) {
            httpServletResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            httpServletResponse.setContentType("text/plain; charset=UTF-8");
            try {
                ex.printStackTrace(httpServletResponse.getWriter());
                registroService.rollback();
            } catch (IOException ex1) {
                Logger.getLogger(RegistroController.class.getName()).log(Level.SEVERE, null, ex1);
            } catch (Exception ee) {

            }
        } finally {
            try {
                registroService.close();
            } catch (Exception eee) {

            }
        }
    }

    @RequestMapping(value = "/RegistroInsert", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public void insertRegistro(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @RequestBody String jsonEntrada) {
        try {
            registroService.beginTransaction();
            Registro registro = (Registro) jsonTransformer.fromJson(jsonEntrada, Registro.class);
            java.util.Date date = Date.from(Instant.now());
            SerieCorrelativo seriecorrelativo = serieCorrelativoService.readNextSerieCorrelativo("REG", date);
            registro.setIdRegistro(seriecorrelativo.getId().getIdSerie() + seriecorrelativo.getUltimoUsado());
            registroService.create(registro);
            seriecorrelativo.setFechaModificacion(date);
            serieCorrelativoService.update(seriecorrelativo);
            String jsonSalida = jsonTransformer.toJson(registro);
            registroService.commit();
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
                registroService.rollback();
            } catch (IOException ex1) {
                Logger.getLogger(RegistroController.class.getName()).log(Level.SEVERE, null, ex1);
            } catch (Exception ee) {

            }
            System.out.println("catch 1" + ex.getMessage());

        } catch (Exception ex) {
            httpServletResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            httpServletResponse.setContentType("text/plain; charset=UTF-8");
            try {
                ex.printStackTrace(httpServletResponse.getWriter());
                registroService.rollback();
                System.out.println("try 3" + ex.getMessage());
            } catch (IOException ex1) {
                Logger.getLogger(RegistroController.class.getName()).log(Level.SEVERE, null, ex1);
                System.out.println("catch 4" + ex1.getMessage());
            } catch (Exception ee) {

            }
            System.out.println("catch 3" + ex.getMessage());
        } finally {
            try {
                registroService.close();
            } catch (Exception ee) {

            }
        }
    }

    @RequestMapping(value = "/RegistroListFindAll", method = RequestMethod.GET, produces = "application/json", consumes = "application/json")
    public void listFindAllRegistro(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        try {

            registroService.beginTransaction();
            String jsonSalida = "[]";
            List<Object> registros = registroService.getAllRegistroList();
            if (registros != null) {
                if (registros.size() > 0) {
                    if (registros.get(0) != null) {
                        jsonSalida = "[" + ((String) registros.get(0)) + "]";
                    }
                }
            }
            registroService.commit();
//            httpServletResponse.addHeader("Access-Control-Allow-Origin", "*");
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
                registroService.rollback();
                System.out.println("2do try ");
            } catch (IOException ex1) {
                Logger.getLogger(RegistroController.class.getName()).log(Level.SEVERE, null, ex1);
                System.out.println("2do catch " + ex1.getMessage());
            } catch (Exception e) {

            }
            System.out.println("1er catch " + ex.getMessage());

        } catch (Exception ex) {
            httpServletResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            try {
                registroService.rollback();
            } catch (Exception e) {
            }
            System.out.println("3er catch " + ex.getMessage());
        } finally {
            try {
                registroService.close();
            } catch (Exception e) {

            }
        }
    }

    @RequestMapping(value = "/RegistroDelete", method = RequestMethod.PUT, consumes = "application/json")
    public void deleteRegistro(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @RequestParam("idRegistro") String idRegistro) {
        try {

            registroService.beginTransaction();

            Registro correspondencia = registroService.read(idRegistro);
            registroService.delete(correspondencia);
            registroService.commit();

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
                registroService.rollback();
            } catch (IOException ex1) {
                Logger.getLogger(RegistroController.class.getName()).log(Level.SEVERE, null, ex1);
            } catch (Exception eee) {

            }
            System.out.println("1er catch " + ex.getMessage());
        } catch (Exception ex) {
            httpServletResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            httpServletResponse.setContentType("text/plain; charset=UTF-8");
            try {
                ex.printStackTrace(httpServletResponse.getWriter());
                registroService.rollback();
            } catch (IOException ex1) {
                Logger.getLogger(RegistroController.class.getName()).log(Level.SEVERE, null, ex1);
            } catch (Exception eee) {

            }
            System.out.println("1er catch " + ex.getMessage());
        } finally {
            try {
                registroService.close();
            } catch (Exception ee) {

            }
        }
    }

    @RequestMapping(value = "/RegistroValidate", method = RequestMethod.PUT, produces = "application/json")
    public void validateRegistro(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
            @RequestParam("idInvestigacion") String idInvestigacion, @RequestParam("idInvestigador") String idInvestigador, @RequestParam("idSede") String idSede) {
        try {
            registroService.beginTransaction();
            String jsonSalida = "[]";
            List<Object> correspondencias = registroService.validateRegistro(idInvestigacion, idInvestigador, idSede);
            if (correspondencias != null) {
                if (correspondencias.size() > 0) {
                    if (correspondencias.get(0) != null) {
                        jsonSalida = "[" + ((String) correspondencias.get(0)) + "]";
                    }
                }
            }
            registroService.commit();
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
                registroService.rollback();
            } catch (IOException ex1) {
                Logger.getLogger(RegistroController.class.getName()).log(Level.SEVERE, null, ex1);
            } catch (Exception ee) {

            }

        } catch (Exception ex) {
            try {
                registroService.rollback();
            } catch (Exception ee) {

            }
            httpServletResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            System.out.println("catch " + ex.getMessage());
        } finally {
            try {
                registroService.close();
            } catch (Exception ee) {

            }
        }

    }
}
