/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comiteetica.hibernate.service.impl;

import com.comiteetica.hibernate.dao.ParametroDao;
import com.comiteetica.hibernate.model.Parametro;
import com.comiteetica.hibernate.service.ParametroService;
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
public class ParametroServiceImpl implements ParametroService{
    
    @Autowired
    private ParametroDao parametroDao;

    @Transactional
    @Override
    public void create(Parametro parametro) throws BussinessException {
        parametroDao.create(parametro);
    }

    @Transactional
    @Override
    public Parametro read(String idParametro) throws BussinessException {
        return parametroDao.read(idParametro);
    }

    @Transactional
    @Override
    public void update(Parametro parametro) throws BussinessException {
        parametroDao.update(parametro);
    }

    @Transactional
    @Override
    public void delete(Parametro parametro) throws BussinessException {
        parametroDao.delete(parametro);
    }

    @Transactional
    @Override
    public List<Parametro> getAllParametro() throws BussinessException {
        return parametroDao.getAllParametro();
    }
    
}
