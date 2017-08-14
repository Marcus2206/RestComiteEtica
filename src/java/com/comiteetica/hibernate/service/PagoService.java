/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comiteetica.hibernate.service;

import com.comiteetica.hibernate.model.Pago;
import com.comiteetica.persistencia.BussinessException;
import java.util.List;

/**
 *
 * @author rasec
 */
public interface PagoService {

    public void create(Pago pago) throws BussinessException;

    public Pago read(String idpago) throws BussinessException;

    public void update(Pago pago) throws BussinessException;

    public void delete(Pago pago) throws BussinessException;

    public List<Pago> getAllPago() throws BussinessException;

    public void beginTransaction() throws BussinessException;

    public void commit() throws BussinessException;

    public void close() throws BussinessException;

    public void rollback() throws BussinessException;

    public List<Object> getAllPagoList() throws BussinessException;
    
        public int sendMail(String idPago) throws BussinessException;
}
