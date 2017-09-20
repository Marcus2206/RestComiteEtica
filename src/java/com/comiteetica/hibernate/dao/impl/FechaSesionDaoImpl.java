/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comiteetica.hibernate.dao.impl;

import com.comiteetica.hibernate.dao.FechaSesionDao;
import com.comiteetica.hibernate.model.FechaSesion;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.jdbc.ReturningWork;
import org.springframework.stereotype.Repository;

/**
 *
 * @author rasec
 */
@Repository
public class FechaSesionDaoImpl implements FechaSesionDao {

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
    public void create(FechaSesion fechaSesion) {
        sessionFactory.getCurrentSession().save(fechaSesion);
    }

    @Override
    public FechaSesion read(int idFechaSesion) {
        FechaSesion fechaSesion = (FechaSesion) sessionFactory.getCurrentSession().get(FechaSesion.class, idFechaSesion);
        return fechaSesion;
    }

    @Override
    public void update(FechaSesion fechaSesion) {
        sessionFactory.getCurrentSession().update(fechaSesion);
    }

    @Override
    public void delete(FechaSesion fechaSesion) {
        sessionFactory.getCurrentSession().delete(fechaSesion);
    }

    @Override
    public List<FechaSesion> getAllFechaSesion() {
        /*Fabrica Query*/
        Query query = sessionFactory.getCurrentSession()
                .createQuery("select fs from FechaSesion fs order by fs.fechaSesion asc");

        List<FechaSesion> fechaSesions = query.list();
        return fechaSesions;
    }

    @Override
    public List<Object> getAllFechaSesionList() {

        String sqlQuery = "select	\n"
                + "		idFechaSesion,\n"
                + "		convert(varchar(10),fechaSesion,103)fechaSesion,\n"
                + "		observacion\n"
                + "from	FechaSesion\n"
                + "order by fechaSesion desc";

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

    @Override
    public List<Object> getAllFechaSesionProx() {
        String sqlQuery = "select	top 6\n"
                + "		idFechaSesion,\n"
                + "		fechaSesion\n"
                + "from	FechaSesion\n"
                + "where FechaSesion>=(\n"
                + "select cast(cast(max(FechaSesion) as date) as datetime) from FechaSesion \n"
                + "where MONTH(getdate())=month(FechaSesion) and YEAR(getdate())=year(FechaSesion))\n"
                + "order by fechaSesion asc";

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
