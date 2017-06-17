/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comiteetica.hibernate.dao;

import com.comiteetica.hibernate.model.InvestigacionInvestigador;
import com.comiteetica.hibernate.model.InvestigacionInvestigadorId;
import java.util.List;

/**
 *
 * @author rasec
 */
public interface InvestigacionInvestigadorDao {
    public void create(InvestigacionInvestigador investigacionInvestigador);
    public InvestigacionInvestigador read(InvestigacionInvestigadorId investigacionInvestigadorId);
    public void update(InvestigacionInvestigador investigacionInvestigador);
    public void delete(InvestigacionInvestigador investigacionInvestigador);
    public List<InvestigacionInvestigador> getAllInvestigacionInvestigador(); 
}
