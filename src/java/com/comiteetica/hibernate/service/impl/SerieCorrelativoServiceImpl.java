/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comiteetica.hibernate.service.impl;

import com.comiteetica.hibernate.dao.SerieCorrelativoDao;
import com.comiteetica.hibernate.model.SerieCorrelativo;
import com.comiteetica.hibernate.model.SerieCorrelativoId;
import com.comiteetica.hibernate.service.SerieCorrelativoService;
import com.comiteetica.persistencia.BussinessException;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author rasec
 */
@Service
public class SerieCorrelativoServiceImpl implements SerieCorrelativoService{

    @Autowired
    private SerieCorrelativoDao serieCorrelativoDao;
    
    @Transactional
    @Override
    public void create(SerieCorrelativo serieCorrelativo) throws BussinessException {
        serieCorrelativoDao.create(serieCorrelativo);
    }

    @Transactional
    @Override
    public SerieCorrelativo read(SerieCorrelativoId serieCorrelativoId) throws BussinessException {
        return serieCorrelativoDao.read(serieCorrelativoId);
    }
    
    @Transactional
    @Override
    public SerieCorrelativo readNextSerieCorrelativo(String serieId,Date fechaTrabajo)throws BussinessException {
        return serieCorrelativoDao.readNextSerieCorrelativo(serieId, fechaTrabajo);
    }

    @Transactional
    @Override
    public void update(SerieCorrelativo serieCorrelativo) throws BussinessException {
        serieCorrelativoDao.update(serieCorrelativo);
    }

    @Transactional
    @Override
    public void delete(SerieCorrelativo serieCorrelativo) throws BussinessException {
        serieCorrelativoDao.delete(serieCorrelativo);
    }

    @Transactional
    @Override
    public List<SerieCorrelativo> getAllSerieCorrelativo() throws BussinessException {
        return serieCorrelativoDao.getAllSerieCorrelativo();
    }
    
}
