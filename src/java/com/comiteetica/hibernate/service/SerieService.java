/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comiteetica.hibernate.service;

import com.comiteetica.hibernate.model.Serie;
import com.comiteetica.persistencia.BussinessException;
import java.util.List;

/**
 *
 * @author rasec
 */
public interface SerieService {
    public void create(Serie serie)throws BussinessException;
    public Serie read(String idSede)throws BussinessException;
    public void update(Serie serie)throws BussinessException;
    public void delete(Serie serie)throws BussinessException;
    public List<Serie> getAllSerie()throws BussinessException;
}
