/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comiteetica.hibernate.dao.impl;

import com.comiteetica.hibernate.dao.InvestigacionDao;
import com.comiteetica.hibernate.model.Investigacion;
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
public class InvestigacionDaoImpl implements InvestigacionDao {

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
    public void create(Investigacion investigacion) {
        sessionFactory.getCurrentSession().save(investigacion);
    }

    @Override
    public Investigacion read(String idInvestigacion) {
        Investigacion investigacion = (Investigacion) sessionFactory.getCurrentSession().get(Investigacion.class, idInvestigacion);
        Hibernate.initialize(investigacion.getPatrocinador());
        Hibernate.initialize(investigacion.getCro());

        return investigacion;
    }

    @Override
    public Investigacion readInvestigacion(String idInvestigacion) {
        /*Fabrica Query*/
        Query query = sessionFactory.getCurrentSession()
                .createQuery("select "
                        + "idInvestigacion, "
                        + "protocolo, "
                        + "titulo, "
                        + "paramEspecialidad, "
                        + "paramFase, "
                        + "paramTipoInvestigacion "
                        + "from    Investigacion "
                        + " where idInvestigacion=:idInvestigacion")
                .setString("idInvestigacion", idInvestigacion);

        /*Crea Objeto contenedor*/
        List<Investigacion> investigacions = new ArrayList<>();
        /*Realiza consulta y devuelve Object[]*/
        List<Object[]> list = query.list();
        /*Itera en cada fila*/
        list.stream().forEach((investigacion) -> {
            Investigacion inv = new Investigacion();
            inv.setIdInvestigacion(investigacion[0].toString());
            inv.setProtocolo(investigacion[1].toString());
            inv.setTitulo(investigacion[2].toString());
            inv.setParamEspecialidad((String) investigacion[3]);
            inv.setParamFase((String) investigacion[4]);
            inv.setParamTipoInvestigacion((String) investigacion[5]);
            investigacions.add(inv);
        });

        return investigacions.get(0);
    }

    @Override
    public void update(Investigacion investigacion) {
        sessionFactory.getCurrentSession().update(investigacion);
    }

    @Override
    public void delete(Investigacion investigacion) {
        sessionFactory.getCurrentSession().delete(investigacion);
    }

    @Override
    public List<Investigacion> getAllInvestigacion() {
        /*Fabrica Query*/
        Query query = sessionFactory.getCurrentSession()
                .createQuery("select "
                        + "idInvestigacion, "
                        + "protocolo, "
                        + "titulo, "
                        + "paramEspecialidad, "
                        + "paramFase, "
                        + "paramTipoInvestigacion "
                        + "from    Investigacion");

        List<Investigacion> investigacions = new ArrayList<>();
        /*Realiza consulta y devuelve Object[]*/
        List<Object[]> list = query.list();
        /*Itera en cada fila*/
        list.stream().forEach((investigacion) -> {
            Investigacion inv = new Investigacion();
            inv.setIdInvestigacion(investigacion[0].toString());
            inv.setProtocolo(investigacion[1].toString());
            inv.setTitulo(investigacion[2].toString());
            inv.setParamEspecialidad((String) investigacion[3]);
            inv.setParamFase((String) investigacion[4]);
            inv.setParamTipoInvestigacion((String) investigacion[5]);

            investigacions.add(inv);
        });

        return investigacions;
    }

    @Override
    public List<Object> getAllInvestigacionList() {
        /*Fabrica Query*/
        String sqlQuery = "select	idInvestigacion,\n"
                + "                 		protocolo,\n"
                + "                 		titulo,\n"
                + "                 		coalesce(e.descripcion,'')paramEspecialidad,\n"
                + "                 		coalesce(f.descripcion,'')paramFase,\n"
                + "                 		coalesce(ti.descripcion,'')paramTipoInvestigacion\n"
                + "from	Investigacion i\n"
                + "left join ParametroDetalle e on e.IdParametro='P003' and e.IdParametroDetalle=i.paramEspecialidad\n"
                + "left join ParametroDetalle f on f.IdParametro='P005' and f.IdParametroDetalle=i.paramFase\n"
                + "left join ParametroDetalle ti on ti.IdParametro='P004' and ti.IdParametroDetalle=i.paramTipoInvestigacion\n"
                + "order by IdInvestigacion";

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
    public List<Object> getAllInvestigacionSimbolos() {
        /*Fabrica Query*/
        String sqlQuery = "select	idSimbolo,\n"
                + "		rtrim(ltrim(descripcion))descripcion \n"
                + "from	simbolo order by idsimbolo asc";

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
