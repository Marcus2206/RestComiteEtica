/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comiteetica.hibernate.service;

import com.comiteetica.hibernate.model.PatrocinadorCro;
import com.comiteetica.hibernate.model.PatrocinadorCroId;
import com.comiteetica.persistencia.BussinessException;
import java.util.List;

/**
 *
 * @author rasec
 */
public interface PatrocinadorCroService {
        public void create(PatrocinadorCro patrocinadorCro)throws BussinessException;

    public PatrocinadorCro read(PatrocinadorCroId id)throws BussinessException;

    public void update(PatrocinadorCro patrocinadorCro)throws BussinessException;

    public void delete(PatrocinadorCro patrocinadorCro)throws BussinessException;

    public List<PatrocinadorCro> getAllPatrocinadorCro()throws BussinessException;

    public void beginTransaction()throws BussinessException;

    public void commit()throws BussinessException;

    public void close()throws BussinessException;

    public void rollback()throws BussinessException;

    public List<Object> getAllPatrocinadorCroSqlList()throws BussinessException;
}
