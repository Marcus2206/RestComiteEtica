/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comiteetica.hibernate.service;

import com.comiteetica.hibernate.model.Investigador;
import com.comiteetica.persistencia.BussinessException;
import java.util.List;

/**
 *
 * @author rasec
 */
public interface InvestigadorService {
    public void create(Investigador investigador) throws BussinessException ;
    public Investigador read(String idInvestigador) throws BussinessException ;
    public void update(Investigador investigador) throws BussinessException ;
    public void delete(Investigador investigador)throws BussinessException ;
    public List<Investigador> getAllInvestigador()throws BussinessException ;
    public void beginTransaction() throws BussinessException;
    public void commit() throws BussinessException;
    public void close() throws BussinessException;
    public void rollback() throws BussinessException;
    public List<Investigador> getInvestigadorSinIdInvestigacion(String idInvestigacion) throws BussinessException;
}
