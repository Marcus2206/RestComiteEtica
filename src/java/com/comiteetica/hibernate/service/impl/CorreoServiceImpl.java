/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comiteetica.hibernate.service.impl;

import com.comiteetica.hibernate.dao.CorreoDao;
import com.comiteetica.hibernate.model.Correo;
import com.comiteetica.hibernate.service.CorreoService;
import com.comiteetica.persistencia.BussinessException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author rasec
 */
public class CorreoServiceImpl implements CorreoService {

    @Autowired
    private CorreoDao correoDao;

    @Override
    @Transactional
    public void create(Correo correo) throws BussinessException {
        correoDao.create(correo);
    }

    @Override
    @Transactional
    public Correo read(int idCorreo) throws BussinessException {
        return correoDao.read(idCorreo);
    }

    @Override
    @Transactional
    public void update(Correo correo) throws BussinessException {
        correoDao.update(correo);
    }

    @Override
    @Transactional
    public void delete(Correo correo) throws BussinessException {
        correoDao.delete(correo);
    }

    @Override
    @Transactional
    public List<Correo> getAllCorreo() throws BussinessException {
        return correoDao.getAllCorreo();
    }

    @Override
    @Transactional
    public void beginTransaction() throws BussinessException {
        correoDao.beginTransaction();
    }

    @Override
    @Transactional
    public void commit() throws BussinessException {
        correoDao.commit();
    }

    @Override
    @Transactional
    public void close() throws BussinessException {
        correoDao.close();
    }

    @Override
    @Transactional
    public void rollback() throws BussinessException {
        correoDao.rollback();
    }

    @Override
    @Transactional
    public List<Object> getAllCorreoList() throws BussinessException {
        return correoDao.getAllCorreoList();
    }

}
