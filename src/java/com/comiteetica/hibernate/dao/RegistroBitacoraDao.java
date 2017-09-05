/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comiteetica.hibernate.dao;

import com.comiteetica.hibernate.model.RegistroBitacora;
import com.comiteetica.hibernate.model.RegistroBitacoraId;
import java.util.List;

/**
 *
 * @author rasec
 */
public interface RegistroBitacoraDao {

    public void create(RegistroBitacora registroBitacora);

    public RegistroBitacora read(RegistroBitacoraId id);

    public void update(RegistroBitacora registroBitacora);

    public void delete(RegistroBitacora registroBitacora);

    public List<RegistroBitacora> getAllRegistroBitacora();

    public void beginTransaction();

    public void commit();

    public void close();

    public void rollback();

    public List<RegistroBitacora> getAllBitacoraByIdRegistro(String idRegistro);

    public int getNextBitacoraByIdRegistro(String idRegistro);

    public List<Object> getAllBitacoraByIdRegistroList(String idRegistro);

}
