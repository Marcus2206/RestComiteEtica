/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comiteetica.hibernate.dao.impl;

import com.comiteetica.hibernate.dao.CorrespondenciaDao;
import com.comiteetica.hibernate.model.Correspondencia;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
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
public class CorrespondenciaDaoImpl implements CorrespondenciaDao {

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
    public void create(Correspondencia correspondencia) {
        sessionFactory.getCurrentSession().save(correspondencia);
    }

    @Override
    public Correspondencia read(String idCorrespondencia) {
        Correspondencia correspondencia = (Correspondencia) sessionFactory.getCurrentSession().get(Correspondencia.class, idCorrespondencia);
        Hibernate.initialize(correspondencia.getRegistro());
        return correspondencia;
    }

    @Override
    public void update(Correspondencia correspondencia) {
        sessionFactory.getCurrentSession().update(correspondencia);
    }

    @Override
    public void delete(Correspondencia correspondencia) {
        sessionFactory.getCurrentSession().delete(correspondencia);
    }

    @Override
    public List<Correspondencia> getAllCorrespondencia() {
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

        List<Correspondencia> correspondencias = new ArrayList<>();
        List<Object[]> list = query.list();
        return correspondencias;
    }

    /*
    
select	convert(varchar(10),FechaCorrespondencia,103) fechaCorrespondencia,
		coalesce(d.ApePaterno,'')+' '+coalesce(d.ApeMaterno,'')+', '+coalesce(d.Nombres,'')investigador,
		coalesce(c.protocolo,'') protocolo,
		coalesce(b.EquivalenciaCorrelativo,'') correlativo,
		(select Descripcion from ParametroDetalle where IdParametro='P002' and IdParametroDetalle=ParamDistribucion) distribucion
from	Correspondencia a
left join Registro b on a.IdRegistro=b.IdRegistro
left join Investigacion c on c.IdInvestigacion=b.IdInvestigacion
left join Investigador d on d.IdInvestigador=b.IdInvestigador
where IdCorrespondencia='CRP1707098'
     */
    @Override
    public List<Object> getDatosHojaRuta(String idCorrespondencia) {
        /*Fabrica Query*/
        Query query = sessionFactory.getCurrentSession()
                .createSQLQuery("select	convert(varchar(10),FechaCorrespondencia,103) fechaCorrespondencia,\n"
                        + "		coalesce(d.ApePaterno,'')+' '+coalesce(d.ApeMaterno,'')+', '+coalesce(d.Nombres,'')investigador,\n"
                        + "		coalesce(c.protocolo,'') protocolo,\n"
                        + "		coalesce(b.EquivalenciaCorrelativo,'') correlativo,\n"
                        + "		(select Descripcion from ParametroDetalle where IdParametro='P002' and IdParametroDetalle=ParamDistribucion) distribucion,\n"
                        + "		coalesce(otro,'')otro,\n"
                        + "		idCorrespondencia\n"
                        + "from	Correspondencia a\n"
                        + "left join Registro b on a.IdRegistro=b.IdRegistro\n"
                        + "left join Investigacion c on c.IdInvestigacion=b.IdInvestigacion\n"
                        + "left join Investigador d on d.IdInvestigador=b.IdInvestigador\n"
                        + "where IdCorrespondencia=:idCorrespondencia")
                .setString("idCorrespondencia", idCorrespondencia);

        List<Correspondencia> correspondencias = new ArrayList<>();
        List<Object[]> list = query.list();
        List<Object> l = new ArrayList();
        list.stream().forEach((lista) -> {
            List<Object> obj = new ArrayList();
            obj.add((String) lista[0]);
            obj.add((String) lista[1]);
            obj.add((String) lista[2]);
            obj.add((String) lista[3]);
            obj.add((String) lista[4]);
            obj.add((String) lista[5]);
            obj.add((String) lista[6]);
            l.add(obj);
        });
        return l;
    }

    @Override
    public List<Object> getDatosCarta(String idCorrespondencia) {
        /*Fabrica Query*/
        Query query = sessionFactory.getCurrentSession()
                .createSQLQuery("select	convert(varchar(10),FechaCorrespondencia,103) fechaCorrespondencia,\n"
                        + "                        		coalesce(d.ApePaterno,'')+' '+coalesce(d.ApeMaterno,'')+' '+coalesce(d.Nombres,'')investigador,\n"
                        + "                        		coalesce(c.protocolo,'') protocolo,\n"
                        + "			coalesce(c.titulo,'') titulo,\n"
                        + "			coalesce(b.nombreSede,'') nombreSede,\n"
                        + "                        		coalesce(b.EquivalenciaCorrelativo,'') correlativo,\n"
                        + "                        		(select Descripcion from ParametroDetalle where IdParametro='P002' and IdParametroDetalle=ParamDistribucion) distribucion,\n"
                        + "                        		coalesce(otro,'')otro,\n"
                        + "                        		idCorrespondencia\n"
                        + "                        from	Correspondencia a\n"
                        + "                        left join Registro b on a.IdRegistro=b.IdRegistro\n"
                        + "                        left join Investigacion c on c.IdInvestigacion=b.IdInvestigacion\n"
                        + "                        left join Investigador d on d.IdInvestigador=b.IdInvestigador\n"
                        + "                        left join Sede e on e.IdSede = b.IdSede\n"
                        + "                        where IdCorrespondencia=:idCorrespondencia")
                .setString("idCorrespondencia", idCorrespondencia);

        List<Correspondencia> correspondencias = new ArrayList<>();
        List<Object[]> list = query.list();
        List<Object> l = new ArrayList();
        list.stream().forEach((lista) -> {
            List<Object> obj = new ArrayList();
            obj.add((String) lista[0]);
            obj.add((String) lista[1]);
            obj.add((String) lista[2]);
            obj.add((String) lista[3]);
            obj.add((String) lista[4]);
            obj.add((String) lista[5]);
            obj.add((String) lista[6]);
            obj.add((String) lista[7]);
            obj.add((String) lista[8]);
            l.add(obj);
        });
        return l;
    }

