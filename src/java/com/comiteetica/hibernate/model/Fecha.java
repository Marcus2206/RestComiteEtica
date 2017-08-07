package com.comiteetica.hibernate.model;
// Generated 04-ago-2017 18:25:51 by Hibernate Tools 4.3.1


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Fecha generated by hbm2java
 */
@Entity
@Table(name="Fecha"
    ,schema="dbo"
    ,catalog="ComiteEtica"
)
public class Fecha  implements java.io.Serializable {


     private byte id;

    public Fecha() {
    }

    public Fecha(byte id) {
       this.id = id;
    }
   
     @Id 

    
    @Column(name="id", unique=true, nullable=false)
    public byte getId() {
        return this.id;
    }
    
    public void setId(byte id) {
        this.id = id;
    }




}


