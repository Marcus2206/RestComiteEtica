/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comiteetica.hibernate.service;

import com.comiteetica.hibernate.model.InvestigacionSede;
import com.comiteetica.hibernate.model.InvestigacionSedeId;
import com.comiteetica.persistencia.BussinessException;
import java.util.List;

/**
 *
 * @author rasec
 */
public interface InvestigacionSedeService {
    public void create(InvestigacionSede investigacionSede) throws BussinessException;
    public InvestigacionSede read(InvestigacionSedeId investigacionSedeId) throws BussinessException;
    public void update(InvestigacionSede investigacionSede) throws BussinessException;
    public void delete(InvestigacionSede investigacionSede) throws BussinessException;
    public List<InvestigacionSede> getAllInvestigacionSede() throws BussinessException;
}
