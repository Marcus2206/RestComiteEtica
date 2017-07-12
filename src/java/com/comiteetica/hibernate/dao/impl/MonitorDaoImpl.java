/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comiteetica.hibernate.dao.impl;

import com.comiteetica.hibernate.dao.MonitorDao;
import com.comiteetica.hibernate.model.Monitor;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

/**
 *
 * @author rasec
 */
@Repository
public class MonitorDaoImpl implements MonitorDao{

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
    public void create(Monitor monitor) {
        sessionFactory.getCurrentSession().save(monitor);
    }

    @Override
    public Monitor read(String idMonitor) {
        Monitor monitor=(Monitor)sessionFactory.getCurrentSession().get(Monitor.class,idMonitor);
//        Hibernate.initialize(monitor.getInvestigacionMonitors());
        return monitor;
    }

    @Override
    public void update(Monitor monitor) {
        sessionFactory.getCurrentSession().update(monitor);
    }

    @Override
    public void delete(Monitor monitor) {
        sessionFactory.getCurrentSession().delete(monitor);
    }

    @Override
    public List<Monitor> getAllMonitor() {
        /*Fabrica Query*/
        Query query=sessionFactory.getCurrentSession()
                                .createQuery("select m.idMonitor,"
                                                   +"m.nombres,"
                                                   +"m.apePaterno,"
                                                   +"m.apeMaterno,"
                                                   +"m.correo "
                                                + " from Monitor m" );
        //query.setFirstResult(ini);
        //query.setMaxResults(fin);
        /*Crea Objeto contenedor*/
        List<Monitor> monitors=new ArrayList<>();
        /*Realiza consulta y devuelve Object[]*/
        List<Object[]> list=query.list();
        /*Itera en cada fila*/
        list.stream().forEach((monitor)->{
            Monitor mon=new Monitor();
            mon.setIdMonitor((String)monitor[0]);
            mon.setNombres((String)monitor[1]);
            mon.setApePaterno((String)monitor[2]);
            mon.setApeMaterno((String)monitor[3]);
            mon.setCorreo((String)monitor[4]);
            monitors.add(mon);
        });
        return monitors;
    }
    
}
