/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comiteetica.hibernate.service.impl;

import com.comiteetica.hibernate.dao.InvestigacionMonitorDao;
import com.comiteetica.hibernate.model.InvestigacionMonitor;
import com.comiteetica.hibernate.model.InvestigacionMonitorId;
import com.comiteetica.hibernate.service.InvestigacionMonitorService;
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
public class InvestigacionMonitorServiceImpl implements InvestigacionMonitorService{
    
    @Autowired
    private InvestigacionMonitorDao investigacionMonitorDao;

    @Transactional
    @Override
    public void create(InvestigacionMonitor investigacionMonitor) throws BussinessException {
        investigacionMonitorDao.create(investigacionMonitor);
    }

    @Transactional
    @Override
    public InvestigacionMonitor read(InvestigacionMonitorId investigacionMonitorId) throws BussinessException {
        return investigacionMonitorDao.read(investigacionMonitorId);
    }

    @Transactional
    @Override
    public void update(InvestigacionMonitor investigacionMonitor) throws BussinessException {
        investigacionMonitorDao.update(investigacionMonitor);
    }

    @Transactional
    @Override
    public void delete(InvestigacionMonitor investigacionMonitor) throws BussinessException {
        investigacionMonitorDao.delete(investigacionMonitor);
    }

    @Transactional
    @Override
    public List<InvestigacionMonitor> getAllInvestigacionMonitor() throws BussinessException {
        return investigacionMonitorDao.getAllInvestigacionMonitor();
    }
}
