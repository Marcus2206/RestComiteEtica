/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comiteetica.hibernate.service;

import com.comiteetica.persistencia.BussinessException;
import java.util.List;

/**
 *
 * @author rasec
 */
public interface NotificacionService {

    public void beginTransaction() throws BussinessException;

    public void commit() throws BussinessException;

    public void close() throws BussinessException;

    public void rollback() throws BussinessException;

    public List<Object> getAllNotificacionList(String usuario) throws BussinessException;

    public void updateSetLeido(int idNotificacion, String usuario) throws BussinessException;

    public void updateSetTodoLeido(String usuario) throws BussinessException;
}
