package com.comiteetica.hibernate.model;
// Generated 04-ago-2017 18:25:51 by Hibernate Tools 4.3.1

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
 * InvestigacionCoordinador generated by hbm2java
 */
@Entity
@Table(name = "InvestigacionCoordinador",
        schema = "dbo",
        catalog = "ComiteEtica"
)
public class InvestigacionCoordinador implements java.io.Serializable {

    private InvestigacionCoordinadorId id;
    private Coordinador coordinador;
    private Investigacion investigacion;
    private String observacion;
    private String usuarioIngresa;
    private Date fechaIngreso;
    private String usuarioModifica;
    private Date fechaModificacion;

    public InvestigacionCoordinador() {
    }

    public InvestigacionCoordinador(InvestigacionCoordinadorId id, Coordinador coordinador, Investigacion investigacion) {
        this.id = id;
        this.coordinador = coordinador;
        this.investigacion = investigacion;
    }

    public InvestigacionCoordinador(InvestigacionCoordinadorId id, Coordinador coordinador, Investigacion investigacion, String observacion, String usuarioIngresa, Date fechaIngreso, String usuarioModifica, Date fechaModificacion) {
        this.id = id;
        this.coordinador = coordinador;
        this.investigacion = investigacion;
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
        @AttributeOverride(name = "idCoordinador", column = @Column(name = "IdCoordinador", nullable = false, length = 10))})
    public InvestigacionCoordinadorId getId() {
        return this.id;
    }

    public void setId(InvestigacionCoordinadorId id) {
        this.id = id;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IdCoordinador", nullable = false, insertable = false, updatable = false)
    public Coordinador getCoordinador() {
        return this.coordinador;
    }

    public void setCoordinador(Coordinador coordinador) {
        this.coordinador = coordinador;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IdInvestigacion", nullable = false, insertable = false, updatable = false)
    public Investigacion getInvestigacion() {
        return this.investigacion;
    }

    public void setInvestigacion(Investigacion investigacion) {
        this.investigacion = investigacion;
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
