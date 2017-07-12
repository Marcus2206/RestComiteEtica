/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comiteetica.hibernate.service;

import com.comiteetica.hibernate.model.Patrocinador;
import com.comiteetica.persistencia.BussinessException;
import java.util.List;

/**
 *
 * @author rasec
 */
public interface PatrocinadorService {
    public void create(Patrocinador patrocinador) throws BussinessException ;
    public Patrocinador read(String idPatrocinador) throws BussinessException ;
    public void update(Patrocinador patrocinador) throws BussinessException ;
    public void delete(Patrocinador patrocinador) throws BussinessException ;
    public List<Patrocinador> getAllPatrocinador() throws BussinessException ;
    public void beginTransaction() throws BussinessException;
    public void commit() throws BussinessException;
    public void close() throws BussinessException;
    public void rollback() throws BussinessException;
}
