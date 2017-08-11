/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comiteetica.hibernate.dao.impl;

import com.comiteetica.hibernate.dao.UsuarioDao;
import com.comiteetica.hibernate.model.Usuario;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
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
public class UsuarioDaoImpl implements UsuarioDao {

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
    public void create(Usuario usuario) {
        sessionFactory.getCurrentSession().save(usuario);
    }

    @Override
    public Usuario read(int idUsuario) {
        Usuario correspondencia = (Usuario) sessionFactory.getCurrentSession().get(Usuario.class, idUsuario);
        return correspondencia;
    }

    @Override
    public void update(Usuario usuario) {
        sessionFactory.getCurrentSession().update(usuario);
    }

    @Override
    public void delete(Usuario usuario) {
        sessionFactory.getCurrentSession().delete(usuario);
    }

    @Override
    public List<Usuario> getAllUsuario() {
        /*Fabrica Query*/
        Query query = sessionFactory.getCurrentSession()
                .createSQLQuery("");

        List<Usuario> usuarios = new ArrayList<>();
        List<Object[]> list = query.list();
        return usuarios;
    }

    @Override
    public List<Object> getAllUsuarioList() {

        String sqlQuery = "select	idUsuario,\n"
                + "		usuario,\n"
                + "		(select Descripcion from ParametroDetalle where IdParametro='P010' and IdParametroDetalle=Perfil) perfil\n"
                + "from	usuario\n"
                + "order by usuario asc";

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
    public void createSql(String usuario, String password, String perfil, String usuarioIngresa, Date fechaIngreso) {
        String sqlQuery = "insert into Usuario(Usuario,[Password], perfil, usuarioIngresa,fechaIngreso) "
                + "values(:usuario, PWDENCRYPT(:password), :perfil, :usuarioIngresa, :fechaIngreso)";
        sessionFactory.getCurrentSession()
                .createSQLQuery(sqlQuery)
                .setString("usuario", usuario)
                .setString("password", password)
                .setString("perfil", perfil)
                .setString("usuarioIngresa", usuarioIngresa)
                .setDate("fechaIngreso", fechaIngreso)
                .executeUpdate();
    }

    @Override
    public void updateSql(String usuario, String password, String usuarioModifica, Date fechaModificacion) {
        String sqlQuery = "update Usuario set [Password]=PWDENCRYPT(:password), usuarioModifica=:usuarioModifica, "
                + "fechaModificacion=:fechaModificacion   where usuario=:usuario";
        sessionFactory.getCurrentSession()
                .createSQLQuery(sqlQuery)
                .setString("usuario", usuario)
                .setString("password", password)
                .setString("usuarioModifica", usuarioModifica)
                .setDate("fechaModificacion", fechaModificacion)
                .executeUpdate();
    }

    @Override
    public List<Object> readSql(String usuario, String password) {

        String sqlQuery = "select	idUsuario,\n"
                + "		usuario,\n"
                + "		(select Descripcion from ParametroDetalle where IdParametro='P010' and IdParametroDetalle=Perfil) nperfil,\n"
                + "                       perfil\n"
                + "from	usuario\n"
                + "where Usuario ='" + usuario + "' and PWDCOMPARE('" + password + "', [Password])= 1";

        List<Object> list = sessionFactory.openSession()
                .doReturningWork(new ReturningWork<List<Object>>() {
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
