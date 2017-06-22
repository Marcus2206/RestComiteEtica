/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comiteetica.hibernate.dao;

import com.comiteetica.hibernate.model.SerieCorrelativo;
import com.comiteetica.hibernate.model.SerieCorrelativoId;
import java.util.Date;
import java.util.List;

/**
 *
 * @author rasec
 */
public interface SerieCorrelativoDao {
    public void create(SerieCorrelativo serieCorrelativo);
    public SerieCorrelativo read(SerieCorrelativoId serieCorrelativoId);
    public void update(SerieCorrelativo serieCorrelativo);
    public void delete(SerieCorrelativo serieCorrelativo);
    public List<SerieCorrelativo> getAllSerieCorrelativo(); 
    public SerieCorrelativo readNextSerieCorrelativo(String serieId,Date fechaTrabajo);
    public void beginTransaction();
    public void commit();
    public void close();
    public void rollback();
}
