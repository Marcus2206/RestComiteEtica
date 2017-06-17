/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comiteetica.hibernate.dao;

import com.comiteetica.hibernate.model.Correspondencia;
import java.util.List;

/**
 *
 * @author rasec
 */
public interface CorrespondenciaDao {
    public void create(Correspondencia correspondencia);
    public Correspondencia read(String idCorrespondencia);
    public void update(Correspondencia correspondencia);
    public void delete(Correspondencia correspondencia);
    public List<Correspondencia> getAllCorrespondencia();
}
