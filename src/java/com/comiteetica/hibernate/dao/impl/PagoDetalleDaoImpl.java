/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comiteetica.hibernate.dao.impl;

import com.comiteetica.hibernate.dao.PagoDetalleDao;
import com.comiteetica.hibernate.model.PagoDetalle;
import com.comiteetica.hibernate.model.PagoDetalleId;
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
public class PagoDetalleDaoImpl implements PagoDetalleDao {

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
    public void create(PagoDetalle pagoDetalle) {
        sessionFactory.getCurrentSession().save(pagoDetalle);
    }

    @Override
    public PagoDetalle read(PagoDetalleId id) {
        PagoDetalle pago = (PagoDetalle) sessionFactory.getCurrentSession().get(PagoDetalle.class, id);
        return pago;
    }

    @Override
    public void update(PagoDetalle pagoDetalle) {
        sessionFactory.getCurrentSession().update(pagoDetalle);
    }

    @Override
    public void delete(PagoDetalle pagoDetalle) {
        sessionFactory.getCurrentSession().delete(pagoDetalle);
    }

    @Override
    public List<PagoDetalle> getAllPagoDetalle() {
        /*Fabrica Query*/
        Query query = sessionFactory.getCurrentSession()
                .createSQLQuery("select pd from PagoDetalle where pd.id.Pago=:idPago")
                        .setString("idPago", null);

        List<PagoDetalle> pagoDetalles = query.list();
//        List<Object[]> list = query.list();
        return pagoDetalles;
    }
    
       @Override
    public List<PagoDetalle> getAllPagoDetalleByPago(String idPago) {
        /*Fabrica Query*/
        Query query = sessionFactory.getCurrentSession()
                .createQuery("select pd from PagoDetalle pd where pd.id.idPago=:idPago")
                        .setString("idPago", idPago);

        List<PagoDetalle> pagoDetalles = query.list();
//        List<Object[]> list = query.list();
        return pagoDetalles;
    }

    @Override
    public List<Object> getAllPagoDetalleList() {

        String sqlQuery = "";

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
    public int getNextPagoDetalleByIdPago(String idPago) {
        /*Fabrica Query*/
        Query query = sessionFactory.getCurrentSession()
                .createQuery("select max(pg.id.idPagoDetalle) \n"
                        + "from PagoDetalle pg\n"
                        + "where pg.id.idPago=:idPago")
                .setString("idPago", idPago);
        List<Object> nextPagoDetalleQuery = query.list();
        int nextFileDetalle = 0;

        if (nextPagoDetalleQuery.size() > 0) {
            if (nextPagoDetalleQuery.get(0) != null) {
                nextFileDetalle = (int) nextPagoDetalleQuery.get(0) + 1;
            } else {
                nextFileDetalle = 1;
            }
        }

        return nextFileDetalle;
    }

    @Override
    public List<Object> getAllPagoDetalleByPagoList(String idPago) {

        String sqlQuery = "select	a.idPago,\n"
                + "		a.idPagoDetalle,\n"
                + "		a.idCorrespondencia,\n"
                + "		a.idCorrespondenciaServicio,\n"
                + "		(select Descripcion from ParametroDetalle where IdParametro='P001' and IdParametroDetalle=a.paramTipoServicio)nparamTipoServicio,\n"
                + "		d.protocolo,\n"
                + "		a.costo,\n"
                + "		coalesce(e.ApePaterno,'')+' '+coalesce(e.ApeMaterno,'')+', '+coalesce(e.Nombres,'') nombreInvestigador\n"
                + "from	PagoDetalle a\n"
                + "left join Correspondencia b on a.IdCorrespondencia=b.IdCorrespondencia\n"
                + "left join registro c on c.IdRegistro=b.IdRegistro\n"
                + "left join Investigacion d on d.IdInvestigacion=c.IdInvestigacion\n"
                + "left join Investigador e on e.IdInvestigador=c.IdInvestigador\n"
                + "where a.idPago='" + idPago + "'";

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
