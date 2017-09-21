/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comiteetica.hibernate.service.impl;

import com.comiteetica.hibernate.dao.CorrespondenciaDao;
import com.comiteetica.hibernate.model.Correspondencia;
import com.comiteetica.hibernate.service.CorrespondenciaService;
import com.comiteetica.persistencia.BussinessException;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author rasec
 */
@Service
public class CorrespondenciaServiceImpl implements CorrespondenciaService {

    @Autowired
    private CorrespondenciaDao correspondenciaDao;

    @Transactional
    @Override
    public void beginTransaction() throws BussinessException {
        correspondenciaDao.beginTransaction();
    }

    @Transactional
    @Override
    public void commit() throws BussinessException {
        correspondenciaDao.commit();
    }

    @Transactional
    @Override
    public void close() throws BussinessException {
        correspondenciaDao.close();
    }

    @Transactional
    @Override
    public void rollback() throws BussinessException {
        correspondenciaDao.rollback();
    }

    @Transactional
    @Override
    public void create(Correspondencia correspondencia) throws BussinessException {
        correspondenciaDao.create(correspondencia);
    }

    @Transactional
    @Override
    public Correspondencia read(String idCorrespondencia) throws BussinessException {
        return correspondenciaDao.read(idCorrespondencia);
    }

    @Transactional
    @Override
    public void update(Correspondencia correspondencia) throws BussinessException {
        correspondenciaDao.update(correspondencia);
    }

    @Transactional
    @Override
    public void delete(Correspondencia correspondencia) throws BussinessException {
        correspondenciaDao.delete(correspondencia);
    }

    @Transactional
    @Override
    public List<Correspondencia> getAllCorrespondencia() throws BussinessException {
        return correspondenciaDao.getAllCorrespondencia();
    }

    @Transactional
    @Override
    public List<Object> getAllCorrespondenciaList() throws BussinessException {
        return correspondenciaDao.getAllCorrespondenciaList();
    }

    @Transactional
    @Override
    public List<Object> getDatosHojaRuta(String idCorrespondencia) throws BussinessException {
        return correspondenciaDao.getDatosHojaRuta(idCorrespondencia);
    }

    @Transactional
    @Override
    public List<Correspondencia> readByFechaSesion(Date fechaSesion) throws BussinessException {
        return correspondenciaDao.readByFechaSesion(fechaSesion);
    }
}
