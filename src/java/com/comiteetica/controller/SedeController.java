/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comiteetica.controller;

import com.comiteetica.hibernate.model.Sede;
import com.comiteetica.hibernate.model.SerieCorrelativo;
import com.comiteetica.hibernate.service.SedeService;
import com.comiteetica.hibernate.service.SerieCorrelativoService;
import com.comiteetica.json.JsonTransformer;
import com.comiteetica.persistencia.BussinessException;
import com.comiteetica.persistencia.BussinessMessage;
import java.io.IOException;
import java.sql.Date;
import java.time.Instant;
import java.util.ArrayList;
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
public class SedeController {
    @Autowired
    JsonTransformer jsonTransformer;
    
    @Autowired
    SedeService sedeService;
    
    @Autowired
    SerieCorrelativoService serieCorrelativoService;
    
    @RequestMapping(value = "/SedeRead/{idSede}", method = RequestMethod.GET, produces = "application/json")
    public void readSede(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @PathVariable("idSede") String idSede) {
        try {
            sedeService.beginTransaction();
            System.out.println("antes de cargar");
            Sede sede = sedeService.read(idSede);
            System.out.println("cargó sede");
            
            String jsonSalida = jsonTransformer.toJson(sede);
            System.out.println(jsonSalida);
            
            httpServletResponse.setStatus(HttpServletResponse.SC_OK);
            httpServletResponse.setContentType("application/json; charset=UTF-8");
            sedeService.commit();
            httpServletResponse.getWriter().println(jsonSalida);
            
        } catch (BussinessException ex) {
            List<BussinessMessage> bussinessMessage=ex.getBussinessMessages();
            String jsonSalida = jsonTransformer.toJson(bussinessMessage);
            
            httpServletResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            httpServletResponse.setContentType("application/json; charset=UTF-8");
            try {
                httpServletResponse.getWriter().println(jsonSalida);
                sedeService.rollback();
            } catch (IOException ex1) {
                Logger.getLogger(InvestigadorController.class.getName()).log(Level.SEVERE, null, ex1);
            } catch(Exception e){
                
            }
            
        } catch (Exception ex) {
            httpServletResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            try{
                sedeService.rollback();
            }catch(Exception e){
                
            }
            System.out.println("catch "+ex.getMessage());
        }finally{
            try{
                sedeService.close();
            }catch(Exception e){
                
            }
        }

    }
//    
//    
    @RequestMapping(value = "/SedeInsert", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public void insertSede(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @RequestBody String jsonEntrada) {
        
        try {
            System.out.println("JSON String: "+jsonEntrada);
            Sede sede = (Sede) jsonTransformer.fromJson(jsonEntrada, Sede.class);
            System.out.println("Sede sede = (Sede) jsonTransformer.fromJson(jsonEntrada, Sede.class);");
            java.util.Date date = Date.from(Instant.now());
            sedeService.beginTransaction();
            SerieCorrelativo seriecorrelativo=serieCorrelativoService.readNextSerieCorrelativo("SED", date);
            sede.setIdSede("SED"+seriecorrelativo.getUltimoUsado());
            sedeService.create(sede);
            seriecorrelativo.setFechaModificacion(date);
            serieCorrelativoService.update(seriecorrelativo);
            sedeService.commit();
            String jsonSalida = jsonTransformer.toJson(sede);
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
                sedeService.rollback();
            } catch (IOException ex1) {
                Logger.getLogger(SedeController.class.getName()).log(Level.SEVERE, null, ex1);
            } catch(Exception e){
                
            }
            System.out.println("catch 1"+ex.getMessage());
            
        } catch (Exception ex) {
            httpServletResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            httpServletResponse.setContentType("text/plain; charset=UTF-8");
            try {
                ex.printStackTrace(httpServletResponse.getWriter());
                sedeService.rollback();
                System.out.println("try 3"+ex.getMessage());
            } catch (IOException ex1) {
                Logger.getLogger(SedeController.class.getName()).log(Level.SEVERE, null, ex1);
                System.out.println("catch 4"+ex1.getMessage());
            } catch(Exception e){
                
            }
            System.out.println("catch 3"+ex.getMessage());
        }finally{
            try{
                sedeService.close();
            }catch(Exception e){
                
            }
                
        }
        
        System.out.println("final");
    }

    
    @RequestMapping(value = "/SedeFindAll", method = RequestMethod.GET, produces = "application/json")
    public void findAllSede(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        try {
            sedeService.beginTransaction();
//            List<Sede> sedes = sedeService.getAllSede();
            List<Object[]> sedes = sedeService.getAllSedeList();
            String jsonSalida = jsonTransformer.toJson(sedes);
            sedeService.commit();
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
                sedeService.rollback();
                System.out.println("2do try ");
            } catch (IOException ex1) {
                Logger.getLogger(SedeController.class.getName()).log(Level.SEVERE, null, ex1);
                System.out.println("2do catch "+ex1.getMessage());
            } catch(Exception e){
                
            }
            System.out.println("1er catch "+ex.getMessage());
        } catch (Exception ex) {
            httpServletResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            try{
                sedeService.rollback();
            }catch(Exception e){
                
            }
            System.out.println("3er catch "+ex.getMessage());
        }finally{
            try{
                sedeService.close();
            }catch(Exception e){
                
            } 
        }
    }
