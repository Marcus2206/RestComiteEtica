/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comiteetica.hibernate.service;

import com.comiteetica.hibernate.model.FechaSesion;
import com.comiteetica.persistencia.BussinessException;
import java.util.List;

/**
 *
 * @author rasec
 */
public interface FechaSesionService {

    public void create(FechaSesion fechaSesion) throws BussinessException;

    public FechaSesion read(String idFechaSesion) throws BussinessException;

    public void update(FechaSesion fechaSesion) throws BussinessException;

    public void delete(FechaSesion fechaSesion) throws BussinessException;

    public List<FechaSesion> getAllFechaSesion() throws BussinessException;

    public void beginTransaction() throws BussinessException;

    public void commit() throws BussinessException;

    public void close() throws BussinessException;

    public void rollback() throws BussinessException;

    public List<Object> getAllFechaSesionList() throws BussinessException;

    public List<Object> getAllFechaSesionProx() throws BussinessException;
}
