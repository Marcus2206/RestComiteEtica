/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comiteetica.hibernate.dao.impl;

import com.comiteetica.hibernate.dao.SerieCorrelativoDao;
import com.comiteetica.hibernate.model.SerieCorrelativo;
import com.comiteetica.hibernate.model.SerieCorrelativoId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.jdbc.ReturningWork;
import org.springframework.stereotype.Repository;
import java.sql.ResultSet;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author rasec
 */
@Repository
public class SerieCorrelativoDaoImpl implements SerieCorrelativoDao{

    SessionFactory sessionFactory=HibernateUtil.getSessionFactory();
    
    @Override
    public void beginTransaction(){
        sessionFactory.getCurrentSession().beginTransaction();
    }
    
    @Override
    public void commit(){
        sessionFactory.getCurrentSession().getTransaction().commit();
    }
    
    @Override
    public void close(){
        sessionFactory.getCurrentSession().close();
    }
    
    @Override
    public void rollback(){
        sessionFactory.getCurrentSession().getTransaction().rollback();
    }
    
    @Override
    public void create(SerieCorrelativo serieCorrelativo) {
            sessionFactory.getCurrentSession().save(serieCorrelativo);
    }

    @Override
    public SerieCorrelativo read(SerieCorrelativoId serieCorrelativoId) {
        SerieCorrelativo serieCorrelativo=(SerieCorrelativo)sessionFactory.getCurrentSession().get(SerieCorrelativo.class,serieCorrelativoId);
        return serieCorrelativo;
    }
    
    @Override
    public SerieCorrelativo readNextSerieCorrelativo(String serieId, Date fechaTrabajo){        
        List<SerieCorrelativo> list =sessionFactory.openSession().doReturningWork(new ReturningWork<List<SerieCorrelativo>>(){
                @Override
                public List<SerieCorrelativo> execute(Connection connection)throws SQLException {
                    CallableStatement statement =null;
                    List<SerieCorrelativo> serieCorrelativoList =new ArrayList<SerieCorrelativo>();
                    String sqlString ="{call uspGetSerieCorrelativo(?,?)}";
                    statement = connection.prepareCall(sqlString);
                    statement.setString(1, serieId);
                    statement.setTimestamp(2, new java.sql.Timestamp(fechaTrabajo.getTime()));
                    ResultSet resultSet = statement.executeQuery();
                    while(resultSet.next()){
                        SerieCorrelativo serieCorrelativo =new SerieCorrelativo();
                        SerieCorrelativoId id =new SerieCorrelativoId(resultSet.getString("IdSerie"),resultSet.getString("IdCorrelativo"));
                        serieCorrelativo.setId(id);
                        serieCorrelativo.setInicioPeriodo(resultSet.getTimestamp("InicioPeriodo"));
                        serieCorrelativo.setUltimoUsado(resultSet.getString("UltimoUsado"));
                        serieCorrelativo.setUsuarioIngresa(resultSet.getString("UsuarioIngresa"));
                        serieCorrelativo.setFechaIngreso(resultSet.getTimestamp("FechaIngreso"));
                        serieCorrelativo.setUsuarioModifica(resultSet.getString("UsuarioModifica"));
                        serieCorrelativo.setFechaModificacion(resultSet.getTimestamp("FechaModificacion"));
                        serieCorrelativoList.add(serieCorrelativo);
 
                    }
                    return serieCorrelativoList;
                }
        });
        if(list!=null)
            return list.get(0);
        else
            return null;
    }
    

    @Override
    public void update(SerieCorrelativo serieCorrelativo) {
        sessionFactory.getCurrentSession().update(serieCorrelativo);
    }

    @Override
    public void delete(SerieCorrelativo serieCorrelativo) {
        sessionFactory.getCurrentSession().delete(serieCorrelativo);
    }

    @Override
    public List<SerieCorrelativo> getAllSerieCorrelativo() {
        Query query=sessionFactory.getCurrentSession()
                                .createQuery("select "
                                                    +"idProducto, " 
                                                    +"nombre, " 
                                                    +"descripcion "  
                                            +"from    Producto p" );

        List<SerieCorrelativo> serieCorrelativos=new ArrayList<>();
        /*Realiza consulta y devuelve Object[]*/
        List<Object[]> list=query.list();
        /*Itera en cada fila*/
        list.stream().forEach((serieCorrelativo)->{
            SerieCorrelativo serieCorrela=new SerieCorrelativo();
            serieCorrela.setId((SerieCorrelativoId)serieCorrelativo[0]);
            serieCorrela.setUltimoUsado(serieCorrelativo[1].toString());
            serieCorrelativos.add(serieCorrela);
        });
        return serieCorrelativos;
    }
    
}
