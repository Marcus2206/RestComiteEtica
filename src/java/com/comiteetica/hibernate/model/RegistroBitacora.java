package com.comiteetica.hibernate.model;
// Generated 17-jun-2017 14:55:19 by Hibernate Tools 4.3.1


import java.util.Date;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * RegistroBitacora generated by hbm2java
 */
@Entity
@Table(name="RegistroBitacora"
    ,schema="dbo"
    ,catalog="ComiteEtica"
)
public class RegistroBitacora  implements java.io.Serializable {


     private RegistroBitacoraId id;
     private Registro registro;
     private String detalle;
     private Date fecha;
     private String usuarioIngresa;
     private Date fechaIngreso;
     private String usuarioModifica;
     private Date fechaModificacion;

    public RegistroBitacora() {
    }

	
    public RegistroBitacora(RegistroBitacoraId id, Registro registro) {
        this.id = id;
        this.registro = registro;
    }
    public RegistroBitacora(RegistroBitacoraId id, Registro registro, String detalle, Date fecha, String usuarioIngresa, Date fechaIngreso, String usuarioModifica, Date fechaModificacion) {
       this.id = id;
       this.registro = registro;
       this.detalle = detalle;
       this.fecha = fecha;
       this.usuarioIngresa = usuarioIngresa;
       this.fechaIngreso = fechaIngreso;
       this.usuarioModifica = usuarioModifica;
       this.fechaModificacion = fechaModificacion;
    }
   
     @EmbeddedId

    
    @AttributeOverrides( {
        @AttributeOverride(name="idRegistro", column=@Column(name="IdRegistro", nullable=false, length=10) ), 
        @AttributeOverride(name="idBitacora", column=@Column(name="IdBitacora", nullable=false) ) } )
    public RegistroBitacoraId getId() {
        return this.id;
    }
    
    public void setId(RegistroBitacoraId id) {
        this.id = id;
    }

@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="IdRegistro", nullable=false, insertable=false, updatable=false)
    public Registro getRegistro() {
        return this.registro;
    }
    
    public void setRegistro(Registro registro) {
        this.registro = registro;
    }

    
    @Column(name="Detalle", length=500)
    public String getDetalle() {
        return this.detalle;
    }
    
    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="Fecha", length=23)
    public Date getFecha() {
        return this.fecha;
    }
    
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    
    @Column(name="UsuarioIngresa", length=50)
    public String getUsuarioIngresa() {
        return this.usuarioIngresa;
    }
    
    public void setUsuarioIngresa(String usuarioIngresa) {
        this.usuarioIngresa = usuarioIngresa;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="FechaIngreso", length=23)
    public Date getFechaIngreso() {
        return this.fechaIngreso;
    }
    
    public void setFechaIngreso(Date fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    
    @Column(name="UsuarioModifica", length=50)
    public String getUsuarioModifica() {
        return this.usuarioModifica;
    }
    
    public void setUsuarioModifica(String usuarioModifica) {
        this.usuarioModifica = usuarioModifica;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="FechaModificacion", length=23)
    public Date getFechaModificacion() {
        return this.fechaModificacion;
    }
    
    public void setFechaModificacion(Date fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }




}


