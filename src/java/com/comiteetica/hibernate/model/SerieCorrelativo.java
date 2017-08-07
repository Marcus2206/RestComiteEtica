package com.comiteetica.hibernate.model;
// Generated 04-ago-2017 18:25:51 by Hibernate Tools 4.3.1


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
 * SerieCorrelativo generated by hbm2java
 */
@Entity
@Table(name="SerieCorrelativo"
    ,schema="dbo"
    ,catalog="ComiteEtica"
)
public class SerieCorrelativo  implements java.io.Serializable {


     private SerieCorrelativoId id;
     private Serie serie;
     private Date inicioPeriodo;
     private String ultimoUsado;
     private String usuarioIngresa;
     private Date fechaIngreso;
     private String usuarioModifica;
     private Date fechaModificacion;

    public SerieCorrelativo() {
    }

	
    public SerieCorrelativo(SerieCorrelativoId id, Serie serie) {
        this.id = id;
        this.serie = serie;
    }
    public SerieCorrelativo(SerieCorrelativoId id, Serie serie, Date inicioPeriodo, String ultimoUsado, String usuarioIngresa, Date fechaIngreso, String usuarioModifica, Date fechaModificacion) {
       this.id = id;
       this.serie = serie;
       this.inicioPeriodo = inicioPeriodo;
       this.ultimoUsado = ultimoUsado;
       this.usuarioIngresa = usuarioIngresa;
       this.fechaIngreso = fechaIngreso;
       this.usuarioModifica = usuarioModifica;
       this.fechaModificacion = fechaModificacion;
    }
   
     @EmbeddedId

    
    @AttributeOverrides( {
        @AttributeOverride(name="idSerie", column=@Column(name="IdSerie", nullable=false, length=3) ), 
        @AttributeOverride(name="idCorrelativo", column=@Column(name="IdCorrelativo", nullable=false, length=7) ) } )
    public SerieCorrelativoId getId() {
        return this.id;
    }
    
    public void setId(SerieCorrelativoId id) {
        this.id = id;
    }

@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="IdSerie", nullable=false, insertable=false, updatable=false)
    public Serie getSerie() {
        return this.serie;
    }
    
    public void setSerie(Serie serie) {
        this.serie = serie;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="InicioPeriodo", length=23)
    public Date getInicioPeriodo() {
        return this.inicioPeriodo;
    }
    
    public void setInicioPeriodo(Date inicioPeriodo) {
        this.inicioPeriodo = inicioPeriodo;
    }

    
    @Column(name="UltimoUsado", length=7)
    public String getUltimoUsado() {
        return this.ultimoUsado;
    }
    
    public void setUltimoUsado(String ultimoUsado) {
        this.ultimoUsado = ultimoUsado;
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


