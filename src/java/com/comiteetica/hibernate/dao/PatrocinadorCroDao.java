/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comiteetica.hibernate.dao;

import com.comiteetica.hibernate.model.PatrocinadorCro;
import com.comiteetica.hibernate.model.PatrocinadorCroId;
import java.util.List;

/**
 *
 * @author rasec
 */
public interface PatrocinadorCroDao {

    public void create(PatrocinadorCro patrocinadorCro);

    public PatrocinadorCro read(PatrocinadorCroId id);

    public void update(PatrocinadorCro patrocinadorCro);

    public void delete(PatrocinadorCro patrocinadorCro);

    public List<PatrocinadorCro> getAllPatrocinadorCro();

    public void beginTransaction();

    public void commit();

    public void close();

    public void rollback();

    public List<Object> getAllPatrocinadorCroSqlList();
}
