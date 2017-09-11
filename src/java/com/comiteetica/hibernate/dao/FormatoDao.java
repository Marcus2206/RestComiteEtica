/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comiteetica.hibernate.dao;

import com.comiteetica.hibernate.model.Formato;
import java.util.List;

/**
 *
 * @author rasec
 */
public interface FormatoDao {

    public void create(Formato formato);

    public Formato read(int idFormato);

    public void update(Formato formato);

    public void delete(Formato formato);

    public List<Formato> getAllFormato();

    public void beginTransaction();

    public void commit();

    public void close();

    public void rollback();

    public List<Object> getAllFormatoList();
}
