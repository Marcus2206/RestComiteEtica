/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comiteetica.hibernate.service.impl;

import com.comiteetica.hibernate.dao.InvestigacionInvestigadorDao;
import com.comiteetica.hibernate.model.InvestigacionInvestigador;
import com.comiteetica.hibernate.model.InvestigacionInvestigadorId;
import com.comiteetica.hibernate.service.InvestigacionInvestigadorService;
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
public class InvestigacionInvestigadorServiceImpl implements InvestigacionInvestigadorService{

    @Autowired
    private InvestigacionInvestigadorDao investigacionInvestigadorDao;
    
    @Transactional
    @Override
    public void beginTransaction() throws BussinessException{
        investigacionInvestigadorDao.beginTransaction();
    }
    
    @Transactional
    @Override
    public void commit() throws BussinessException{
        investigacionInvestigadorDao.commit();
    }
    
    @Transactional
    @Override
    public void close() throws BussinessException{
        investigacionInvestigadorDao.close();
    }
    
    @Transactional
    @Override
    public void rollback() throws BussinessException{
        investigacionInvestigadorDao.rollback();
    }
    
    @Transactional
    @Override
    public void create(InvestigacionInvestigador investigacionInvestigador) throws BussinessException {
        investigacionInvestigadorDao.create(investigacionInvestigador);
    }

    @Transactional
    @Override
    public InvestigacionInvestigador read(InvestigacionInvestigadorId investigacionInvestigadorId) throws BussinessException {
        return investigacionInvestigadorDao.read(investigacionInvestigadorId);
    }

    @Transactional
    @Override
    public void update(InvestigacionInvestigador investigacionInvestigador) throws BussinessException {
        investigacionInvestigadorDao.update(investigacionInvestigador);
    }

    @Transactional
    @Override
    public void delete(InvestigacionInvestigador investigacionInvestigador) throws BussinessException {
        investigacionInvestigadorDao.delete(investigacionInvestigador);
    }

    @Transactional
    @Override
    public List<InvestigacionInvestigador> getAllInvestigacionInvestigador() throws BussinessException {
        return investigacionInvestigadorDao.getAllInvestigacionInvestigador();
    }
    
    @Transactional
    @Override
    public List<Object> getInvestigacionInvestigadorByIdInvestigacion(String idInvestigacion){
        return investigacionInvestigadorDao.getInvestigacionInvestigadorByIdInvestigacion(idInvestigacion);
    } 
    
}
