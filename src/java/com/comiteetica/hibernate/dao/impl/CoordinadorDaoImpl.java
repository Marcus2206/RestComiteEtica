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

    @Override
    public void create(Coordinador coordinador) {
        SessionFactory sessionFactory=HibernateUtil.getSessionFactory();
        sessionFactory.getCurrentSession().beginTransaction();
        coordinador.setIdCoordinador(getNextId());
        sessionFactory.getCurrentSession().save(coordinador);
        sessionFactory.getCurrentSession().getTransaction().commit();
        sessionFactory.getCurrentSession().close(); 
    }

    @Override
    public String getNextId() {
        SessionFactory sessionFactory=HibernateUtil.getSessionFactory();
        //sessionFactory.getCurrentSession().beginTransaction();
        
        String serie="COD";
        java.util.Date date = Date.from(Instant.now());
        java.sql.Timestamp param = new java.sql.Timestamp(date.getTime());
        Query query = sessionFactory.getCurrentSession()
                .createSQLQuery("select dbo.fneGetSerieCorrelativo(?,?) from fecha" )
                .setString(0, serie)
                .setDate(1, param);
        String nextId="-1";        
        List result = query.list();
        for(int i=0; i<result.size(); i++){
                nextId=result.get(i).toString();
        }

        //sessionFactory.getCurrentSession().getTransaction().commit();
        //sessionFactory.getCurrentSession().close(); 
        return nextId;
    }
    @Override
    public Coordinador read(String idCoordinador) {
        SessionFactory sessionFactory=HibernateUtil.getSessionFactory();
        sessionFactory.openSession();
        sessionFactory.getCurrentSession().beginTransaction();
        Coordinador investigador=(Coordinador)sessionFactory.getCurrentSession().get(Coordinador.class,idCoordinador);
        Hibernate.initialize(investigador.getInvestigacionCoordinadors());
        sessionFactory.getCurrentSession().getTransaction().commit();
        sessionFactory.getCurrentSession().close();
        return investigador;
    }

    @Override
    public void update(Coordinador coordinador) {
        SessionFactory sessionFactory=HibernateUtil.getSessionFactory();
        sessionFactory.getCurrentSession().beginTransaction();
        sessionFactory.getCurrentSession().update(coordinador);
        sessionFactory.getCurrentSession().getTransaction().commit();
        sessionFactory.getCurrentSession().close();
    }

    @Override
    public void delete(Coordinador coordinador) {
        SessionFactory sessionFactory=HibernateUtil.getSessionFactory();
        sessionFactory.getCurrentSession().beginTransaction();
        sessionFactory.getCurrentSession().delete(coordinador);
        sessionFactory.getCurrentSession().getTransaction().commit();
        sessionFactory.getCurrentSession().close();
    }

    @Override
    public List<Coordinador> getAllCoordinador() {
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
        List<Coordinador> coordinadors=new ArrayList<>();
        /*Realiza consulta y devuelve Object[]*/
        List<Object[]> list=query.list();
        /*Itera en cada fila*/
        list.stream().forEach((coordinador)->{
            Coordinador coor=new Coordinador();
            coor.setIdCoordinador(coordinador[0].toString());
            coor.setNombres(coordinador[1].toString());
            coor.setApePaterno(coordinador[2].toString());
            coordinadors.add(coor);
        });
        
        //System.out.println("terminó del createQuery"+productos.get(0).getDescripcion());
        sessionFactory.getCurrentSession().getTransaction().commit();
        sessionFactory.getCurrentSession().close();
        
        return coordinadors;
    }
    
}
