/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comiteetica.controller;

import com.comiteetica.hibernate.model.Cro;
import com.comiteetica.hibernate.model.SerieCorrelativo;
import com.comiteetica.hibernate.service.CroService;
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
@RequestMapping("/Cro")
public class CroController {

    @Autowired
    JsonTransformer jsonTransformer;

    @Autowired
    CroService croService;

    @Autowired
    SerieCorrelativoService serieCorrelativoService;

    @RequestMapping(value = "/CroRead/{idCro}", method = RequestMethod.GET, produces = "application/json")
    public void readCro(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @PathVariable("idCro") String idCro) {
        try {
            croService.beginTransaction();
            Cro cro = croService.read(idCro);
            String jsonSalida = jsonTransformer.toJson(cro);
            croService.commit();
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
                croService.rollback();
            } catch (IOException ex1) {
                Logger.getLogger(CroController.class.getName()).log(Level.SEVERE, null, ex1);
            } catch (Exception ee) {

            }

        } catch (Exception ex) {
            try {
                croService.rollback();
            } catch (Exception ee) {

            }
            httpServletResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            System.out.println("catch " + ex.getMessage());
        } finally {
            try {
                croService.close();
            } catch (Exception ee) {

            }
        }

    }

    @RequestMapping(value = "/CroInsert", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public void insertCro(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @RequestBody String jsonEntrada) {

        try {
            croService.beginTransaction();
            Cro cro = (Cro) jsonTransformer.fromJson(jsonEntrada, Cro.class);
            java.util.Date date = Date.from(Instant.now());
            SerieCorrelativo seriecorrelativo = serieCorrelativoService.readNextSerieCorrelativo("CRO", date);
            cro.setIdCro(seriecorrelativo.getId().getIdSerie() + seriecorrelativo.getUltimoUsado());
            croService.create(cro);
            seriecorrelativo.setFechaModificacion(date);
            serieCorrelativoService.update(seriecorrelativo);
            String jsonSalida = jsonTransformer.toJson(cro);
            croService.commit();
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
                croService.rollback();
            } catch (IOException ex1) {
                Logger.getLogger(CroController.class.getName()).log(Level.SEVERE, null, ex1);
            } catch (Exception ee) {

            }
            System.out.println("catch 1" + ex.getMessage());

        } catch (Exception ex) {
            httpServletResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            httpServletResponse.setContentType("text/plain; charset=UTF-8");
            try {
                ex.printStackTrace(httpServletResponse.getWriter());
                croService.rollback();
                System.out.println("try 3" + ex.getMessage());
            } catch (IOException ex1) {
                Logger.getLogger(CroController.class.getName()).log(Level.SEVERE, null, ex1);
                System.out.println("catch 4" + ex1.getMessage());
            } catch (Exception ee) {

            }
            System.out.println("catch 3" + ex.getMessage());
        } finally {
            try {
                croService.close();
            } catch (Exception ee) {

            }
        }
    }

    @RequestMapping(value = "/CroFindAll", method = RequestMethod.GET, produces = "application/json")
    public void findAllCro(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        try {
            croService.beginTransaction();
            String jsonSalida = "[]";
            List<Object> cros = croService.getAllCroList();
            if (cros != null) {
                if (cros.size() > 0) {
                    if (cros.get(0) != null) {
                        jsonSalida = "[" + ((String) cros.get(0)) + "]";
                    }
                }
            }
            croService.commit();
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
                croService.rollback();
                System.out.println("2do try ");
            } catch (IOException ex1) {
                Logger.getLogger(CroController.class.getName()).log(Level.SEVERE, null, ex1);
                System.out.println("2do catch " + ex1.getMessage());
            } catch (Exception ee) {

            }

            System.out.println("1er catch " + ex.getMessage());

        } catch (Exception ex) {
            httpServletResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            try {
                croService.rollback();
            } catch (Exception ee) {

            }

            System.out.println("3er catch " + ex.getMessage());
        } finally {
            try {
                croService.close();
            } catch (Exception ee) {

            }
        }

    }

    @RequestMapping(value = "/CroUpdate", method = RequestMethod.PUT, consumes = "application/json", produces = "application/json")
    public void updateCro(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @RequestBody String jsonEntrada) {
        try {
            croService.beginTransaction();
            Cro cro = (Cro) jsonTransformer.fromJson(jsonEntrada, Cro.class);
            croService.update(cro);
            String jsonSalida = jsonTransformer.toJson(cro);
            croService.commit();
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
                croService.rollback();
            } catch (IOException ex1) {
                Logger.getLogger(CroController.class.getName()).log(Level.SEVERE, null, ex1);
            } catch (Exception ee) {

            }

        } catch (IOException ex) {
            httpServletResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            httpServletResponse.setContentType("text/plain; charset=UTF-8");
            try {
                ex.printStackTrace(httpServletResponse.getWriter());
                croService.rollback();
            } catch (IOException ex1) {
                Logger.getLogger(CroController.class.getName()).log(Level.SEVERE, null, ex1);
            } catch (Exception ee) {

            }
        } finally {
            try {
                croService.close();
            } catch (Exception eee) {

            }
        }
    }

    @RequestMapping(value = "/CroDelete", method = RequestMethod.PUT, consumes = "application/json")
    public void deleteCro(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @RequestParam("idCro") String idCro) {
        try {
            croService.beginTransaction();
            Cro cro =croService.read(idCro);
            croService.delete(cro);
            croService.commit();

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
                croService.rollback();
            } catch (IOException ex1) {
                Logger.getLogger(CroController.class.getName()).log(Level.SEVERE, null, ex1);
            } catch (Exception eee) {

            }
        } catch (Exception ex) {
            httpServletResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            httpServletResponse.setContentType("text/plain; charset=UTF-8");
            try {
                ex.printStackTrace(httpServletResponse.getWriter());
                croService.rollback();
            } catch (IOException ex1) {
                Logger.getLogger(CroController.class.getName()).log(Level.SEVERE, null, ex1);
            } catch (Exception eee) {

            }
        } finally {
            try {
                croService.close();
            } catch (Exception ee) {

            }
        }
    }

    @RequestMapping(value = "/CroByPatrocinadorList/{idPatrocinador}", method = RequestMethod.GET, produces = "application/json")
    public void listCroByPatrocinador(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @PathVariable("idPatrocinador") String idPatrocinador) {
        try {
            croService.beginTransaction();
            List<Cro> cro = croService.getCroByPatrocinador(idPatrocinador);
            String jsonSalida = jsonTransformer.toJson(cro);
            croService.commit();
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
                croService.rollback();
            } catch (IOException ex1) {
                Logger.getLogger(CroController.class.getName()).log(Level.SEVERE, null, ex1);
            } catch (Exception ee) {

            }

        } catch (Exception ex) {
            try {
                croService.rollback();
            } catch (Exception ee) {

            }
            httpServletResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            System.out.println("catch " + ex.getMessage());
        } finally {
            try {
                croService.close();
            } catch (Exception ee) {

            }
        }
    }
    
    @RequestMapping(value = "/CroSinIdPatrocinadorFind/{idPatrocinador}", method = RequestMethod.GET, produces = "application/json")
    public void findCroSinIdPatrocinador(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @PathVariable("idPatrocinador") String idPatrocinador) {
        try {
            croService.beginTransaction();
            List<Cro> coordinadors = croService.getCroSinIdPatrocinador(idPatrocinador);
            croService.commit();
            httpServletResponse.addHeader("Access-Control-Allow-Origin", "*");
            String jsonSalida = jsonTransformer.toJson(coordinadors);
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
                croService.rollback();
                System.out.println("2do try ");
            } catch (IOException ex1) {
                Logger.getLogger(CroController.class.getName()).log(Level.SEVERE, null, ex1);
                System.out.println("2do catch "+ex1.getMessage());
            } catch(Exception e){
                
            }
            
            System.out.println("1er catch "+ex.getMessage());
            
        } catch (Exception ex) {
            httpServletResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            try{
                croService.rollback();
            }catch(Exception e){
                
            }
            System.out.println("3er catch "+ex.getMessage());
        }finally{
            try{
                croService.close();
            }catch(Exception e){
                
            }
        }

    }
    
}
