/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comiteetica.hibernate.dao.impl;

import com.comiteetica.hibernate.dao.FormatoDao;
import com.comiteetica.hibernate.model.Formato;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.jdbc.ReturningWork;

/**
 *
 * @author rasec
 */
public class FormatoDaoImpl implements FormatoDao{
    
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
    public void create(Formato formato) {
        sessionFactory.getCurrentSession().save(formato);
    }

    @Override
    public Formato read(int idFormato) {
        Formato formato = (Formato) sessionFactory.getCurrentSession().get(Formato.class, idFormato);
        Hibernate.initialize(formato.getFormatoLineas());
        return formato;
    }

    @Override
    public void update(Formato formato) {
        sessionFactory.getCurrentSession().update(formato);
    }

    @Override
    public void delete(Formato formato) {
        sessionFactory.getCurrentSession().delete(formato);
    }

    @Override
    public List<Formato> getAllFormato() {
        /*Fabrica Query*/
        Query query = sessionFactory.getCurrentSession()
                .createSQLQuery("");

        List<Formato> formatos = new ArrayList<>();
        List<Object[]> list = query.list();
        return formatos;
    }

    @Override
    public List<Object> getAllFormatoList() {

        String sqlQuery = "select	idCorreo,\n"
                + "		apePaterno,\n"
                + "		apeMaterno,\n"
                + "		nombres,\n"
                + "		(select Descripcion from ParametroDetalle where IdParametro='P011' and IdParametroDetalle=paramAreaTrabajo) paramAreaTrabajo,\n"
                + "		correo,\n"
                + "		coalesce(estado,0) estado\n"
                + "from	correo\n";
//                + "order by apePaterno asc";

        List<Object> list = sessionFactory.openSession().doReturningWork(new ReturningWork<List<Object>>() {
            @Override
            public List<Object> execute(Connection connection) throws SQLException {
                CallableStatement statement = null;
                List<Object> obj = new ArrayList<Object>();
                String sqlString = "{call uspGetJsonFromQuery(?)}";
                statement = connection.prepareCall(sqlString);
                statement.setString(1, sqlQuery);
                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    obj.add(resultSet.getString(1));
                }
                return obj;
            }
        });

        if (list != null) {
            if (list.size() > 0) {
                return list;
            } else {
                return null;
            }
        } else {
            return null;
        }
    }
}
