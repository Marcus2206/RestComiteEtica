/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comiteetica.hibernate.dao;

import com.comiteetica.hibernate.model.InvestigacionSede;
import com.comiteetica.hibernate.model.InvestigacionSedeId;
import java.util.List;

/**
 *
 * @author rasec
 */
public interface InvestigacionSedeDao {
    public void create(InvestigacionSede investigacionSede);
    public InvestigacionSede read(InvestigacionSedeId investigacionSedeId);
    public void update(InvestigacionSede investigacionSede);
    public void delete(InvestigacionSede investigacionSede);
    public List<InvestigacionSede> getAllInvestigacionSede();
    public void beginTransaction();
    public void commit();
    public void close();
    public void rollback();
    public List<Object> getInvestigacionSedeByIdInvestigacion(String idInvestigacion);
}
