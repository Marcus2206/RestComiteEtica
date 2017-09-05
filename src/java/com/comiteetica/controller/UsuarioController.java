/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comiteetica.controller;

import com.comiteetica.hibernate.model.Usuario;
import com.comiteetica.hibernate.service.UsuarioService;
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
@RequestMapping("/Usuario")
public class UsuarioController {

    @Autowired
    JsonTransformer jsonTransformer;

    @Autowired
    UsuarioService usuarioService;

    @RequestMapping(value = "/UsuarioRead/{idUsuario}", method = RequestMethod.GET, produces = "application/json")
    public void readUsuario(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @PathVariable("idUsuario") int idUsuario) {
        try {
            usuarioService.beginTransaction();
            Usuario usuario = usuarioService.read(idUsuario);
            String jsonSalida = jsonTransformer.toJson(usuario);
            usuarioService.commit();
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
                usuarioService.rollback();
            } catch (IOException ex1) {
                Logger.getLogger(CorrespondenciaController.class.getName()).log(Level.SEVERE, null, ex1);
            } catch (Exception ee) {

            }

        } catch (Exception ex) {
            try {
                usuarioService.rollback();
            } catch (Exception ee) {

            }
            httpServletResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            System.out.println("catch " + ex.getMessage());
        } finally {
            try {
                usuarioService.close();
            } catch (Exception ee) {

            }
        }

    }

    @RequestMapping(value = "/UsuarioUpdate", method = RequestMethod.PUT, consumes = "application/json", produces = "application/json")
    public void updateUsuario(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @RequestBody String jsonEntrada) {
        try {
            usuarioService.beginTransaction();
            System.out.println("jsonEntrada"+jsonEntrada);
            Usuario usuario = (Usuario) jsonTransformer.fromJson(jsonEntrada, Usuario.class);
//             System.out.println("Usuario"+usuario.getPassword());
            usuarioService.update(usuario);
            String jsonSalida = jsonTransformer.toJson(usuario);
            System.out.println("jsonSalida"+jsonSalida);
            usuarioService.commit();
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
                usuarioService.rollback();
            } catch (IOException ex1) {
                Logger.getLogger(UsuarioController.class.getName()).log(Level.SEVERE, null, ex1);
            } catch (Exception ee) {

            }

        } catch (IOException ex) {
            httpServletResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            httpServletResponse.setContentType("text/plain; charset=UTF-8");
            try {
                ex.printStackTrace(httpServletResponse.getWriter());
                usuarioService.rollback();
            } catch (IOException ex1) {
                Logger.getLogger(UsuarioController.class.getName()).log(Level.SEVERE, null, ex1);
            } catch (Exception ee) {

            }
        } finally {
            try {
                usuarioService.close();
            } catch (Exception eee) {

            }
        }
    }

    @RequestMapping(value = "/PasswordUpdate", method = RequestMethod.PUT, consumes = "application/json", produces = "application/json")
    public void updatePassword(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
            @RequestParam("idUsuario") int idUsuario, @RequestParam("password") String password, @RequestParam("usuarioModifica") String usuarioModifica) {
        try {
            usuarioService.beginTransaction();
//            Usuario usuario = (Usuario) jsonTransformer.fromJson(jsonEntrada, Usuario.class);
            java.util.Date date = java.sql.Date.from(Instant.now());
            usuarioService.updateSql(idUsuario, password, usuarioModifica, date);
            usuarioService.commit();
            httpServletResponse.setStatus(HttpServletResponse.SC_OK);
            httpServletResponse.setContentType("application/json; charset=UTF-8");
            httpServletResponse.getWriter().println("");

        } catch (BussinessException ex) {
            List<BussinessMessage> bussinessMessage = ex.getBussinessMessages();
            String jsonSalida = jsonTransformer.toJson(bussinessMessage);

            httpServletResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            httpServletResponse.setContentType("application/json; charset=UTF-8");
            try {
                httpServletResponse.getWriter().println(jsonSalida);
                usuarioService.rollback();
            } catch (IOException ex1) {
                Logger.getLogger(UsuarioController.class.getName()).log(Level.SEVERE, null, ex1);
            } catch (Exception ee) {

            }

        } catch (IOException ex) {
            httpServletResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            httpServletResponse.setContentType("text/plain; charset=UTF-8");
            try {
                ex.printStackTrace(httpServletResponse.getWriter());
                usuarioService.rollback();
            } catch (IOException ex1) {
                Logger.getLogger(UsuarioController.class.getName()).log(Level.SEVERE, null, ex1);
            } catch (Exception ee) {

            }
        } finally {
            try {
                usuarioService.close();
            } catch (Exception eee) {

            }
        }
    }

    @RequestMapping(value = "/UsuarioReadValidate", method = RequestMethod.GET, produces = "application/json")
    public void validateReadUsuario(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @RequestParam("usuario") String usuario, @RequestParam("password") String password) {
        try {
            usuarioService.beginTransaction();
            String jsonSalida = "[]";
            List<Object> user = usuarioService.readSql(usuario, password);
            if (user != null) {
                if (user.size() > 0) {
                    if (user.get(0) != null) {
                        jsonSalida = "[" + ((String) user.get(0)) + "]";
                    }
                }
            }
            usuarioService.commit();
            httpServletResponse.setStatus(HttpServletResponse.SC_OK);
            httpServletResponse.setContentType("application/json; charset=UTF-8");
            System.out.println("validateReadUsuario"+jsonSalida);
            httpServletResponse.getWriter().println(jsonSalida);
        } catch (BussinessException ex) {
            List<BussinessMessage> bussinessMessage = ex.getBussinessMessages();
            String jsonSalida = jsonTransformer.toJson(bussinessMessage);

            httpServletResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            httpServletResponse.setContentType("application/json; charset=UTF-8");
            try {
                httpServletResponse.getWriter().println(jsonSalida);
                usuarioService.rollback();
            } catch (IOException ex1) {
                Logger.getLogger(CorrespondenciaController.class.getName()).log(Level.SEVERE, null, ex1);
            } catch (Exception ee) {

            }

        } catch (Exception ex) {
            try {
                usuarioService.rollback();
            } catch (Exception ee) {

            }
            httpServletResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            System.out.println("catch " + ex.getMessage());
        } finally {
            try {
                usuarioService.close();
            } catch (Exception ee) {

            }
        }

    }

    @RequestMapping(value = "/UsuarioInsert", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public void insertUsuario(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
            @RequestParam("usuario") String usuario, @RequestParam("password") String password, @RequestParam("perfil") String perfil,
            @RequestParam("usuarioIngresa") String usuarioIngresa, @RequestParam("estado") Boolean estado) {
        try {
            usuarioService.beginTransaction();
            System.out.println("usuarioService.beginTransaction();");
            java.util.Date date = java.sql.Date.from(Instant.now());
            int i = usuarioService.createSql(usuario, password, perfil, usuarioIngresa, date, estado );
            System.out.println("i: "+i);
//            usuarioService.commit();
            System.out.println("usuarioService.commit();");
            String jsonSalida = "{}";
            if (i==1) {
//                usuarioService.beginTransaction();
                System.out.println("usuarioService.beginTransaction();"+usuario+"///"+password);
                List<Object> user = usuarioService.readSql(usuario, password);
                System.out.println("List<Object> user");
                if (user != null) {
                    if (user.size() > 0) {
                        if (user.get(0) != null) {
                            jsonSalida = "" + ((String) user.get(0)) + "";
                            System.out.println("jsonSalida: "+jsonSalida);
                        }
                    }
                }
                usuarioService.commit();
                System.out.println(" usuarioService.commit();: ");
            }
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
                usuarioService.rollback();
            } catch (IOException ex1) {
                Logger.getLogger(UsuarioController.class.getName()).log(Level.SEVERE, null, ex1);
            } catch (Exception ee) {

            }
            System.out.println("catch 1" + ex.getMessage());

        } catch (Exception ex) {
            httpServletResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            httpServletResponse.setContentType("text/plain; charset=UTF-8");
            try {
                ex.printStackTrace(httpServletResponse.getWriter());
                usuarioService.rollback();
                System.out.println("try 3" + ex.getMessage());
            } catch (IOException ex1) {
                Logger.getLogger(UsuarioController.class.getName()).log(Level.SEVERE, null, ex1);
                System.out.println("catch 4" + ex1.getMessage());
            } catch (Exception ee) {

            }
            System.out.println("catch 3" + ex.getMessage());
        } finally {
            try {
                usuarioService.close();
            } catch (Exception ee) {

            }
        }
    }

    @RequestMapping(value = "/UsuarioListFindAll", method = RequestMethod.GET, produces = "application/json", consumes = "application/json")
    public void findAllUsuarioList(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        try {
            
            usuarioService.beginTransaction();
            String jsonSalida = "[]";
            List<Object> usuarios = usuarioService.getAllUsuarioList();
            if (usuarios != null) {
                if (usuarios.size() > 0) {
                    if (usuarios.get(0) != null) {
                        jsonSalida = "[" + ((String) usuarios.get(0)) + "]";
                    }
                }
            }
            usuarioService.commit();
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
                usuarioService.rollback();
                System.out.println("2do try ");
            } catch (IOException ex1) {
                Logger.getLogger(UsuarioController.class.getName()).log(Level.SEVERE, null, ex1);
                System.out.println("2do catch " + ex1.getMessage());
            } catch (Exception e) {

            }
            System.out.println("1er catch " + ex.getMessage());

        } catch (Exception ex) {
            httpServletResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            try {
                usuarioService.rollback();
            } catch (Exception e) {
            }
            System.out.println("3er catch " + ex.getMessage());
        } finally {
            try {
                usuarioService.close();
            } catch (Exception e) {

            }
        }
    }

    @RequestMapping(value = "/UsuarioDelete", method = RequestMethod.PUT, consumes = "application/json")
    public void deleteUsuario(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @RequestParam int idUsuario) {
        try {

            usuarioService.beginTransaction();
            Usuario usuario = usuarioService.read(idUsuario);
            usuarioService.delete(usuario);
            usuarioService.commit();
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
                usuarioService.rollback();
            } catch (IOException ex1) {
                Logger.getLogger(UsuarioController.class.getName()).log(Level.SEVERE, null, ex1);
            } catch (Exception eee) {

            }
            System.out.println("1er catch " + ex.getMessage());
        } catch (Exception ex) {
            httpServletResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            httpServletResponse.setContentType("text/plain; charset=UTF-8");
            try {
                ex.printStackTrace(httpServletResponse.getWriter());
                usuarioService.rollback();
            } catch (IOException ex1) {
                Logger.getLogger(UsuarioController.class.getName()).log(Level.SEVERE, null, ex1);
            } catch (Exception eee) {

            }
            System.out.println("1er catch " + ex.getMessage());
        } finally {
            try {
                usuarioService.close();
            } catch (Exception ee) {

            }
        }
    }
}
