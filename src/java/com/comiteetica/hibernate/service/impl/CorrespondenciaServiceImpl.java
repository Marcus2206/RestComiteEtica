/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comiteetica.hibernate.service.impl;

import com.comiteetica.hibernate.dao.CorrespondenciaDao;
import com.comiteetica.hibernate.model.Correspondencia;
import com.comiteetica.hibernate.service.CorrespondenciaService;
import com.comiteetica.persistencia.BussinessException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author rasec
 */
@Service
public class CorrespondenciaServiceImpl implements CorrespondenciaService{

    @Autowired
    private CorrespondenciaDao correspondenciaDao;
    
    @Override
    public void create(Correspondencia correspondencia) throws BussinessException {
        correspondenciaDao.create(correspondencia);
    }

    @Override
    public Correspondencia read(String idCorrespondencia) throws BussinessException {
        return correspondenciaDao.read(idCorrespondencia);
    }

    @Override
    public void update(Correspondencia correspondencia) throws BussinessException {
        correspondenciaDao.update(correspondencia);
    }

    @Override
    public void delete(Correspondencia correspondencia) throws BussinessException {
        correspondenciaDao.delete(correspondencia);
    }

    @Override
    public List<Correspondencia> getAllCorrespondencia() throws BussinessException {
        return correspondenciaDao.getAllCorrespondencia();
    }
    
}
