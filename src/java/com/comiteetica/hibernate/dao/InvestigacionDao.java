/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comiteetica.hibernate.dao;

import com.comiteetica.hibernate.model.Investigacion;
import java.util.List;

/**
 *
 * @author rasec
 */
public interface InvestigacionDao {

    public void create(Investigacion investigacion);

    public Investigacion read(String idInvestigacion);

    public Investigacion readInvestigacion(String idInvestigacion);

    public void update(Investigacion investigacion);

    public void delete(Investigacion investigacion);

    public List<Investigacion> getAllInvestigacion();

    public void beginTransaction();

    public void commit();

    public void close();

    public void rollback();

    public List<Object> getAllInvestigacionList();

    public List<Object> getAllInvestigacionSimbolos();
}
