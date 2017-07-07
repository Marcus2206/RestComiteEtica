package com.comiteetica.hibernate.model;
// Generated 05-jul-2017 13:55:59 by Hibernate Tools 4.3.1


import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * RegistroBitacoraId generated by hbm2java
 */
@Embeddable
public class RegistroBitacoraId  implements java.io.Serializable {


     private String idRegistro;
     private int idBitacora;

    public RegistroBitacoraId() {
    }

    public RegistroBitacoraId(String idRegistro, int idBitacora) {
       this.idRegistro = idRegistro;
       this.idBitacora = idBitacora;
    }
   


    @Column(name="IdRegistro", nullable=false, length=10)
    public String getIdRegistro() {
        return this.idRegistro;
    }
    
    public void setIdRegistro(String idRegistro) {
        this.idRegistro = idRegistro;
    }


    @Column(name="IdBitacora", nullable=false)
    public int getIdBitacora() {
        return this.idBitacora;
    }
    
    public void setIdBitacora(int idBitacora) {
        this.idBitacora = idBitacora;
    }


   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof RegistroBitacoraId) ) return false;
		 RegistroBitacoraId castOther = ( RegistroBitacoraId ) other; 
         
		 return ( (this.getIdRegistro()==castOther.getIdRegistro()) || ( this.getIdRegistro()!=null && castOther.getIdRegistro()!=null && this.getIdRegistro().equals(castOther.getIdRegistro()) ) )
 && (this.getIdBitacora()==castOther.getIdBitacora());
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getIdRegistro() == null ? 0 : this.getIdRegistro().hashCode() );
         result = 37 * result + this.getIdBitacora();
         return result;
   }   


}


