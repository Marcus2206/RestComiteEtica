/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comiteetica.hibernate.service.impl;

import com.comiteetica.hibernate.dao.SedeDao;
import com.comiteetica.hibernate.model.Sede;
import com.comiteetica.hibernate.service.SedeService;
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
public class SedeServiceImpl implements SedeService{

    @Autowired
    private SedeDao sedeDao;
    
    @Transactional
    @Override
    public void beginTransaction() throws BussinessException{
        sedeDao.beginTransaction();
    }
    
    @Transactional
    @Override
    public void commit() throws BussinessException{
        sedeDao.commit();
    }
    
    @Transactional
    @Override
    public void close() throws BussinessException{
        sedeDao.close();
    }
    
    @Transactional
    @Override
    public void rollback() throws BussinessException{
        sedeDao.rollback();
    }
    
    @Transactional
    @Override
    public void create(Sede sede) throws BussinessException {
        sedeDao.create(sede);
    }

    @Transactional
    @Override
    public Sede read(String idSede) throws BussinessException {
        return sedeDao.read(idSede);
    }

    @Transactional
    @Override
    public void update(Sede sede) throws BussinessException {
        sedeDao.update(sede);
    }

    @Transactional
    @Override
    public void delete(Sede sede) throws BussinessException {
        sedeDao.delete(sede);
    }

    @Transactional
    @Override
    public List<Sede> getAllSede() throws BussinessException {
        return sedeDao.getAllSede();
    }
    
}
