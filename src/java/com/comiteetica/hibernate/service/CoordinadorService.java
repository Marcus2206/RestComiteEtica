/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comiteetica.hibernate.service;

import com.comiteetica.hibernate.model.Coordinador;
import com.comiteetica.persistencia.BussinessException;
import java.util.List;

/**
 *
 * @author rasec
 */
public interface CoordinadorService {
    public void create(Coordinador coordinador) throws BussinessException;
    public Coordinador read(String idCoordinador) throws BussinessException;
    public void update(Coordinador coordinador) throws BussinessException;
    public void delete(Coordinador coordinador) throws BussinessException;
    public List<Coordinador> getAllCoordinador() throws BussinessException;
    public void beginTransaction() throws BussinessException;
    public void commit() throws BussinessException;
    public void close() throws BussinessException;
    public void rollback() throws BussinessException;
    public List<Coordinador> getCoordinadorSinIdInvestigacion(String idInvestigacion)  throws BussinessException;
}
