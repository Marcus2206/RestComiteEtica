/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comiteetica.hibernate.service.impl;

import com.comiteetica.hibernate.dao.InvestigacionCoordinadorDao;
import com.comiteetica.hibernate.model.InvestigacionCoordinador;
import com.comiteetica.hibernate.model.InvestigacionCoordinadorId;
import com.comiteetica.hibernate.service.InvestigacionCoordinadorService;
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
public class InvestigacionCoordinadorServiceImpl implements InvestigacionCoordinadorService{

    @Autowired
    private InvestigacionCoordinadorDao investigacionCoordinadorDao;
    
    @Transactional
    @Override
    public void beginTransaction() throws BussinessException{
        investigacionCoordinadorDao.beginTransaction();
    }
    
    @Transactional
    @Override
    public void commit() throws BussinessException{
        investigacionCoordinadorDao.commit();
    }
    
    @Transactional
    @Override
    public void close() throws BussinessException{
        investigacionCoordinadorDao.close();
    }
    
    @Transactional
    @Override
    public void rollback() throws BussinessException{
        investigacionCoordinadorDao.rollback();
    }
    
    @Transactional
    @Override
    public void create(InvestigacionCoordinador investigacionCoordinador) throws BussinessException {
        investigacionCoordinadorDao.create(investigacionCoordinador);
    }

    @Transactional
    @Override
    public InvestigacionCoordinador read(InvestigacionCoordinadorId investigacionCoordinadorId) throws BussinessException {
        return investigacionCoordinadorDao.read(investigacionCoordinadorId);
    }

    @Transactional
    @Override
    public void update(InvestigacionCoordinador investigacionCoordinador) throws BussinessException {
        investigacionCoordinadorDao.update(investigacionCoordinador);
    }

    @Transactional
    @Override
    public void delete(InvestigacionCoordinador investigacionCoordinador) throws BussinessException {
        investigacionCoordinadorDao.delete(investigacionCoordinador);
    }

    @Transactional
    @Override
    public List<InvestigacionCoordinador> getAllInvestigacionCoordinador() throws BussinessException {
        return investigacionCoordinadorDao.getAllInvestigacionCoordinador();
    }
    
    @Transactional
    @Override
    public List<Object> getInvestigacionCoordinadorByIdInvestigacion(String idInvestigacion){
        return investigacionCoordinadorDao.getInvestigacionCoordinadorByIdInvestigacion(idInvestigacion);
    } 
}
