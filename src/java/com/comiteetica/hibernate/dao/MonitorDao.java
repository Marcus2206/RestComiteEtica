/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comiteetica.hibernate.dao;

import com.comiteetica.hibernate.model.Monitor;
import java.util.List;

/**
 *
 * @author rasec
 */
public interface MonitorDao {
    public void create(Monitor monitor);
    public Monitor read(String idMonitor);
    public void update(Monitor monitor);
    public void delete(Monitor monitor);
    public List<Monitor> getAllMonitor();
}
