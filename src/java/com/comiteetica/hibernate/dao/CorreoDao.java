/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comiteetica.hibernate.dao;

import com.comiteetica.hibernate.model.Correo;
import java.util.List;

/**
 *
 * @author rasec
 */
public interface CorreoDao {
    
    public void create(Correo correo);

    public Correo read(int idCorreo);

    public void update(Correo correo);

    public void delete(Correo correo);

    public List<Correo> getAllCorreo();

    public void beginTransaction();

    public void commit();

    public void close();

    public void rollback();

    public List<Object> getAllCorreoList();
    
}
