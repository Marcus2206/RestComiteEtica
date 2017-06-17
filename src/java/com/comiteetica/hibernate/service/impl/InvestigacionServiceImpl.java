/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comiteetica.hibernate.service.impl;

import com.comiteetica.hibernate.dao.InvestigacionDao;
import com.comiteetica.hibernate.model.Investigacion;
import com.comiteetica.hibernate.service.InvestigacionService;
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
public class InvestigacionServiceImpl implements InvestigacionService{

    @Autowired
    private InvestigacionDao investigacionDao;
    
    @Transactional
    @Override
    public void create(Investigacion investigacion) throws BussinessException {
        investigacionDao.create(investigacion);
    }

    @Transactional
    @Override
    public Investigacion read(String idInvestigacion) throws BussinessException {
        return investigacionDao.read(idInvestigacion);
    }

    @Transactional
    @Override
    public void update(Investigacion investigacion) throws BussinessException {
        investigacionDao.update(investigacion);
    }

    @Transactional
    @Override
    public void delete(Investigacion investigacion) throws BussinessException {
        investigacionDao.delete(investigacion);
    }

    @Transactional
    @Override
    public List<Investigacion> getAllInvestigacion() throws BussinessException {
        return investigacionDao.getAllInvestigacion();
    }
    
}
