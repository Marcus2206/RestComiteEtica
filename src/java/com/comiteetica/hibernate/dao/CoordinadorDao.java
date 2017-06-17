/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comiteetica.hibernate.dao;

import com.comiteetica.hibernate.model.Coordinador;
import java.util.List;
/**
 *
 * @author rasec
 */
public interface CoordinadorDao {
    public void create(Coordinador coordinador);
    public Coordinador read(String idCoordinador);
    public void update(Coordinador coordinador);
    public void delete(Coordinador coordinador);
    public List<Coordinador> getAllCoordinador();
}
