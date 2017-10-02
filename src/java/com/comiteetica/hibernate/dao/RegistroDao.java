/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comiteetica.hibernate.dao;

import com.comiteetica.hibernate.model.Correspondencia;
import com.comiteetica.hibernate.model.Registro;
import java.util.List;

/**
 *
 * @author rasec
 */
public interface RegistroDao {

    public void create(Registro registro);

    public Registro read(String idRegistro);

    public void update(Registro registro);

    public void delete(Registro registro);

    public List<Registro> getAllRegistro();

    public void beginTransaction();

    public void commit();

    public void close();

    public void rollback();

    List<Object> getAllRegistroList();

    public List<Object> validateRegistro(String idInvestigacion, String idInvestigador, String idSede);

    public List<Correspondencia> validateRegistroEnCorrespondencia(String idRegistro);

    public String getCorrespondenciasByRegistro(String idRegistro);
}
