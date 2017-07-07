/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comiteetica.hibernate.service;

import com.comiteetica.hibernate.model.Departamento;
import com.comiteetica.persistencia.BussinessException;
import java.util.List;

/**
 *
 * @author rasec
 */
public interface DepartamentoService {
    public void create(Departamento departamento) throws BussinessException;
    public Departamento read(String idDepartamento) throws BussinessException;
    public void update(Departamento departamento) throws BussinessException;
    public void delete(Departamento departamento) throws BussinessException;
    public List<Departamento> getAllDepartamento() throws BussinessException;
    public void beginTransaction() throws BussinessException;
    public void commit() throws BussinessException;
    public void close() throws BussinessException;
    public void rollback() throws BussinessException;
}
