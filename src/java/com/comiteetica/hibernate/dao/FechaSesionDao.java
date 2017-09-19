/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comiteetica.hibernate.dao;

import com.comiteetica.hibernate.model.FechaSesion;
import java.util.List;

/**
 *
 * @author rasec
 */
public interface FechaSesionDao {

    public void create(FechaSesion fechaSesion);

    public FechaSesion read(int idFechaSesion);

    public void update(FechaSesion fechaSesion);

    public void delete(FechaSesion fechaSesion);

    public List<FechaSesion> getAllFechaSesion();

    public void beginTransaction();

    public void commit();

    public void close();

    public void rollback();

    public List<Object> getAllFechaSesionList();
    
    public List<Object> getAllFechaSesionProx();
}
