/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comiteetica.hibernate.dao;

import com.comiteetica.hibernate.model.Cro;
import java.util.List;

/**
 *
 * @author rasec
 */
public interface CroDao {
    public void create(Cro cro);
    public Cro read(String idCro);
    public void update(Cro cro);
    public void delete(Cro cro);
    public List<Cro> getAllCro();
    public void beginTransaction();
    public void commit();
    public void close();
    public void rollback();
}
