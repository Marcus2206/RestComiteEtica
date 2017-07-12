/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comiteetica.hibernate.service.impl;

import com.comiteetica.hibernate.dao.MonitorDao;
import com.comiteetica.hibernate.model.Monitor;
import com.comiteetica.hibernate.service.MonitorService;
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
public class MonitorServiceImpl implements MonitorService{

    @Autowired
    private MonitorDao monitorDao;
    
    @Transactional
    @Override
    public void beginTransaction() throws BussinessException{
        monitorDao.beginTransaction();
    }
    
    @Transactional
    @Override
    public void commit() throws BussinessException{
        monitorDao.commit();
    }
    
    @Transactional
    @Override
    public void close() throws BussinessException{
        monitorDao.close();
    }
    
    @Transactional
    @Override
    public void rollback() throws BussinessException{
        monitorDao.rollback();
    }
    
    @Transactional
    @Override
    public void create(Monitor monitor) throws BussinessException {
        monitorDao.create(monitor);
    }

    @Transactional
    @Override
    public Monitor read(String idMonitor) throws BussinessException {
        return monitorDao.read(idMonitor);
    }

    @Transactional
    @Override
    public void update(Monitor monitor) throws BussinessException {
        monitorDao.update(monitor);
    }

    @Transactional
    @Override
    public void delete(Monitor monitor) throws BussinessException {
        monitorDao.delete(monitor);
    }

    @Transactional
    @Override
    public List<Monitor> getAllMonitor() throws BussinessException {
        return monitorDao.getAllMonitor();
    }
    
}
