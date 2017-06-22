/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comiteetica.controller;

import com.comiteetica.hibernate.model.InvestigacionCoordinador;
import com.comiteetica.hibernate.model.InvestigacionCoordinadorId;
import com.comiteetica.hibernate.service.InvestigacionCoordinadorService;
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
public class InvestigacionCoordinadorController {
    
    @Autowired 
    private JsonTransformer jsonTransformer;
    
    @Autowired
    private InvestigacionCoordinadorService investigacionCoordinadorService;
    
    @RequestMapping(value = "/InvestigacionCoordinadorRead/{idInvestigacion}/{idCoordinador}", method = RequestMethod.GET, produces = "application/json")
    public void readInvestigacionCoordinador(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @PathVariable("idInvestigacion") String idInvestigacion,@PathVariable("idCoordinador") String idCoordinador) {
        try {
            investigacionCoordinadorService.beginTransaction();
            InvestigacionCoordinadorId investigacionCoordinadorId=new InvestigacionCoordinadorId(idInvestigacion,idCoordinador);
            InvestigacionCoordinador investigacionCoordinador = investigacionCoordinadorService.read(investigacionCoordinadorId);
            investigacionCoordinadorService.commit();
            String jsonSalida = jsonTransformer.toJson(investigacionCoordinador);            
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
                investigacionCoordinadorService.rollback();
            } catch (IOException ex1) {
                Logger.getLogger(InvestigadorController.class.getName()).log(Level.SEVERE, null, ex1);
            } catch (Exception e){
                
            }
        } catch (Exception ex) {
            httpServletResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            try{
                investigacionCoordinadorService.rollback();
            }catch(Exception e){
                
            }
            System.out.println("catch "+ex.getMessage());
        }finally{
            try{
                investigacionCoordinadorService.close();
            }catch(Exception e){
                
            }
        }
    }
