/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comiteetica.hibernate.service;

import com.comiteetica.hibernate.model.Usuario;
import com.comiteetica.persistencia.BussinessException;
import java.util.Date;
import java.util.List;

/**
 *
 * @author rasec
 */
public interface UsuarioService {

    public void create(Usuario usuario) throws BussinessException;

    public Usuario read(int idUsuario) throws BussinessException;

    public void update(Usuario usuario) throws BussinessException;

    public void delete(Usuario usuario) throws BussinessException;

    public List<Usuario> getAllUsuario() throws BussinessException;

    public void beginTransaction() throws BussinessException;

    public void commit() throws BussinessException;

    public void close() throws BussinessException;

    public void rollback() throws BussinessException;

    public List<Object> getAllUsuarioList() throws BussinessException;

    public int createSql(String usuario, String password, String perfil, String usuarioIngresa, Date fechaIngreso, Boolean estado) throws BussinessException;

    public List<Object> readSql(String usuario, String password) throws BussinessException;

    public void updateSql(int idUsuario, String password, String usuarioModifica, Date fechaModificacion) throws BussinessException;
}
