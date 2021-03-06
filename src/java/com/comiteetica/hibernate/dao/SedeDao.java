/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comiteetica.hibernate.dao;

import com.comiteetica.hibernate.model.Sede;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author rasec
 */
public interface SedeDao {
    public void create(Sede sede);
    public Sede read(String idSede);
    public void update(Sede sede);
    public void delete(Sede sede);
    public List<Sede> getAllSede();
    public void beginTransaction();
    public void commit();
    public void close();
    public void rollback();
    public List<Object[]> getAllSedeList();
    public List<Sede> getSedeSinIdInvestigacion(String idInvestigacion);
}
