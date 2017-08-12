package com.comiteetica.hibernate.model;
// Generated 11-ago-2017 18:56:32 by Hibernate Tools 4.3.1


import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * PatrocinadorCroId generated by hbm2java
 */
@Embeddable
public class PatrocinadorCroId  implements java.io.Serializable {


     private String idPatrocinador;
     private String idCro;

    public PatrocinadorCroId() {
    }

    public PatrocinadorCroId(String idPatrocinador, String idCro) {
       this.idPatrocinador = idPatrocinador;
       this.idCro = idCro;
    }
   


    @Column(name="IdPatrocinador", nullable=false, length=10)
    public String getIdPatrocinador() {
        return this.idPatrocinador;
    }
    
    public void setIdPatrocinador(String idPatrocinador) {
        this.idPatrocinador = idPatrocinador;
    }


    @Column(name="IdCro", nullable=false, length=10)
    public String getIdCro() {
        return this.idCro;
    }
    
    public void setIdCro(String idCro) {
        this.idCro = idCro;
    }




}


