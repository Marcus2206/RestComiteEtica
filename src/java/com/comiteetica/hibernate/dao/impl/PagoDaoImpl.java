/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comiteetica.hibernate.dao.impl;

import com.comiteetica.hibernate.dao.PagoDao;
import com.comiteetica.hibernate.model.Pago;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.internal.SessionFactoryImpl;
import org.hibernate.jdbc.ReturningWork;
import org.springframework.stereotype.Repository;

/**
 *
 * @author rasec
 */
@Repository
public class PagoDaoImpl implements PagoDao {

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
    public void create(Pago pago) {
        sessionFactory.getCurrentSession().save(pago);
    }

    @Override
    public Pago read(String idPago) {
        Pago pago = (Pago) sessionFactory.getCurrentSession().get(Pago.class, idPago);
        return pago;
    }

    @Override
    public void update(Pago pago) {
        sessionFactory.getCurrentSession().update(pago);
    }

    @Override
    public void delete(Pago pago) {
        sessionFactory.getCurrentSession().delete(pago);
    }

    @Override
    public List<Pago> getAllPago() {
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

        List<Pago> pagos = new ArrayList<>();
        List<Object[]> list = query.list();
        return pagos;
    }

    @Override
    public List<Object> getAllPagoList() {

        String sqlQuery = "select	idPago,\n"
                + "		costo,\n"
                + "		nroFactura,\n"
                + "		observacion,\n"
                + "		(select Descripcion from ParametroDetalle where IdParametro='P009' and IdParametroDetalle=paramEstadoPago)paramEstadoPago\n"
                + "from	pago \n"
                + "order by IdPago";

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
    public int sendMail(String idPago) {
//        
//        List<Object> list = sessionFactory.openSession().doReturningWork(new ReturningWork<List<Object>>() {
//            @Override
//            public List<Object> execute(Connection connection) throws SQLException {
//                CallableStatement statement = null;
//                List<Object> obj = new ArrayList<Object>();
//                String sqlString = "{call uspCorreoPago(?)}";
//                statement = connection.prepareCall(sqlString);
//                statement.setString(1, idPago);
//                ResultSet resultSet = statement.executeQuery();
//                while (resultSet.next()) {
//                    System.out.println("resultSet");
//                    obj.add(resultSet.getInt(1));
//
//                }
//                return obj;
//            }
//        });

        List<Object> list = sessionFactory.openSession().doReturningWork(new ReturningWork<List<Object>>() {
            @Override
            public List<Object> execute(Connection connection) throws SQLException {
                PreparedStatement statement = null;
                List<Object> obj = new ArrayList<Object>();
                Properties connectionProps = ((SessionFactoryImpl) sessionFactory).getProperties();
                String url = (String) connectionProps.get("hibernate.connection.url");
                String username = (String) connectionProps.get("hibernate.connection.username");
                String password = (String) connectionProps.get("hibernate.connection.password");
                 Properties cProps = new Properties();
                cProps.put("user", username);
                cProps.put("password", password);
                Connection conn = null;
                conn = DriverManager.getConnection(url, cProps);
                statement = conn.prepareStatement("exec uspCorreoPago ?");
                statement.setEscapeProcessing(true);
                statement.setQueryTimeout(90);
                statement.setString(1, idPago);
                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    obj.add(resultSet.getInt(1));
                }
                return obj;
            }
        });

        if (list != null) {
            if (list.size() > 0) {
                if (list.get(0) != null) {
                    return (int) list.get(0);
                } else {
                    return 0;
                }
            } else {
                return 0;
            }
        } else {
            return 0;
        }

    }
}
