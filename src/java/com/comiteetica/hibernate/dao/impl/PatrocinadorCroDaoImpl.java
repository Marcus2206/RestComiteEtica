/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comiteetica.hibernate.dao.impl;

import com.comiteetica.hibernate.dao.PatrocinadorCroDao;
import com.comiteetica.hibernate.model.Cro;
import com.comiteetica.hibernate.model.Patrocinador;
import com.comiteetica.hibernate.model.PatrocinadorCro;
import com.comiteetica.hibernate.model.PatrocinadorCroId;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.jdbc.ReturningWork;

/**
 *
 * @author rasec
 */
public class PatrocinadorCroDaoImpl implements PatrocinadorCroDao {

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
    public void create(PatrocinadorCro patrocinadorCro) {
        sessionFactory.getCurrentSession().save(patrocinadorCro);
    }

    @Override
    public PatrocinadorCro read(PatrocinadorCroId id) {
        PatrocinadorCro patrocinadorCro = (PatrocinadorCro) sessionFactory.getCurrentSession().get(PatrocinadorCro.class, id);
        return patrocinadorCro;
    }

    @Override
    public void update(PatrocinadorCro patrocinadorCro) {
        sessionFactory.getCurrentSession().update(patrocinadorCro);
    }

    @Override
    public void delete(PatrocinadorCro patrocinadorCro) {
        sessionFactory.getCurrentSession().delete(patrocinadorCro);
    }

    @Override
    public List<PatrocinadorCro> getAllPatrocinadorCro() {
        /*Fabrica Query*/
        Query query = sessionFactory.getCurrentSession()
                .createQuery("select p.idPatrocinador,"
                        + "p.nombre from Patrocinador p");
        //query.setFirstResult(ini);
        //query.setMaxResults(fin);
        /*Crea Objeto contenedor*/
        List<PatrocinadorCro> patrocinadors = new ArrayList<>();
        /*Realiza consulta y devuelve Object[]*/
        List<Object[]> list = query.list();
        /*Itera en cada fila*/
        list.stream().forEach((patrocinador) -> {
            PatrocinadorCro patro = new PatrocinadorCro();
            patro.setId((PatrocinadorCroId) patrocinador[0]);
            patro.setDescripcion((String) patrocinador[1]);
            patrocinadors.add(patro);
        });
        return patrocinadors;
    }

    @Override
    public List<Object> getAllPatrocinadorCroSqlList() {
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
    public List<Object> getPatrocinadorByIdCro(String idCro) {
        /*Fabrica Query*/
        Query query = sessionFactory.getCurrentSession()
                .createQuery("select pc,p\n"
                        + "from PatrocinadorCro pc\n"
                        + "inner join pc.patrocinador p\n"
                        + "where pc.id.idCro='"+idCro+"'");

        //query.setFirstResult(ini);
        //query.setMaxResults(fin);
        /*Crea Objeto contenedor*/
        List<Object> objetos = new ArrayList<>();
        /*Realiza consulta y devuelve Object[]*/
        List<Object[]> list = query.list();
        /*Itera en cada fila*/
        list.stream().forEach((objeto) -> {
            Object[] o = new Object[2];
            o[0] = (PatrocinadorCro) objeto[0];
            o[1] = (Patrocinador) objeto[1];
            objetos.add(o);
        });

        return objetos;
    }
    
    @Override
    public List<Object> getCroByIdPatrocinador(String idPatrocinador) {
        /*Fabrica Query*/
        Query query = sessionFactory.getCurrentSession()
                .createQuery("select pc,p\n"
                        + "from PatrocinadorCro pc\n"
                        + "inner join pc.cro p\n"
                        + "where pc.id.idPatrocinador='"+idPatrocinador+"'");

        //query.setFirstResult(ini);
        //query.setMaxResults(fin);
        /*Crea Objeto contenedor*/
        List<Object> objetos = new ArrayList<>();
        /*Realiza consulta y devuelve Object[]*/
        List<Object[]> list = query.list();
        /*Itera en cada fila*/
        list.stream().forEach((objeto) -> {
            Object[] o = new Object[2];
            o[0] = (PatrocinadorCro) objeto[0];
            o[1] = (Cro) objeto[1];
            objetos.add(o);
        });

        return objetos;
    }
}
