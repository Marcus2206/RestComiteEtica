package com.comiteetica.hibernate.model;
// Generated 11-ago-2017 18:56:32 by Hibernate Tools 4.3.1

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.math.BigDecimal;
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
 * CorrespondenciaServicio generated by hbm2java
 */
@Entity
@Table(name = "CorrespondenciaServicio",
         schema = "dbo",
         catalog = "ComiteEtica"
)
public class CorrespondenciaServicio implements java.io.Serializable {

    private CorrespondenciaServicioId id;
    private Correspondencia correspondencia;
    private String paramTipoServicio;
    private int transferido;
    private BigDecimal costo;
    private String observacion;
    private String usuarioIngresa;
    private Date fechaIngreso;
    private String usuarioModifica;
    private Date fechaModificacion;

    public CorrespondenciaServicio() {
    }

    public CorrespondenciaServicio(CorrespondenciaServicioId id, Correspondencia correspondencia) {
        this.id = id;
        this.correspondencia = correspondencia;
    }

    public CorrespondenciaServicio(CorrespondenciaServicioId id, Correspondencia correspondencia, String paramTipoServicio, int transferido, BigDecimal costo, String observacion, String usuarioIngresa, Date fechaIngreso, String usuarioModifica, Date fechaModificacion) {
        this.id = id;
        this.correspondencia = correspondencia;
        this.paramTipoServicio = paramTipoServicio;
        this.transferido = transferido;
        this.costo = costo;
        this.observacion = observacion;
        this.usuarioIngresa = usuarioIngresa;
        this.fechaIngreso = fechaIngreso;
        this.usuarioModifica = usuarioModifica;
        this.fechaModificacion = fechaModificacion;
    }

    @EmbeddedId

    @AttributeOverrides({
        @AttributeOverride(name = "idCorrespondencia", column = @Column(name = "IdCorrespondencia", nullable = false, length = 10))
        , 
        @AttributeOverride(name = "idCorrespondenciaServicio", column = @Column(name = "IdCorrespondenciaServicio", nullable = false))})
    public CorrespondenciaServicioId getId() {
        return this.id;
    }

    public void setId(CorrespondenciaServicioId id) {
        this.id = id;
    }

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IdCorrespondencia", nullable = false, insertable = false, updatable = false)
    public Correspondencia getCorrespondencia() {
        return this.correspondencia;
    }

    public void setCorrespondencia(Correspondencia correspondencia) {
        this.correspondencia = correspondencia;
    }

    @Column(name = "ParamTipoServicio", length = 4)
    public String getParamTipoServicio() {
        return this.paramTipoServicio;
    }

    public void setParamTipoServicio(String paramTipoServicio) {
        this.paramTipoServicio = paramTipoServicio;
    }

    @Column(name = "Transferido")
    public int getTransferido() {
        return this.transferido;
    }

    public void setTransferido(int transferido) {
        this.transferido = transferido;
    }

    @Column(name = "Costo", precision = 9)
    public BigDecimal getCosto() {
        return this.costo;
    }

    public void setCosto(BigDecimal costo) {
        this.costo = costo;
    }

    @Column(name = "Observacion", length = 150)
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
