/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comiteetica.hibernate.dao.impl;

import com.comiteetica.hibernate.dao.SerieCorrelativoDao;
import com.comiteetica.hibernate.model.HibernateUtil;
import com.comiteetica.hibernate.model.SerieCorrelativo;
import com.comiteetica.hibernate.model.SerieCorrelativoId;
import java.sql.CallableStatement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.jdbc.Work;
import org.hibernate.jdbc.ReturningWork;
import org.springframework.stereotype.Repository;
import java.sql.Connection;
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

    @Override
    public void create(SerieCorrelativo serieCorrelativo) {
        
        SessionFactory sessionFactory=HibernateUtil.getSessionFactory();
        try{
            sessionFactory.getCurrentSession().beginTransaction();
            sessionFactory.getCurrentSession().save(serieCorrelativo);
            sessionFactory.getCurrentSession().getTransaction().commit();
        }catch(Exception e){
            sessionFactory.getCurrentSession().getTransaction().rollback();
        }finally{
            sessionFactory.getCurrentSession().close(); 
        }
        
    }

    @Override
    public SerieCorrelativo read(SerieCorrelativoId serieCorrelativoId) {
        SessionFactory sessionFactory=HibernateUtil.getSessionFactory();
        sessionFactory.openSession();
        sessionFactory.getCurrentSession().beginTransaction();
        SerieCorrelativo serieCorrelativo=(SerieCorrelativo)sessionFactory.getCurrentSession().get(SerieCorrelativo.class,serieCorrelativoId);
        sessionFactory.getCurrentSession().getTransaction().commit();
        sessionFactory.getCurrentSession().close();
        return serieCorrelativo;
    }
    
    @Override
    public SerieCorrelativo readNextSerieCorrelativo(String serieId, Date fechaTrabajo){
        System.out.println("readNextSerieCorrelativo");
        SessionFactory sessionFactory=HibernateUtil.getSessionFactory();
        System.out.println("SessionFactory sessionFactory");
        
        Session session=sessionFactory.openSession();
        //sessionFactory.getCurrentSession().beginTransaction();
        //System.out.println("sessionFactory.getCurrentSession().beginTransaction();");
        session.beginTransaction();
        System.out.println("session.beginTransaction();");
        /*
        Query query = sessionFactory.getCurrentSession().createSQLQuery(
                "CALL uspGetSerieCorrelativo(:serie,:fechaTrabajo)")
                .addEntity(SerieCorrelativo.class)
                .setParameter("serie", serieId)
                .setParameter("fechaTrabajo", fechaTrabajo);
        System.out.println("tam: "+query.list().size());
        */
        
        /*String queryString = "uspGetSerieCorrelativo '"+serieId+"','"+fechaTrabajo+"'";

        Query query = sessionFactory.getCurrentSession().createSQLQuery(queryString);
*/
        
        /*
        CallableStatement callableStatement = session.connection().prepareCall("call GetMarketDataCDS(?,?)");
        callableStatement.setString(1,"JPM");
        callableStatement.registerOutParameter(1, OracleTypes.CURSOR);
        callableStatement.execute();
        ResultSet resultSet=(ResultSet) callableStatement.getObject(1);*/
        
        List<SerieCorrelativo> list =session.doReturningWork(new ReturningWork<List<SerieCorrelativo>>(){
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
        //query.list();

        //List<SerieCorrelativo> result = query.list();
        /*for(int i=0; i<result.size(); i++){
                SerieCorrelativo serieCorrelativo = (SerieCorrelativo)result.get(i);
                System.out.println(stock.getStockCode());
        }*/
    
        session.getTransaction().commit();
        session.close();
        //sessionFactory.getCurrentSession().getTransaction().commit();
        //sessionFactory.getCurrentSession().close();
        return list.get(0);
    }
    

    @Override
    public void update(SerieCorrelativo serieCorrelativo) {
        SessionFactory sessionFactory=HibernateUtil.getSessionFactory();
        sessionFactory.getCurrentSession().beginTransaction();
        sessionFactory.getCurrentSession().update(serieCorrelativo);
        sessionFactory.getCurrentSession().getTransaction().commit();
        sessionFactory.getCurrentSession().close();
    }

    @Override
    public void delete(SerieCorrelativo serieCorrelativo) {
        SessionFactory sessionFactory=HibernateUtil.getSessionFactory();
        sessionFactory.getCurrentSession().beginTransaction();
        sessionFactory.getCurrentSession().delete(serieCorrelativo);
        sessionFactory.getCurrentSession().getTransaction().commit();
        sessionFactory.getCurrentSession().close();
    }

    @Override
    public List<SerieCorrelativo> getAllSerieCorrelativo() {
        SessionFactory sessionFactory=HibernateUtil.getSessionFactory();
        sessionFactory.getCurrentSession().beginTransaction();

        /*Fabrica Query*/
        Query query=sessionFactory.getCurrentSession()
                                .createQuery("select "
                                                    +"idProducto, " 
                                                    +"nombre, " 
                                                    +"descripcion "  
                                            +"from    Producto p" );
        //query.setFirstResult(ini);
        //query.setMaxResults(fin);
        /*Crea Objeto contenedor*/
        List<SerieCorrelativo> serieCorrelativoss=new ArrayList<>();
        /*Realiza consulta y devuelve Object[]*/
        List<Object[]> list=query.list();
        /*Itera en cada fila*/
        list.stream().forEach((serieCorrelativo)->{
            SerieCorrelativo serieCorrela=new SerieCorrelativo();
            serieCorrela.setId((SerieCorrelativoId)serieCorrelativo[0]);
            serieCorrela.setUltimoUsado(serieCorrelativo[1].toString());
            serieCorrelativoss.add(serieCorrela);
        });
        
        //System.out.println("terminó del createQuery"+productos.get(0).getDescripcion());
        sessionFactory.getCurrentSession().getTransaction().commit();
        sessionFactory.getCurrentSession().close();
        
        return serieCorrelativoss;
    }
    
}
