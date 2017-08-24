/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comiteetica.hibernate.service;

import com.comiteetica.hibernate.model.Cro;
import com.comiteetica.persistencia.BussinessException;
import java.util.List;

/**
 *
 * @author rasec
 */
public interface CroService {

    public void create(Cro cro) throws BussinessException;

    public Cro read(String idCro) throws BussinessException;

    public void update(Cro cro) throws BussinessException;

    public void delete(Cro cro) throws BussinessException;

    public List<Cro> getAllCro() throws BussinessException;

    public void beginTransaction() throws BussinessException;

    public void commit() throws BussinessException;

    public void close() throws BussinessException;

    public void rollback() throws BussinessException;

    public List<Cro> getCroByPatrocinador(String idPatrocinador) throws BussinessException;

    public List<Cro> getCroSinIdPatrocinador(String idPatrocinador) throws BussinessException;

    public List<Object> getAllCroList() throws BussinessException;

}
