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
    public FechaSesion read(String idFechaSesion) {
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
                .createSQLQuery("select	c.idCorrespondencia,\n"
                        + "		c.fechaCorrespondencia,\n"
                        + "		c.fechaCarta,\n"
                        + "		c.idRegistro,\n"
                        + "		(select Descripcion from ParametroDetalle where IdParametro='P001' and IdParametroDetalle=c.ParamTipoServicio)ParamTipoServicio,\n"
                        + "		c.otro,\n"
                        + "		(select Descripcion from ParametroDetalle where IdParametro='P002' and IdParametroDetalle=c.paramDistribucion)paramDistribucion,\n"
                        + "		c.fechaSesion,\n"
                        + "		c.enviarCorreo,\n"
                        + "		c.enviado\n"
                        + "from	Correspondencia c\n"
                        + "order by idCorrespondencia");

        List<FechaSesion> fechaSesions = new ArrayList<>();
        List<Object[]> list = query.list();
        return fechaSesions;
    }

    @Override
    public List<Object> getAllFechaSesionList() {

        String sqlQuery = "select	c.idCorrespondencia,\n"
                + "		c.fechaCorrespondencia,\n"
                + "		c.fechaCarta,\n"
                + "		c.idRegistro idRegistro,\n"
                + "		(select Descripcion from ParametroDetalle where IdParametro='P001' and IdParametroDetalle=c.ParamTipoServicio)paramTipoServicio,\n"
                + "		c.otro,\n"
                + "		(select Descripcion from ParametroDetalle where IdParametro='P002' and IdParametroDetalle=c.paramDistribucion)paramDistribucion,\n"
                + "		c.fechaSesion,\n"
                + "		cast(c.enviarCorreo as int)enviarCorreo,\n"
                + "		cast(c.enviado as int )enviado\n"
                + "from	Correspondencia c\n"
                + "order by idCorrespondencia";

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
                + "select cast(cast(FechaSesion as date) as datetime) from FechaSesion \n"
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
