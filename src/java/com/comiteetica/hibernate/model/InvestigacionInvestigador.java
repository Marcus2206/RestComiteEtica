package com.comiteetica.hibernate.model;
// Generated 05-jul-2017 13:55:59 by Hibernate Tools 4.3.1


import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * InvestigacionInvestigador generated by hbm2java
 */
@Entity
@Table(name="InvestigacionInvestigador"
    ,schema="dbo"
    ,catalog="ComiteEtica"
)
public class InvestigacionInvestigador  implements java.io.Serializable {


     private InvestigacionInvestigadorId id;
     private Investigacion investigacion;
     private Investigador investigador;
     private String observacion;
     private String usuarioIngresa;
     private Date fechaIngreso;
     private String usuarioModifica;
     private Date fechaModificacion;
     private Set<Registro> registros = new HashSet<Registro>(0);

    public InvestigacionInvestigador() {
    }

	
    public InvestigacionInvestigador(InvestigacionInvestigadorId id, Investigacion investigacion, Investigador investigador) {
        this.id = id;
        this.investigacion = investigacion;
        this.investigador = investigador;
    }
    public InvestigacionInvestigador(InvestigacionInvestigadorId id, Investigacion investigacion, Investigador investigador, String observacion, String usuarioIngresa, Date fechaIngreso, String usuarioModifica, Date fechaModificacion, Set<Registro> registros) {
       this.id = id;
       this.investigacion = investigacion;
       this.investigador = investigador;
       this.observacion = observacion;
       this.usuarioIngresa = usuarioIngresa;
       this.fechaIngreso = fechaIngreso;
       this.usuarioModifica = usuarioModifica;
       this.fechaModificacion = fechaModificacion;
       this.registros = registros;
    }
   
     @EmbeddedId

    
    @AttributeOverrides( {
        @AttributeOverride(name="idInvestigacion", column=@Column(name="IdInvestigacion", nullable=false, length=10) ), 
        @AttributeOverride(name="idInvestigador", column=@Column(name="IdInvestigador", nullable=false, length=10) ) } )
    public InvestigacionInvestigadorId getId() {
        return this.id;
    }
    
    public void setId(InvestigacionInvestigadorId id) {
        this.id = id;
    }

@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="IdInvestigacion", nullable=false, insertable=false, updatable=false)
    public Investigacion getInvestigacion() {
        return this.investigacion;
    }
    
    public void setInvestigacion(Investigacion investigacion) {
        this.investigacion = investigacion;
    }

@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="IdInvestigador", nullable=false, insertable=false, updatable=false)
    public Investigador getInvestigador() {
        return this.investigador;
    }
    
    public void setInvestigador(Investigador investigador) {
        this.investigador = investigador;
    }

    
    @Column(name="Observacion", length=250)
    public String getObservacion() {
        return this.observacion;
    }
    
    public void setObservacion(String observacion) {
        this.observacion = observacion;
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

@OneToMany(fetch=FetchType.LAZY, mappedBy="investigacionInvestigador")
    public Set<Registro> getRegistros() {
        return this.registros;
    }
    
    public void setRegistros(Set<Registro> registros) {
        this.registros = registros;
    }




}


