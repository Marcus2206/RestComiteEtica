package com.comiteetica.hibernate.model;
// Generated 11-ago-2017 18:56:32 by Hibernate Tools 4.3.1

import com.fasterxml.jackson.annotation.JsonIgnore;
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
 * InvestigacionSede generated by hbm2java
 */
@Entity
@Table(name = "InvestigacionSede",
         schema = "dbo",
         catalog = "ComiteEtica"
)
public class InvestigacionSede implements java.io.Serializable {

    private InvestigacionSedeId id;
    private Investigacion investigacion;
    private Sede sede;
    private String observacion;
    private String usuarioIngresa;
    private Date fechaIngreso;
    private String usuarioModifica;
    private Date fechaModificacion;

    public InvestigacionSede() {
    }

    public InvestigacionSede(InvestigacionSedeId id, Investigacion investigacion, Sede sede) {
        this.id = id;
        this.investigacion = investigacion;
        this.sede = sede;
    }

    public InvestigacionSede(InvestigacionSedeId id, Investigacion investigacion, Sede sede, String observacion, String usuarioIngresa, Date fechaIngreso, String usuarioModifica, Date fechaModificacion) {
        this.id = id;
        this.investigacion = investigacion;
        this.sede = sede;
        this.observacion = observacion;
        this.usuarioIngresa = usuarioIngresa;
        this.fechaIngreso = fechaIngreso;
        this.usuarioModifica = usuarioModifica;
        this.fechaModificacion = fechaModificacion;
    }

    @EmbeddedId

    @AttributeOverrides({
        @AttributeOverride(name = "idInvestigacion", column = @Column(name = "IdInvestigacion", nullable = false, length = 10))
        , 
        @AttributeOverride(name = "idSede", column = @Column(name = "IdSede", nullable = false, length = 10))})
    public InvestigacionSedeId getId() {
        return this.id;
    }

    public void setId(InvestigacionSedeId id) {
        this.id = id;
    }

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IdInvestigacion", nullable = false, insertable = false, updatable = false)
    public Investigacion getInvestigacion() {
        return this.investigacion;
    }

    public void setInvestigacion(Investigacion investigacion) {
        this.investigacion = investigacion;
    }

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IdSede", nullable = false, insertable = false, updatable = false)
    public Sede getSede() {
        return this.sede;
    }

    public void setSede(Sede sede) {
        this.sede = sede;
    }

    @Column(name = "Observacion", length = 250)
    public String getObservacion() {
        return this.observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    @Column(name = "UsuarioIngresa", length = 50)
    public String getUsuarioIngresa() {
        return this.usuarioIngresa;
    }

    public void setUsuarioIngresa(String usuarioIngresa) {
        this.usuarioIngresa = usuarioIngresa;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "FechaIngreso", length = 23)
    public Date getFechaIngreso() {
        return this.fechaIngreso;
    }

    public void setFechaIngreso(Date fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    @Column(name = "UsuarioModifica", length = 50)
    public String getUsuarioModifica() {
        return this.usuarioModifica;
    }

    public void setUsuarioModifica(String usuarioModifica) {
        this.usuarioModifica = usuarioModifica;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "FechaModificacion", length = 23)
    public Date getFechaModificacion() {
        return this.fechaModificacion;
    }

    public void setFechaModificacion(Date fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

}
