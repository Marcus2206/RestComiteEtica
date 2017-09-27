/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comiteetica.hibernate.dao;

import com.comiteetica.hibernate.model.CorrespondenciaServicio;
import com.comiteetica.hibernate.model.CorrespondenciaServicioId;
import java.util.List;

/**
 *
 * @author rasec
 */
public interface CorrespondenciaServicioDao {

    public void create(CorrespondenciaServicio correspondenciaServicio);

    public CorrespondenciaServicio read(CorrespondenciaServicioId id);
    
    public List<CorrespondenciaServicio> readByIdCorrespondencia(String idCorrespondencia);

    public void update(CorrespondenciaServicio correspondenciaServicio);

    public void delete(CorrespondenciaServicio correspondenciaServicio);

    public List<CorrespondenciaServicio> getAllCorrespondenciaServicio();

    public void beginTransaction();

    public void commit();

    public void close();

    public void rollback();

    public List<Object> getAllCorrespondenciaServicioSinPagoList();

    public int getNextServicioDetalleByIdCorrespondencia(String idCorrespondencia);

    public List<Object> getAllCorrespondenciaServicioByCorrespondencia(String idCorrespondencia);

}
