package com.comiteetica.hibernate.model;
// Generated 04-ago-2017 18:25:51 by Hibernate Tools 4.3.1

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
 * PatrocinadorCro generated by hbm2java
 */
@Entity
@Table(name = "PatrocinadorCro",
         schema = "dbo",
         catalog = "ComiteEtica"
)
public class PatrocinadorCro implements java.io.Serializable {

    private PatrocinadorCroId id;
    private Cro cro;
    private Patrocinador patrocinador;
    private String descripcion;
    private String observacion;
    private String usuarioIngresa;
    private Date fechaIngreso;
    private String usuarioModifica;
    private Date fechaModificacion;
    private Set<Investigacion> investigacions = new HashSet<Investigacion>(0);

    public PatrocinadorCro() {
    }

    public PatrocinadorCro(PatrocinadorCroId id, Cro cro, Patrocinador patrocinador) {
        this.id = id;
        this.cro = cro;
        this.patrocinador = patrocinador;
    }

    public PatrocinadorCro(PatrocinadorCroId id, Cro cro, Patrocinador patrocinador, String descripcion, String observacion, String usuarioIngresa, Date fechaIngreso, String usuarioModifica, Date fechaModificacion, Set<Investigacion> investigacions) {
        this.id = id;
        this.cro = cro;
        this.patrocinador = patrocinador;
        this.descripcion = descripcion;
        this.observacion = observacion;
        this.usuarioIngresa = usuarioIngresa;
        this.fechaIngreso = fechaIngreso;
        this.usuarioModifica = usuarioModifica;
        this.fechaModificacion = fechaModificacion;
        this.investigacions = investigacions;
    }

    @EmbeddedId

    @AttributeOverrides({
        @AttributeOverride(name = "idPatrocinador", column = @Column(name = "IdPatrocinador", nullable = false, length = 10))
        , 
        @AttributeOverride(name = "idCro", column = @Column(name = "IdCro", nullable = false, length = 10))})
    public PatrocinadorCroId getId() {
        return this.id;
    }

    public void setId(PatrocinadorCroId id) {
        this.id = id;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IdCro", nullable = false, insertable = false, updatable = false)
    public Cro getCro() {
        return this.cro;
    }

    public void setCro(Cro cro) {
        this.cro = cro;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IdPatrocinador", nullable = false, insertable = false, updatable = false)
    public Patrocinador getPatrocinador() {
        return this.patrocinador;
    }

    public void setPatrocinador(Patrocinador patrocinador) {
        this.patrocinador = patrocinador;
    }

    @Column(name = "Descripcion", length = 250)
    public String getDescripcion() {
        return this.descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Column(name = "Observacion", length = 500)
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

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "patrocinadorCro")
    public Set<Investigacion> getInvestigacions() {
        return this.investigacions;
    }

    public void setInvestigacions(Set<Investigacion> investigacions) {
        this.investigacions = investigacions;
    }

}
