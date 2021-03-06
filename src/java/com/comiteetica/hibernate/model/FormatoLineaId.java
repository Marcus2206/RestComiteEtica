package com.comiteetica.hibernate.model;
// Generated 11-sep-2017 10:52:48 by Hibernate Tools 4.3.1


import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * FormatoLineaId generated by hbm2java
 */
@Embeddable
public class FormatoLineaId  implements java.io.Serializable {


     private int idFormato;
     private int idFormatoLinea;

    public FormatoLineaId() {
    }

    public FormatoLineaId(int idFormato, int idFormatoLinea) {
       this.idFormato = idFormato;
       this.idFormatoLinea = idFormatoLinea;
    }
   


    @Column(name="IdFormato", nullable=false)
    public int getIdFormato() {
        return this.idFormato;
    }
    
    public void setIdFormato(int idFormato) {
        this.idFormato = idFormato;
    }


    @Column(name="IdFormatoLinea", nullable=false)
    public int getIdFormatoLinea() {
        return this.idFormatoLinea;
    }
    
    public void setIdFormatoLinea(int idFormatoLinea) {
        this.idFormatoLinea = idFormatoLinea;
    }


   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof FormatoLineaId) ) return false;
		 FormatoLineaId castOther = ( FormatoLineaId ) other; 
         
		 return (this.getIdFormato()==castOther.getIdFormato())
 && (this.getIdFormatoLinea()==castOther.getIdFormatoLinea());
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + this.getIdFormato();
         result = 37 * result + this.getIdFormatoLinea();
         return result;
   }   


}


