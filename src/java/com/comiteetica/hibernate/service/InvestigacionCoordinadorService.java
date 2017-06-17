/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comiteetica.hibernate.service;

import com.comiteetica.hibernate.model.InvestigacionCoordinador;
import com.comiteetica.hibernate.model.InvestigacionCoordinadorId;
import com.comiteetica.persistencia.BussinessException;
import java.util.List;

/**
 *
 * @author rasec
 */
public interface InvestigacionCoordinadorService {
    public void create(InvestigacionCoordinador investigacionCoordinador) throws BussinessException;
    public InvestigacionCoordinador read(InvestigacionCoordinadorId investigacionCoordinadorId) throws BussinessException;
    public void update(InvestigacionCoordinador investigacionCoordinador) throws BussinessException;
    public void delete(InvestigacionCoordinador investigacionCoordinador)throws BussinessException;
    public List<InvestigacionCoordinador> getAllInvestigacionCoordinador() throws BussinessException;
}
