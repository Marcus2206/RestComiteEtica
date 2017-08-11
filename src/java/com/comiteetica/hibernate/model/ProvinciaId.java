package com.comiteetica.hibernate.model;
// Generated 09-ago-2017 12:35:36 by Hibernate Tools 4.3.1


import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * ProvinciaId generated by hbm2java
 */
@Embeddable
public class ProvinciaId  implements java.io.Serializable {


     private String idDepartamento;
     private String idProvincia;

    public ProvinciaId() {
    }

    public ProvinciaId(String idDepartamento, String idProvincia) {
       this.idDepartamento = idDepartamento;
       this.idProvincia = idProvincia;
    }
   


    @Column(name="IdDepartamento", nullable=false, length=2)
    public String getIdDepartamento() {
        return this.idDepartamento;
    }
    
    public void setIdDepartamento(String idDepartamento) {
        this.idDepartamento = idDepartamento;
    }


    @Column(name="IdProvincia", nullable=false, length=2)
    public String getIdProvincia() {
        return this.idProvincia;
    }
    
    public void setIdProvincia(String idProvincia) {
        this.idProvincia = idProvincia;
    }




}


