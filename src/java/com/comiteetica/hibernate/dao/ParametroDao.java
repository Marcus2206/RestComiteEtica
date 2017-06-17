/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comiteetica.hibernate.dao;

import com.comiteetica.hibernate.model.Parametro;
import java.util.List;

/**
 *
 * @author rasec
 */
public interface ParametroDao {
    public void create(Parametro parametro);
    public Parametro read(String idParametro);
    public void update(Parametro parametro);
    public void delete(Parametro parametro);
    public List<Parametro> getAllParametro();
}
