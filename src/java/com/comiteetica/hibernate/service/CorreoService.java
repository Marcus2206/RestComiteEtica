/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comiteetica.hibernate.service;

import com.comiteetica.hibernate.model.Correo;
import com.comiteetica.persistencia.BussinessException;
import java.util.List;

/**
 *
 * @author rasec
 */
public interface CorreoService {

    public void create(Correo correo) throws BussinessException;

    public Correo read(int idCorreo) throws BussinessException;

    public void update(Correo correo) throws BussinessException;

    public void delete(Correo correo) throws BussinessException;

    public List<Correo> getAllCorreo() throws BussinessException;

    public void beginTransaction() throws BussinessException;

    public void commit() throws BussinessException;

    public void close() throws BussinessException;

    public void rollback() throws BussinessException;

    public List<Object> getAllCorreoList() throws BussinessException;
}
