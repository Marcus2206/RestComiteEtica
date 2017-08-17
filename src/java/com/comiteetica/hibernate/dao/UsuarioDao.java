/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comiteetica.hibernate.dao;

import com.comiteetica.hibernate.model.Usuario;
import java.util.Date;
import java.util.List;

/**
 *
 * @author rasec
 */
public interface UsuarioDao {

    public void create(Usuario usuario);

    public Usuario read(int idUsuario);

    public void update(Usuario usuario);

    public void delete(Usuario usuario);

    public List<Usuario> getAllUsuario();

    public void beginTransaction();

    public void commit();

    public void close();

    public void rollback();

    public List<Object> getAllUsuarioList();

    public int createSql(String usuario, String password, String perfil, String usuarioIngresa, Date fechaIngreso, Boolean estado);

    public List<Object> readSql(String usuario, String password);

    public void updateSql(int idUsuario, String password, String usuarioModifica, Date fechaModificacion);
}
