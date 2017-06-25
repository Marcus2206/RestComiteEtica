/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comiteetica.hibernate.dao.impl;

import com.comiteetica.hibernate.dao.InvestigacionDao;
import com.comiteetica.hibernate.model.Coordinador;
import com.comiteetica.hibernate.model.HibernateUtil;
import com.comiteetica.hibernate.model.Investigacion;
import com.comiteetica.hibernate.model.InvestigacionCoordinador;
import com.comiteetica.hibernate.model.InvestigacionMonitor;
import com.comiteetica.hibernate.model.Registro;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

/**
 *
 * @author rasec
 */
@Repository
public class InvestigacionDaoImpl implements InvestigacionDao{

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
    public void create(Investigacion investigacion) {
//        SessionFactory sessionFactory=HibernateUtil.getSessionFactory();
//        sessionFactory.getCurrentSession().beginTransaction();
        sessionFactory.getCurrentSession().save(investigacion);
//        sessionFactory.getCurrentSession().getTransaction().commit();
//        sessionFactory.getCurrentSession().close(); 
    }

    @Override
    public Investigacion read(String idInvestigacion) {
        Investigacion investigacion=(Investigacion)sessionFactory.getCurrentSession().get(Investigacion.class,idInvestigacion);
//        Hibernate.initialize(investigacion.getInvestigacionSedes());
//        Set<InvestigacionCoordinador> investigacionCoordinadors = new HashSet<InvestigacionCoordinador>(0);
//        investigacion.setInvestigacionCoordinadors(investigacionCoordinadors);
//        Hibernate.initialize(investigacion.getInvestigacionCoordinadors());
//        System.out.println("Hibernate.initialize(investigacion.getInvestigacionCoordinadors());");
//        investigacion.getInvestigacionCoordinadors().forEach((investigacionCoordinador)->{
//                System.out.println("Inicializado---investigacionCoordinador: "+investigacionCoordinador.getCoordinador().getIdCoordinador());
//                //Hibernate.initialize(investigacionCoordinador.getCoordinador());
//                System.out.println("investigacionCoordinador.getCoordinador().getApePaterno(): "+investigacionCoordinador.getCoordinador().getApePaterno());
//        });
//        Hibernate.initialize(investigacion.getInvestigacionInvestigadors());
//        Hibernate.initialize(investigacion.getInvestigacionMonitors());
//        Set<Registro> registros = new HashSet<Registro>(0);
//        investigacion.setRegistros(registros);
        return investigacion;
    }

    @Override
    public Investigacion readInvestigacion(String idInvestigacion) {
        /*Fabrica Query*/
        Query query=sessionFactory.getCurrentSession()
                                .createQuery("select "
                                                    +"idInvestigacion, " 
                                                    +"protocolo, " 
                                                    +"titulo, "  
                                                    +"paramEspecialidad, " 
                                                    +"paramFase, " 
                                                    +"paramTipoInvestigacion " 
                                            + "from    Investigacion "
                                            + " where idInvestigacion=:idInvestigacion" )
                                .setString("idInvestigacion", idInvestigacion);

        /*Crea Objeto contenedor*/
        List<Investigacion> investigacions=new ArrayList<>();
        /*Realiza consulta y devuelve Object[]*/
        List<Object[]> list=query.list();
        /*Itera en cada fila*/
        list.stream().forEach((investigacion)->{
            Investigacion inv=new Investigacion();
            inv.setIdInvestigacion(investigacion[0].toString());
            inv.setProtocolo(investigacion[1].toString());
            inv.setTitulo(investigacion[2].toString());
            inv.setParamEspecialidad((String)investigacion[3]);
            inv.setParamFase((String)investigacion[4]);
            inv.setParamTipoInvestigacion((String)investigacion[5]);
            investigacions.add(inv);
        });
   
        return investigacions.get(0);
    }
    
    @Override
    public void update(Investigacion investigacion) {
//        SessionFactory sessionFactory=HibernateUtil.getSessionFactory();
//        sessionFactory.getCurrentSession().beginTransaction();
        sessionFactory.getCurrentSession().update(investigacion);
//        sessionFactory.getCurrentSession().getTransaction().commit();
//        sessionFactory.getCurrentSession().close();
    }

    @Override
    public void delete(Investigacion investigacion) {
//        SessionFactory sessionFactory=HibernateUtil.getSessionFactory();
//        sessionFactory.getCurrentSession().beginTransaction();
        Investigacion managedInvestigacion = (Investigacion)sessionFactory.getCurrentSession().merge(investigacion);
        sessionFactory.getCurrentSession().delete(managedInvestigacion);
//        sessionFactory.getCurrentSession().getTransaction().commit();
//        sessionFactory.getCurrentSession().close();
    }

    @Override
    public List<Investigacion> getAllInvestigacion() {
        /*Fabrica Query*/
        Query query=sessionFactory.getCurrentSession()
                                .createQuery("select "
                                                    +"idInvestigacion, " 
                                                    +"protocolo, " 
                                                    +"titulo, "  
                                                    +"paramEspecialidad, " 
                                                    +"paramFase, " 
                                                    +"paramTipoInvestigacion " 
                                            +"from    Investigacion" );
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
            inv.setProtocolo(investigacion[1].toString());
            inv.setTitulo(investigacion[2].toString());
            inv.setParamEspecialidad((String)investigacion[3]);
            inv.setParamFase((String)investigacion[4]);
            inv.setParamTipoInvestigacion((String)investigacion[5]);
            
            investigacions.add(inv);
        });
   
        return investigacions;
    }
    
}
