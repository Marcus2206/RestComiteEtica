/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comiteetica.hibernate.service.impl;

import com.comiteetica.hibernate.dao.ProvinciaDao;
import com.comiteetica.hibernate.model.Provincia;
import com.comiteetica.hibernate.service.ProvinciaService;
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
public class ProvinciaServiceImpl implements ProvinciaService{
    
    @Autowired
    private ProvinciaDao provinciaDao;
    
    @Transactional
    @Override
    public void beginTransaction() throws BussinessException{
        provinciaDao.beginTransaction();
    }
    
    @Transactional
    @Override
    public void commit() throws BussinessException{
        provinciaDao.commit();
    }
    
    @Transactional
    @Override
    public void close() throws BussinessException{
        provinciaDao.close();
    }
    
    @Transactional
    @Override
    public void rollback() throws BussinessException{
        provinciaDao.rollback();
    }

    @Transactional
    @Override
    public List<Provincia> getAllProvinciaByDepartamento(String idDepartamento) throws BussinessException {
        return provinciaDao.getAllProvinciaByDepartamento(idDepartamento);
    }
    
}
