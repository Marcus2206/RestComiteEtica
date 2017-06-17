/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comiteetica.hibernate.dao.impl;

import com.comiteetica.hibernate.dao.SerieDao;
import com.comiteetica.hibernate.model.HibernateUtil;
import com.comiteetica.hibernate.model.Serie;
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
public class SerieDaoImpl implements SerieDao{

    @Override
    public void create(Serie serie) {
        SessionFactory sessionFactory=HibernateUtil.getSessionFactory();
        sessionFactory.getCurrentSession().beginTransaction();
        sessionFactory.getCurrentSession().save(serie);
        sessionFactory.getCurrentSession().getTransaction().commit();
        sessionFactory.getCurrentSession().close(); 
    }

    @Override
    public Serie read(String idSerie) {
        SessionFactory sessionFactory=HibernateUtil.getSessionFactory();
        sessionFactory.openSession();
        sessionFactory.getCurrentSession().beginTransaction();
        Serie serie=(Serie)sessionFactory.getCurrentSession().get(Serie.class,idSerie);
        Hibernate.initialize(serie.getSerieCorrelativos());
        sessionFactory.getCurrentSession().getTransaction().commit();
        sessionFactory.getCurrentSession().close();
        return serie;
    }

    @Override
    public void update(Serie serie) {
        SessionFactory sessionFactory=HibernateUtil.getSessionFactory();
        sessionFactory.getCurrentSession().beginTransaction();
        sessionFactory.getCurrentSession().update(serie);
        sessionFactory.getCurrentSession().getTransaction().commit();
        sessionFactory.getCurrentSession().close();
    }

    @Override
    public void delete(Serie serie) {
        SessionFactory sessionFactory=HibernateUtil.getSessionFactory();
        sessionFactory.getCurrentSession().beginTransaction();
        sessionFactory.getCurrentSession().delete(serie);
        sessionFactory.getCurrentSession().getTransaction().commit();
        sessionFactory.getCurrentSession().close();
    }

    @Override
    public List<Serie> getAllSerie() {
        SessionFactory sessionFactory=HibernateUtil.getSessionFactory();
        sessionFactory.getCurrentSession().beginTransaction();

        /*Fabrica Query*/
        Query query=sessionFactory.getCurrentSession()
                                .createQuery("select "
                                                    +"idProducto, " 
                                                    +"nombre, " 
                                                    +"descripcion "  
                                            +"from    Producto p" );
        //query.setFirstResult(ini);
        //query.setMaxResults(fin);
        /*Crea Objeto contenedor*/
        List<Serie> series=new ArrayList<>();
        /*Realiza consulta y devuelve Object[]*/
        List<Object[]> list=query.list();
        /*Itera en cada fila*/
        list.stream().forEach((serie)->{
            Serie ser=new Serie();
            ser.setIdSerie(serie[0].toString());
            ser.setDescripcion(serie[1].toString());
            series.add(ser);
        });
        
        //System.out.println("terminó del createQuery"+productos.get(0).getDescripcion());
        sessionFactory.getCurrentSession().getTransaction().commit();
        sessionFactory.getCurrentSession().close();
        
        return series;
    }
    
}
