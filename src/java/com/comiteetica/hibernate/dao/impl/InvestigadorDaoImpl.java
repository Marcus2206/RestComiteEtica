/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comiteetica.hibernate.dao.impl;

import com.comiteetica.hibernate.dao.InvestigadorDao;
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
    public void create(Investigador investigador) {
        sessionFactory.getCurrentSession().save(investigador);
    }

    @Override
    public Investigador read(String idInvestigador) {
        Investigador investigador=(Investigador)sessionFactory.getCurrentSession().get(Investigador.class,idInvestigador);
        Hibernate.initialize(investigador.getInvestigacionInvestigadors());
        return investigador;
    }

    @Override
    public void update(Investigador investigador) {
        sessionFactory.getCurrentSession().update(investigador);
    }

    @Override
    public void delete(Investigador investigador) {
        sessionFactory.getCurrentSession().delete(investigador);
    }

    @Override
    public List<Investigador> getAllInvestigador() {
        /*Fabrica Query*/
        Query query=sessionFactory.getCurrentSession()
                                .createSQLQuery("select	idInvestigador,\n" +
"		apePaterno,\n" +
"		apeMaterno,\n" +
"		nombres,\n" +
"		correo,\n" +
"		(select Descripcion from ParametroDetalle where IdParametro='P008' and IdParametroDetalle=a.ParamEspecialidadInvestigador)paramEspecialidadInvestigador\n" +
"from	Investigador a" );
        //query.setFirstResult(ini);
        //query.setMaxResults(fin);
        /*Crea Objeto contenedor*/
        List<Investigador> investigadors=new ArrayList<>();
        /*Realiza consulta y devuelve Object[]*/
        List<Object[]> list=query.list();
        /*Itera en cada fila*/
        list.stream().forEach((investigador)->{
            Investigador inv=new Investigador();
            inv.setIdInvestigador((String)investigador[0]);
            inv.setApePaterno((String)investigador[1]);
            inv.setApeMaterno((String)investigador[2]);
            inv.setNombres((String)investigador[3]);
            inv.setCorreo((String)investigador[4]);
            inv.setParamEspecialidadInvestigador((String)investigador[5]);
            investigadors.add(inv);
        });        
        return investigadors;
    }
    
    @Override
    public List<Investigador> getInvestigadorSinIdInvestigacion(String idInvestigacion) {
        List<Investigador> investigadors=new ArrayList<>();

        /*Fabrica Query*/
        Query query=sessionFactory.getCurrentSession()
                                .createQuery("select    i.idInvestigador, "
                                            + "         i.apePaterno," 
                                            + "         i.apeMaterno," 
                                            + "         i.nombres " 
                                            + "from     Investigador i " 
                                            + "where    i.idInvestigador not in (select distinct ii.id.idInvestigador "
                                                                             + "from InvestigacionInvestigador ii "
                                                                             + "where ii.id.idInvestigacion='"+idInvestigacion+"')");
        
        List<Object[]> list=query.list();
        /*Itera en cada fila*/
        list.stream().forEach((investigador)->{
            Investigador inv=new Investigador();
            inv.setIdInvestigador(investigador[0].toString());
            inv.setApePaterno(investigador[1].toString());
            inv.setApeMaterno(investigador[2].toString());
            inv.setNombres(investigador[3].toString());
            investigadors.add(inv);
        });

        return investigadors;
    }
    
    
}
