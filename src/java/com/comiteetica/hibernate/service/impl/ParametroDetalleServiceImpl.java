/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comiteetica.hibernate.service.impl;

import com.comiteetica.hibernate.dao.ParametroDetalleDao;
import com.comiteetica.hibernate.model.ParametroDetalle;
import com.comiteetica.hibernate.model.ParametroDetalleId;
import com.comiteetica.hibernate.service.ParametroDetalleService;
import com.comiteetica.persistencia.BussinessException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author rasec
 */
@Service
public class ParametroDetalleServiceImpl implements ParametroDetalleService{

    @Autowired
    private ParametroDetalleDao parametroDetalleDao;
    
    @Transactional
    @Override
    public void create(ParametroDetalle parametroDetalle) throws BussinessException {
        parametroDetalleDao.create(parametroDetalle);
    }

    @Transactional
    @Override
    public ParametroDetalle read(ParametroDetalleId parametroDetalleId) throws BussinessException {
        return parametroDetalleDao.read(parametroDetalleId);
    }

    @Transactional
    @Override
    public void update(ParametroDetalle parametroDetalle) throws BussinessException {
        parametroDetalleDao.update(parametroDetalle);
    }

    @Transactional
    @Override
    public void delete(ParametroDetalle parametroDetalle) throws BussinessException {
        parametroDetalleDao.delete(parametroDetalle);
    }

    @Transactional
    @Override
    public List<ParametroDetalle> getAllParametroDetalle() throws BussinessException {
        return parametroDetalleDao.getAllParametroDetalle();
    }
    
}
