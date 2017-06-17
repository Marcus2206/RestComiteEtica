/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comiteetica.hibernate.dao.impl;

import com.comiteetica.hibernate.dao.InvestigacionInvestigadorDao;
import com.comiteetica.hibernate.model.HibernateUtil;
import com.comiteetica.hibernate.model.InvestigacionInvestigador;
import com.comiteetica.hibernate.model.InvestigacionInvestigadorId;
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
public class InvestigacionInvestigadorDaoImpl implements InvestigacionInvestigadorDao{

    @Override
    public void create(InvestigacionInvestigador investigacionInvestigador) {
        SessionFactory sessionFactory=HibernateUtil.getSessionFactory();
        sessionFactory.getCurrentSession().beginTransaction();
        sessionFactory.getCurrentSession().save(investigacionInvestigador);
        sessionFactory.getCurrentSession().getTransaction().commit();
        sessionFactory.getCurrentSession().close(); 
    }

    @Override
    public InvestigacionInvestigador read(InvestigacionInvestigadorId investigacionInvestigadorId) {
        SessionFactory sessionFactory=HibernateUtil.getSessionFactory();
        sessionFactory.openSession();
        sessionFactory.getCurrentSession().beginTransaction();
        InvestigacionInvestigador investigacionInvestigador=(InvestigacionInvestigador)sessionFactory.getCurrentSession().get(InvestigacionInvestigador.class,investigacionInvestigadorId);
        //Hibernate.initialize(monitor.getInvestigacionMonitors());
        sessionFactory.getCurrentSession().getTransaction().commit();
        sessionFactory.getCurrentSession().close();
        return investigacionInvestigador;
    }

    @Override
    public void update(InvestigacionInvestigador investigacionInvestigador) {
        SessionFactory sessionFactory=HibernateUtil.getSessionFactory();
        sessionFactory.getCurrentSession().beginTransaction();
        sessionFactory.getCurrentSession().update(investigacionInvestigador);
        sessionFactory.getCurrentSession().getTransaction().commit();
        sessionFactory.getCurrentSession().close();
    }

    @Override
    public void delete(InvestigacionInvestigador investigacionInvestigador) {
        SessionFactory sessionFactory=HibernateUtil.getSessionFactory();
        sessionFactory.getCurrentSession().beginTransaction();
        sessionFactory.getCurrentSession().delete(investigacionInvestigador);
        sessionFactory.getCurrentSession().getTransaction().commit();
        sessionFactory.getCurrentSession().close();
    }

    @Override
    public List<InvestigacionInvestigador> getAllInvestigacionInvestigador() {
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
        List<InvestigacionInvestigador> investigacionInvestigadors=new ArrayList<>();
        /*Realiza consulta y devuelve Object[]*/
        List<Object[]> list=query.list();
        /*Itera en cada fila*/
        list.stream().forEach((investigacionInvestigador)->{
            InvestigacionInvestigador invInvestigador=new InvestigacionInvestigador();
            invInvestigador.setId((InvestigacionInvestigadorId)investigacionInvestigador[0]);
            invInvestigador.setObservacion(investigacionInvestigador[1].toString());
            investigacionInvestigadors.add(invInvestigador);
        });
        
        //System.out.println("terminó del createQuery"+productos.get(0).getDescripcion());
        sessionFactory.getCurrentSession().getTransaction().commit();
        sessionFactory.getCurrentSession().close();
        
        return investigacionInvestigadors;
    }
    
}
