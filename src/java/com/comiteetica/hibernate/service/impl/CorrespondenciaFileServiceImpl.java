/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comiteetica.hibernate.service.impl;

import com.comiteetica.hibernate.dao.CorrespondenciaFileDao;
import com.comiteetica.hibernate.model.CorrespondenciaFile;
import com.comiteetica.hibernate.model.CorrespondenciaFileId;
import com.comiteetica.hibernate.service.CorrespondenciaFileService;
import com.comiteetica.persistencia.BussinessException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author rasec
 */
@Service
public class CorrespondenciaFileServiceImpl implements CorrespondenciaFileService{

    @Autowired
    private CorrespondenciaFileDao correspondenciaFileDao;
    
    @Transactional
    @Override
    public void create(CorrespondenciaFile correspondenciaFile) throws BussinessException {
        correspondenciaFileDao.create(correspondenciaFile);
    }

    @Transactional
    @Override
    public CorrespondenciaFile read(CorrespondenciaFileId correspondenciaFileId) throws BussinessException {
        return correspondenciaFileDao.read(correspondenciaFileId);
    }

    @Transactional
    @Override
    public void update(CorrespondenciaFile correspondenciaFile) throws BussinessException {
        correspondenciaFileDao.update(correspondenciaFile);
    }

    @Transactional
    @Override
    public void delete(CorrespondenciaFile correspondenciaFile) throws BussinessException {
        correspondenciaFileDao.delete(correspondenciaFile);
    }

    @Transactional
    @Override
    public List<CorrespondenciaFile> getAllCorrespondenciaFile() throws BussinessException {
        return correspondenciaFileDao.getAllCorrespondenciaFile();
    }
    
}
