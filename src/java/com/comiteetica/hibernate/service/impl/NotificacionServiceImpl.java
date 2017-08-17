/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comiteetica.hibernate.service.impl;

import com.comiteetica.hibernate.dao.NotificacionDao;
import com.comiteetica.hibernate.service.NotificacionService;
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
public class NotificacionServiceImpl implements NotificacionService {

    @Autowired
    private NotificacionDao notificacionDao;

    @Transactional
    @Override
    public void beginTransaction() throws BussinessException {
        notificacionDao.beginTransaction();
    }

    @Transactional
    @Override
    public void commit() throws BussinessException {
        notificacionDao.commit();
    }

    @Transactional
    @Override
    public void close() throws BussinessException {
        notificacionDao.close();
    }

    @Transactional
    @Override
    public void rollback() throws BussinessException {
        notificacionDao.rollback();
    }

    @Transactional
    @Override
    public List<Object> getAllNotificacionList(String usuario) throws BussinessException {
        return notificacionDao.getAllNotificacionList(usuario);
    }

    @Transactional
    @Override
    public void updateSetLeido(int idNotificacion, String usuario) throws BussinessException {
        notificacionDao.updateSetLeido(idNotificacion, usuario);
    }

    @Transactional
    @Override
    public void updateSetTodoLeido(String usuario) throws BussinessException {
        notificacionDao.updateSetTodoLeido(usuario);
    }
}
