/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comiteetica.hibernate.service.impl;

import com.comiteetica.hibernate.dao.InvestigadorDao;
import com.comiteetica.hibernate.model.Investigador;
import com.comiteetica.hibernate.service.InvestigadorService;
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
public class InvestigadorServiceImpl implements InvestigadorService{

    @Autowired
    private InvestigadorDao investigadorDao;
    
    @Transactional
    @Override
    public void beginTransaction() throws BussinessException{
        investigadorDao.beginTransaction();
    }
    
    @Transactional
    @Override
    public void commit() throws BussinessException{
        investigadorDao.commit();
    }
    
    @Transactional
    @Override
    public void close() throws BussinessException{
        investigadorDao.close();
    }
    
    @Transactional
    @Override
    public void rollback() throws BussinessException{
        investigadorDao.rollback();
    }
    
    @Transactional
    @Override
    public void create(Investigador investigador) throws BussinessException {
        investigadorDao.create(investigador);
    }

    @Transactional
    @Override
    public Investigador read(String idInvestigador) throws BussinessException {
        return investigadorDao.read(idInvestigador);
    }

    @Transactional
    @Override
    public void update(Investigador investigador) throws BussinessException {
        investigadorDao.update(investigador);
    }

    @Transactional
    @Override
    public void delete(Investigador investigador) throws BussinessException {
        investigadorDao.delete(investigador);
    }

    @Transactional
    @Override
    public List<Investigador> getAllInvestigador() throws BussinessException {
        return investigadorDao.getAllInvestigador();
    }
    
    @Transactional
    @Override
    public List<Investigador> getInvestigadorSinIdInvestigacion(String idInvestigacion){
        return investigadorDao.getInvestigadorSinIdInvestigacion(idInvestigacion);
    }
}
