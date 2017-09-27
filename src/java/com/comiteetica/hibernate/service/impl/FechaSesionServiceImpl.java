/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comiteetica.hibernate.service.impl;

import com.comiteetica.hibernate.dao.FechaSesionDao;
import com.comiteetica.hibernate.model.FechaSesion;
import com.comiteetica.hibernate.service.FechaSesionService;
import com.comiteetica.persistencia.BussinessException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author rasec
 */
@Service
public class FechaSesionServiceImpl implements FechaSesionService {

    @Autowired
    private FechaSesionDao fechaSesionDao;

    @Transactional
    @Override
    public void create(FechaSesion fechaSesion) throws BussinessException {
        fechaSesionDao.create(fechaSesion);
    }

    @Transactional
    @Override
    public FechaSesion read(int idFechaSesion) throws BussinessException {
        return fechaSesionDao.read(idFechaSesion);
    }

    @Transactional
    @Override
    public void update(FechaSesion fechaSesion) throws BussinessException {
        fechaSesionDao.update(fechaSesion);
    }

    @Transactional
    @Override
    public void delete(FechaSesion fechaSesion) throws BussinessException {
        fechaSesionDao.delete(fechaSesion);
    }

    @Transactional
    @Override
    public List<FechaSesion> getAllFechaSesion() throws BussinessException {
        return fechaSesionDao.getAllFechaSesion();
    }

    @Transactional
    @Override
    public void beginTransaction() throws BussinessException {
        fechaSesionDao.beginTransaction();
    }

    @Transactional
    @Override
    public void commit() throws BussinessException {
        fechaSesionDao.commit();
    }

    @Transactional
    @Override
    public void close() throws BussinessException {
        fechaSesionDao.close();
    }

    @Transactional
    @Override
    public void rollback() throws BussinessException {
        fechaSesionDao.rollback();
    }

    @Transactional
    @Override
    public List<Object> getAllFechaSesionList() throws BussinessException {
        return fechaSesionDao.getAllFechaSesionList();
    }

    @Transactional
    @Override
    public List<Object> getAllFechaSesionProx() throws BussinessException {
        return fechaSesionDao.getAllFechaSesionProx();
    }
}
