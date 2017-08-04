/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comiteetica.hibernate.service.impl;

import com.comiteetica.hibernate.dao.PatrocinadorCroDao;
import com.comiteetica.hibernate.model.PatrocinadorCro;
import com.comiteetica.hibernate.model.PatrocinadorCroId;
import com.comiteetica.hibernate.service.PatrocinadorCroService;
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
public class PatrocinadorCroServiceImpl implements PatrocinadorCroService {

    @Autowired
    private PatrocinadorCroDao patrocinadorCroDao;

    @Transactional
    @Override
    public void beginTransaction() throws BussinessException {
        patrocinadorCroDao.beginTransaction();
    }

    @Transactional
    @Override
    public void commit() throws BussinessException {
        patrocinadorCroDao.commit();
    }

    @Transactional
    @Override
    public void close() throws BussinessException {
        patrocinadorCroDao.close();
    }

    @Transactional
    @Override
    public void rollback() throws BussinessException {
        patrocinadorCroDao.rollback();
    }

    @Transactional
    @Override
    public void create(PatrocinadorCro patrocinadorCro) throws BussinessException {
        patrocinadorCroDao.create(patrocinadorCro);
    }

    @Transactional
    @Override
    public PatrocinadorCro read(PatrocinadorCroId id) throws BussinessException {
        return patrocinadorCroDao.read(id);
    }

    @Transactional
    @Override
    public void update(PatrocinadorCro patrocinadorCro) throws BussinessException {
        patrocinadorCroDao.update(patrocinadorCro);
    }

    @Transactional
    @Override
    public void delete(PatrocinadorCro patrocinadorCro) throws BussinessException {
        patrocinadorCroDao.delete(patrocinadorCro);
    }

    @Transactional
    @Override
    public List<Object> getAllPatrocinadorCroSqlList() throws BussinessException {
        return patrocinadorCroDao.getAllPatrocinadorCroSqlList();
    }

    @Transactional
    @Override
    public List<PatrocinadorCro> getAllPatrocinadorCro() throws BussinessException {
        return patrocinadorCroDao.getAllPatrocinadorCro();
    }

}
