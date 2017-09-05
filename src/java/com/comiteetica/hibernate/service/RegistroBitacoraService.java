/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comiteetica.hibernate.service;

import com.comiteetica.hibernate.model.RegistroBitacora;
import com.comiteetica.hibernate.model.RegistroBitacoraId;
import com.comiteetica.persistencia.BussinessException;
import java.util.List;

/**
 *
 * @author rasec
 */
public interface RegistroBitacoraService {

    public void create(RegistroBitacora registroBitacora) throws BussinessException;

    public RegistroBitacora read(RegistroBitacoraId id) throws BussinessException;

    public void update(RegistroBitacora registroBitacora) throws BussinessException;

    public void delete(RegistroBitacora registroBitacora) throws BussinessException;

    public List<RegistroBitacora> getAllRegistroBitacora() throws BussinessException;

    public void beginTransaction() throws BussinessException;

    public void commit() throws BussinessException;

    public void close() throws BussinessException;

    public void rollback() throws BussinessException;

    public List<RegistroBitacora> getAllBitacoraByIdRegistro(String idRegistro) throws BussinessException;

    public int getNextBitacoraByIdRegistro(String idRegistro) throws BussinessException;
    
    public List<Object> getAllBitacoraByIdRegistroList(String idRegistro) throws BussinessException;
}
