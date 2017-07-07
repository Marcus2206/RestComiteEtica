/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comiteetica.hibernate.dao.impl;

import com.comiteetica.hibernate.dao.DistritoDao;
import com.comiteetica.hibernate.model.Distrito;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

/**
 *
 * @author rasec
 */
@Repository
public class DistritoDaoImpl implements DistritoDao{
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
    public List<Distrito> getAllDistritoByDepartamentoProvincia(String idDepartamento,String idProvincia) {
        Query query=sessionFactory.getCurrentSession()
                                .createQuery("select d from Distrito d where d.id.idDepartamento=:idDepartamento "
                                            + "and d.id.idProvincia=:idProvincia" )
                                .setString("idDepartamento", idDepartamento)
                                .setString("idProvincia", idProvincia);
        List<Distrito> list=query.list();   
        return list;
    }
}
