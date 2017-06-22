/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comiteetica.hibernate.dao.impl;

import com.comiteetica.hibernate.dao.CoordinadorDao;
import com.comiteetica.hibernate.model.Coordinador;
import com.comiteetica.hibernate.model.HibernateUtil;
import java.sql.Date;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Hibernate;
import org.hibernate.SessionFactory;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author rasec
 */
@Repository
public class CoordinadorDaoImpl implements CoordinadorDao{

    SessionFactory sessionFactory=HibernateUtil.getSessionFactory();
    
    @Override
    public void beginTransaction(){
        sessionFactory.getCurrentSession().beginTransaction();
    }
    
    @Override
    public void commit(){
        sessionFactory.getCurrentSession().getTransaction().commit();
    }
    
    @Override
    public void close(){
        sessionFactory.getCurrentSession().close();
    }
    
    @Override
    public void rollback(){
        sessionFactory.getCurrentSession().getTransaction().rollback();
    }
    
    @Override
    public void create(Coordinador coordinador) {
//        try{
//        sessionFactory.getCurrentSession().beginTransaction();
        sessionFactory.getCurrentSession().save(coordinador);
//        sessionFactory.getCurrentSession().getTransaction().commit();
//        }catch(Exception e){
//            sessionFactory.getCurrentSession().getTransaction().rollback();
//            System.out.println("Error en: CoordinadorDaoImpl - create. "+e.getMessage());
//        }
//        finally{
//            try{
//                sessionFactory.getCurrentSession().close(); 
//            }catch(Exception e1){
//                System.out.println("Error en: CoordinadorDaoImpl - create - finally. "+e1.getMessage());
//            }
//        }
    }

    @Override
    public Coordinador read(String idCoordinador) {
//        SessionFactory sessionFactory=HibernateUtil.getSessionFactory();
        Coordinador investigador=null;
//        try{
//        sessionFactory.getCurrentSession().beginTransaction();
        investigador=(Coordinador)sessionFactory.getCurrentSession().get(Coordinador.class,idCoordinador);
        Hibernate.initialize(investigador.getInvestigacionCoordinadors());
//        sessionFactory.getCurrentSession().getTransaction().commit();
//        }catch(Exception e){
//            sessionFactory.getCurrentSession().getTransaction().rollback();
//            System.out.println("Error en: CoordinadorDaoImpl - read. "+e.getMessage());
//        }finally{
//            try{
//                sessionFactory.getCurrentSession().close();
//            }catch(Exception e1){
//                System.out.println("Error en: CoordinadorDaoImpl - read - finally. "+e1.getMessage());
//            }
//        }
        
        return investigador;
    }

    @Override
    public void update(Coordinador coordinador) {
//        SessionFactory sessionFactory=HibernateUtil.getSessionFactory();
//        try{
//        sessionFactory.getCurrentSession().beginTransaction();
        sessionFactory.getCurrentSession().update(coordinador);
//        sessionFactory.getCurrentSession().getTransaction().commit();
//        }catch(Exception e){
//            sessionFactory.getCurrentSession().getTransaction().rollback();
//            System.out.println("Error en: CoordinadorDaoImpl - update. "+e.getMessage());
//        }finally{
//            try{
//                sessionFactory.getCurrentSession().close();
//            }catch(Exception e1){
//                System.out.println("Error en: CoordinadorDaoImpl - update - finally. "+e1.getMessage());
//            }
//        }
        
        
    }

    @Override
    public void delete(Coordinador coordinador) {
        //SessionFactory sessionFactory=HibernateUtil.getSessionFactory();
//        try{
        //sessionFactory.getCurrentSession().beginTransaction();
        sessionFactory.getCurrentSession().delete(coordinador);
        //sessionFactory.getCurrentSession().getTransaction().commit();
//        }catch(Exception e){
////            sessionFactory.getCurrentSession().getTransaction().rollback();
//            System.out.println("Error en: CoordinadorDaoImpl - delete. "+e.getMessage());
//        }finally{
//            try{
//                //sessionFactory.getCurrentSession().close();
//            }catch(Exception e1){
//                System.out.println("Error en: CoordinadorDaoImpl - delete - finally. "+e1.getMessage());
//            }
//        }
        
    }

    @Override
    public List<Coordinador> getAllCoordinador() {
        //SessionFactory sessionFactory=HibernateUtil.getSessionFactory();
        List<Coordinador> coordinadors=new ArrayList<>();
        //try{
//        sessionFactory.getCurrentSession().beginTransaction();

        /*Fabrica Query*/
        Query query=sessionFactory.getCurrentSession()
                                .createQuery("select "
                                                    +"idCoordinador, " 
                                                    +"apePaterno, " 
                                                    +"apeMaterno,"
                                                    +"nombres "  
                                            +"from    Coordinador" );
        //query.setFirstResult(ini);
        //query.setMaxResults(fin);
        /*Crea Objeto contenedor*/
        
        /*Realiza consulta y devuelve Object[]*/
        List<Object[]> list=query.list();
        /*Itera en cada fila*/
        list.stream().forEach((coordinador)->{
            Coordinador coor=new Coordinador();
            coor.setIdCoordinador(coordinador[0].toString());
            coor.setApePaterno(coordinador[1].toString());
            coor.setApeMaterno(coordinador[2].toString());
            coor.setNombres(coordinador[3].toString());
            coordinadors.add(coor);
        });
        
        //System.out.println("terminó del createQuery"+productos.get(0).getDescripcion());
//        sessionFactory.getCurrentSession().getTransaction().commit();
        //}catch(Exception e){
            //sessionFactory.getCurrentSession().getTransaction().rollback();
            //System.out.println("Error en: CoordinadorDaoImpl - getAllCoordinador. "+e.getMessage());
        //}finally{
//            try{
//                sessionFactory.getCurrentSession().close();
//            }catch(Exception e1){
//                System.out.println("Error en: CoordinadorDaoImpl - getAllCoordinador - finally. "+e1.getMessage());
//            }
//        }
        
        
        return coordinadors;
    }
    
    @Override
    public List<Coordinador> getCoordinadorSinIdInvestigacion(String idInvestigacion) {
        List<Coordinador> coordinadors=new ArrayList<>();

        /*Fabrica Query*/
        Query query=sessionFactory.getCurrentSession()
                                .createQuery("select    c.idCoordinador, "
                                            + "         c.apePaterno," 
                                            + "         c.apeMaterno," 
                                            + "         c.nombres " 
                                            + "from     Coordinador c " 
                                            + "where    c.idCoordinador not in (select distinct ic.id.idCoordinador "
                                                                             + "from InvestigacionCoordinador ic "
                                                                             + "where ic.id.idInvestigacion='"+idInvestigacion+"')");
        
        List<Object[]> list=query.list();
        /*Itera en cada fila*/
        list.stream().forEach((coordinador)->{
            Coordinador coor=new Coordinador();
            coor.setIdCoordinador(coordinador[0].toString());
            coor.setApePaterno(coordinador[1].toString());
            coor.setApeMaterno(coordinador[2].toString());
            coor.setNombres(coordinador[3].toString());
            coordinadors.add(coor);
        });

        return coordinadors;
    }
}
