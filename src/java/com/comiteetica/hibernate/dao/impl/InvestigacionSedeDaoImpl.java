/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comiteetica.hibernate.dao.impl;

import com.comiteetica.hibernate.dao.InvestigacionSedeDao;
import com.comiteetica.hibernate.model.InvestigacionSede;
import com.comiteetica.hibernate.model.InvestigacionSedeId;
import com.comiteetica.hibernate.model.Sede;
import com.comiteetica.hibernate.model.SerieCorrelativo;
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
public class InvestigacionSedeDaoImpl implements InvestigacionSedeDao {

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
    public void create(InvestigacionSede investigacionSede) {
        sessionFactory.getCurrentSession().save(investigacionSede);
    }

    @Override
    public InvestigacionSede read(InvestigacionSedeId investigacionSedeId) {
        InvestigacionSede investigacionSede = (InvestigacionSede) sessionFactory.getCurrentSession().get(InvestigacionSede.class, investigacionSedeId);
        //Hibernate.initialize(monitor.getInvestigacionMonitors());
        return investigacionSede;
    }

    @Override
    public void update(InvestigacionSede investigacionSede) {
        sessionFactory.getCurrentSession().update(investigacionSede);
    }

    @Override
    public void delete(InvestigacionSede investigacionSede) {
        sessionFactory.getCurrentSession().delete(investigacionSede);
    }

    @Override
    public List<InvestigacionSede> getAllInvestigacionSede() {
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
        List<InvestigacionSede> investigacionSedes = new ArrayList<>();
        /*Realiza consulta y devuelve Object[]*/
        List<Object[]> list = query.list();
        /*Itera en cada fila*/
        list.stream().forEach((investigacionSede) -> {
            InvestigacionSede invSede = new InvestigacionSede();
            invSede.setId((InvestigacionSedeId) investigacionSede[0]);
            invSede.setObservacion(investigacionSede[1].toString());
            investigacionSedes.add(invSede);
        });
 
        return investigacionSedes;
    }

    @Override
    public List<Object> getInvestigacionSedeByIdInvestigacion(String idInvestigacion) {

        String sqlQuery = "select		distinct \n"
                + "			a.idInvestigacion,\n"
                + "			a.idSede,\n"
                + "			b.nombre,\n"
                + "			(select Descripcion from Departamento where IdDepartamento=b.IdDepartamento)idDepartamento,\n"
                + "			(select Descripcion from Provincia where IdDepartamento=b.IdDepartamento and IdProvincia=b.IdProvincia)idProvincia,\n"
                + "			(select Descripcion from Distrito where IdDepartamento=b.IdDepartamento and IdProvincia=b.IdProvincia and IdDistrito=b.IdDistrito)idDistrito\n"
                + "from		InvestigacionSede a\n"
                + "inner join	sede b on a.IdSede=b.IdSede\n"
                + "where		a.IdInvestigacion='" + idInvestigacion + "'";
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
