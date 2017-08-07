package com.comiteetica.hibernate.model;
// Generated 04-ago-2017 18:25:51 by Hibernate Tools 4.3.1


import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * DistritoId generated by hbm2java
 */
@Embeddable
public class DistritoId  implements java.io.Serializable {


     private String idDepartamento;
     private String idProvincia;
     private String idDistrito;

    public DistritoId() {
    }

    public DistritoId(String idDepartamento, String idProvincia, String idDistrito) {
       this.idDepartamento = idDepartamento;
       this.idProvincia = idProvincia;
       this.idDistrito = idDistrito;
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


    @Column(name="IdDistrito", nullable=false, length=2)
    public String getIdDistrito() {
        return this.idDistrito;
    }
    
    public void setIdDistrito(String idDistrito) {
        this.idDistrito = idDistrito;
    }


   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof DistritoId) ) return false;
		 DistritoId castOther = ( DistritoId ) other; 
         
		 return ( (this.getIdDepartamento()==castOther.getIdDepartamento()) || ( this.getIdDepartamento()!=null && castOther.getIdDepartamento()!=null && this.getIdDepartamento().equals(castOther.getIdDepartamento()) ) )
 && ( (this.getIdProvincia()==castOther.getIdProvincia()) || ( this.getIdProvincia()!=null && castOther.getIdProvincia()!=null && this.getIdProvincia().equals(castOther.getIdProvincia()) ) )
 && ( (this.getIdDistrito()==castOther.getIdDistrito()) || ( this.getIdDistrito()!=null && castOther.getIdDistrito()!=null && this.getIdDistrito().equals(castOther.getIdDistrito()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getIdDepartamento() == null ? 0 : this.getIdDepartamento().hashCode() );
         result = 37 * result + ( getIdProvincia() == null ? 0 : this.getIdProvincia().hashCode() );
         result = 37 * result + ( getIdDistrito() == null ? 0 : this.getIdDistrito().hashCode() );
         return result;
   }   


}