//    
//    @RequestMapping(value = "/ProductoByStep", method = RequestMethod.PUT, produces = "application/json",consumes = "application/json")
//    public void findProductoByStep(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,@RequestBody String jsonEntrada) {
//        //Product prod=new Product();
//        try {
//            
//            /* esto sí funcionó
//            System.out.println("antes de listar");
//            List<Producto> productos = prod.getProductos();
//            System.out.println("terminó");
//            */ 
//            System.out.println("jsonEntrada "+jsonEntrada);
//            Paginacion paginacion = (Paginacion)jsonTransformer.fromJson(jsonEntrada, Paginacion.class);
////            Object list = (Object)jsonTransformer.fromJson(jsonEntrada, Object.class);
////            List<Object[]> list = (List<Object[]> )jsonTransformer.fromJson(jsonEntrada, Object[].class);
//            System.out.println("antes de listar ini ::"+paginacion.getInicia());
//            
//            System.out.println("antes de listar fin ::"+paginacion.getMax());
//            //System.out.println("antes de listar fin ::"+list.get(1).toString());
//           
//           
//            List<Producto> productos = productoService.getAllProducto(paginacion.getInicia(),paginacion.getMax());
//            System.out.println("terminó "+productos.size());
//            //System.out.println("Listó"+productos.get(0).getMarca().getDescripcion());
//
//            String jsonSalida = jsonTransformer.toJson(productos);
//            System.out.println("transformó lista completa: "+jsonSalida);
//            //httpServletRequest.
//            httpServletResponse.addHeader("Access-Control-Allow-Origin", "*");
//            httpServletResponse.setStatus(HttpServletResponse.SC_OK);
//            httpServletResponse.setContentType("application/json; charset=UTF-8");
//            httpServletResponse.getWriter().println(jsonSalida);
//            
//        } catch (BussinessException ex) {
//            List<BussinessMessage> bussinessMessage=ex.getBussinessMessages();
//            String jsonSalida = jsonTransformer.toJson(bussinessMessage);
//            
//            httpServletResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
//            httpServletResponse.setContentType("application/json; charset=UTF-8");
//            try {
//                httpServletResponse.getWriter().println(jsonSalida);
//                System.out.println("2do try ");
//            } catch (IOException ex1) {
//                Logger.getLogger(ProductoController.class.getName()).log(Level.SEVERE, null, ex1);
//                System.out.println("2do catch "+ex1.getMessage());
//            }
//            
//            System.out.println("1er catch "+ex.getMessage());
//            
//        } catch (Exception ex) {
//            httpServletResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
//            System.out.println("3er catch "+ex.getMessage());
//        }
//
//    }
//    
//    @RequestMapping(value = "/ProductoTotalCount", method = RequestMethod.GET, produces = "application/json")
//    public void findProductoTotalCount(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
//        try {
//            System.out.println("antes de listar findProductoTotalCount ::");
//
//            int productosCount = productoService.getProductoCount();
//            System.out.println("terminó "+productosCount);
//            
//            httpServletResponse.addHeader("Access-Control-Allow-Origin", "*");
//            httpServletResponse.setStatus(HttpServletResponse.SC_OK);
//            httpServletResponse.setContentType("application/json; charset=UTF-8");
//            httpServletResponse.getWriter().println(productosCount);
//            
//        } catch (BussinessException ex) {
//            List<BussinessMessage> bussinessMessage=ex.getBussinessMessages();
//            String jsonSalida = jsonTransformer.toJson(bussinessMessage);
//            
//            httpServletResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
//            httpServletResponse.setContentType("application/json; charset=UTF-8");
//            try {
//                httpServletResponse.getWriter().println(jsonSalida);
//                System.out.println("2do try ");
//            } catch (IOException ex1) {
//                Logger.getLogger(ProductoController.class.getName()).log(Level.SEVERE, null, ex1);
//                System.out.println("2do catch "+ex1.getMessage());
//            }
//            
//            System.out.println("1er catch "+ex.getMessage());
//            
//        } catch (Exception ex) {
//            httpServletResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
//            System.out.println("3er catch "+ex.getMessage());
//        }
//
//    }
    
    @RequestMapping(value = "/SedeUpdate", method = RequestMethod.PUT, consumes = "application/json", produces = "application/json")
    public void updateSede(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @RequestBody String jsonEntrada) {
        try {
            System.out.println("entró Producto updateController");
            sedeService.beginTransaction();
            Sede sede = (Sede) jsonTransformer.fromJson(jsonEntrada, Sede.class);
            sedeService.update(sede);
            System.out.println("actualizó");
            sedeService.commit();
            String jsonSalida = jsonTransformer.toJson(sede);
            System.out.println("devuelve json"+jsonSalida);
            
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
                sedeService.rollback();
            } catch (IOException ex1) {
                Logger.getLogger(SedeController.class.getName()).log(Level.SEVERE, null, ex1);
            } catch(Exception e){
                
            }

        } catch (IOException ex) {
            httpServletResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            httpServletResponse.setContentType("text/plain; charset=UTF-8");
            try {
                ex.printStackTrace(httpServletResponse.getWriter());
                sedeService.rollback();
            } catch (IOException ex1) {
                Logger.getLogger(SedeController.class.getName()).log(Level.SEVERE, null, ex1);
            } catch (Exception e){
                
            }
        }finally{
            try{
                sedeService.close();
            }catch(Exception e){
                
            }
        }
    }
    
    @RequestMapping(value = "/SedeDelete", method = RequestMethod.PUT, consumes = "application/json")
    public void deleteProducto(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @RequestBody String jsonEntrada) {
        try {
            sedeService.beginTransaction();
            System.out.println("entró deleteProducto Controller");
            Sede sede = (Sede) jsonTransformer.fromJson(jsonEntrada, Sede.class);
            sedeService.delete(sede);
            sedeService.commit();
            System.out.println("borró Producto Del Controller");
            //String jsonSalida = jsonTransformer.toJson(producto);
            //System.out.println("devuelve json"+jsonSalida);
            
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
                sedeService.rollback();
            } catch (IOException ex1) {
                Logger.getLogger(SedeController.class.getName()).log(Level.SEVERE, null, ex1);
            } catch (Exception e){
                
            }
        } catch (Exception ex) {
            httpServletResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            httpServletResponse.setContentType("text/plain; charset=UTF-8");
            try {
                ex.printStackTrace(httpServletResponse.getWriter());
                sedeService.rollback();
            } catch (IOException ex1) {
                Logger.getLogger(SedeController.class.getName()).log(Level.SEVERE, null, ex1);
            } catch (Exception e){
                
            }
        }finally{
            try{
                sedeService.close();
            }catch (Exception e){
                
            }
        }
    }
    
