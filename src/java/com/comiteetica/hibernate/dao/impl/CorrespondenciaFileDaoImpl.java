/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comiteetica.hibernate.dao.impl;

import com.comiteetica.hibernate.dao.CorrespondenciaFileDao;
import com.comiteetica.hibernate.model.CorrespondenciaFile;
import com.comiteetica.hibernate.model.CorrespondenciaFileId;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

/**
 *
 * @author rasec
 */
@Repository
public class CorrespondenciaFileDaoImpl implements CorrespondenciaFileDao {

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
    public void create(CorrespondenciaFile correspondenciaFile) {

        sessionFactory.getCurrentSession().save(correspondenciaFile);
    }

    @Override
    public CorrespondenciaFile read(CorrespondenciaFileId correspondenciaFileId) {
        CorrespondenciaFile correspondenciaFile = (CorrespondenciaFile) sessionFactory.getCurrentSession().get(CorrespondenciaFile.class, correspondenciaFileId);
        return correspondenciaFile;
    }

    @Override
    public void update(CorrespondenciaFile correspondenciaFile) {
        sessionFactory.getCurrentSession().update(correspondenciaFile);
    }

    @Override
    public void delete(CorrespondenciaFile correspondenciaFile) {
        sessionFactory.getCurrentSession().delete(correspondenciaFile);
    }

    @Override
    public List<CorrespondenciaFile> getAllCorrespondenciaFile() {
        /*Fabrica Query*/
        Query query = sessionFactory.getCurrentSession()
                .createQuery("select cf from CorrespondenciaFile cf");

        List<CorrespondenciaFile> correspondenciaFiles = query.list();
        return correspondenciaFiles;
    }

    @Override
    public int getNextFileDetalleByIdCorrespondencia(String idCorrespondencia) {
        /*Fabrica Query*/
        Query query = sessionFactory.getCurrentSession()
                .createQuery("select max(cf.id.fileDetalle)\n"
                        + "from CorrespondenciaFile cf\n"
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
    public List<CorrespondenciaFile> getAllCorrespondenciaFileByIdCorrepondencia(String idCorrespondencia) {
        /*Fabrica Query*/
        Query query = sessionFactory.getCurrentSession()
                .createQuery("select cf "
                        + "from CorrespondenciaFile cf "
                        + "where cf.id.idCorrespondencia=:idCorrespondencia")
                .setString("idCorrespondencia", idCorrespondencia);
        List<CorrespondenciaFile> correspondenciaFiles = query.list();
        return correspondenciaFiles;
    }

    @Override
    public void deleteAllCorrespondencia(String idCorrespondencia) {
        String sqlQuery = "delete from correspondenciaFile where idCorrespondencia=:idCorrespondencia";
        sessionFactory.getCurrentSession().createSQLQuery(sqlQuery).setString("idCorrespondencia", idCorrespondencia).executeUpdate();
    }

}
