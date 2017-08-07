/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comiteetica.hibernate.service;

import com.comiteetica.hibernate.model.CorrespondenciaServicio;
import com.comiteetica.hibernate.model.CorrespondenciaServicioId;
import com.comiteetica.persistencia.BussinessException;
import java.util.List;

/**
 *
 * @author rasec
 */
public interface CorrespondenciaServicioService {

    public void create(CorrespondenciaServicio correspondenciaServicio) throws BussinessException;

    public CorrespondenciaServicio read(CorrespondenciaServicioId id) throws BussinessException;

    public void update(CorrespondenciaServicio correspondenciaServicio) throws BussinessException;

    public void delete(CorrespondenciaServicio correspondenciaServicio) throws BussinessException;

    public List<CorrespondenciaServicio> getAllCorrespondenciaServicio() throws BussinessException;

    public void beginTransaction() throws BussinessException;

    public void commit() throws BussinessException;

    public void close() throws BussinessException;

    public void rollback() throws BussinessException;

    public List<Object> getAllCorrespondenciaServicioSinPagoList() throws BussinessException;

    public int getNextServicioDetalleByIdCorrespondencia(String idCorrespondencia) throws BussinessException;

    public List<Object> getAllCorrespondenciaServicioByCorrespondencia(String idCorrespondencia) throws BussinessException;
}
