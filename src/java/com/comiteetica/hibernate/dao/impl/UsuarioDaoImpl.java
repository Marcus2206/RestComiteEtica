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
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
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
                + "		(select Descripcion from ParametroDetalle(nolock) where IdParametro='P010' and IdParametroDetalle=Perfil) perfil,\n"
                + "		(case when estado=0 then 'Inactivo' when estado=1 then 'Activo' end)estado\n"
                + "from	usuario(nolock)\n"
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
    public int createSql(String usuario, String password, String perfil, String usuarioIngresa, Date fechaIngreso, Boolean estado) {

        if (usuarioRepetido(usuario)) {
            return 0;
        }
//        String sqlQuery = "insert into Usuario(Usuario,[Password], perfil, usuarioIngresa,fechaIngreso, estado) "
//                + "values(:usuario, PWDENCRYPT(:password), :perfil, :usuarioIngresa, :fechaIngreso, :estado)";
//        sessionFactory.getCurrentSession()
//                .createSQLQuery(sqlQuery)
//                .setString("usuario", usuario)
//                .setString("password", password)
//                .setString("perfil", perfil)
//                .setString("usuarioIngresa", usuarioIngresa)
//                .setDate("fechaIngreso", fechaIngreso)
//                .setBoolean("estado", estado)
//                .executeUpdate();
//        return 1;
        List<Object> list = sessionFactory.openSession().doReturningWork(new ReturningWork<List<Object>>() {
            @Override
            public List<Object> execute(Connection connection) throws SQLException {

//                CallableStatement statement = null;
//                List<Object> obj = new ArrayList<Object>();
//                String sqlString = "{call uspInsertUsuario(?,?,?,?,?)}";
//                statement = connection.prepareCall(sqlString);
//                statement.setString(1, usuario);
//                statement.setString(2, password);
//                statement.setString(3, perfil);
//                statement.setBoolean(4, estado);
//                statement.setString(5, usuarioIngresa);
                PreparedStatement statement = null;
                List<Object> obj = new ArrayList<Object>();
                Properties connectionProps = ((SessionFactoryImpl) sessionFactory).getProperties();
                String url = (String) connectionProps.get("hibernate.connection.url");
                String username = (String) connectionProps.get("hibernate.connection.username");
                String pass = (String) connectionProps.get("hibernate.connection.password");
                Properties cProps = new Properties();
                cProps.put("user", username);
                cProps.put("password", pass);
                Connection conn = null;
                conn = DriverManager.getConnection(url, cProps);
                statement = conn.prepareStatement("exec uspInsertUsuario ?,?,?,?,?");
                statement.setEscapeProcessing(true);
                statement.setQueryTimeout(90);
                statement.setString(1, usuario);
                statement.setString(2, password);
                statement.setString(3, perfil);
                statement.setBoolean(4, estado);
                statement.setString(5, usuarioIngresa);
                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    obj.add(resultSet.getString(1));
                }
                return obj;
            }
        });

        if (list != null) {
            if (list.size() > 0) {
                if (list.get(0) != null) {
                    return 1;
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

    public boolean usuarioRepetido(String usuario) {
        String sqlQuery = "select	idUsuario\n"
                + "from	usuario(nolock)\n"
                + "where usuario='" + usuario + "'";

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
                if (list.get(0) != null) {
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    @Override
    public void updateSql(int idUsuario, String password, String usuarioModifica, Date fechaModificacion) {
        List<Object> list = sessionFactory.openSession().doReturningWork(new ReturningWork<List<Object>>() {
            @Override
            public List<Object> execute(Connection connection) throws SQLException {
                PreparedStatement statement = null;
                List<Object> obj = new ArrayList<Object>();
                Properties connectionProps = ((SessionFactoryImpl) sessionFactory).getProperties();
                String url = (String) connectionProps.get("hibernate.connection.url");
                String username = (String) connectionProps.get("hibernate.connection.username");
                String pass = (String) connectionProps.get("hibernate.connection.password");
                Properties cProps = new Properties();
                cProps.put("user", username);
                cProps.put("password", pass);
                Connection conn = null;
                conn = DriverManager.getConnection(url, cProps);
                statement = conn.prepareStatement("exec uspUpdatePassUsuario ?,?,?");
                statement.setEscapeProcessing(true);
                statement.setQueryTimeout(90);
                statement.setInt(1, idUsuario);
                statement.setString(2, password);
                statement.setString(3, usuarioModifica);
                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    obj.add(resultSet.getInt(1));
                }
                return obj;
            }
        });
    }

    @Override
    public List<Object> readSql(String usuario, String password) {
        List<Object> list = sessionFactory.openSession().doReturningWork(new ReturningWork<List<Object>>() {
            @Override
            public List<Object> execute(Connection connection) throws SQLException {                
                PreparedStatement statement = null;
                List<Object> obj = new ArrayList<Object>();
                Properties connectionProps = ((SessionFactoryImpl) sessionFactory).getProperties();
                String url = (String) connectionProps.get("hibernate.connection.url");
                String username = (String) connectionProps.get("hibernate.connection.username");
                String pass = (String) connectionProps.get("hibernate.connection.password");
                Properties cProps = new Properties();
                cProps.put("user", username);
                cProps.put("password", pass);
                Connection conn = null;
                conn = DriverManager.getConnection(url, cProps);
                statement = conn.prepareStatement("exec uspSelectUsuario ?,?");
                statement.setEscapeProcessing(true);
                statement.setQueryTimeout(90);
                statement.setString(1, usuario);
                statement.setString(2, password);

                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    obj.add(resultSet.getString(1));
                }
                return obj;
            }
        });

        return list;
    }
}
