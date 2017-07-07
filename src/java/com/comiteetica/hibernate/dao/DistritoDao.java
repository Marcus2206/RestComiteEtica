/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comiteetica.hibernate.dao;

import com.comiteetica.hibernate.model.Distrito;
import java.util.List;

/**
 *
 * @author rasec
 */
public interface DistritoDao {
    public List<Distrito> getAllDistritoByDepartamentoProvincia(String idDepartamento,String idProvincia);
    public void beginTransaction();
    public void commit();
    public void close();
    public void rollback();
}
