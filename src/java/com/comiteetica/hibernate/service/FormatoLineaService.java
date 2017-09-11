/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comiteetica.hibernate.service;

import com.comiteetica.hibernate.model.FormatoLinea;
import com.comiteetica.persistencia.BussinessException;
import java.util.List;
import model.FormatoLineaId;

/**
 *
 * @author rasec
 */
public interface FormatoLineaService {

    public void create(FormatoLinea formatoLinea) throws BussinessException;

    public FormatoLinea read(FormatoLineaId id) throws BussinessException;

    public void update(FormatoLinea formatoLinea) throws BussinessException;

    public void delete(FormatoLinea formatoLinea) throws BussinessException;

    public List<FormatoLinea> getAllFormatoLinea() throws BussinessException;

    public void beginTransaction() throws BussinessException;

    public void commit() throws BussinessException;

    public void close() throws BussinessException;

    public void rollback() throws BussinessException;

    public List<Object> getAllFormatoLineaList() throws BussinessException;
}
