/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comiteetica.hibernate.dao.impl;

import com.comiteetica.hibernate.dao.FormatoLineaDao;
import com.comiteetica.hibernate.model.Formato;
import com.comiteetica.hibernate.model.FormatoLinea;
import com.comiteetica.hibernate.model.FormatoLineaId;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
//import model.FormatoLineaId;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.jdbc.ReturningWork;

/**
 *
 * @author rasec
 */
public class FormatoLineaDaoImpl implements FormatoLineaDao {

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
    public void create(FormatoLinea formatoLinea) {
        sessionFactory.getCurrentSession().save(formatoLinea);
    }

    @Override
    public FormatoLinea read(FormatoLineaId id) {
        FormatoLinea formatoLinea = (FormatoLinea) sessionFactory.getCurrentSession().get(FormatoLinea.class, id);
        return formatoLinea;
    }

    @Override
    public void update(FormatoLinea formatoLinea) {
        sessionFactory.getCurrentSession().update(formatoLinea);
    }

    @Override
    public void delete(FormatoLinea formatoLinea) {
        sessionFactory.getCurrentSession().delete(formatoLinea);
    }

    @Override
    public List<FormatoLinea> getAllFormatoLinea() {
        /*Fabrica Query*/
        Query query = sessionFactory.getCurrentSession()
                .createSQLQuery("");

        List<FormatoLinea> formatoLineas = new ArrayList<>();
        List<Object[]> list = query.list();
        return formatoLineas;
    }

    @Override
    public List<Object> getAllFormatoLineaList() {

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

    @Override
    public List<Object> getFormatoLineaByidFormato(String idFormato) {
        Query query = sessionFactory.getCurrentSession()
                .createSQLQuery("select a.contenido,\n"
                        + "		a.estilo,\n"
                        + "		a.alineacion,\n"
                        + "		a.color \n"
                        + "		from FormatoLinea a\n"
                        + "		left join Formato b on a.IdFormato=b.IdFormato\n"
                        + "		where b.IdFormato = :idFormato")
                .setString("idFormato", idFormato);

        List<Object[]> list = query.list();
        List<Object> l = new ArrayList();
        list.stream().forEach((lista) -> {
            List<Object> obj = new ArrayList();
            obj.add((String) lista[0]);
            obj.add((String) lista[1]);
            obj.add((String) lista[2]);
            obj.add((String) lista[3]);
            l.add(obj);
        });
        return l;
    }

}
