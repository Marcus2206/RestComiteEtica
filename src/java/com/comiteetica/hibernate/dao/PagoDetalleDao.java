/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comiteetica.hibernate.dao;

import com.comiteetica.hibernate.model.PagoDetalle;
import com.comiteetica.hibernate.model.PagoDetalleId;
import java.util.List;

/**
 *
 * @author rasec
 */
public interface PagoDetalleDao {

    public void create(PagoDetalle pagoDetalle);

    public PagoDetalle read(PagoDetalleId idPagoDetalle);

    public void update(PagoDetalle pagoDetalle);

    public void delete(PagoDetalle pagoDetalle);

    public List<PagoDetalle> getAllPagoDetalle();

    public void beginTransaction();

    public void commit();

    public void close();

    public void rollback();

    public List<Object> getAllPagoDetalleList();

    public int getNextPagoDetalleByIdPago(String idPago);

    public List<Object> getAllPagoDetalleByPagoList(String idPago);
}
