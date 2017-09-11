/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comiteetica.hibernate.dao;

import com.comiteetica.hibernate.model.FormatoLinea;
import com.comiteetica.hibernate.model.FormatoLineaId;
import java.util.List;
//import model.FormatoLineaId;

/**
 *
 * @author rasec
 */
public interface FormatoLineaDao {

    public void create(FormatoLinea formatoLinea);

    public FormatoLinea read(FormatoLineaId id);

    public void update(FormatoLinea formatoLinea);

    public void delete(FormatoLinea formatoLinea);

    public List<FormatoLinea> getAllFormatoLinea();

    public void beginTransaction();

    public void commit();

    public void close();

    public void rollback();

    public List<Object> getAllFormatoLineaList();
}
