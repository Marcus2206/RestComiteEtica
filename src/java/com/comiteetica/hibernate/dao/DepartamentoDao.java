/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comiteetica.hibernate.dao;

import com.comiteetica.hibernate.model.Departamento;
import java.util.List;

/**
 *
 * @author rasec
 */
public interface DepartamentoDao {
    public void create(Departamento departamento);
    public Departamento read(String idDepartamento);
    public void update(Departamento departamento);
    public void delete(Departamento departamento);
    public List<Departamento> getAllDepartamento();
    public void beginTransaction();
    public void commit();
    public void close();
    public void rollback();
}
