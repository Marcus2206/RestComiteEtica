/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comiteetica.hibernate.service.impl;

import com.comiteetica.hibernate.dao.DepartamentoDao;
import com.comiteetica.hibernate.model.Departamento;
import com.comiteetica.hibernate.service.DepartamentoService;
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
public class DepartamentoServiceImpl implements DepartamentoService{
    
    @Autowired
    private DepartamentoDao departamentoDao;
    
    @Transactional
    @Override
    public void beginTransaction() throws BussinessException{
        departamentoDao.beginTransaction();
    }
    
    @Transactional
    @Override
    public void commit() throws BussinessException{
        departamentoDao.commit();
    }
    
    @Transactional
    @Override
    public void close() throws BussinessException{
        departamentoDao.close();
    }
    
    @Transactional
    @Override
    public void rollback() throws BussinessException{
        departamentoDao.rollback();
    }
    
    @Transactional
    @Override
    public void create(Departamento departamento) throws BussinessException {
        departamentoDao.create(departamento);
    }

    @Transactional
    @Override
    public Departamento read(String idDepartamento) throws BussinessException {
        return departamentoDao.read(idDepartamento);
    }

    @Transactional
    @Override
    public void update(Departamento departamento) throws BussinessException {
        departamentoDao.update(departamento);
    }

    @Transactional
    @Override
    public void delete(Departamento departamento) throws BussinessException {
        departamentoDao.delete(departamento);
    }

    @Transactional
    @Override
    public List<Departamento> getAllDepartamento() throws BussinessException {
        return departamentoDao.getAllDepartamento();
    }
    
}
