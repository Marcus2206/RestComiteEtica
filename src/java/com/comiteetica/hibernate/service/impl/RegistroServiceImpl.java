/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comiteetica.hibernate.service.impl;

import com.comiteetica.hibernate.dao.RegistroDao;
import com.comiteetica.hibernate.model.Correspondencia;
import com.comiteetica.hibernate.model.Registro;
import com.comiteetica.hibernate.service.RegistroService;
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
public class RegistroServiceImpl implements RegistroService {

    @Autowired
    private RegistroDao registroDao;

    @Transactional
    @Override
    public void beginTransaction() throws BussinessException {
        registroDao.beginTransaction();
    }

    @Transactional
    @Override
    public void commit() throws BussinessException {
        registroDao.commit();
    }

    @Transactional
    @Override
    public void close() throws BussinessException {
        registroDao.close();
    }

    @Transactional
    @Override
    public void rollback() throws BussinessException {
        registroDao.rollback();
    }

    @Transactional
    @Override
    public void create(Registro registro) throws BussinessException {
        registroDao.create(registro);
    }

    @Transactional
    @Override
    public Registro read(String idRegistro) throws BussinessException {
        return registroDao.read(idRegistro);
    }

    @Transactional
    @Override
    public void update(Registro registro) throws BussinessException {
        registroDao.update(registro);
    }

    @Transactional
    @Override
    public void delete(Registro registro) throws BussinessException {
        registroDao.delete(registro);
    }

    @Transactional
    @Override
    public List<Registro> getAllRegistro() throws BussinessException {
        return registroDao.getAllRegistro();
    }

    @Transactional
    @Override
    public List<Object> getAllRegistroList() throws BussinessException {
        return registroDao.getAllRegistroList();
    }

    @Transactional
    @Override
    public List<Object> validateRegistro(String idInvestigacion, String idInvestigador, String idSede) throws BussinessException {
        return registroDao.validateRegistro(idInvestigacion, idInvestigador, idSede);
    }

    @Transactional
    @Override
    public List<Correspondencia> validateRegistroEnCorrespondencia(String idRegistro) throws BussinessException {
        return registroDao.validateRegistroEnCorrespondencia(idRegistro);
    }

    @Transactional
    @Override
    public String getCorrespondenciasByRegistro(String idRegistro) throws BussinessException {
        return registroDao.getCorrespondenciasByRegistro(idRegistro);
    }
    
    @Transactional
    @Override
    public List<Object> getDatosCierre(String idRegistro) throws BussinessException{
        return registroDao.getDatosCierre(idRegistro);
    }
    
    @Transactional
    @Override
    public List<Object> getDatosVisita(String idRegistro) throws BussinessException{
        return registroDao.getDatosVisita(idRegistro);
    }
    
    @Transactional
    @Override
    public List<Object> getDocumentosRegistro(String idRegistro) throws BussinessException{
        return registroDao.getDocumentosRegistro(idRegistro);
    }

}
