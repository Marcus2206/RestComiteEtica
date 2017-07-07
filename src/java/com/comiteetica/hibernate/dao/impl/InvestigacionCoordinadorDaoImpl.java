/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comiteetica.hibernate.dao.impl;

import com.comiteetica.hibernate.model.InvestigacionCoordinador;
import com.comiteetica.hibernate.model.InvestigacionCoordinadorId;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import com.comiteetica.hibernate.dao.InvestigacionCoordinadorDao;
import com.comiteetica.hibernate.model.Coordinador;

/**
 *
 * @author rasec
 */
@Repository
public class InvestigacionCoordinadorDaoImpl implements InvestigacionCoordinadorDao{

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
    public void create(InvestigacionCoordinador investigacionCoordinador) {
        sessionFactory.getCurrentSession().save(investigacionCoordinador);
    }

    @Override
    public InvestigacionCoordinador read(InvestigacionCoordinadorId investigacionCoordinadorId) {
        InvestigacionCoordinador investigacionCoordinador=(InvestigacionCoordinador)sessionFactory.getCurrentSession().get(InvestigacionCoordinador.class,investigacionCoordinadorId);
        return investigacionCoordinador;
    }

    @Override
    public void update(InvestigacionCoordinador investigacionCoordinador) {
        sessionFactory.getCurrentSession().update(investigacionCoordinador);
    }

    @Override
    public void delete(InvestigacionCoordinador investigacionCoordinador) {
        sessionFactory.getCurrentSession().delete(investigacionCoordinador);
    }

    @Override
    public List<InvestigacionCoordinador> getAllInvestigacionCoordinador() {
        /*Fabrica Query*/
        Query query=sessionFactory.getCurrentSession()
                                .createQuery("select "
                                                    +"id, " 
                                                    +"observacion "  
                                            +"from    InvestigacionCoordinador " );
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
        
        return investigacionCoordinadors;
    }

    @Override
    public List<Object> getInvestigacionCoordinadorByIdInvestigacion(String idInvestigacion) {
        /*Fabrica Query*/
        Query query=sessionFactory.getCurrentSession()
                                .createQuery("select ic,c " +
                                             "	from InvestigacionCoordinador ic " +
                                             "inner join ic.coordinador c " +
                                             "where  ic.id.idInvestigacion='"+idInvestigacion+"'" );

        /*Crea Objeto contenedor*/
        List<Object> objetos=new ArrayList<>();
        /*Realiza consulta y devuelve Object[]*/
        List<Object[]> list=query.list();
        /*Itera en cada fila*/
        list.stream().forEach((objeto)->{
            Object[] o=new Object[2];
            o[0]=(InvestigacionCoordinador)objeto[0];
            o[1]=(Coordinador)objeto[1];
            objetos.add(o);
        });
   
        return objetos;
    }
    
}
