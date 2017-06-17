/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comiteetica.controller;

import com.comiteetica.hibernate.model.InvestigacionInvestigador;
import com.comiteetica.hibernate.model.InvestigacionInvestigadorId;
import com.comiteetica.hibernate.service.InvestigacionInvestigadorService;
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
public class InvestigacionInvestigadorController{
    @Autowired
    private JsonTransformer jsonTransformer;
    
    @Autowired
    private InvestigacionInvestigadorService investigacionInvestigadorService;
    
//    @RequestMapping(value = "/Investigacion/{idInvestigacion}/{idCoordinador}", method = RequestMethod.GET, produces = "application/json")
//    public void read(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @PathVariable("idInvestigacion") String idInvestigacion,@PathVariable("idCoordinador") String idCoordinador) {
//        try {
//            System.out.println("antes de cargar");
//            InvestigacionCoordinadorId investigacionCoordinadorId=new InvestigacionCoordinadorId(idInvestigacion,idCoordinador);
//            InvestigacionCoordinador investigacionCoordinador = investigacionInvestigadorService.read(investigacionCoordinadorId);
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
//    @RequestMapping(value = "/Producto", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
//    public void insert(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @RequestBody String jsonEntrada) {
//        
//        try {
//            Producto producto = (Producto) jsonTransformer.fromJson(jsonEntrada, Producto.class);
//            producto.setIdProducto( productoService.getNextIdProducto());
//            productoService.create(producto);
//            String jsonSalida = jsonTransformer.toJson(producto);
//            httpServletResponse.setStatus(HttpServletResponse.SC_OK);
//            httpServletResponse.setContentType("application/json; charset=UTF-8");
//            httpServletResponse.getWriter().println(jsonSalida);
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
//            System.out.println("catch 1"+ex.getMessage());
//            
//        } catch (Exception ex) {
//            httpServletResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
//            httpServletResponse.setContentType("text/plain; charset=UTF-8");
//            try {
//                ex.printStackTrace(httpServletResponse.getWriter());
//                System.out.println("try 3"+ex.getMessage());
//            } catch (IOException ex1) {
//                Logger.getLogger(ProductoController.class.getName()).log(Level.SEVERE, null, ex1);
//                System.out.println("catch 4"+ex1.getMessage());
//            }
//            System.out.println("catch 3"+ex.getMessage());
//        }
//        
//        System.out.println("final");
//    }
//
//    
//    @RequestMapping(value = "/Producto", method = RequestMethod.GET, produces = "application/json")
//    public void find(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, String jsonEntrada) {
//        //Product prod=new Product();
//        try {
//            
//            /* esto sí funcionó
//            System.out.println("antes de listar");
//            List<Producto> productos = prod.getProductos();
//            System.out.println("terminó");
//            */
//            //ArrayList list = (ArrayList)jsonTransformer.fromJson(jsonEntrada, ArrayList.class);
//            
////            System.out.println("antes de listar ini ::"+list.get(0).toString());
////            System.out.println("antes de listar fin ::"+list.get(1).toString());
//            System.out.println("jsonEntrada "+jsonEntrada);
//           
//            List<Producto> productos = productoService.getAllProducto(0,25);
//            System.out.println("terminó");
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
    @RequestMapping(value = "/InvestigacionInvestigadorRead", method = RequestMethod.PATCH, produces = "application/json",consumes = "application/json")
    public void readInvestigacionInvestigador(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,@RequestBody String jsonEntrada) {
        //Product prod=new Product();
        try {
            
            /* esto sí funcionó
            System.out.println("antes de listar");
            List<Producto> productos = prod.getProductos();
            System.out.println("terminó");
            */ 
            System.out.println("jsonEntrada "+jsonEntrada);
            InvestigacionInvestigadorId investigacionInvestigadorId = (InvestigacionInvestigadorId)jsonTransformer.fromJson(jsonEntrada, InvestigacionInvestigadorId.class);
//            Object list = (Object)jsonTransformer.fromJson(jsonEntrada, Object.class);
//            List<Object[]> list = (List<Object[]> )jsonTransformer.fromJson(jsonEntrada, Object[].class);
            System.out.println("antes de listar getIdInvestigador ::"+investigacionInvestigadorId.getIdInvestigador());
            
            System.out.println("antes de listar getIdInvestigacion ::"+investigacionInvestigadorId.getIdInvestigacion());
            //System.out.println("antes de listar fin ::"+list.get(1).toString());
           
           
            InvestigacionInvestigador investigacionInvestigador = investigacionInvestigadorService.read(investigacionInvestigadorId);
            //System.out.println("terminó "+productos.size());
            //System.out.println("Listó"+productos.get(0).getMarca().getDescripcion());

            String jsonSalida = jsonTransformer.toJson(investigacionInvestigador);
            System.out.println("transformó lista completa: "+jsonSalida);
            //httpServletRequest.
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
