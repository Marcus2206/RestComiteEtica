/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comiteetica.hibernate.dao;

import com.comiteetica.hibernate.model.Patrocinador;
import java.util.List;

/**
 *
 * @author rasec
 */
public interface PatrocinadorDao {
    public void create(Patrocinador patrocinador);
    public Patrocinador read(String idPatrocinador);
    public void update(Patrocinador patrocinador);
    public void delete(Patrocinador patrocinador);
    public List<Patrocinador> getAllPatrocinador();
}
