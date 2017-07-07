/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comiteetica.hibernate.service;

import com.comiteetica.hibernate.model.InvestigacionInvestigador;
import com.comiteetica.hibernate.model.InvestigacionInvestigadorId;
import com.comiteetica.persistencia.BussinessException;
import java.util.List;

/**
 *
 * @author rasec
 */
public interface InvestigacionInvestigadorService {
    public void create(InvestigacionInvestigador investigacionInvestigador)  throws BussinessException;
    public InvestigacionInvestigador read(InvestigacionInvestigadorId investigacionInvestigadorId)  throws BussinessException;
    public void update(InvestigacionInvestigador investigacionInvestigador)  throws BussinessException;
    public void delete(InvestigacionInvestigador investigacionInvestigador)  throws BussinessException;
    public List<InvestigacionInvestigador> getAllInvestigacionInvestigador()  throws BussinessException; 
    public void beginTransaction()throws BussinessException;
    public void commit()throws BussinessException;
    public void close()throws BussinessException;
    public void rollback()throws BussinessException;
    public List<Object> getInvestigacionInvestigadorByIdInvestigacion(String idInvestigacion) throws BussinessException;
}
