/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comiteetica.hibernate.service;

import com.comiteetica.hibernate.model.CorrespondenciaFile;
import com.comiteetica.hibernate.model.CorrespondenciaFileId;
import com.comiteetica.persistencia.BussinessException;
import java.util.List;

/**
 *
 * @author rasec
 */
public interface CorrespondenciaFileService {

    public void create(CorrespondenciaFile correspondenciaFile) throws BussinessException;

    public CorrespondenciaFile read(CorrespondenciaFileId correspondenciaFileId) throws BussinessException;

    public void update(CorrespondenciaFile correspondenciaFile) throws BussinessException;

    public void delete(CorrespondenciaFile correspondenciaFile) throws BussinessException;

    public List<CorrespondenciaFile> getAllCorrespondenciaFile() throws BussinessException;

    public void beginTransaction() throws BussinessException;

    public void commit() throws BussinessException;

    public void close() throws BussinessException;

    public void rollback() throws BussinessException;

    public List<CorrespondenciaFile> getAllCorrespondenciaFileByIdCorrepondencia(String idCorrespondencia) throws BussinessException;

    public int getNextFileDetalleByIdCorrespondencia(String idCorrespondencia) throws BussinessException;

    public void deleteAllCorrespondencia(String idCorrespondencia) throws BussinessException;
}
