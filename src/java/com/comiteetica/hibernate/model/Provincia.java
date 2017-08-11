package com.comiteetica.hibernate.model;
// Generated 09-ago-2017 12:35:36 by Hibernate Tools 4.3.1

import com.fasterxml.jackson.annotation.JsonIgnore;
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
 * Provincia generated by hbm2java
 */
@Entity
@Table(name = "Provincia",
        schema = "dbo",
        catalog = "ComiteEtica"
)
public class Provincia implements java.io.Serializable {

    private ProvinciaId id;
    private Departamento departamento;
    private String descripcion;
    private String usuarioIngresa;
    private Date fechaIngreso;
    private String usuarioModifica;
    private Date fechaModificacion;
    private Set<Distrito> distritos = new HashSet<Distrito>(0);

    public Provincia() {
    }

    public Provincia(ProvinciaId id, Departamento departamento) {
        this.id = id;
        this.departamento = departamento;
    }

    public Provincia(ProvinciaId id, Departamento departamento, String descripcion, String usuarioIngresa, Date fechaIngreso, String usuarioModifica, Date fechaModificacion, Set<Distrito> distritos) {
        this.id = id;
        this.departamento = departamento;
        this.descripcion = descripcion;
        this.usuarioIngresa = usuarioIngresa;
        this.fechaIngreso = fechaIngreso;
        this.usuarioModifica = usuarioModifica;
        this.fechaModificacion = fechaModificacion;
        this.distritos = distritos;
    }

    @EmbeddedId

    @AttributeOverrides({
        @AttributeOverride(name = "idDepartamento", column = @Column(name = "IdDepartamento", nullable = false, length = 2))
        , 
        @AttributeOverride(name = "idProvincia", column = @Column(name = "IdProvincia", nullable = false, length = 2))})
    public ProvinciaId getId() {
        return this.id;
    }

    public void setId(ProvinciaId id) {
        this.id = id;
    }

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IdDepartamento", nullable = false, insertable = false, updatable = false)
    public Departamento getDepartamento() {
        return this.departamento;
    }

    public void setDepartamento(Departamento departamento) {
        this.departamento = departamento;
    }

    @Column(name = "Descripcion", length = 250)
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
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "provincia")
    public Set<Distrito> getDistritos() {
        return this.distritos;
    }

    public void setDistritos(Set<Distrito> distritos) {
        this.distritos = distritos;
    }

}
