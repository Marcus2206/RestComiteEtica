/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comiteetica.hibernate.dao.impl;

import com.comiteetica.hibernate.dao.NotificacionDao;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.hibernate.SessionFactory;
import org.hibernate.jdbc.ReturningWork;
import org.springframework.stereotype.Repository;

/**
 *
 * @author rasec
 */
@Repository
public class NotificacionDaoImpl implements NotificacionDao {

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
    public List<Object> getAllNotificacionList(String usuario) {

        String sqlQuery = "select	idNotificacion,\n"
                + "		idDocumento,\n"
                + "		tablaProcedencia,\n"
                + "		(datosRelevante +'...')datosRelevante,\n"
                + "		estadoNotificacion \n"
                + "from	Notificacion\n"
                + "where	Usuario='" + usuario + "'\n"
                + "order by FechaIngreso desc";

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
    public void updateSetLeido(int idNotificacion, String usuario) {
        String sqlQuery = "update Notificacion \n"
                + "set EstadoNotificacion=(case when EstadoNotificacion=0 then 1 else 0 end),\n"
                + "	UsuarioModifica=:usuario,\n"
                + "	FechaModificacion=getdate()\n"
                + "where IdNotificacion=:idNotificacion";
        sessionFactory.getCurrentSession()
                .createSQLQuery(sqlQuery)
                .setString("usuario", usuario)
                .setInteger("idNotificacion", idNotificacion)
                .executeUpdate();
    }

    @Override
    public void updateSetTodoLeido(String usuario) {
        String sqlQuery = "update Notificacion \n"
                + "set EstadoNotificacion=1,\n"
                + "	UsuarioModifica=:usuario,\n"
                + "	FechaModificacion=getdate()\n"
                 + "where usuario=:usuario";
        sessionFactory.getCurrentSession()
                .createSQLQuery(sqlQuery)
                .setString("usuario", usuario)
                .executeUpdate();
    }
}
