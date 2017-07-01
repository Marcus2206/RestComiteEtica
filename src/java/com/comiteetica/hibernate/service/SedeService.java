/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comiteetica.hibernate.service;

import com.comiteetica.hibernate.model.Sede;
import com.comiteetica.persistencia.BussinessException;
import java.util.List;

/**
 *
 * @author rasec
 */
public interface SedeService {
    public void create(Sede sede) throws BussinessException;
    public Sede read(String idSede) throws BussinessException;
    public void update(Sede sede) throws BussinessException;
    public void delete(Sede sede) throws BussinessException;
    public List<Sede> getAllSede() throws BussinessException;
    public void beginTransaction() throws BussinessException;
    public void commit() throws BussinessException;
    public void close() throws BussinessException;
    public void rollback() throws BussinessException;
}
