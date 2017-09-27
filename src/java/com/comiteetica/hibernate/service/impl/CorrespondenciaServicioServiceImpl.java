/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comiteetica.hibernate.service.impl;

import com.comiteetica.hibernate.dao.CorrespondenciaServicioDao;
import com.comiteetica.hibernate.model.CorrespondenciaServicio;
import com.comiteetica.hibernate.model.CorrespondenciaServicioId;
import com.comiteetica.hibernate.service.CorrespondenciaServicioService;
import com.comiteetica.persistencia.BussinessException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author rasec
 */
public class CorrespondenciaServicioServiceImpl implements CorrespondenciaServicioService {

    @Autowired
    private CorrespondenciaServicioDao correspondenciaServicioDao;

    @Transactional
    @Override
    public void beginTransaction() throws BussinessException {
        correspondenciaServicioDao.beginTransaction();
    }

    @Transactional
    @Override
    public void commit() throws BussinessException {
        correspondenciaServicioDao.commit();
    }

    @Transactional
    @Override
    public void close() throws BussinessException {
        correspondenciaServicioDao.close();
    }

    @Transactional
    @Override
    public void rollback() throws BussinessException {
        correspondenciaServicioDao.rollback();
    }

    @Transactional
    @Override
    public void create(CorrespondenciaServicio correspondenciaServicio) throws BussinessException {
        correspondenciaServicioDao.create(correspondenciaServicio);
    }

    @Transactional
    @Override
    public CorrespondenciaServicio read(CorrespondenciaServicioId id) throws BussinessException {
        return correspondenciaServicioDao.read(id);
    }

    @Transactional
    @Override
    public List<CorrespondenciaServicio> readByIdCorrespondencia(String idCorrespondencia) throws BussinessException {
        return correspondenciaServicioDao.readByIdCorrespondencia(idCorrespondencia);
    }

    @Transactional
    @Override
    public void update(CorrespondenciaServicio correspondenciaServicio) throws BussinessException {
        correspondenciaServicioDao.update(correspondenciaServicio);
    }

    @Transactional
    @Override
    public void delete(CorrespondenciaServicio correspondenciaServicio) throws BussinessException {
        correspondenciaServicioDao.delete(correspondenciaServicio);
    }

    @Transactional
    @Override
    public List<CorrespondenciaServicio> getAllCorrespondenciaServicio() throws BussinessException {
        return correspondenciaServicioDao.getAllCorrespondenciaServicio();
    }

    @Transactional
    @Override
    public List<Object> getAllCorrespondenciaServicioSinPagoList() throws BussinessException {
        return correspondenciaServicioDao.getAllCorrespondenciaServicioSinPagoList();
    }

    @Transactional
    @Override
    public int getNextServicioDetalleByIdCorrespondencia(String idCorrespondencia) throws BussinessException {
        return correspondenciaServicioDao.getNextServicioDetalleByIdCorrespondencia(idCorrespondencia);
    }

    @Transactional
    @Override
    public List<Object> getAllCorrespondenciaServicioByCorrespondencia(String idCorrespondencia) throws BussinessException {
        return correspondenciaServicioDao.getAllCorrespondenciaServicioByCorrespondencia(idCorrespondencia);
    }
}
