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

        List<PagoDetalle> pagoDetalles = new ArrayList<>();
        List<Object[]> list = query.list();
        return pagoDetalles;
    }

    @Override
    public List<Object> getAllPagoDetalleList() {

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
//            System.out.println(nextFileDetalleQuery.get(0));
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
                + "		d.titulo\n"
                + "from	PagoDetalle a\n"
                + "inner join Correspondencia b on a.IdCorrespondencia=b.IdCorrespondencia\n"
                + "inner join registro c on c.IdRegistro=b.IdRegistro\n"
                + "inner join Investigacion d on d.IdInvestigacion=c.IdInvestigacion\n"
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
