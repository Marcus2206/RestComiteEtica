/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comiteetica.hibernate.service;

import com.comiteetica.hibernate.model.ParametroDetalle;
import com.comiteetica.hibernate.model.ParametroDetalleId;
import com.comiteetica.persistencia.BussinessException;
import java.util.List;

/**
 *
 * @author rasec
 */
public interface ParametroDetalleService {

    public void create(ParametroDetalle parametroDetalle) throws BussinessException;

    public ParametroDetalle read(ParametroDetalleId parametroDetalleId) throws BussinessException;

    public void update(ParametroDetalle parametroDetalle) throws BussinessException;

    public void delete(ParametroDetalle parametroDetalle) throws BussinessException;

    public List<ParametroDetalle> getAllParametroDetalle() throws BussinessException;

    public List<ParametroDetalle> getParametroDetalleByIdParametro(String idParametro) throws BussinessException;

    public void beginTransaction() throws BussinessException;

    public void commit() throws BussinessException;

    public void close() throws BussinessException;

    public void rollback() throws BussinessException;

    public String getNextParametroDetalleByIdParametro(String idParametro) throws BussinessException;
}
