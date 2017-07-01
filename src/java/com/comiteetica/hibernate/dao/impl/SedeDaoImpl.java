/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comiteetica.hibernate.dao.impl;

import com.comiteetica.hibernate.dao.SedeDao;
import com.comiteetica.hibernate.model.HibernateUtil;
import com.comiteetica.hibernate.model.Sede;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

/**
 *
 * @author rasec
 */
@Repository
public class SedeDaoImpl implements SedeDao{

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
    public void create(Sede sede) {
        sessionFactory.getCurrentSession().save(sede);
    }

    @Override
    public Sede read(String idSede) {
        Sede sede=(Sede)sessionFactory.getCurrentSession().get(Sede.class,idSede);
//        Hibernate.initialize(sede.getInvestigacionSedes());
        return sede;
    }

    @Override
    public void update(Sede sede) {
        sessionFactory.getCurrentSession().update(sede);
    }

    @Override
    public void delete(Sede sede) {
        sessionFactory.getCurrentSession().delete(sede);
    }

    @Override
    public List<Sede> getAllSede() {
        /*Fabrica Query*/
        Query query=sessionFactory.getCurrentSession()
                                .createQuery("select s from Sede s" );
        //query.setFirstResult(ini);
        //query.setMaxResults(fin);
        /*Crea Objeto contenedor*/
//        List<Sede> sedes=new ArrayList<>();
        /*Realiza consulta y devuelve Object[]*/
        List<Sede> sedes=query.list();
        /*Itera en cada fila*/
//        list.stream().forEach((sede)->{
//            Sede sed=new Sede();
//            sed.setIdSede(sede[0].toString());
//            sed.setNombre(sede[1].toString());
//            sedes.add(sed);
//        });
        
        //System.out.println("terminó del createQuery"+productos.get(0).getDescripcion());        
        return sedes;
    }
    
}
