/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comiteetica.hibernate.service;

import com.comiteetica.hibernate.model.InvestigacionMonitor;
import com.comiteetica.hibernate.model.InvestigacionMonitorId;
import com.comiteetica.persistencia.BussinessException;
import java.util.List;

/**
 *
 * @author rasec
 */
public interface InvestigacionMonitorService {
    public void create(InvestigacionMonitor investigacionMonitor) throws BussinessException;
    public InvestigacionMonitor read(InvestigacionMonitorId investigacionMonitorId) throws BussinessException;
    public void update(InvestigacionMonitor investigacionMonitor) throws BussinessException;
    public void delete(InvestigacionMonitor investigacionMonitor) throws BussinessException;
    public List<InvestigacionMonitor> getAllInvestigacionMonitor() throws BussinessException;
}
