/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comiteetica.hibernate.dao.impl;

import com.comiteetica.hibernate.dao.CoordinadorDao;
import com.comiteetica.hibernate.model.Coordinador;
//import com.comiteetica.hibernate.dao.impl.HibernateUtil;
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
    public void create(Coordinador coordinador) {
        sessionFactory.getCurrentSession().save(coordinador);
    }

    @Override
    public Coordinador read(String idCoordinador) {
        Coordinador investigador;
        investigador=(Coordinador)sessionFactory.getCurrentSession().get(Coordinador.class,idCoordinador);
        Hibernate.initialize(investigador.getInvestigacionCoordinadors());
        return investigador;
    }

    @Override
    public void update(Coordinador coordinador) {
        sessionFactory.getCurrentSession().update(coordinador);        
    }

    @Override
    public void delete(Coordinador coordinador) {
        sessionFactory.getCurrentSession().delete(coordinador);
    }

    @Override
    public List<Coordinador> getAllCoordinador() {
        List<Coordinador> coordinadors=new ArrayList<>();
        /*Fabrica Query*/
        Query query=sessionFactory.getCurrentSession()
                                .createQuery("select "
                                                    +"idCoordinador, " 
                                                    +"apePaterno, " 
                                                    +"apeMaterno,"
                                                    +"nombres, "  
                                                    +"correo "
                                            +"from    Coordinador" );
        
        /*Realiza consulta y devuelve Object[]*/
        List<Object[]> list=query.list();
        /*Itera en cada fila*/
        list.stream().forEach((coordinador)->{
            Coordinador coor=new Coordinador();
            coor.setIdCoordinador((String)coordinador[0]);
            coor.setApePaterno((String)coordinador[1]);
            coor.setApeMaterno((String)coordinador[2]);
            coor.setNombres((String)coordinador[3]);
            coor.setCorreo((String)coordinador[4]);
            coordinadors.add(coor);
        });        
        return coordinadors;
    }
    
    @Override
    public List<Coordinador> getCoordinadorSinIdInvestigacion(String idInvestigacion) {
        List<Coordinador> coordinadors=new ArrayList<>();

        /*Fabrica Query*/
        Query query=sessionFactory.getCurrentSession()
                                .createQuery("select    c.idCoordinador, "
                                            + "         c.apePaterno," 
                                            + "         c.apeMaterno," 
                                            + "         c.nombres " 
                                            + "from     Coordinador c " 
                                            + "where    c.idCoordinador not in (select distinct ic.id.idCoordinador "
                                                                             + "from InvestigacionCoordinador ic "
                                                                             + "where ic.id.idInvestigacion='"+idInvestigacion+"')");
        
        List<Object[]> list=query.list();
        /*Itera en cada fila*/
        list.stream().forEach((coordinador)->{
            Coordinador coor=new Coordinador();
            coor.setIdCoordinador(coordinador[0].toString());
            coor.setApePaterno(coordinador[1].toString());
            coor.setApeMaterno(coordinador[2].toString());
            coor.setNombres(coordinador[3].toString());
            coordinadors.add(coor);
        });

        return coordinadors;
    }
}
