package com.comiteetica.hibernate.model;
// Generated 16/06/2017 12:59:54 PM by Hibernate Tools 4.3.1


import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * InvestigacionCoordinadorId generated by hbm2java
 */
@Embeddable
public class InvestigacionCoordinadorId  implements java.io.Serializable {


     private String idInvestigacion;
     private String idCoordinador;

    public InvestigacionCoordinadorId() {
    }

    public InvestigacionCoordinadorId(String idInvestigacion, String idCoordinador) {
       this.idInvestigacion = idInvestigacion;
       this.idCoordinador = idCoordinador;
    }
   


    @Column(name="IdInvestigacion", nullable=false, length=10)
    public String getIdInvestigacion() {
        return this.idInvestigacion;
    }
    
    public void setIdInvestigacion(String idInvestigacion) {
        this.idInvestigacion = idInvestigacion;
    }


    @Column(name="IdCoordinador", nullable=false, length=10)
    public String getIdCoordinador() {
        return this.idCoordinador;
    }
    
    public void setIdCoordinador(String idCoordinador) {
        this.idCoordinador = idCoordinador;
    }


   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof InvestigacionCoordinadorId) ) return false;
		 InvestigacionCoordinadorId castOther = ( InvestigacionCoordinadorId ) other; 
         
		 return ( (this.getIdInvestigacion()==castOther.getIdInvestigacion()) || ( this.getIdInvestigacion()!=null && castOther.getIdInvestigacion()!=null && this.getIdInvestigacion().equals(castOther.getIdInvestigacion()) ) )
 && ( (this.getIdCoordinador()==castOther.getIdCoordinador()) || ( this.getIdCoordinador()!=null && castOther.getIdCoordinador()!=null && this.getIdCoordinador().equals(castOther.getIdCoordinador()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getIdInvestigacion() == null ? 0 : this.getIdInvestigacion().hashCode() );
         result = 37 * result + ( getIdCoordinador() == null ? 0 : this.getIdCoordinador().hashCode() );
         return result;
   }   


}


