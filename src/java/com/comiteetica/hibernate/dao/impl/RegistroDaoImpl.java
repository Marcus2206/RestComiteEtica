/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comiteetica.hibernate.dao.impl;

import com.comiteetica.hibernate.dao.RegistroDao;
import com.comiteetica.hibernate.model.Registro;
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
public class RegistroDaoImpl implements RegistroDao {

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
    public void create(Registro registro) {
        sessionFactory.getCurrentSession().save(registro);
    }

    @Override
    public Registro read(String idRegistro) {
        Registro patrocinador = (Registro) sessionFactory.getCurrentSession().get(Registro.class, idRegistro);
        Hibernate.initialize(patrocinador.getCorrespondencias());
        return patrocinador;
    }

    @Override
    public void update(Registro registro) {
        sessionFactory.getCurrentSession().update(registro);
    }

    @Override
    public void delete(Registro registro) {
        sessionFactory.getCurrentSession().delete(registro);
    }

    @Override
    public List<Registro> getAllRegistro() {
        /*Fabrica Query*/
        Query query = sessionFactory.getCurrentSession()
                .createQuery("select "
                        + "idProducto, "
                        + "nombre, "
                        + "descripcion "
                        + "from    Producto p");
        //query.setFirstResult(ini);
        //query.setMaxResults(fin);
        /*Crea Objeto contenedor*/
        List<Registro> registros = new ArrayList<>();
        /*Realiza consulta y devuelve Object[]*/
        List<Object[]> list = query.list();
        /*Itera en cada fila*/
        list.stream().forEach((registro) -> {
            Registro reg = new Registro();
            reg.setIdRegistro(registro[0].toString());
            reg.setObservacion(registro[1].toString());
            registros.add(reg);
        });
        return registros;
    }

    @Override
    public List<Object> getAllRegistroList() {

        String sqlQuery = "select	r.idRegistro,\n"
                + "		r.fechaAprobacion,\n"
                + "		(select Protocolo+' - '+Titulo from Investigacion where IdInvestigacion=r.IdInvestigacion)idInvestigacion,\n"
                + "		(select Nombre from Sede where IdSede=r.idSede)idSede,\n"
                + "		(select ApePaterno+' '+ApeMaterno+', '+Nombres from Investigador where IdInvestigador=r.IdInvestigador)idInvestigador,\n"
                + "		(select Descripcion from ParametroDetalle where IdParametro='P006' and IdParametroDetalle=r.paramEstado)paramEstado,\n"
                + "		r.observacion,\n"
                + "		r.farmacoExperimental,\n"
                + "		r.placebo,\n"
                + "		r.pacienteEas,\n"
                + "		r.easLocal,\n"
                + "		(select Descripcion from ParametroDetalle where IdParametro='P007' and IdParametroDetalle=r.paramNotificacion)paramNotificacion,\n"
                + "		r.fechaEas,\n"
                + "		r.visitaInspeccion,\n"
                + "		r.estudioNinos,\n"
                + "		r.visitaInspeccionIns\n"
                + "from	Registro r\n"
                + "order by idRegistro asc";

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
