/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comiteetica.hibernate.dao;

import com.comiteetica.hibernate.model.InvestigacionCoordinador;
import com.comiteetica.hibernate.model.InvestigacionCoordinadorId;
import java.util.List;

/**
 *
 * @author rasec
 */
public interface InvestigacionCoordinadorDao {
    public void create(InvestigacionCoordinador investigacionCoordinador);
    public InvestigacionCoordinador read(InvestigacionCoordinadorId investigacionCoordinadorId);
    public void update(InvestigacionCoordinador investigacionCoordinador);
    public void delete(InvestigacionCoordinador investigacionCoordinador);
    public List<InvestigacionCoordinador> getAllInvestigacionCoordinador();
    public void beginTransaction();
    public void commit();
    public void close();
    public void rollback();
    public List<Object> getInvestigacionCoordinadorByIdInvestigacion(String idInvestigacion);
}
