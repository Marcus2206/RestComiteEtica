/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comiteetica.hibernate.dao.impl;

import com.comiteetica.hibernate.dao.ParametroDao;
import com.comiteetica.hibernate.model.Parametro;
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

    @Override
    public List<Object> getAllParametroListSql() {

        String sqlQuery = "select	idParametro,\n"
                + "		descripcion \n"
                + "from	parametro\n"
                + "order by idParametro";
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
    
    @Override
    public String getNextIdParametro() {
        /*Fabrica Query*/
        Query query = sessionFactory.getCurrentSession()
                .createQuery("select max(p.idParametro) \n"
                        + "from Parametro p\n");
        List<Object> nextParametroQuery = query.list();
        String param="P001";

        if (nextParametroQuery.size() > 0) {
//            System.out.println(nextFileDetalleQuery.get(0));
            if (nextParametroQuery.get(0) != null) {
                param=(String)nextParametroQuery.get(0);//P001
                param=param.substring(1, 4);//001
                int nextParametro = 0;
                nextParametro = Integer.parseInt(param) + 1;//2
                //P 0001
                //P 00012
                param=("000"+nextParametro);//00010
                param="P"+param.substring(param.length()-3, param.length());
            } else {
                param = "P001";
            }
        }

        return param;
    }
}
