/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comiteetica.hibernate.dao.impl;

import com.comiteetica.hibernate.dao.RegistroBitacoraDao;
import com.comiteetica.hibernate.model.CorrespondenciaFile;
import com.comiteetica.hibernate.model.CorrespondenciaFileId;
import com.comiteetica.hibernate.model.RegistroBitacora;
import com.comiteetica.hibernate.model.RegistroBitacoraId;
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
public class RegistroBitacoraDaoImpl implements RegistroBitacoraDao {

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
    public void create(RegistroBitacora registroBitacora) {
        sessionFactory.getCurrentSession().save(registroBitacora);
    }

    @Override
    public RegistroBitacora read(RegistroBitacoraId id) {
        RegistroBitacora correspondenciaFile = (RegistroBitacora) sessionFactory.getCurrentSession().get(RegistroBitacora.class, id);
        return correspondenciaFile;
    }

    @Override
    public void update(RegistroBitacora registroBitacora) {
        sessionFactory.getCurrentSession().update(registroBitacora);
    }

    @Override
    public void delete(RegistroBitacora registroBitacora) {
        sessionFactory.getCurrentSession().delete(registroBitacora);
    }

    @Override
    public List<RegistroBitacora> getAllRegistroBitacora() {
        /*Fabrica Query*/
        Query query = sessionFactory.getCurrentSession()
                .createQuery("select rb from RegistroBitacora rb");

        List<RegistroBitacora> correspondenciaFiles = query.list();
        return correspondenciaFiles;
    }

    @Override
    public int getNextBitacoraByIdRegistro(String idRegistro) {
        /*Fabrica Query*/
        Query query = sessionFactory.getCurrentSession()
                .createQuery("select max(rb.id.idBitacora)\n"
                        + "                        from RegistroBitacora rb\n"
                        + "                        where rb.id.idRegistro = :idRegistro")
                .setString("idRegistro", idRegistro);
        List<Object> nextBitacoraQuery = query.list();
        int nextFileDetalle = 0;

        if (nextBitacoraQuery.size() > 0) {
//            System.out.println(nextFileDetalleQuery.get(0));
            if (nextBitacoraQuery.get(0) != null) {
                nextFileDetalle = (int) nextBitacoraQuery.get(0) + 1;
            } else {
                nextFileDetalle = 1;
            }
        }

        return nextFileDetalle;
    }

    @Override
    public List<RegistroBitacora> getAllBitacoraByIdRegistro(String idRegistro) {
        /*Fabrica Query*/
        Query query = sessionFactory.getCurrentSession()
                .createQuery("select rb\n"
                        + "                        from RegistroBitacora rb \n"
                        + "                        where rb.id.idRegistro=:idRegistro")
                .setString("idRegistro", idRegistro);
        List<RegistroBitacora> registroBitacoras = query.list();
        return registroBitacoras;
    }

    @Override
    public List<Object> getAllBitacoraByIdRegistroList(String idRegistro) {
        /*Fabrica Query*/
        String sqlQuery = "select	idRegistro,\n"
                + "		idBitacora,\n"
                + "		detalle,\n"
                + "		convert(varchar(10),fecha,103)fecha,\n"
                + "		(select descripcion from parametrodetalle where idParametro='P013' and idParametroDetalle=paramTipoBitacora)paramTipoBitacora,\n"
                + "		(case when paramTipoBitacora='PD04' then\n"
                + "		(select descripcion from parametrodetalle where idParametro='P007' and idParametroDetalle=ParamDetalleBitacora)\n"
                + "		when paramTipoBitacora='PD05' then\n"
                + "		(select descripcion from parametrodetalle where idParametro='P014' and idParametroDetalle=ParamDetalleBitacora)\n"
                + "		else '' end) paramDetalleBitacora\n"
                + "from	RegistroBitacora \n"
                + "where	idRegistro='" + idRegistro + "'";

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
