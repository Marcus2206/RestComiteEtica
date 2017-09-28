/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comiteetica.hibernate.dao.impl;

import com.comiteetica.hibernate.dao.RegistroDao;
import com.comiteetica.hibernate.model.Correspondencia;
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
        Registro registro = (Registro) sessionFactory.getCurrentSession().get(Registro.class, idRegistro);
        Hibernate.initialize(registro.getInvestigacion());
        Hibernate.initialize(registro.getInvestigador());
        Hibernate.initialize(registro.getSede());
        return registro;
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
                + "                      CONVERt(varchar(10),r.fechaAprobacion,103)fechaAprobacion,\n"
                + "                      coalesce(Protocolo,'')protocolo,\n"
                + "		coalesce(Titulo,'') titulo,\n"
                + "		coalesce(s.Nombre,'')nombreSede,\n"
                + "		coalesce(iv.ApePaterno,'')+' '+coalesce(iv.ApeMaterno,'')+', '+coalesce(iv.Nombres,'')nombreInvestigador,\n"
                + "        (select Descripcion from ParametroDetalle where IdParametro='P006' and IdParametroDetalle=r.paramEstado)paramEstado,\n"
                + "        (select Descripcion from ParametroDetalle where IdParametro='P012' and IdParametroDetalle=r.paramEstado)paramEstadoRegistro,\n"
                + "        r.observacion,\n"
                + "        r.farmacoExperimental,\n"
                + "        (case when r.placebo=0 then 'NO' when r.placebo=0 then 'SI' end)placebo,\n"
                + "        r.pacienteEas,\n"
                + "        r.easLocal,\n"
                + "        (select Descripcion from ParametroDetalle where IdParametro='P007' and IdParametroDetalle=r.paramNotificacion)paramNotificacion,\n"
                + "        CONVERt(varchar(10),r.fechaEas,103)fechaEas,\n"
                + "        r.visitaInspeccion,\n"
                + "		(case when r.estudioNinos=0 then 'NO' when r.estudioNinos=0 then 'SI' end)estudioNinos,\n"
                + "        r.visitaInspeccionIns,\n"
                + "        r.equivalenciaCorrelativo\n"
                + "from	Registro r\n"
                + "left join Investigacion i on r.IdInvestigacion=i.IdInvestigacion\n"
                + "left join Sede s on s.IdSede=r.IdSede\n"
                + "left join Investigador iv on iv.IdInvestigador=r.IdInvestigador\n"
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

    @Override
    public List<Object> validateRegistro(String idInvestigacion, String idInvestigador, String idSede) {

        String sqlQuery = "select idRegistro from Registro\n"
                + "                       where idInvestigacion='" + idInvestigacion + "'\n"
                + "                       and idInvestigador='" + idInvestigador + "'\n"
                + "                       and idSede='" + idSede + "'\n";

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
    public List<Correspondencia> validateRegistroEnCorrespondencia(String idRegistro) {
        /*Fabrica Query*/
        Query query = sessionFactory.getCurrentSession()
                .createQuery("select c from Correspondencia c\n"
                        + "where c.registro.idRegistro=:idRegistro")
                .setString("idRegistro", idRegistro);
        List<Correspondencia> correspondencias = query.list();

        return correspondencias;
    }

}
