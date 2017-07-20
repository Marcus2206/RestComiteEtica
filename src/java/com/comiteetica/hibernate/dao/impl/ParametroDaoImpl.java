/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comiteetica.hibernate.dao.impl;

import com.comiteetica.hibernate.dao.ParametroDao;
import com.comiteetica.hibernate.model.Parametro;
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
public class ParametroDaoImpl implements ParametroDao {

    SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    @Override
    public void beginTransaction() {
        sessionFactory.getCurrentSession().beginTransaction();
    }

    @Override
    public void commit() {
        sessionFactory.getCurrentSession().getTransaction().commit();
    }

    @Override
    public void close() {
        sessionFactory.getCurrentSession().close();
    }

    @Override
    public void rollback() {
        sessionFactory.getCurrentSession().getTransaction().rollback();
    }

    @Override
    public void create(Parametro parametro) {
        sessionFactory.getCurrentSession().save(parametro);
    }

    @Override
    public Parametro read(String idParametro) {
        Parametro parametro = (Parametro) sessionFactory.getCurrentSession().get(Parametro.class, idParametro);
        Hibernate.initialize(parametro.getParametroDetalles());
        return parametro;
    }

    @Override
    public void update(Parametro parametro) {
        sessionFactory.getCurrentSession().update(parametro);
    }

    @Override
    public void delete(Parametro parametro) {
        sessionFactory.getCurrentSession().delete(parametro);
    }

    @Override
    public List<Parametro> getAllParametro() {
        /*Fabrica Query*/
        Query query = sessionFactory.getCurrentSession()
                .createQuery("select p "
                        + "from    Parametro p ");

        /*Crea Objeto contenedor*/
        List<Parametro> parametros = new ArrayList<>();
        /*Realiza consulta y devuelve Object[]*/
//        List<Object[]> list=query.list();
        parametros = query.list();
        /*Itera en cada fila*/
        parametros.stream().forEach((parametro) -> {
            Hibernate.initialize(parametro.getParametroDetalles());
        });

        return parametros;
    }

}
