/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comiteetica.hibernate.service;

import com.comiteetica.hibernate.model.Investigacion;
import com.comiteetica.persistencia.BussinessException;
import java.util.List;

/**
 *
 * @author rasec
 */
public interface InvestigacionService {
    public void create(Investigacion investigacion) throws BussinessException;
    public Investigacion read(String idInvestigacion) throws BussinessException;
    public void update(Investigacion investigacion) throws BussinessException;
    public void delete(Investigacion investigacion) throws BussinessException;
    public List<Investigacion> getAllInvestigacion() throws BussinessException;
}
