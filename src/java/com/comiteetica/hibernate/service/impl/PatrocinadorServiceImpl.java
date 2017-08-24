/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comiteetica.hibernate.service.impl;

import com.comiteetica.hibernate.dao.PatrocinadorDao;
import com.comiteetica.hibernate.model.Patrocinador;
import com.comiteetica.hibernate.service.PatrocinadorService;
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
public class PatrocinadorServiceImpl implements PatrocinadorService {

    @Autowired
    private PatrocinadorDao patrocinadorDao;

    @Transactional
    @Override
    public void beginTransaction() throws BussinessException {
        patrocinadorDao.beginTransaction();
    }

    @Transactional
    @Override
    public void commit() throws BussinessException {
        patrocinadorDao.commit();
    }

    @Transactional
    @Override
    public void close() throws BussinessException {
        patrocinadorDao.close();
    }

    @Transactional
    @Override
    public void rollback() throws BussinessException {
        patrocinadorDao.rollback();
    }

    @Transactional
    @Override
    public void create(Patrocinador patrocinador) throws BussinessException {
        patrocinadorDao.create(patrocinador);
    }

    @Transactional
    @Override
    public Patrocinador read(String idPatrocinador) throws BussinessException {
        return patrocinadorDao.read(idPatrocinador);
    }

    @Transactional
    @Override
    public void update(Patrocinador patrocinador) throws BussinessException {
        patrocinadorDao.update(patrocinador);
    }

    @Transactional
    @Override
    public void delete(Patrocinador patrocinador) throws BussinessException {
        patrocinadorDao.delete(patrocinador);
    }

    @Transactional
    @Override
    public List<Patrocinador> getAllPatrocinador() throws BussinessException {
        return patrocinadorDao.getAllPatrocinador();
    }

        @Transactional
    @Override
        public List<Patrocinador> getPatrocinadorSinIdCro(String idCro)throws BussinessException {
         return patrocinadorDao.getPatrocinadorSinIdCro(idCro);
        }
}
