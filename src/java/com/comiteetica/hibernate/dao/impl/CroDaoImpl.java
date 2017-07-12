/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comiteetica.hibernate.dao.impl;

import com.comiteetica.hibernate.dao.CroDao;
import com.comiteetica.hibernate.model.Cro;
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
public class CroDaoImpl implements CroDao{

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
    public void create(Cro cro) {
        sessionFactory.getCurrentSession().save(cro);
    }

    @Override
    public Cro read(String idCro) {
        Cro cro=(Cro)sessionFactory.getCurrentSession().get(Cro.class,idCro);
        return cro;
    }

    @Override
    public void update(Cro cro) {
        sessionFactory.getCurrentSession().update(cro);
    }

    @Override
    public void delete(Cro cro) {
        sessionFactory.getCurrentSession().delete(cro);
    }

    @Override
    public List<Cro> getAllCro() {
        /*Fabrica Query*/
        Query query=sessionFactory.getCurrentSession()
                                .createQuery("select c.idCro, c.nombre from Cro c" );
        //query.setFirstResult(ini);
        //query.setMaxResults(fin);
        /*Crea Objeto contenedor*/
        List<Cro> cros=new ArrayList<>();
        /*Realiza consulta y devuelve Object[]*/
        List<Object[]> list=query.list();
        /*Itera en cada fila*/
        list.stream().forEach((cro)->{
            Cro c=new Cro();
            c.setIdCro((String)cro[0]);
            c.setNombre((String)cro[1]);
            cros.add(c);
        });
        return cros;
    }
    
}
