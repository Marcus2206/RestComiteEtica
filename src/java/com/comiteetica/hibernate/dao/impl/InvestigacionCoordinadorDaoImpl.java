/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comiteetica.hibernate.dao.impl;

import com.comiteetica.hibernate.model.HibernateUtil;
import com.comiteetica.hibernate.model.InvestigacionCoordinador;
import com.comiteetica.hibernate.model.InvestigacionCoordinadorId;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import com.comiteetica.hibernate.dao.InvestigacionCoordinadorDao;
import com.comiteetica.hibernate.model.Coordinador;
import org.hibernate.Hibernate;

/**
 *
 * @author rasec
 */
@Repository
public class InvestigacionCoordinadorDaoImpl implements InvestigacionCoordinadorDao{

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
    public void create(InvestigacionCoordinador investigacionCoordinador) {
//        SessionFactory sessionFactory=HibernateUtil.getSessionFactory();
//        sessionFactory.getCurrentSession().beginTransaction();
        sessionFactory.getCurrentSession().save(investigacionCoordinador);
//        sessionFactory.getCurrentSession().getTransaction().commit();
//        sessionFactory.getCurrentSession().close(); 
    }

    @Override
    public InvestigacionCoordinador read(InvestigacionCoordinadorId investigacionCoordinadorId) {
//        SessionFactory sessionFactory=HibernateUtil.getSessionFactory();
//        sessionFactory.openSession();
//        sessionFactory.getCurrentSession().beginTransaction();
        InvestigacionCoordinador investigacionCoordinador=(InvestigacionCoordinador)sessionFactory.getCurrentSession().get(InvestigacionCoordinador.class,investigacionCoordinadorId);
//        sessionFactory.getCurrentSession().getTransaction().commit();
//        sessionFactory.getCurrentSession().close();
        return investigacionCoordinador;
    }

    @Override
    public void update(InvestigacionCoordinador investigacionCoordinador) {
//        SessionFactory sessionFactory=HibernateUtil.getSessionFactory();
//        sessionFactory.getCurrentSession().beginTransaction();
        sessionFactory.getCurrentSession().update(investigacionCoordinador);
//        sessionFactory.getCurrentSession().getTransaction().commit();
//        sessionFactory.getCurrentSession().close();
    }

    @Override
    public void delete(InvestigacionCoordinador investigacionCoordinador) {
//        SessionFactory sessionFactory=HibernateUtil.getSessionFactory();
//        sessionFactory.getCurrentSession().beginTransaction();
        sessionFactory.getCurrentSession().delete(investigacionCoordinador);
//        sessionFactory.getCurrentSession().getTransaction().commit();
//        sessionFactory.getCurrentSession().close();
    }

    @Override
    public List<InvestigacionCoordinador> getAllInvestigacionCoordinador() {
//        SessionFactory sessionFactory=HibernateUtil.getSessionFactory();
//        sessionFactory.getCurrentSession().beginTransaction();

        /*Fabrica Query*/
        Query query=sessionFactory.getCurrentSession()
                                .createQuery("select "
                                                    +"id, " 
                                                    +"observacion "  
                                            +"from    InvestigacionCoordinador " );
        //query.setFirstResult(ini);
        //query.setMaxResults(fin);
        /*Crea Objeto contenedor*/
        List<InvestigacionCoordinador> investigacionCoordinadors=new ArrayList<>();
        /*Realiza consulta y devuelve Object[]*/
        List<Object[]> list=query.list();
        /*Itera en cada fila*/
        list.stream().forEach((investigacionCoordinador)->{
            InvestigacionCoordinador invCoordinador=new InvestigacionCoordinador();
            invCoordinador.setId((InvestigacionCoordinadorId)investigacionCoordinador[0]);
            invCoordinador.setObservacion(investigacionCoordinador[1].toString());
            investigacionCoordinadors.add(invCoordinador);
        });
        
        //System.out.println("terminó del createQuery"+productos.get(0).getDescripcion());
//        sessionFactory.getCurrentSession().getTransaction().commit();
//        sessionFactory.getCurrentSession().close();
        
        return investigacionCoordinadors;
    }
    
    
    
    @Override
    public List<Object> getInvestigacionCoordinadorByIdInvestigacion(String idInvestigacion) {
        /*Fabrica Query*/
        Query query=sessionFactory.getCurrentSession()
                                .createQuery("select ic,c " +
                                             "	from InvestigacionCoordinador ic " +
                                             "inner join ic.coordinador c " +
                                             "where  ic.id.idInvestigacion='"+idInvestigacion+"'" );
        
        //query.setFirstResult(ini);
        //query.setMaxResults(fin);
        /*Crea Objeto contenedor*/
        List<Object> objetos=new ArrayList<>();
        /*Realiza consulta y devuelve Object[]*/
        List<Object[]> list=query.list();
        /*Itera en cada fila*/
        list.stream().forEach((objeto)->{
            Object[] o=new Object[2];
            o[0]=(InvestigacionCoordinador)objeto[0];
            o[1]=(Coordinador)objeto[1];
            objetos.add(o);
        });
   
        return objetos;
    }
    
}
