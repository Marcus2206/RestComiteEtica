/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comiteetica.hibernate.service.impl;

import com.comiteetica.hibernate.dao.DistritoDao;
import com.comiteetica.hibernate.model.Distrito;
import com.comiteetica.hibernate.service.DistritoService;
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
public class DistritoServiceImpl implements DistritoService{
    
    @Autowired
    private DistritoDao distritoDao;
    
    @Transactional
    @Override
    public void beginTransaction() throws BussinessException{
        distritoDao.beginTransaction();
    }
    
    @Transactional
    @Override
    public void commit() throws BussinessException{
        distritoDao.commit();
    }
    
    @Transactional
    @Override
    public void close() throws BussinessException{
        distritoDao.close();
    }
    
    @Transactional
    @Override
    public void rollback() throws BussinessException{
        distritoDao.rollback();
    }

    @Transactional
    @Override
    public List<Distrito> getAllDistritoByDepartamentoProvincia(String idDepartamento,String idProvincia) throws BussinessException {
        return distritoDao.getAllDistritoByDepartamentoProvincia(idDepartamento,idProvincia);
    }
}
