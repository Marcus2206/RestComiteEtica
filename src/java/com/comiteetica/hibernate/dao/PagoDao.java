/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comiteetica.hibernate.dao;

import com.comiteetica.hibernate.model.Pago;
import java.util.List;

/**
 *
 * @author rasec
 */
public interface PagoDao {

    public void create(Pago pago);

    public Pago read(String idpago);

    public void update(Pago pago);

    public void delete(Pago pago);

    public List<Pago> getAllPago();

    public void beginTransaction();

    public void commit();

    public void close();

    public void rollback();

    public List<Object> getAllPagoList();

    public int sendMail(String idPago);

}
