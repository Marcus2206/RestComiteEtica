/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comiteetica.hibernate.dao;

import com.comiteetica.hibernate.model.ParametroDetalle;
import com.comiteetica.hibernate.model.ParametroDetalleId;
import java.util.List;

/**
 *
 * @author rasec
 */
public interface ParametroDetalleDao {

    public void create(ParametroDetalle parametroDetalle);

    public ParametroDetalle read(ParametroDetalleId parametroDetalleId);

    public void update(ParametroDetalle parametroDetalle);

    public void delete(ParametroDetalle parametroDetalle);

    public List<ParametroDetalle> getAllParametroDetalle();

    public List<ParametroDetalle> getParametroDetalleByIdParametro(String idParametro);

    public void beginTransaction();

    public void commit();

    public void close();

    public void rollback();

    public String getNextParametroDetalleByIdParametro(String idParametro);
}
