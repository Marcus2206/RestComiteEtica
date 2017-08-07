/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comiteetica.hibernate.service;

import com.comiteetica.hibernate.model.PagoDetalle;
import com.comiteetica.hibernate.model.PagoDetalleId;
import com.comiteetica.persistencia.BussinessException;
import java.util.List;

/**
 *
 * @author rasec
 */
public interface PagoDetalleService {

    public void create(PagoDetalle pagoDetalle) throws BussinessException;

    public PagoDetalle read(PagoDetalleId id) throws BussinessException;

    public void update(PagoDetalle pagoDetalle) throws BussinessException;

    public void delete(PagoDetalle pagoDetalle) throws BussinessException;

    public List<PagoDetalle> getAllPagoDetalle() throws BussinessException;

    public void beginTransaction() throws BussinessException;

    public void commit() throws BussinessException;

    public void close() throws BussinessException;

    public void rollback() throws BussinessException;

    public List<Object> getAllPagoDetalleList() throws BussinessException;

    public int getNextPagoDetalleByIdPago(String idPago) throws BussinessException;

    public List<Object> getAllPagoDetalleByPagoList(String idPago) throws BussinessException;
}
