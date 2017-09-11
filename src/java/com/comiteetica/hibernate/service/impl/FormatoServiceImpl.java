/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comiteetica.hibernate.service.impl;

import com.comiteetica.hibernate.dao.FormatoDao;
import com.comiteetica.hibernate.model.Formato;
import com.comiteetica.hibernate.service.FormatoService;
import com.comiteetica.persistencia.BussinessException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author rasec
 */
public class FormatoServiceImpl implements FormatoService {

    @Autowired
    private FormatoDao formatoDao;

    @Override
    public void create(Formato formato) throws BussinessException {
        formatoDao.create(formato);
    }

    @Override
    public Formato read(int idFormato) throws BussinessException {
        return formatoDao.read(idFormato);
    }

    @Override
    public void update(Formato formato) throws BussinessException {
        formatoDao.update(formato);
    }

    @Override
    public void delete(Formato formato) throws BussinessException {
        formatoDao.delete(formato);
    }

    @Override
    public List<Formato> getAllFormato() throws BussinessException {
        return formatoDao.getAllFormato();
    }

    @Override
    public void beginTransaction() throws BussinessException {
        formatoDao.beginTransaction();
    }

    @Override
    public void commit() throws BussinessException {
        formatoDao.commit();
    }

    @Override
    public void close() throws BussinessException {
        formatoDao.close();
    }

    @Override
    public void rollback() throws BussinessException {
        formatoDao.rollback();
    }

    @Override
    public List<Object> getAllFormatoList() throws BussinessException {
        return formatoDao.getAllFormatoList();
    }

}
