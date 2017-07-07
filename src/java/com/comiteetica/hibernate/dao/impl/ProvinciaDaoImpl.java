/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comiteetica.hibernate.dao.impl;

import com.comiteetica.hibernate.dao.ProvinciaDao;
import com.comiteetica.hibernate.model.Provincia;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

/**
 *
 * @author rasec
 */
@Repository
public class ProvinciaDaoImpl implements ProvinciaDao{
    
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
    public List<Provincia> getAllProvinciaByDepartamento(String idDepartamento) {
        Query query=sessionFactory.getCurrentSession()
                                .createQuery("select p from Provincia p where p.id.idDepartamento=:idDepartamento" )
                                .setString("idDepartamento", idDepartamento);
        List<Provincia> list=query.list();   
        return list;
    }
    

}
