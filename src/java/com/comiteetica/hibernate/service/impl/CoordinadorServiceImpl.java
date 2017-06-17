/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comiteetica.hibernate.service.impl;

import com.comiteetica.hibernate.dao.CoordinadorDao;
import com.comiteetica.hibernate.model.Coordinador;
import com.comiteetica.hibernate.service.CoordinadorService;
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
public class CoordinadorServiceImpl implements CoordinadorService{

    @Autowired
    private CoordinadorDao coordinadorDao;
    
    @Transactional
    @Override
    public void create(Coordinador coordinador) throws BussinessException {
        coordinadorDao.create(coordinador);
    }

    @Transactional
    @Override
    public Coordinador read(String idCoordinador) throws BussinessException {
        return coordinadorDao.read(idCoordinador);
    }

    @Transactional
    @Override
    public void update(Coordinador coordinador) throws BussinessException {
        coordinadorDao.update(coordinador);
    }

    @Transactional
    @Override
    public void delete(Coordinador coordinador) throws BussinessException {
        coordinadorDao.delete(coordinador);
    }

    @Transactional
    @Override
    public List<Coordinador> getAllCoordinador() throws BussinessException {
        return coordinadorDao.getAllCoordinador();
    }
    
}
