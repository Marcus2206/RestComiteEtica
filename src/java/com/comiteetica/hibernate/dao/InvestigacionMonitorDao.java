/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comiteetica.hibernate.dao;

import com.comiteetica.hibernate.model.InvestigacionMonitor;
import com.comiteetica.hibernate.model.InvestigacionMonitorId;
import java.util.List;

/**
 *
 * @author rasec
 */
public interface InvestigacionMonitorDao {
    public void create(InvestigacionMonitor investigacionMonitor);
    public InvestigacionMonitor read(InvestigacionMonitorId investigacionMonitorId);
    public void update(InvestigacionMonitor investigacionMonitor);
    public void delete(InvestigacionMonitor investigacionMonitor);
    public List<InvestigacionMonitor> getAllInvestigacionMonitor();
}
