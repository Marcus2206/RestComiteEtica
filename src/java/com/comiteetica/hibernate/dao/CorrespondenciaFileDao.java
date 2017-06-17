/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comiteetica.hibernate.dao;

import com.comiteetica.hibernate.model.CorrespondenciaFile;
import com.comiteetica.hibernate.model.CorrespondenciaFileId;
import java.util.List;

/**
 *
 * @author rasec
 */
public interface CorrespondenciaFileDao {
    public void create(CorrespondenciaFile correspondenciaFile);
    public CorrespondenciaFile read(CorrespondenciaFileId correspondenciaFileId);
    public void update(CorrespondenciaFile correspondenciaFile);
    public void delete(CorrespondenciaFile correspondenciaFile);
    public List<CorrespondenciaFile> getAllCorrespondenciaFile();
}
