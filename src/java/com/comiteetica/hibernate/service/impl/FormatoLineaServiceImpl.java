/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comiteetica.hibernate.service.impl;

import com.comiteetica.hibernate.dao.FormatoLineaDao;
import com.comiteetica.hibernate.model.FormatoLinea;
import com.comiteetica.hibernate.model.FormatoLineaId;
import com.comiteetica.hibernate.service.FormatoLineaService;
import com.comiteetica.persistencia.BussinessException;
import java.util.List;
//import model.FormatoLineaId;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author rasec
 */
public class FormatoLineaServiceImpl implements FormatoLineaService {

    @Autowired
    private FormatoLineaDao formatoLineaDao;

    @Override
    public void create(FormatoLinea formatoLinea) throws BussinessException {
        formatoLineaDao.create(formatoLinea);
    }

    @Override
    public FormatoLinea read(FormatoLineaId id) throws BussinessException {
        return formatoLineaDao.read(id);
    }

    @Override
    public void update(FormatoLinea formatoLinea) throws BussinessException {
        formatoLineaDao.update(formatoLinea);
    }

    @Override
    public void delete(FormatoLinea formatoLinea) throws BussinessException {
        formatoLineaDao.delete(formatoLinea);
    }

    @Override
    public List<FormatoLinea> getAllFormatoLinea() throws BussinessException {
        return formatoLineaDao.getAllFormatoLinea();
    }

    @Override
    public void beginTransaction() throws BussinessException {
        formatoLineaDao.beginTransaction();
    }

    @Override
    public void commit() throws BussinessException {
        formatoLineaDao.commit();
    }

    @Override
    public void close() throws BussinessException {
        formatoLineaDao.close();
    }

    @Override
    public void rollback() throws BussinessException {
        formatoLineaDao.rollback();
    }

    @Override
    public List<Object> getAllFormatoLineaList() throws BussinessException {
        return formatoLineaDao.getAllFormatoLineaList();
    }
    
    
    @Override
    public List<Object> getLineaFormatoByIdFormato(String idFormato) throws BussinessException{
        return formatoLineaDao.getFormatoLineaByidFormato(idFormato);
    }
    
}
