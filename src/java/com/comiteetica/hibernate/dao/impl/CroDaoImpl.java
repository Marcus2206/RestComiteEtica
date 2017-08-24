/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comiteetica.hibernate.dao.impl;

import com.comiteetica.hibernate.dao.CroDao;
import com.comiteetica.hibernate.model.Cro;
import com.comiteetica.hibernate.model.Patrocinador;
import com.comiteetica.hibernate.model.PatrocinadorCro;
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
public class CroDaoImpl implements CroDao {

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
    public void create(Cro cro) {
        sessionFactory.getCurrentSession().save(cro);
    }

    @Override
    public Cro read(String idCro) {
        Cro cro = (Cro) sessionFactory.getCurrentSession().get(Cro.class, idCro);
        return cro;
    }

    @Override
    public void update(Cro cro) {
        sessionFactory.getCurrentSession().update(cro);
    }

    @Override
    public void delete(Cro cro) {
        sessionFactory.getCurrentSession().delete(cro);
    }

    @Override
    public List<Cro> getAllCro() {
        /*Fabrica Query*/
        Query query = sessionFactory.getCurrentSession()
                .createQuery("select c from Cro c order by c.nombre asc");
        List<Cro> cros = query.list();
        return cros;
    }

    @Override
    public List<Cro> getCroByPatrocinador(String idPatrocinador) {
        /*Fabrica Query*/
        Query query = sessionFactory.getCurrentSession()
                .createQuery("select c \n"
                        + "from Cro c \n"
                        + "where c.idCro in (select p.id.idCro from PatrocinadorCro p where p.id.idPatrocinador='" + idPatrocinador + "')\n"
                        + "order by c.nombre asc");
        List<Cro> cros = query.list();
        return cros;
    }

    @Override
    public List<Object> getAllCroList() {

        String sqlQuery = "select	idCro,\n"
                + "		nombre \n"
                + "from	cro\n"
                + "order by Nombre";

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
    public List<Cro> getCroSinIdPatrocinador(String idPatrocinador) {
        List<Cro> cros = new ArrayList<>();

        /*Fabrica Query*/
        Query query = sessionFactory.getCurrentSession()
                .createQuery("select p.idCro, p.nombre \n"
                        + "from Cro p\n"
                        + "where p.idCro not in(select distinct pc.id.idCro \n"
                        + "from PatrocinadorCro pc\n"
                        + "where pc.id.idPatrocinador='"+idPatrocinador+"')\n"
                        + "");

        List<Object[]> list = query.list();
        /*Itera en cada fila*/
        list.stream().forEach((c) -> {
            Cro cro = new Cro();
            cro.setIdCro(c[0].toString());
            cro.setNombre(c[1].toString());
            cros.add(cro);
        });

        return cros;
    }

}
