/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comiteetica.hibernate.dao.impl;

import com.comiteetica.hibernate.dao.ParametroDetalleDao;
import com.comiteetica.hibernate.model.HibernateUtil;
import com.comiteetica.hibernate.model.ParametroDetalle;
import com.comiteetica.hibernate.model.ParametroDetalleId;
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
public class ParametroDetalleDaoImpl implements ParametroDetalleDao{
    
    @Override
    public void create(ParametroDetalle parametroDetalle) {
        SessionFactory sessionFactory=HibernateUtil.getSessionFactory();
        sessionFactory.getCurrentSession().beginTransaction();
        sessionFactory.getCurrentSession().save(parametroDetalle);
        sessionFactory.getCurrentSession().getTransaction().commit();
        sessionFactory.getCurrentSession().close(); 
    }

    @Override
    public ParametroDetalle read(ParametroDetalleId parametroDetalleId) {
        SessionFactory sessionFactory=HibernateUtil.getSessionFactory();
        sessionFactory.openSession();
        sessionFactory.getCurrentSession().beginTransaction();
        ParametroDetalle parametroDetalle=(ParametroDetalle)sessionFactory.getCurrentSession().get(ParametroDetalle.class,parametroDetalleId);
        sessionFactory.getCurrentSession().getTransaction().commit();
        sessionFactory.getCurrentSession().close();
        return parametroDetalle;
    }

    @Override
    public void update(ParametroDetalle parametroDetalle) {
        SessionFactory sessionFactory=HibernateUtil.getSessionFactory();
        sessionFactory.getCurrentSession().beginTransaction();
        sessionFactory.getCurrentSession().update(parametroDetalle);
        sessionFactory.getCurrentSession().getTransaction().commit();
        sessionFactory.getCurrentSession().close();
    }

    @Override
    public void delete(ParametroDetalle parametroDetalle) {
        SessionFactory sessionFactory=HibernateUtil.getSessionFactory();
        sessionFactory.getCurrentSession().beginTransaction();
        sessionFactory.getCurrentSession().delete(parametroDetalle);
        sessionFactory.getCurrentSession().getTransaction().commit();
        sessionFactory.getCurrentSession().close();
    }

    @Override
    public List<ParametroDetalle> getAllParametroDetalle() {
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
        List<ParametroDetalle> parametroDetalles=new ArrayList<>();
        /*Realiza consulta y devuelve Object[]*/
        List<Object[]> list=query.list();
        /*Itera en cada fila*/
        list.stream().forEach((parametroDetalle)->{
            ParametroDetalle paramDetalle=new ParametroDetalle();
            paramDetalle.setId((ParametroDetalleId)parametroDetalle[0]);
            paramDetalle.setDescripcion(parametroDetalle[1].toString());
            parametroDetalles.add(paramDetalle);
        });
        
        //System.out.println("terminó del createQuery"+productos.get(0).getDescripcion());
        sessionFactory.getCurrentSession().getTransaction().commit();
        sessionFactory.getCurrentSession().close();
        
        return parametroDetalles;
    }
    
}
