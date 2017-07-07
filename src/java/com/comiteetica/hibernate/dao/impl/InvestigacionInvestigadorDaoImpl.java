/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comiteetica.hibernate.dao.impl;

import com.comiteetica.hibernate.dao.InvestigacionInvestigadorDao;
import com.comiteetica.hibernate.model.InvestigacionInvestigador;
import com.comiteetica.hibernate.model.InvestigacionInvestigadorId;
import com.comiteetica.hibernate.model.Investigador;
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
    public void create(InvestigacionInvestigador investigacionInvestigador) {
        sessionFactory.getCurrentSession().save(investigacionInvestigador);
    }

    @Override
    public InvestigacionInvestigador read(InvestigacionInvestigadorId investigacionInvestigadorId) {
        InvestigacionInvestigador investigacionInvestigador=(InvestigacionInvestigador)sessionFactory.getCurrentSession().get(InvestigacionInvestigador.class,investigacionInvestigadorId);
        //Hibernate.initialize(monitor.getInvestigacionMonitors());
        return investigacionInvestigador;
    }

    @Override
    public void update(InvestigacionInvestigador investigacionInvestigador) {
        sessionFactory.getCurrentSession().update(investigacionInvestigador);
    }

    @Override
    public void delete(InvestigacionInvestigador investigacionInvestigador) {
        sessionFactory.getCurrentSession().delete(investigacionInvestigador);
    }

    @Override
    public List<InvestigacionInvestigador> getAllInvestigacionInvestigador() {
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
        return investigacionInvestigadors;
    }
    
    @Override
    public List<Object> getInvestigacionInvestigadorByIdInvestigacion(String idInvestigacion) {
        /*Fabrica Query*/
        Query query=sessionFactory.getCurrentSession()
                                .createQuery("select ic,c " +
                                             "	from InvestigacionInvestigador ic " +
                                             "inner join ic.investigador c " +
                                             "where  ic.id.idInvestigacion='"+idInvestigacion+"'" );
        
        //query.setFirstResult(ini);
        //query.setMaxResults(fin);
        /*Crea Objeto contenedor*/
        List<Object> objetos=new ArrayList<>();
        /*Realiza consulta y devuelve Object[]*/
        List<Object[]> list=query.list();
        /*Itera en cada fila*/
        list.stream().forEach((objeto)->{
            Object[] o=new Object[2];
            o[0]=(InvestigacionInvestigador)objeto[0];
            o[1]=(Investigador)objeto[1];
            objetos.add(o);
        });
   
        return objetos;
    }
}
