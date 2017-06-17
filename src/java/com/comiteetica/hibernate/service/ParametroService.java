/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comiteetica.hibernate.service;

import com.comiteetica.hibernate.model.Parametro;
import com.comiteetica.persistencia.BussinessException;
import java.util.List;

/**
 *
 * @author rasec
 */
public interface ParametroService {
    public void create(Parametro parametro)throws BussinessException;
    public Parametro read(String idParametro)throws BussinessException;
    public void update(Parametro parametro)throws BussinessException;
    public void delete(Parametro parametro)throws BussinessException;
    public List<Parametro> getAllParametro() throws BussinessException;
}