    @Override
    public List<Object> getAllCorrespondenciaList() {

        String sqlQuery = "select 	c.idCorrespondencia,\n"
                + "                 		coalesce(c.correlativoInterno,'') correlativoInterno,\n"
//                + "	replace(replace(replace(replace(coalesce(protocolo,''),CHAR(10),', '),CHAR(13),', '),'/','-'),':','.-') protocolo,\n"
                + "                 		coalesce(c.protocolo,'') protocolo,\n"
                + "                 		coalesce(c.fechaCorrespondencia,'') fechaCorrespondencia,\n"
                + "                 		coalesce(c.fechaCarta,'') fechaCarta,\n"
                + "                 		coalesce(c.idRegistro,'') idRegistro,\n"
                
                + "                 		coalesce(c.equivalenciaCorrelativo,'') equivalenciaCorrelativo,\n"
                + "                 		coalesce(b.Descripcion,'') paramTipoServicio,\n"
                + "                 		coalesce(c.otro,'') otro,\n"
                + "                 		coalesce(a.Descripcion,'') paramDistribucion,\n"
                + "                 		coalesce(convert(varchar(10),c.fechaSesion,103),'') fechaSesion,\n"
                + "                 		cast(coalesce(c.enviarCorreo,0) as int)enviarCorreo,\n"
                + "                 		cast(coalesce(c.enviado,0) as int )enviado\n"
                + "from	Correspondencia c\n"
                + "left join ParametroDetalle a on a.IdParametro='P002' and a.IdParametroDetalle=paramDistribucion \n"
                + "left join ParametroDetalle b on a.IdParametro='P001' and a.IdParametroDetalle=paramTipoServicio \n"
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
    public List<Correspondencia> readByFechaSesion(Date fechaSesion) {
        Query query = sessionFactory.getCurrentSession()
                .createQuery("select c from Correspondencia c\n"
                        + "where c.fechaSesion=:fechaSesion")
                .setDate("fechaSesion", fechaSesion);
        List<Correspondencia> correspondencias = query.list();
        return correspondencias;

//        Correspondencia correspondencia = (Correspondencia) sessionFactory.getCurrentSession().get(Correspondencia.class, idCorrespondencia);
//        Hibernate.initialize(correspondencia.getRegistro());
//        return correspondencia;
    }

    @Override
    public List<Object> readCorrespondenciasValidas(String idRegistro) {

        String sqlQuery = "select 	distinct c.idCorrespondencia,\n"
                + "                                  		coalesce(c.correlativoInterno,'') correlativoInterno,\n"
                + "                                  		coalesce(c.fechaCorrespondencia,'') fechaCorrespondencia,\n"
                + "                                  		coalesce(c.fechaCarta,'') fechaCarta,\n"
                + "                                  		coalesce(c.idRegistro,'') idRegistro,\n"
                + "                                  		coalesce(c.equivalenciaCorrelativo,'') equivalenciaCorrelativo,\n"
                + "				coalesce(c.protocolo,'') protocolo,\n"
                + "                                  		coalesce(c.otro,'') otro,\n"
                + "                                  		coalesce(a.Descripcion,'') paramDistribucion,\n"
                + "                                  		coalesce(convert(varchar(10),c.fechaSesion,103),'') fechaSesion,\n"
                + "                                  		(case when cast(coalesce(c.enviarCorreo,0) as int)=0 then 'No' else 'Sí' end) enviarCorreo,\n"
                + "                                  		(case when cast(coalesce(c.enviado,0) as int )=0 then 'No' else 'Sí' end) enviado\n"
                + "                 from	Correspondencia c\n"
                + "                 left join ParametroDetalle a on a.IdParametro='P002' and a.IdParametroDetalle=paramDistribucion \n"
                + "                 left join ParametroDetalle b on a.IdParametro='P001' and a.IdParametroDetalle=paramTipoServicio \n"
                + "				 left join	CorrespondenciaServicio cs on c.IdCorrespondencia=cs.IdCorrespondencia\n"
                + "                 where		c.IdRegistro='" + idRegistro + "' and cs.ParamTipoServicio in ('PD10','PD07','PD08','PD06')\n"
                + "                 order by idCorrespondencia";

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
