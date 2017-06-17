/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comiteetica.hibernate.dao;

import com.comiteetica.hibernate.model.Investigador;
import java.util.List;

/**
 *
 * @author rasec
 */
public interface InvestigadorDao {
    public void create(Investigador investigador);
    public Investigador read(String idInvestigador);
    public void update(Investigador investigador);
    public void delete(Investigador investigador);
    public List<Investigador> getAllInvestigador();
    //public String getNextIdProducto();
    //public int getProductoCount();
}
