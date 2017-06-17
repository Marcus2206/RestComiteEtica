/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comiteetica.hibernate.dao.impl;

import com.comiteetica.hibernate.model.HibernateUtil;
import com.comiteetica.hibernate.model.InvestigacionCoordinador;
import com.comiteetica.hibernate.model.InvestigacionCoordinadorId;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import com.comiteetica.hibernate.dao.InvestigacionCoordinadorDao;
import org.hibernate.Hibernate;

/**
 *
 * @author rasec
 */
@Repository
public class InvestigacionCoordinadorDaoImpl implements InvestigacionCoordinadorDao{

    @Override
    public void create(InvestigacionCoordinador investigacionCoordinador) {
        SessionFactory sessionFactory=HibernateUtil.getSessionFactory();
        sessionFactory.getCurrentSession().beginTransaction();
        sessionFactory.getCurrentSession().save(investigacionCoordinador);
        sessionFactory.getCurrentSession().getTransaction().commit();
        sessionFactory.getCurrentSession().close(); 
    }

    @Override
    public InvestigacionCoordinador read(InvestigacionCoordinadorId investigacionCoordinadorId) {
        SessionFactory sessionFactory=HibernateUtil.getSessionFactory();
        sessionFactory.openSession();
        sessionFactory.getCurrentSession().beginTransaction();
        InvestigacionCoordinador investigacionCoordinador=(InvestigacionCoordinador)sessionFactory.getCurrentSession().get(InvestigacionCoordinador.class,investigacionCoordinadorId);
        sessionFactory.getCurrentSession().getTransaction().commit();
        sessionFactory.getCurrentSession().close();
        return investigacionCoordinador;
    }

    @Override
    public void update(InvestigacionCoordinador investigacionCoordinador) {
        SessionFactory sessionFactory=HibernateUtil.getSessionFactory();
        sessionFactory.getCurrentSession().beginTransaction();
        sessionFactory.getCurrentSession().update(investigacionCoordinador);
        sessionFactory.getCurrentSession().getTransaction().commit();
        sessionFactory.getCurrentSession().close();
    }

    @Override
    public void delete(InvestigacionCoordinador investigacionCoordinador) {
        SessionFactory sessionFactory=HibernateUtil.getSessionFactory();
        sessionFactory.getCurrentSession().beginTransaction();
        sessionFactory.getCurrentSession().delete(investigacionCoordinador);
        sessionFactory.getCurrentSession().getTransaction().commit();
        sessionFactory.getCurrentSession().close();
    }

    @Override
    public List<InvestigacionCoordinador> getAllInvestigacionCoordinador() {
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
        List<InvestigacionCoordinador> investigacionCoordinadors=new ArrayList<>();
        /*Realiza consulta y devuelve Object[]*/
        List<Object[]> list=query.list();
        /*Itera en cada fila*/
        list.stream().forEach((investigacionCoordinador)->{
            InvestigacionCoordinador invCoordinador=new InvestigacionCoordinador();
            invCoordinador.setId((InvestigacionCoordinadorId)investigacionCoordinador[0]);
            invCoordinador.setObservacion(investigacionCoordinador[1].toString());
            investigacionCoordinadors.add(invCoordinador);
        });
        
        //System.out.println("terminó del createQuery"+productos.get(0).getDescripcion());
        sessionFactory.getCurrentSession().getTransaction().commit();
        sessionFactory.getCurrentSession().close();
        
        return investigacionCoordinadors;
    }
    
}
