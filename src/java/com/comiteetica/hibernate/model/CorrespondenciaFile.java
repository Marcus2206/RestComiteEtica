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
 * CorrespondenciaFile generated by hbm2java
 */
@Entity
@Table(name = "CorrespondenciaFile",
         schema = "dbo",
         catalog = "ComiteEtica"
)
public class CorrespondenciaFile implements java.io.Serializable {

    private CorrespondenciaFileId id;
    private Correspondencia correspondencia;
    private String nombreArchivo;
    private String direccion;
    private String usuarioIngresa;
    private Date fechaIngreso;
    private String usuarioModifica;
    private Date fechaModificacion;

    public CorrespondenciaFile() {
    }

    public CorrespondenciaFile(CorrespondenciaFileId id, Correspondencia correspondencia) {
        this.id = id;
        this.correspondencia = correspondencia;
    }

    public CorrespondenciaFile(CorrespondenciaFileId id, Correspondencia correspondencia, String nombreArchivo, String direccion, String usuarioIngresa, Date fechaIngreso, String usuarioModifica, Date fechaModificacion) {
        this.id = id;
        this.correspondencia = correspondencia;
        this.nombreArchivo = nombreArchivo;
        this.direccion = direccion;
        this.usuarioIngresa = usuarioIngresa;
        this.fechaIngreso = fechaIngreso;
        this.usuarioModifica = usuarioModifica;
        this.fechaModificacion = fechaModificacion;
    }

    @EmbeddedId

    @AttributeOverrides({
        @AttributeOverride(name = "idCorrespondencia", column = @Column(name = "IdCorrespondencia", nullable = false, length = 10))
        , 
        @AttributeOverride(name = "fileDetalle", column = @Column(name = "FileDetalle", nullable = false))})
    public CorrespondenciaFileId getId() {
        return this.id;
    }

    public void setId(CorrespondenciaFileId id) {
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

    @Column(name = "NombreArchivo", length = 1000)
    public String getNombreArchivo() {
        return this.nombreArchivo;
    }

    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

    @Column(name = "Direccion", length = 1000)
    public String getDireccion() {
        return this.direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
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
