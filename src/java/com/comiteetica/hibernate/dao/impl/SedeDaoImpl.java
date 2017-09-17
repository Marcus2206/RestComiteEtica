/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comiteetica.hibernate.dao.impl;

import com.comiteetica.hibernate.dao.SedeDao;
import com.comiteetica.hibernate.model.Sede;
import java.util.ArrayList;
import java.util.HashMap;
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
public class SedeDaoImpl implements SedeDao{

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
    public void create(Sede sede) {
        sessionFactory.getCurrentSession().save(sede);
    }

    @Override
    public Sede read(String idSede) {
        Sede sede=(Sede)sessionFactory.getCurrentSession().get(Sede.class,idSede);
//        Hibernate.initialize(sede.getDepartamento());
//        Hibernate.initialize(sede.getProvincia());
//        Hibernate.initialize(sede.getDistrito());
        return sede;
    }

    @Override
    public void update(Sede sede) {
        sessionFactory.getCurrentSession().update(sede);
    }

    @Override
    public void delete(Sede sede) {
        sessionFactory.getCurrentSession().delete(sede);
    }

    @Override
    public List<Sede> getAllSede() {
        /*Fabrica Query*/
        Query query=sessionFactory.getCurrentSession()
                                .createQuery("select s from Sede s" );

        List<Sede> sedes=query.list();
        sedes.stream().forEach((sede)->{
        });
        
        return sedes;
    }
    
    @Override
    public List<Object[]> getAllSedeList() {
        /*Fabrica Query*/
        Query query=sessionFactory.getCurrentSession()
                                .createSQLQuery("select	IdSede,\n" +
"		Nombre,\n" +
"		Direccion,\n" +
"		(select Descripcion from Departamento d where d.IdDepartamento=s.IdDepartamento)IdDepartamento,\n" +
"		(select Descripcion from Provincia p where p.IdDepartamento=s.IdDepartamento and p.IdProvincia=s.IdProvincia)IdProvincia,\n" +
"		(select Descripcion from Distrito d where d.IdDepartamento=s.IdDepartamento and d.IdProvincia=s.IdProvincia and d.IdDistrito=s.IdDistrito)IdDistrito		 \n" +
"from	Sede s" );
        //query.setFirstResult(ini);
        //query.setMaxResults(fin);
        /*Crea Objeto contenedor*/
        ArrayList sedes=new ArrayList();
        /*Realiza consulta y devuelve Object[]*/
        List<Object[]> list=query.list();
        /*Itera en cada fila*/
        list.stream().forEach((sede)->{
            Sede sed=new Sede();
            sed.setIdSede(sede[0].toString());
            sed.setNombre(sede[1].toString());
            sed.setDireccion((String)sede[2]);
            sed.setIdDepartamento((String)sede[3]);
            sed.setIdProvincia((String)sede[4]);
            sed.setIdDistrito((String)sede[5]);
            
            sedes.add(sed);
//            HashMap mMap = new HashMap(); // create a new one!
//            mMap.put("idSede",sede[0].toString());
//            mMap.put("nombre",sede[1].toString());
//            mMap.put("direccion",sede[2].toString());
//            mMap.put("idDepartamento",sede[3].toString());
//            mMap.put("idProvincia",sede[4].toString());
//            mMap.put("idDistrito",sede[5].toString());
//            ss.add(mMap);
        });
        return sedes;
    }
    
    
    @Override
    public List<Sede> getSedeSinIdInvestigacion(String idInvestigacion) {
        List<Sede> sedes=new ArrayList<>();

        /*Fabrica Query*/
        Query query=sessionFactory.getCurrentSession()
                                .createSQLQuery("select	s.IdSede,\n" +
                                        "		s.Nombre,\n" +
                                        "		(select Descripcion from Departamento where IdDepartamento=s.IdDepartamento)IdDepartamento,\n" +
                                        "		(select Descripcion from Provincia where IdDepartamento=s.IdDepartamento and IdProvincia=s.IdProvincia)IdProvincia,\n" +
                                        "		(select Descripcion from Distrito where IdDepartamento=s.IdDepartamento and IdProvincia=s.IdProvincia and IdDistrito=s.IdDistrito)IdDistrito\n" +
                                        "from	Sede s\n" +
                                        "where	s.IdSede not in(select distinct invs.IdSede from InvestigacionSede invs where invs.IdInvestigacion=:IdInvestigacion)")
                                                        .setString("IdInvestigacion", idInvestigacion);
        
        List<Object[]> list=query.list();
        /*Itera en cada fila*/
        list.stream().forEach((sede)->{
            Sede sed=new Sede();
            sed.setIdSede(sede[0].toString());
            sed.setNombre(sede[1].toString());
            sed.setIdDepartamento((String)sede[2]);
            sed.setIdProvincia((String)sede[3]);
            sed.setIdDistrito((String)sede[4]);
            sedes.add(sed);
        });

        return sedes;
    }
}
