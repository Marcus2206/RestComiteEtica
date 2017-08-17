/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comiteetica.hibernate.dao;

import java.util.List;

/**
 *
 * @author rasec
 */
public interface NotificacionDao {

    public void beginTransaction();

    public void commit();

    public void close();

    public void rollback();

    public List<Object> getAllNotificacionList(String usuario);

    public void updateSetLeido(int idNotificacion, String usuario);

    public void updateSetTodoLeido(String usuario);
//    public void updateSql(int idUsuario, String password, String usuarioModifica, Date fechaModificacion);
}
