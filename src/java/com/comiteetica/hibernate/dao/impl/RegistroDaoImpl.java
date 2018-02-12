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

        String sqlQuery = "select		r.idRegistro,\n"
                + "            coalesce(CONVERt(varchar(10),r.fechaAprobacion,103),'')fechaAprobacion,\n"
                + "            coalesce(Protocolo,'')protocolo,\n"
                + "            coalesce(Titulo,'') titulo,\n"
                + "            coalesce(r.nombreSede,'')nombreSede,\n"
                + "            rtrim(ltrim(coalesce(iv.ApePaterno,'')+' '+coalesce(iv.ApeMaterno,'')+' '+coalesce(iv.Nombres,''))) nombreInvestigador,\n"
                + "            pe.Descripcion paramEstado,\n"
                + "            per.Descripcion paramEstadoRegistro,\n"
                + "            r.observacion,\n"
                + "            r.farmacoExperimental,\n"
                + "            (case when r.placebo=0 then 'NO' when r.placebo=0 then 'SI' end)placebo,\n"
                + "            r.pacienteEas,\n"
                + "            r.easLocal,\n"
                + "            noti.Descripcion paramNotificacion,\n"
                + "            CONVERt(varchar(10),r.fechaEas,103)fechaEas,\n"
                + "            r.visitaInspeccion,\n"
                + "            (case when r.estudioNinos=0 then 'NO' when r.estudioNinos=0 then 'SI' end)estudioNinos,\n"
                + "            r.visitaInspeccionIns,\n"
                + "            r.equivalenciaCorrelativo,\n"
                + "			coalesce(ti.Descripcion,'')paramTipoInvestigacion,\n"
                + "			coalesce(e.Descripcion,'')paramEspecialidad\n"
                + "from		Registro r\n"
                + "left join	Investigacion i on r.IdInvestigacion=i.IdInvestigacion\n"
                + "left join	Sede s on s.IdSede=r.IdSede\n"
                + "left join	Investigador iv on iv.IdInvestigador=r.IdInvestigador\n"
                + "left join	ParametroDetalle pe on pe.IdParametro='P006' and pe.IdParametroDetalle=r.paramEstado\n"
                + "left join	ParametroDetalle per on per.IdParametro='P012' and per.IdParametroDetalle=r.paramEstadoRegistro\n"
                + "left join	ParametroDetalle noti on noti.IdParametro='P007' and noti.IdParametroDetalle=r.paramNotificacion\n"
                + "left join	ParametroDetalle ti on ti.IdParametro='P004' and ti.IdParametroDetalle=i.paramTipoInvestigacion\n"
                + "left join	ParametroDetalle e on e.IdParametro='P003' and e.IdParametroDetalle=i.ParamEspecialidad\n"
                + "order by	idRegistro asc";

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

    /*
    

    
     */
    @Override
    public String getCorrespondenciasByRegistro(String idRegistro) {
        List<String> list = sessionFactory.openSession().doReturningWork(new ReturningWork<List<String>>() {
            @Override
            public List<String> execute(Connection connection) throws SQLException {
                CallableStatement statement = null;
                List<String> correspondenciasList = new ArrayList<String>();
                String sqlString = "{call uspGetCorrespondenciasByRegistroHtml(?)}";
                statement = connection.prepareCall(sqlString);
                statement.setString(1, idRegistro);
                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    String correspondencia = "";
                    correspondencia = (resultSet.getString(1));
                    correspondenciasList.add(correspondencia);
                }
                return correspondenciasList;
            }
        });
        if (list != null) {
            return list.get(0);
        } else {
            return null;
        }
    }

    /*
    @Override
    public List<Object> getCorrespondenciasByRegistro(String idRegistro) {

        String sqlQuery = "declare @idCorrespondencia varchar(10)\n"
                + "declare @nombreArchivo varchar(1000), @direccion varchar(1000)\n"
                + "declare @idRegistro varchar(10)\n"
                + "\n"
                + "set @idRegistro='"+idRegistro+"'\n"
                + "\n"
                + "declare @tabla varchar(max)\n"
                + "declare @filas varchar(max)\n"
                + "declare @fila varchar(max)\n"
                + "\n"
                + "set @tabla='<div id=\"no-more-tables\" style=\"width: 100%;\">\n"
                + "                            <table class=\"table-bordered table-striped table-condensed cf\" style=\"width: 100%;\">\n"
                + "                                <thead class=\"cf\"> \n"
                + "                                    <tr>\n"
                + "                                        <td class=\"col-sm-9\">Nombre de Archivo</td>\n"
                + "                                        <td class=\"col-sm-3\"></td>\n"
                + "                                    </tr>\n"
                + "                                </thead>\n"
                + "                                <tbody>'\n"
                + "set @fila=''\n"
                + "set @filas=''\n"
                + "declare i cursor for\n"
                + "					select		distinct c.IdCorrespondencia\n"
                + "					from		Correspondencia c\n"
                + "					left join	CorrespondenciaServicio cs on c.IdCorrespondencia=cs.IdCorrespondencia\n"
                + "					where		c.IdRegistro=@idRegistro and cs.ParamTipoServicio in ('PD10','PD07','PD08','PD06')\n"
                + "open i \n"
                + "fetch next from i into @idCorrespondencia\n"
                + "	while @@FETCH_STATUS=0\n"
                + "	begin\n"
                + "		\n"
                + "		declare ii cursor for\n"
                + "								select	NombreArchivo,\n"
                + "										Direccion \n"
                + "								from	Correspondenciafile\n"
                + "								where	IdCorrespondencia=@idCorrespondencia\n"
                + "		open ii \n"
                + "		fetch next from ii into @nombreArchivo, @direccion\n"
                + "			while @@FETCH_STATUS=0\n"
                + "			begin\n"
                + "				set @fila=	'<tr><td data-title=\"Nombre\"  style=\"white-space: nowrap;overflow: hidden;text-overflow: ellipsis;\">'+\n"
                + "							@nombreArchivo+\n"
                + "							'</td><td ><a class=\"btn btn-primary\" ng-click=\"downloadFile('''+@direccion+''')\">\n"
                + "							<i class=\"fa fa-eye\" aria-hidden=\"true\"></i><span class=\"hidden-xs\"></span></a></td></tr>'\n"
                + "				fetch next from ii into @nombreArchivo, @direccion\n"
                + "			end\n"
                + "		close ii\n"
                + "		deallocate ii\n"
                + "\n"
                + "		set @filas=@filas+@fila\n"
                + "		fetch next from i into @idCorrespondencia\n"
                + "	end\n"
                + "close i\n"
                + "deallocate i\n"
                + "set @tabla=@tabla+@filas\n"
                + "set @tabla=@tabla+'</tbody></table></div>'\n"
                + "\n"
                + "select @tabla";

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
     */
}
