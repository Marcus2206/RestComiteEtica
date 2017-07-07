/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comiteetica.hibernate.dao;

import com.comiteetica.hibernate.model.Provincia;
import java.util.List;

/**
 *
 * @author rasec
 */
public interface ProvinciaDao {
    public List<Provincia> getAllProvinciaByDepartamento(String idDepartamento);
    public void beginTransaction();
    public void commit();
    public void close();
    public void rollback();
}
