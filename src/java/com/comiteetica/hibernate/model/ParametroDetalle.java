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
 * ParametroDetalle generated by hbm2java
 */
@Entity
@Table(name="ParametroDetalle"
    ,schema="dbo"
    ,catalog="ComiteEtica"
)
public class ParametroDetalle  implements java.io.Serializable {


     private ParametroDetalleId id;
     private Parametro parametro;
     private String descripcion;
     private String usuarioIngresa;
     private Date fechaIngreso;
     private String usuarioModifica;
     private Date fechaModificacion;

    public ParametroDetalle() {
    }

	
    public ParametroDetalle(ParametroDetalleId id, Parametro parametro) {
        this.id = id;
        this.parametro = parametro;
    }
    public ParametroDetalle(ParametroDetalleId id, Parametro parametro, String descripcion, String usuarioIngresa, Date fechaIngreso, String usuarioModifica, Date fechaModificacion) {
       this.id = id;
       this.parametro = parametro;
       this.descripcion = descripcion;
       this.usuarioIngresa = usuarioIngresa;
       this.fechaIngreso = fechaIngreso;
       this.usuarioModifica = usuarioModifica;
       this.fechaModificacion = fechaModificacion;
    }
   
     @EmbeddedId

    
    @AttributeOverrides( {
        @AttributeOverride(name="idParametro", column=@Column(name="IdParametro", nullable=false, length=4) ), 
        @AttributeOverride(name="idParametroDetalle", column=@Column(name="IdParametroDetalle", nullable=false, length=4) ) } )
    public ParametroDetalleId getId() {
        return this.id;
    }
    
    public void setId(ParametroDetalleId id) {
        this.id = id;
    }

@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="IdParametro", nullable=false, insertable=false, updatable=false)
    public Parametro getParametro() {
        return this.parametro;
    }
    
    public void setParametro(Parametro parametro) {
        this.parametro = parametro;
    }

    
    @Column(name="Descripcion", length=250)
    public String getDescripcion() {
        return this.descripcion;
    }
    
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
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


