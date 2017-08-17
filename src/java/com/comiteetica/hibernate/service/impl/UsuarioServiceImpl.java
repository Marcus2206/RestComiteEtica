/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comiteetica.hibernate.service.impl;

import com.comiteetica.hibernate.dao.UsuarioDao;
import com.comiteetica.hibernate.model.Usuario;
import com.comiteetica.hibernate.service.UsuarioService;
import com.comiteetica.persistencia.BussinessException;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author rasec
 */
@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioDao usuarioDao;

    @Override
    public void create(Usuario usuario) throws BussinessException {
        usuarioDao.create(usuario);
    }

    @Override
    public Usuario read(int idUsuario) throws BussinessException {
        return usuarioDao.read(idUsuario);
    }

    @Override
    public void update(Usuario usuario) throws BussinessException {
        usuarioDao.update(usuario);
    }

    @Override
    public void delete(Usuario usuario) throws BussinessException {
        usuarioDao.delete(usuario);
    }

    @Override
    public List<Usuario> getAllUsuario() throws BussinessException {
        return usuarioDao.getAllUsuario();
    }

    @Override
    public void beginTransaction() throws BussinessException {
        usuarioDao.beginTransaction();
    }

    @Override
    public void commit() throws BussinessException {
        usuarioDao.commit();
    }

    @Override
    public void close() throws BussinessException {
        usuarioDao.close();
    }

    @Override
    public void rollback() throws BussinessException {
        usuarioDao.rollback();
    }

    @Override
    public List<Object> getAllUsuarioList() throws BussinessException {
        return usuarioDao.getAllUsuarioList();
    }

    @Override
    public int createSql(String usuario, String password, String perfil, String usuarioIngresa, Date fechaIngreso, Boolean estado) throws BussinessException {
        return usuarioDao.createSql(usuario, password, perfil, usuarioIngresa, fechaIngreso, estado);
    }

    @Override
    public List<Object> readSql(String usuario, String password) throws BussinessException {
        return usuarioDao.readSql(usuario, password);
    }

    @Override
    public void updateSql(int idUsuario, String password, String usuarioModifica, Date fechaModificacion) throws BussinessException {
        usuarioDao.updateSql(idUsuario, password, usuarioModifica, fechaModificacion);
    }

}
