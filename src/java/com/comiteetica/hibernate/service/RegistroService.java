/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comiteetica.hibernate.service;

import com.comiteetica.hibernate.model.Correspondencia;
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

    public void beginTransaction() throws BussinessException;

    public void commit() throws BussinessException;

    public void close() throws BussinessException;

    public void rollback() throws BussinessException;

    public List<Object> getAllRegistroList() throws BussinessException;

    public List<Object> validateRegistro(String idInvestigacion, String idInvestigador, String idSede) throws BussinessException;

    public List<Correspondencia> validateRegistroEnCorrespondencia(String idRegistro) throws BussinessException;

    public String getCorrespondenciasByRegistro(String idRegistro) throws BussinessException;
}
