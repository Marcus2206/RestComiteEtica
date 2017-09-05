/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comiteetica.hibernate.service.impl;

import com.comiteetica.hibernate.dao.PagoDao;
import com.comiteetica.hibernate.model.Pago;
import com.comiteetica.hibernate.service.PagoService;
import com.comiteetica.persistencia.BussinessException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author rasec
 */
public class PagoServiceImpl implements PagoService {

    @Autowired
    private PagoDao pagoDao;

    @Transactional
    @Override
    public void beginTransaction() throws BussinessException {
        pagoDao.beginTransaction();
    }

    @Transactional
    @Override
    public void commit() throws BussinessException {
        pagoDao.commit();
    }

    @Transactional
    @Override
    public void close() throws BussinessException {
        pagoDao.close();
    }

    @Transactional
    @Override
    public void rollback() throws BussinessException {
        pagoDao.rollback();
    }

    @Transactional
    @Override
    public void create(Pago pago) throws BussinessException {
        pagoDao.create(pago);
    }

    @Transactional
    @Override
    public Pago read(String idPago) throws BussinessException {
        return pagoDao.read(idPago);
    }

    @Transactional
    @Override
    public void update(Pago pago) throws BussinessException {
        pagoDao.update(pago);
    }

    @Transactional
    @Override
    public void delete(Pago pago) throws BussinessException {
        pagoDao.delete(pago);
    }

    @Transactional
    @Override
    public List<Pago> getAllPago() throws BussinessException {
        return pagoDao.getAllPago();
    }

    @Transactional
    @Override
    public List<Object> getAllPagoList() throws BussinessException {
        return pagoDao.getAllPagoList();
    }

    @Transactional
    @Override
    public int sendMail(String idPago, String copiaCorreo) throws BussinessException {
        return pagoDao.sendMail(idPago,copiaCorreo);
    }
}