//    @RequestMapping(value = "/Producto/{idProducto}", method = RequestMethod.DELETE, consumes = "application/json")
//    public void delete(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,@PathVariable("idProducto") String idProducto  ) {
//        try {
//            System.out.println("entro delete controller");
//            //Producto producto = (Producto) jsonTransformer.fromJson(jsonEntrada, Producto.class);
//            Producto producto=productoService.read(idProducto);
//            productoService.delete(producto);
//            System.out.println("borró");
//            httpServletResponse.setStatus(HttpServletResponse.SC_NO_CONTENT);
//            
//        } catch (BussinessException ex) {
//            List<BussinessMessage> bussinessMessage=ex.getBussinessMessages();
//            String jsonSalida = jsonTransformer.toJson(bussinessMessage);
//            
//            httpServletResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
//            httpServletResponse.setContentType("application/json; charset=UTF-8");
//            try {
//                httpServletResponse.getWriter().println(jsonSalida);
//                System.out.println("try 2: "+ex.getMessage());
//            } catch (IOException ex1) {
//                Logger.getLogger(ProductoController.class.getName()).log(Level.SEVERE, null, ex1);
//                System.out.println("catch 3: "+ex1.getMessage());
//            }
//            System.out.println("catch 2: "+ex.getMessage());
//            
//        } catch (Exception ex) {
//            httpServletResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
//            System.out.println("catch 1: "+ex.getMessage());
//        }
//
//    }    
//    
}
