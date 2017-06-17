/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comiteetica.hibernate.dao.impl;

import com.comiteetica.hibernate.dao.InvestigadorDao;
import com.comiteetica.hibernate.model.HibernateUtil;
import com.comiteetica.hibernate.model.Investigador;
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
public class InvestigadorDaoImpl implements InvestigadorDao{

    @Override
    public void create(Investigador investigador) {
        SessionFactory sessionFactory=HibernateUtil.getSessionFactory();
        sessionFactory.getCurrentSession().beginTransaction();
        sessionFactory.getCurrentSession().save(investigador);
        sessionFactory.getCurrentSession().getTransaction().commit();
        sessionFactory.getCurrentSession().close(); 
    }

    @Override
    public Investigador read(String idInvestigador) {
        SessionFactory sessionFactory=HibernateUtil.getSessionFactory();
        System.out.println("SessionFactory sessionFactory=HibernateUtil.getSessionFactory();");
        sessionFactory.openSession();
        System.out.println("sessionFactory.openSession();");
        sessionFactory.getCurrentSession().beginTransaction();
        System.out.println("sessionFactory.getCurrentSession().beginTransaction(); ");
        Investigador investigador=(Investigador)sessionFactory.getCurrentSession().get(Investigador.class,idInvestigador);
        System.out.println("Investigador investigador=(Investigador)sessionFactory.getCurrentSession().get(Investigador.class,idInvestigador); ");
        
        Hibernate.initialize(investigador.getInvestigacionInvestigadors());
        
        sessionFactory.getCurrentSession().getTransaction().commit();
        sessionFactory.getCurrentSession().close();
        return investigador;
    }

    @Override
    public void update(Investigador investigador) {
        SessionFactory sessionFactory=HibernateUtil.getSessionFactory();
        sessionFactory.getCurrentSession().beginTransaction();
        sessionFactory.getCurrentSession().update(investigador);
        sessionFactory.getCurrentSession().getTransaction().commit();
        sessionFactory.getCurrentSession().close();
    }

    @Override
    public void delete(Investigador investigador) {
        SessionFactory sessionFactory=HibernateUtil.getSessionFactory();
        sessionFactory.getCurrentSession().beginTransaction();
        sessionFactory.getCurrentSession().delete(investigador);
        sessionFactory.getCurrentSession().getTransaction().commit();
        sessionFactory.getCurrentSession().close();
    }

    @Override
    public List<Investigador> getAllInvestigador() {
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
        List<Investigador> investigadors=new ArrayList<>();
        /*Realiza consulta y devuelve Object[]*/
        List<Object[]> list=query.list();
        /*Itera en cada fila*/
        list.stream().forEach((investigador)->{
            Investigador inv=new Investigador();
            inv.setIdInvestigador(investigador[0].toString());
            inv.setNombres(investigador[1].toString());
            inv.setApePaterno(investigador[2].toString());
            investigadors.add(inv);
        });
        
        //System.out.println("terminó del createQuery"+productos.get(0).getDescripcion());
        sessionFactory.getCurrentSession().getTransaction().commit();
        sessionFactory.getCurrentSession().close();
        
        return investigadors;
    }
    
}