//    
//    @RequestMapping(value = "/InvestigacionCoordinadorRead/", method = RequestMethod.PATCH, produces = "application/json",consumes = "application/json")
//    public void readInvestigacionCoordinador(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @PathVariable("idInvestigacion") String idInvestigacion,@PathVariable("idCoordinador") String idCoordinador) {
//        try {
//            System.out.println("antes de cargar");
//            InvestigacionCoordinadorId investigacionCoordinadorId=new InvestigacionCoordinadorId(idInvestigacion,idCoordinador);
//            InvestigacionCoordinador investigacionCoordinador = investigacionCoordinadorService.read(investigacionCoordinadorId);
//            System.out.println("cargó investigacionCoordinador");
//            String jsonSalida = jsonTransformer.toJson(investigacionCoordinador);
//            System.out.println(jsonSalida);
//            
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
//            } catch (IOException ex1) {
//                Logger.getLogger(InvestigadorController.class.getName()).log(Level.SEVERE, null, ex1);
//            }
//            
//        } catch (Exception ex) {
//            httpServletResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
//            System.out.println("catch "+ex.getMessage());
//        }
//
//    }
//    
//    
    
    
//    
//    
    @RequestMapping(value = "/InvestigacionCoordinadorInsert", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public void insertInvestigacionCoordinador(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @RequestBody String jsonEntrada) {
        
        try {
            investigacionCoordinadorService.beginTransaction();
            InvestigacionCoordinador investigacionCoordinador = (InvestigacionCoordinador) jsonTransformer.fromJson(jsonEntrada, InvestigacionCoordinador.class);
            investigacionCoordinadorService.create(investigacionCoordinador);
            investigacionCoordinadorService.commit();
            String jsonSalida = jsonTransformer.toJson(investigacionCoordinador);
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
                investigacionCoordinadorService.rollback();
            } catch (IOException ex1) {
                Logger.getLogger(InvestigacionCoordinadorController.class.getName()).log(Level.SEVERE, null, ex1);
            } catch(Exception e){
                
            }
            System.out.println("catch 1"+ex.getMessage());
            
        } catch (Exception ex) {
            httpServletResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            httpServletResponse.setContentType("text/plain; charset=UTF-8");
            try {
                ex.printStackTrace(httpServletResponse.getWriter());
                investigacionCoordinadorService.rollback();
                System.out.println("try 3"+ex.getMessage());
            } catch (IOException ex1) {
                Logger.getLogger(InvestigacionCoordinadorController.class.getName()).log(Level.SEVERE, null, ex1);
                System.out.println("catch 4"+ex1.getMessage());
            } catch (Exception e){
                
            }
            System.out.println("catch 3"+ex.getMessage());
        }finally{
            try{
                investigacionCoordinadorService.close();
            }catch (Exception e){
                
            }
        }
    }

    
    @RequestMapping(value = "/InvestigacionCoordinadorFindAll", method = RequestMethod.GET, produces = "application/json")
    public void findAllInvestigacionCoordinador(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, String jsonEntrada) {
        //Product prod=new Product();
        try {
            investigacionCoordinadorService.beginTransaction();
            List<InvestigacionCoordinador> investigacionCoordinadors = investigacionCoordinadorService.getAllInvestigacionCoordinador();
            investigacionCoordinadorService.commit();
            String jsonSalida = jsonTransformer.toJson(investigacionCoordinadors);
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
                investigacionCoordinadorService.rollback();
                System.out.println("2do try ");
            } catch (IOException ex1) {
                Logger.getLogger(InvestigacionCoordinadorController.class.getName()).log(Level.SEVERE, null, ex1);
                System.out.println("2do catch "+ex1.getMessage());
            } catch(Exception e){
                
            }
            
            System.out.println("1er catch "+ex.getMessage());
            
        } catch (Exception ex) {
            httpServletResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            try{
                investigacionCoordinadorService.rollback();
            }catch(Exception e){
                
            }
            
            System.out.println("3er catch "+ex.getMessage());
        }

    }
    
    @RequestMapping(value = "/InvestigacionCoordinadorByIdInvestigacionFind/{idInvestigacion}", method = RequestMethod.GET, produces = "application/json",consumes = "application/json")
    public void findInvestigacionCoordinadorByIdInvestigacion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,@PathVariable("idInvestigacion") String idInvestigacion) {
        //Product prod=new Product();
        try {
            //List<InvestigacionCoordinador> investigacionCoordinadors = investigacionCoordinadorService.getAllInvestigacionCoordinador();
            investigacionCoordinadorService.beginTransaction();
            List<Object> investigacionCoordinadors = investigacionCoordinadorService.getInvestigacionCoordinadorByIdInvestigacion(idInvestigacion);
            investigacionCoordinadorService.commit();
            String jsonSalida = jsonTransformer.toJson(investigacionCoordinadors);
            System.out.println(jsonSalida);
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
                investigacionCoordinadorService.rollback();
                System.out.println("2do try ");
            } catch (IOException ex1) {
                Logger.getLogger(InvestigacionCoordinadorController.class.getName()).log(Level.SEVERE, null, ex1);
                System.out.println("2do catch "+ex1.getMessage());
            } catch(Exception e){
                
            }
            
            System.out.println("1er catch "+ex.getMessage());
            
        } catch (Exception ex) {
            httpServletResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            try{
                investigacionCoordinadorService.rollback();
            }catch(Exception e){
            }
            System.out.println("3er catch "+ex.getMessage());
        }finally{
            try{
                investigacionCoordinadorService.close();
            }catch(Exception e){
                
            }
        }

    }
    
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
//    
//    @RequestMapping(value = "/Producto", method = RequestMethod.PUT, consumes = "application/json", produces = "application/json")
//    public void update(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @RequestBody String jsonEntrada) {
//        try {
//            System.out.println("entró Producto updateController");
//            Producto producto = (Producto) jsonTransformer.fromJson(jsonEntrada, Producto.class);
//            System.out.println("se transformó producto "+producto.getIdProducto());
//            productoService.update(producto);
//            System.out.println("actualizó");
//            String jsonSalida = jsonTransformer.toJson(producto);
//            System.out.println("devuelve json"+jsonSalida);
//            
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
//            } catch (IOException ex1) {
//                Logger.getLogger(ProductoController.class.getName()).log(Level.SEVERE, null, ex1);
//            }
//
//        } catch (IOException ex) {
//            httpServletResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
//            httpServletResponse.setContentType("text/plain; charset=UTF-8");
//            try {
//                ex.printStackTrace(httpServletResponse.getWriter());
//            } catch (IOException ex1) {
//                Logger.getLogger(ProductoController.class.getName()).log(Level.SEVERE, null, ex1);
//            }
//        }
//    }
//    
//    @RequestMapping(value = "/ProductoDel", method = RequestMethod.PUT, consumes = "application/json")
//    public void deleteProducto(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @RequestBody String jsonEntrada) {
//        try {
//            System.out.println("entró deleteProducto Controller");
//            Producto producto = (Producto) jsonTransformer.fromJson(jsonEntrada, Producto.class);
//            System.out.println("se transformó producto "+producto.getIdProducto());
//            productoService.delete(producto);
//            System.out.println("borró Producto Del Controller");
//            //String jsonSalida = jsonTransformer.toJson(producto);
//            //System.out.println("devuelve json"+jsonSalida);
//            
//            httpServletResponse.setStatus(HttpServletResponse.SC_OK);
//            //httpServletResponse.setContentType("application/json; charset=UTF-8");
//            //httpServletResponse.getWriter().println(jsonSalida);
//            
//        } catch (BussinessException ex) {
//            List<BussinessMessage> bussinessMessage=ex.getBussinessMessages();
//            String jsonSalida = jsonTransformer.toJson(bussinessMessage);
//            
//            httpServletResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
//            httpServletResponse.setContentType("application/json; charset=UTF-8");
//            try {
//                httpServletResponse.getWriter().println(jsonSalida);
//            } catch (IOException ex1) {
//                Logger.getLogger(ProductoController.class.getName()).log(Level.SEVERE, null, ex1);
//            }
//
//            
//        } catch (Exception ex) {
//            httpServletResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
//            httpServletResponse.setContentType("text/plain; charset=UTF-8");
//            try {
//                ex.printStackTrace(httpServletResponse.getWriter());
//            } catch (IOException ex1) {
//                Logger.getLogger(ProductoController.class.getName()).log(Level.SEVERE, null, ex1);
//            }
//        }
//    }
//    
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
