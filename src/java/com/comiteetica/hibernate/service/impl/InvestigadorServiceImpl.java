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
    public void create(Investigador investigador) throws BussinessException {
        investigadorDao.create(investigador);
    }

    @Override
    public Investigador read(String idInvestigador) throws BussinessException {
        return investigadorDao.read(idInvestigador);
    }

    @Override
    public void update(Investigador investigador) throws BussinessException {
        investigadorDao.update(investigador);
    }

    @Override
    public void delete(Investigador investigador) throws BussinessException {
        investigadorDao.delete(investigador);
    }

    @Override
    public List<Investigador> getAllInvestigador() throws BussinessException {
        return investigadorDao.getAllInvestigador();
    }
    
}
