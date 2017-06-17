/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comiteetica.hibernate.service.impl;

import com.comiteetica.hibernate.dao.SerieDao;
import com.comiteetica.hibernate.model.Serie;
import com.comiteetica.hibernate.service.SerieService;
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
public class SerieServiceImpl implements SerieService{

    @Autowired
    private SerieDao serieDao;
    
    @Transactional
    @Override
    public void create(Serie serie) throws BussinessException {
        serieDao.create(serie);
    }

    @Transactional
    @Override
    public Serie read(String idSede) throws BussinessException {
        return serieDao.read(idSede);
    }

    @Transactional
    @Override
    public void update(Serie serie) throws BussinessException {
        serieDao.update(serie);
    }

    @Transactional
    @Override
    public void delete(Serie serie) throws BussinessException {
        serieDao.delete(serie);
    }

    @Transactional
    @Override
    public List<Serie> getAllSerie() throws BussinessException {
        return serieDao.getAllSerie();
    }
    
}
