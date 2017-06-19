/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comiteetica.hibernate.service;

import com.comiteetica.hibernate.model.SerieCorrelativo;
import com.comiteetica.hibernate.model.SerieCorrelativoId;
import com.comiteetica.persistencia.BussinessException;
import java.util.Date;
import java.util.List;

/**
 *
 * @author rasec
 */
public interface SerieCorrelativoService {
    public void create(SerieCorrelativo serieCorrelativo)throws BussinessException;
    public SerieCorrelativo read(SerieCorrelativoId serieCorrelativoId)throws BussinessException;
    public void update(SerieCorrelativo serieCorrelativo)throws BussinessException;
    public void delete(SerieCorrelativo serieCorrelativo)throws BussinessException;
    public List<SerieCorrelativo> getAllSerieCorrelativo()throws BussinessException;
    public SerieCorrelativo readNextSerieCorrelativo(String serieId,Date fechaTrabajo)throws BussinessException;
}
