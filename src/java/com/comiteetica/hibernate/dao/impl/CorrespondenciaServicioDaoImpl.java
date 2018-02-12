/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comiteetica.hibernate.dao.impl;

import com.comiteetica.hibernate.dao.CorrespondenciaServicioDao;
import com.comiteetica.hibernate.model.CorrespondenciaServicio;
import com.comiteetica.hibernate.model.CorrespondenciaServicioId;
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

/**
 *
 * @author rasec
 */
public class CorrespondenciaServicioDaoImpl implements CorrespondenciaServicioDao {
    
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
    public void create(CorrespondenciaServicio correspondenciaServicio) {
        sessionFactory.getCurrentSession().save(correspondenciaServicio);
    }
    
    @Override
    public int getNextServicioDetalleByIdCorrespondencia(String idCorrespondencia) {
        /*Fabrica Query*/
        Query query = sessionFactory.getCurrentSession()
                .createQuery("select max(cf.id.idCorrespondenciaServicio)\n"
                        + "from CorrespondenciaServicio cf\n"
                        + "where cf.id.idCorrespondencia = :idCorrespondencia")
                .setString("idCorrespondencia", idCorrespondencia);
        List<Object> nextFileDetalleQuery = query.list();
        int nextFileDetalle = 0;
        
        if (nextFileDetalleQuery.size() > 0) {
            if (nextFileDetalleQuery.get(0) != null) {
                nextFileDetalle = (int) nextFileDetalleQuery.get(0) + 1;
            } else {
                nextFileDetalle = 1;
            }
        }
        
        return nextFileDetalle;
    }
    
    @Override
    public CorrespondenciaServicio read(CorrespondenciaServicioId id) {
        CorrespondenciaServicio correspondencia = (CorrespondenciaServicio) sessionFactory.getCurrentSession().get(CorrespondenciaServicio.class, id);
        return correspondencia;
    }
    
    @Override
    public List<CorrespondenciaServicio> readByIdCorrespondencia(String idCorrespondencia) {
        Query query = sessionFactory.getCurrentSession()
                .createQuery("select cs from CorrespondenciaServicio cs\n"
                        + "where cs.id.idCorrespondencia=:idCorrespondencia")
                .setString("idCorrespondencia", idCorrespondencia);
        
        List<CorrespondenciaServicio> correspondenciaServicios = query.list();
        return correspondenciaServicios;
    }
    
    @Override
    public void update(CorrespondenciaServicio correspondenciaServicio) {
        sessionFactory.getCurrentSession().update(correspondenciaServicio);
    }
    
    @Override
    public void delete(CorrespondenciaServicio correspondenciaServicio) {
        sessionFactory.getCurrentSession().delete(correspondenciaServicio);
    }
    
    @Override
    public List<CorrespondenciaServicio> getAllCorrespondenciaServicio() {
        /*Fabrica Query*/
        Query query = sessionFactory.getCurrentSession()
                .createSQLQuery("");
        
        List<CorrespondenciaServicio> correspondenciaServicios = new ArrayList<>();
        List<Object[]> list = query.list();
        return correspondenciaServicios;
    }
    
    @Override
    public List<Object> getAllCorrespondenciaServicioSinPagoList() {
        
        String sqlQuery = "select	a.idCorrespondencia,\n"
                + "		a.idCorrespondenciaServicio,\n"
                + "		d.idInvestigacion,\n"
                + "		d.protocolo,\n"
                + "		d.titulo,\n"
                + "             convert(varchar(10),b.fechaCorrespondencia,103) as fechaCorrespondencia,\n"
                + "             convert(varchar(10),b.fechaCarta,103) as fechaCarta,\n"
                + "		(select Descripcion from ParametroDetalle where IdParametro='P001' and IdParametroDetalle=a.paramTipoServicio)nparamTipoServicio,\n"
                + "		a.paramTipoServicio,\n"
                + "		a.costo,\n"
                + "		a.observacion,\n"
                +"                       coalesce(e.ApePaterno,'')+' '+coalesce(e.ApeMaterno,'')+' '+coalesce(e.Nombres,'') investigador \n"
                + "from	correspondenciaServicio a\n"
                + "inner join Correspondencia b on a.IdCorrespondencia=b.IdCorrespondencia\n"
                + "inner join Registro c on b.IdRegistro=c.IdRegistro\n"
                + "inner join Investigacion d on c.IdInvestigacion=d.IdInvestigacion\n"
                + "inner join Investigador e on e.IdInvestigador=c.IdInvestigador\n"
                + "where a.Transferido=0\n"
                + "order by a.idCorrespondencia, a.IdCorrespondenciaServicio desc";
        
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
    public List<Object> getAllCorrespondenciaServicioByCorrespondencia(String idCorrespondencia) {
        
        String sqlQuery = "select	idCorrespondencia,\n"
                + "		idCorrespondenciaServicio,\n"
                + "		(select Descripcion from ParametroDetalle where IdParametro='P001' and IdParametroDetalle=paramTipoServicio)paramTipoServicio,\n"
                + "		costo,\n"
                + "		observacion\n"
                + "from	correspondenciaServicio\n"
                + "where	idCorrespondencia='" + idCorrespondencia + "'\n"
                + "order by idCorrespondenciaServicio asc";
        
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
