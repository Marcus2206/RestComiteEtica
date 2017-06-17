/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comiteetica.hibernate.service;

import com.comiteetica.hibernate.model.Monitor;
import com.comiteetica.persistencia.BussinessException;
import java.util.List;

/**
 *
 * @author rasec
 */
public interface MonitorService {
    public void create(Monitor monitor) throws BussinessException ;
    public Monitor read(String idMonitor) throws BussinessException ;
    public void update(Monitor monitor) throws BussinessException ;
    public void delete(Monitor monitor) throws BussinessException ;
    public List<Monitor> getAllMonitor() throws BussinessException ;
}
