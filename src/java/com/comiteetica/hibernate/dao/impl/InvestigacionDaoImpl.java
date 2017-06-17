/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comiteetica.hibernate.dao.impl;

import com.comiteetica.hibernate.dao.InvestigacionDao;
import com.comiteetica.hibernate.model.HibernateUtil;
import com.comiteetica.hibernate.model.Investigacion;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

/**
 *
 * @author rasec
 */
@Repository
public class InvestigacionDaoImpl implements InvestigacionDao{

    @Override
    public void create(Investigacion investigacion) {
        SessionFactory sessionFactory=HibernateUtil.getSessionFactory();
        sessionFactory.getCurrentSession().beginTransaction();
        sessionFactory.getCurrentSession().save(investigacion);
        sessionFactory.getCurrentSession().getTransaction().commit();
        sessionFactory.getCurrentSession().close(); 
    }

    @Override
    public Investigacion read(String idInvestigacion) {
        SessionFactory sessionFactory=HibernateUtil.getSessionFactory();
        sessionFactory.openSession();
        sessionFactory.getCurrentSession().beginTransaction();
        Investigacion investigacion=(Investigacion)sessionFactory.getCurrentSession().get(Investigacion.class,idInvestigacion);
        //Hibernate.initialize(sede.getInvestigacionSedes());
        sessionFactory.getCurrentSession().getTransaction().commit();
        sessionFactory.getCurrentSession().close();
        return investigacion;
    }

    @Override
    public void update(Investigacion investigacion) {
        SessionFactory sessionFactory=HibernateUtil.getSessionFactory();
        sessionFactory.getCurrentSession().beginTransaction();
        sessionFactory.getCurrentSession().update(investigacion);
        sessionFactory.getCurrentSession().getTransaction().commit();
        sessionFactory.getCurrentSession().close();
    }

    @Override
    public void delete(Investigacion investigacion) {
        SessionFactory sessionFactory=HibernateUtil.getSessionFactory();
        sessionFactory.getCurrentSession().beginTransaction();
        sessionFactory.getCurrentSession().delete(investigacion);
        sessionFactory.getCurrentSession().getTransaction().commit();
        sessionFactory.getCurrentSession().close();
    }

    @Override
    public List<Investigacion> getAllInvestigacion() {
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
        List<Investigacion> investigacions=new ArrayList<>();
        /*Realiza consulta y devuelve Object[]*/
        List<Object[]> list=query.list();
        /*Itera en cada fila*/
        list.stream().forEach((investigacion)->{
            Investigacion inv=new Investigacion();
            inv.setIdInvestigacion(investigacion[0].toString());
            inv.setTitulo(investigacion[1].toString());
            investigacions.add(inv);
        });
        
        //System.out.println("terminó del createQuery"+productos.get(0).getDescripcion());
        sessionFactory.getCurrentSession().getTransaction().commit();
        sessionFactory.getCurrentSession().close();
        
        return investigacions;
    }
    
}
