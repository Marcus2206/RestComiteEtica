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
        //query.setFirstResult(ini);
        //query.setMaxResults(fin);
        /*Crea Objeto contenedor*/
//        List<CorrespondenciaFile> correspondenciaFiles = new ArrayList<>();
        /*Realiza consulta y devuelve Object[]*/
//        List<Object[]> list = query.list();
        List<CorrespondenciaFile> correspondenciaFiles = query.list();
        /*Itera en cada fila*/
//        list.stream().forEach((correspondencia) -> {
//            CorrespondenciaFile corrFile = new CorrespondenciaFile();
//            corrFile.setId((CorrespondenciaFileId) correspondencia[0]);
//            corrFile.setNombreArchivo(correspondencia[1].toString());
//            correspondenciaFiles.add(corrFile);
//        });

        //System.out.println("terminó del createQuery"+productos.get(0).getDescripcion());
        return correspondenciaFiles;
    }

    @Override
    public List<CorrespondenciaFile> getAllCorrespondenciaFileByIdCorrepondencia(String idCorrespondencia) {
        /*Fabrica Query*/
        Query query = sessionFactory.getCurrentSession()
                .createQuery("select cf "
                        + "from CorrespondenciaFile cf "
                        + "where cf.id.idCorrespondencia=':idCorrespondencia'")
                .setString("idCorrespondencia", idCorrespondencia);
        //query.setFirstResult(ini);
        //query.setMaxResults(fin);
        /*Crea Objeto contenedor*/
//        List<CorrespondenciaFile> correspondenciaFiles = new ArrayList<>();
        /*Realiza consulta y devuelve Object[]*/
//        List<Object[]> list = query.list();
        List<CorrespondenciaFile> correspondenciaFiles = query.list();
        /*Itera en cada fila*/
//        list.stream().forEach((correspondencia) -> {
//            CorrespondenciaFile corrFile = new CorrespondenciaFile();
//            corrFile.setId((CorrespondenciaFileId) correspondencia[0]);
//            corrFile.setNombreArchivo(correspondencia[1].toString());
//            correspondenciaFiles.add(corrFile);
//        });

        //System.out.println("terminó del createQuery"+productos.get(0).getDescripcion());
        return correspondenciaFiles;
    }

}
