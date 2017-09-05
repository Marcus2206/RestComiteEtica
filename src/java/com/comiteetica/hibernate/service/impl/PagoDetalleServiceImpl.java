/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comiteetica.hibernate.service.impl;

import com.comiteetica.hibernate.dao.PagoDao;
import com.comiteetica.hibernate.dao.PagoDetalleDao;
import com.comiteetica.hibernate.model.Pago;
import com.comiteetica.hibernate.model.PagoDetalle;
import com.comiteetica.hibernate.model.PagoDetalleId;
import com.comiteetica.hibernate.service.PagoDetalleService;
import com.comiteetica.persistencia.BussinessException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author rasec
 */
public class PagoDetalleServiceImpl implements PagoDetalleService {

    @Autowired
    private PagoDetalleDao pagoDetalleDao;

    @Transactional
    @Override
    public void beginTransaction() throws BussinessException {
        pagoDetalleDao.beginTransaction();
    }

    @Transactional
    @Override
    public void commit() throws BussinessException {
        pagoDetalleDao.commit();
    }

    @Transactional
    @Override
    public void close() throws BussinessException {
        pagoDetalleDao.close();
    }

    @Transactional
    @Override
    public void rollback() throws BussinessException {
        pagoDetalleDao.rollback();
    }

    @Transactional
    @Override
    public void create(PagoDetalle pagoDetalle) throws BussinessException {
        pagoDetalleDao.create(pagoDetalle);
    }

    @Transactional
    @Override
    public PagoDetalle read(PagoDetalleId id) throws BussinessException {
        return pagoDetalleDao.read(id);
    }

    @Transactional
    @Override
    public void update(PagoDetalle pagoDetalle) throws BussinessException {
        pagoDetalleDao.update(pagoDetalle);
    }

    @Transactional
    @Override
    public void delete(PagoDetalle pagoDetalle) throws BussinessException {
        pagoDetalleDao.delete(pagoDetalle);
    }

    @Transactional
    @Override
    public List<PagoDetalle> getAllPagoDetalle() throws BussinessException {
        return pagoDetalleDao.getAllPagoDetalle();
    }

    @Transactional
    @Override
    public List<PagoDetalle> getAllPagoDetalleByPago(String idPago) throws BussinessException {
        return pagoDetalleDao.getAllPagoDetalleByPago(idPago);
    }

    @Transactional
    @Override
    public List<Object> getAllPagoDetalleList() throws BussinessException {
        return pagoDetalleDao.getAllPagoDetalleList();
    }

    @Transactional
    @Override
    public int getNextPagoDetalleByIdPago(String idPago) throws BussinessException {
        return pagoDetalleDao.getNextPagoDetalleByIdPago(idPago);
    }

    @Transactional
    @Override
    public List<Object> getAllPagoDetalleByPagoList(String idPago) throws BussinessException {
        return pagoDetalleDao.getAllPagoDetalleByPagoList(idPago);
    }
}
