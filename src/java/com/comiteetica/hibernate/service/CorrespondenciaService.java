/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comiteetica.hibernate.service;

import com.comiteetica.hibernate.model.Correspondencia;
import com.comiteetica.persistencia.BussinessException;
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
}
