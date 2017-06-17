/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comiteetica.hibernate.service;

import com.comiteetica.hibernate.model.Registro;
import com.comiteetica.persistencia.BussinessException;
import java.util.List;

/**
 *
 * @author rasec
 */
public interface RegistroService {
    public void create(Registro registro) throws BussinessException;
    public Registro read(String idRegistro) throws BussinessException;
    public void update(Registro registro) throws BussinessException;
    public void delete(Registro registro) throws BussinessException;
    public List<Registro> getAllRegistro() throws BussinessException;
}
