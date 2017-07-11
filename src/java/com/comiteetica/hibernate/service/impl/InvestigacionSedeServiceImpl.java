/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comiteetica.hibernate.service.impl;

import com.comiteetica.hibernate.dao.InvestigacionSedeDao;
import com.comiteetica.hibernate.model.InvestigacionSede;
import com.comiteetica.hibernate.model.InvestigacionSedeId;
import com.comiteetica.hibernate.service.InvestigacionSedeService;
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
public class InvestigacionSedeServiceImpl implements InvestigacionSedeService{

    @Autowired
    private InvestigacionSedeDao investigacionSedeDao;
    
    @Transactional
    @Override
    public void beginTransaction() throws BussinessException{
        investigacionSedeDao.beginTransaction();
    }
    
    @Transactional
    @Override
    public void commit() throws BussinessException{
        investigacionSedeDao.commit();
    }
    
    @Transactional
    @Override
    public void close() throws BussinessException{
        investigacionSedeDao.close();
    }
    
    @Transactional
    @Override
    public void rollback() throws BussinessException{
        investigacionSedeDao.rollback();
    }
    
    @Transactional
    @Override
    public void create(InvestigacionSede investigacionSede) throws BussinessException {
        investigacionSedeDao.create(investigacionSede);
    }

    @Transactional
    @Override
    public InvestigacionSede read(InvestigacionSedeId investigacionSedeId) throws BussinessException {
        return investigacionSedeDao.read(investigacionSedeId);
    }

    @Transactional
    @Override
    public void update(InvestigacionSede investigacionSede) throws BussinessException {
        investigacionSedeDao.update(investigacionSede);
    }

    @Transactional
    @Override
    public void delete(InvestigacionSede investigacionSede) throws BussinessException {
        investigacionSedeDao.delete(investigacionSede);
    }

    @Transactional
    @Override
    public List<InvestigacionSede> getAllInvestigacionSede() throws BussinessException {
        return investigacionSedeDao.getAllInvestigacionSede();
    }
    
    @Transactional
    @Override
    public List<Object> getInvestigacionSedeByIdInvestigacion(String idInvestigacion) throws BussinessException {
        return investigacionSedeDao.getInvestigacionSedeByIdInvestigacion(idInvestigacion);
    }
    
}
