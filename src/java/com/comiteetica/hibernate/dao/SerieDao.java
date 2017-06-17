/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comiteetica.hibernate.dao;

import com.comiteetica.hibernate.model.Serie;
import java.util.List;

/**
 *
 * @author rasec
 */
public interface SerieDao {
    public void create(Serie serie);
    public Serie read(String idSede);
    public void update(Serie serie);
    public void delete(Serie serie);
    public List<Serie> getAllSerie();
}
