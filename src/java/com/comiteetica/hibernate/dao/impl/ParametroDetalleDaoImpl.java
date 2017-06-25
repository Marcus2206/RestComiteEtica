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
    public void create(ParametroDetalle parametroDetalle) {
        sessionFactory.getCurrentSession().save(parametroDetalle);
    }

    @Override
    public ParametroDetalle read(ParametroDetalleId parametroDetalleId) {
        ParametroDetalle parametroDetalle=(ParametroDetalle)sessionFactory.getCurrentSession().get(ParametroDetalle.class,parametroDetalleId);
        return parametroDetalle;
    }

    @Override
    public void update(ParametroDetalle parametroDetalle) {
        sessionFactory.getCurrentSession().update(parametroDetalle);
    }

    @Override
    public void delete(ParametroDetalle parametroDetalle) {
        sessionFactory.getCurrentSession().delete(parametroDetalle);
    }

    @Override
    public List<ParametroDetalle> getAllParametroDetalle() {
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
        return parametroDetalles;
    }
    
    @Override
    public List<ParametroDetalle> getParametroDetalleByIdParametro(String idParametro) {
        /*Fabrica Query*/
        Query query=sessionFactory.getCurrentSession()
                                .createQuery("select pd "
                                            + "from ParametroDetalle pd "
                                            + "where pd.id.idParametro=:idParametro" )
                                .setString("idParametro", idParametro);

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
        return parametroDetalles;
    }
}
