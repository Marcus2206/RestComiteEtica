/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comiteetica.hibernate.service.impl;

import com.comiteetica.hibernate.dao.CroDao;
import com.comiteetica.hibernate.model.Cro;
import com.comiteetica.hibernate.service.CroService;
import com.comiteetica.persistencia.BussinessException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author rasec
 */
public class CroServiceImpl implements CroService{

    @Autowired
    private CroDao croDao;
    
    @Transactional
    @Override
    public void create(Cro cro) throws BussinessException {
        croDao.create(cro);
    }

    @Transactional
    @Override
    public Cro read(String idCro) throws BussinessException {
        return croDao.read(idCro);
    }

    @Transactional
    @Override
    public void update(Cro cro) throws BussinessException {
        croDao.update(cro);
    }

    @Transactional
    @Override
    public void delete(Cro cro) throws BussinessException {
        croDao.delete(cro);
    }

    @Transactional
    @Override
    public List<Cro> getAllCro() throws BussinessException {
        return croDao.getAllCro();
    }
    
}
