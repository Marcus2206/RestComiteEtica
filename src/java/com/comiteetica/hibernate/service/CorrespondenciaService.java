/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comiteetica.hibernate.service;

import com.comiteetica.hibernate.model.Correspondencia;
import com.comiteetica.persistencia.BussinessException;
import java.util.Date;
import java.util.List;

/**
 *
 * @author rasec
 */
public interface CorrespondenciaService {

    public void create(Correspondencia correspondencia) throws BussinessException;

    public Correspondencia read(String idCorrespondencia) throws BussinessException;

    public void update(Correspondencia correspondencia) throws BussinessException;

    public void delete(Correspondencia correspondencia) throws BussinessException;

    public List<Correspondencia> getAllCorrespondencia() throws BussinessException;

    public void beginTransaction() throws BussinessException;

    public void commit() throws BussinessException;

    public void close() throws BussinessException;

    public void rollback() throws BussinessException;

    public List<Object> getAllCorrespondenciaList() throws BussinessException;

    public List<Object> getDatosHojaRuta(String idCorrespondencia) throws BussinessException;

    public List<Object> getDatosCarta(String idCorrespondencia) throws BussinessException;

    public List<Correspondencia> readByFechaSesion(Date fechaSesion) throws BussinessException;

    public List<Object> readCorrespondenciasValidas(String idRegistro) throws BussinessException;
    
    public List<Object> getAllcorrespondenciaByFechaSesion(Date fechaSesion)  throws BussinessException;
}
