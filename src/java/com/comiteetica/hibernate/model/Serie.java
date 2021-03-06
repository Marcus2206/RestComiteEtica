package com.comiteetica.hibernate.model;
// Generated 11-ago-2017 18:56:32 by Hibernate Tools 4.3.1

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Serie generated by hbm2java
 */
@Entity
@Table(name = "Serie",
         schema = "dbo",
         catalog = "ComiteEtica"
)
public class Serie implements java.io.Serializable {

    private String idSerie;
    private String descripcion;
    private String usuarioIngresa;
    private Date fechaIngreso;
    private String usuarioModifica;
    private Date fechaModificacion;
    private Set<SerieCorrelativo> serieCorrelativos = new HashSet<SerieCorrelativo>(0);

    public Serie() {
    }

    public Serie(String idSerie) {
        this.idSerie = idSerie;
    }

    public Serie(String idSerie, String descripcion, String usuarioIngresa, Date fechaIngreso, String usuarioModifica, Date fechaModificacion, Set<SerieCorrelativo> serieCorrelativos) {
        this.idSerie = idSerie;
        this.descripcion = descripcion;
        this.usuarioIngresa = usuarioIngresa;
        this.fechaIngreso = fechaIngreso;
        this.usuarioModifica = usuarioModifica;
        this.fechaModificacion = fechaModificacion;
        this.serieCorrelativos = serieCorrelativos;
    }

    @Id

    @Column(name = "IdSerie", nullable = false, length = 3)
    public String getIdSerie() {
        return this.idSerie;
    }

    public void setIdSerie(String idSerie) {
        this.idSerie = idSerie;
    }

    @Column(name = "Descripcion", length = 50)
    public String getDescripcion() {
        return this.descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
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

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "serie")
    public Set<SerieCorrelativo> getSerieCorrelativos() {
        return this.serieCorrelativos;
    }

    public void setSerieCorrelativos(Set<SerieCorrelativo> serieCorrelativos) {
        this.serieCorrelativos = serieCorrelativos;
    }

}
