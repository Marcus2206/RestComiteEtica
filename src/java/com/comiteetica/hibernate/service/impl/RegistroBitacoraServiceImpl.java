/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comiteetica.hibernate.service.impl;

import com.comiteetica.hibernate.dao.RegistroBitacoraDao;
import com.comiteetica.hibernate.model.RegistroBitacora;
import com.comiteetica.hibernate.model.RegistroBitacoraId;
import com.comiteetica.hibernate.service.RegistroBitacoraService;
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
public class RegistroBitacoraServiceImpl implements RegistroBitacoraService {

    @Autowired
    private RegistroBitacoraDao registroBitacoraDao;

    @Transactional
    @Override
    public void create(RegistroBitacora registroBitacora) throws BussinessException {
        registroBitacoraDao.create(registroBitacora);
    }

    @Transactional
    @Override
    public RegistroBitacora read(RegistroBitacoraId id) throws BussinessException {
        return registroBitacoraDao.read(id);
    }

    @Transactional
    @Override
    public void update(RegistroBitacora registroBitacora) throws BussinessException {
        registroBitacoraDao.update(registroBitacora);
    }

    @Transactional
    @Override
    public void delete(RegistroBitacora registroBitacora) throws BussinessException {
        registroBitacoraDao.delete(registroBitacora);
    }

    @Transactional
    @Override
    public List<RegistroBitacora> getAllRegistroBitacora() throws BussinessException {
        return registroBitacoraDao.getAllRegistroBitacora();
    }

    @Transactional
    @Override
    public void beginTransaction() throws BussinessException {
        registroBitacoraDao.beginTransaction();
    }

    @Transactional
    @Override
    public void commit() throws BussinessException {
        registroBitacoraDao.commit();
    }

    @Transactional
    @Override
    public void close() throws BussinessException {
        registroBitacoraDao.close();
    }

    @Transactional
    @Override
    public void rollback() throws BussinessException {
        registroBitacoraDao.rollback();
    }

    @Transactional
    @Override
    public List<RegistroBitacora> getAllBitacoraByIdRegistro(String idRegistro) throws BussinessException {
        return registroBitacoraDao.getAllBitacoraByIdRegistro(idRegistro);
    }

    @Transactional
    @Override
    public int getNextBitacoraByIdRegistro(String idRegistro) throws BussinessException {
        return registroBitacoraDao.getNextBitacoraByIdRegistro(idRegistro);
    }

    @Transactional
    @Override
    public List<Object> getAllBitacoraByIdRegistroList(String idRegistro) throws BussinessException {
        return registroBitacoraDao.getAllBitacoraByIdRegistroList(idRegistro);
    }
}
