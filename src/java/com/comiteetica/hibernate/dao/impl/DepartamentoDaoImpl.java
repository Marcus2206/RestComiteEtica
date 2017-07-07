/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comiteetica.hibernate.dao.impl;

import com.comiteetica.hibernate.dao.DepartamentoDao;
import com.comiteetica.hibernate.model.Departamento;
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
public class DepartamentoDaoImpl implements DepartamentoDao{
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
    public void create(Departamento departamento) {
        sessionFactory.getCurrentSession().save(departamento);
    }

    @Override
    public Departamento read(String idDepartamento) {
        Departamento departamento=null;
        departamento=(Departamento)sessionFactory.getCurrentSession().get(Departamento.class,idDepartamento);
        return departamento;
    }

    @Override
    public void update(Departamento departamento) {
        sessionFactory.getCurrentSession().update(departamento);
    }

    @Override
    public void delete(Departamento departamento) {
        sessionFactory.getCurrentSession().delete(departamento);
    }

    @Override
    public List<Departamento> getAllDepartamento() {
        List<Departamento> departamentos=new ArrayList<>();
        //try{
//        sessionFactory.getCurrentSession().beginTransaction();

        /*Fabrica Query*/
        Query query=sessionFactory.getCurrentSession()
                                .createQuery("select d from Departamento d" );
        /*Realiza consulta y devuelve Object[]*/
        List<Departamento> list=query.list();
        /*Itera en cada fila*/
//        list.stream().forEach((coordinador)->{
//            Coordinador coor=new Coordinador();
//            coor.setIdCoordinador(coordinador[0].toString());
//            coor.setApePaterno(coordinador[1].toString());
//            coor.setApeMaterno(coordinador[2].toString());
//            coor.setNombres(coordinador[3].toString());
//            coordinadors.add(coor);
//        });        
        return list;
    }
    
}
