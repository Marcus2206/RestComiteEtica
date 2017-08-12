/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comiteetica.hibernate.dao.impl;

import com.comiteetica.hibernate.dao.CorreoDao;
import com.comiteetica.hibernate.model.Correo;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.jdbc.ReturningWork;

/**
 *
 * @author rasec
 */
public class CorreoDaoImpl implements CorreoDao {

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
    public void create(Correo correo) {
        sessionFactory.getCurrentSession().save(correo);
    }

    @Override
    public Correo read(int idCorreo) {
        Correo correo = (Correo) sessionFactory.getCurrentSession().get(Correo.class, idCorreo);
        return correo;
    }

    @Override
    public void update(Correo correo) {
        sessionFactory.getCurrentSession().update(correo);
    }

    @Override
    public void delete(Correo correo) {
        sessionFactory.getCurrentSession().delete(correo);
    }

    @Override
    public List<Correo> getAllCorreo() {
        /*Fabrica Query*/
        Query query = sessionFactory.getCurrentSession()
                .createSQLQuery("");

        List<Correo> correos = new ArrayList<>();
        List<Object[]> list = query.list();
        return correos;
    }

    @Override
    public List<Object> getAllCorreoList() {

        String sqlQuery = "select	idCorreo,\n"
                + "		apePaterno,\n"
                + "		apeMaterno,\n"
                + "		nombres,\n"
                + "		paramAreaTrabajo,\n"
                + "		correo,\n"
                + "		estado\n"
                + "from	correo\n"
                + "order by apePaterno asc";

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
