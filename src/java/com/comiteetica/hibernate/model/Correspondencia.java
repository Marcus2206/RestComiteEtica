package com.comiteetica.hibernate.model;
// Generated 09-ago-2017 12:35:36 by Hibernate Tools 4.3.1

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Correspondencia generated by hbm2java
 */
@Entity
@Table(name = "Correspondencia",
         schema = "dbo",
         catalog = "ComiteEtica"
)
public class Correspondencia implements java.io.Serializable {

    private String idCorrespondencia;
    private Registro registro;
    private Date fechaCorrespondencia;
    private Date fechaCarta;
    private String paramTipoServicio;
    private String otro;
    private String paramDistribucion;
    private Date fechaSesion;
    private Boolean enviarCorreo;
    private Boolean enviado;
    private String equivalenciaCorrelativo;
    private String usuarioIngresa;
    private Date fechaIngreso;
    private String usuarioModifica;
    private Date fechaModificacion;
    private Set<CorrespondenciaServicio> correspondenciaServicios = new HashSet<CorrespondenciaServicio>(0);
    private Set<CorrespondenciaFile> correspondenciaFiles = new HashSet<CorrespondenciaFile>(0);

    public Correspondencia() {
    }

    public Correspondencia(String idCorrespondencia) {
        this.idCorrespondencia = idCorrespondencia;
    }

    public Correspondencia(String idCorrespondencia, Registro registro, Date fechaCorrespondencia, Date fechaCarta, String paramTipoServicio, String otro, String paramDistribucion, Date fechaSesion, Boolean enviarCorreo, Boolean enviado, String equivalenciaCorrelativo, String usuarioIngresa, Date fechaIngreso, String usuarioModifica, Date fechaModificacion, Set<CorrespondenciaServicio> correspondenciaServicios, Set<CorrespondenciaFile> correspondenciaFiles) {
        this.idCorrespondencia = idCorrespondencia;
        this.registro = registro;
        this.fechaCorrespondencia = fechaCorrespondencia;
        this.fechaCarta = fechaCarta;
        this.paramTipoServicio = paramTipoServicio;
        this.otro = otro;
        this.paramDistribucion = paramDistribucion;
        this.fechaSesion = fechaSesion;
        this.enviarCorreo = enviarCorreo;
        this.enviado = enviado;
        this.equivalenciaCorrelativo = equivalenciaCorrelativo;
        this.usuarioIngresa = usuarioIngresa;
        this.fechaIngreso = fechaIngreso;
        this.usuarioModifica = usuarioModifica;
        this.fechaModificacion = fechaModificacion;
        this.correspondenciaServicios = correspondenciaServicios;
        this.correspondenciaFiles = correspondenciaFiles;
    }

    @Id

    @Column(name = "IdCorrespondencia", nullable = false, length = 10)
    public String getIdCorrespondencia() {
        return this.idCorrespondencia;
    }

    public void setIdCorrespondencia(String idCorrespondencia) {
        this.idCorrespondencia = idCorrespondencia;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IdRegistro")
    public Registro getRegistro() {
        return this.registro;
    }

    public void setRegistro(Registro registro) {
        this.registro = registro;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "FechaCorrespondencia", length = 23)
    public Date getFechaCorrespondencia() {
        return this.fechaCorrespondencia;
    }

    public void setFechaCorrespondencia(Date fechaCorrespondencia) {
        this.fechaCorrespondencia = fechaCorrespondencia;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "FechaCarta", length = 23)
    public Date getFechaCarta() {
        return this.fechaCarta;
    }

    public void setFechaCarta(Date fechaCarta) {
        this.fechaCarta = fechaCarta;
    }

    @Column(name = "ParamTipoServicio", length = 4)
    public String getParamTipoServicio() {
        return this.paramTipoServicio;
    }

    public void setParamTipoServicio(String paramTipoServicio) {
        this.paramTipoServicio = paramTipoServicio;
    }

    @Column(name = "Otro", length = 250)
    public String getOtro() {
        return this.otro;
    }

    public void setOtro(String otro) {
        this.otro = otro;
    }

    @Column(name = "ParamDistribucion", length = 4)
    public String getParamDistribucion() {
        return this.paramDistribucion;
    }

    public void setParamDistribucion(String paramDistribucion) {
        this.paramDistribucion = paramDistribucion;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "FechaSesion", length = 23)
    public Date getFechaSesion() {
        return this.fechaSesion;
    }

    public void setFechaSesion(Date fechaSesion) {
        this.fechaSesion = fechaSesion;
    }

    @Column(name = "EnviarCorreo")
    public Boolean getEnviarCorreo() {
        return this.enviarCorreo;
    }

    public void setEnviarCorreo(Boolean enviarCorreo) {
        this.enviarCorreo = enviarCorreo;
    }

    @Column(name = "Enviado")
    public Boolean getEnviado() {
        return this.enviado;
    }

    public void setEnviado(Boolean enviado) {
        this.enviado = enviado;
    }

    @Column(name = "EquivalenciaCorrelativo", length = 20)
    public String getEquivalenciaCorrelativo() {
        return this.equivalenciaCorrelativo;
    }

    public void setEquivalenciaCorrelativo(String equivalenciaCorrelativo) {
        this.equivalenciaCorrelativo = equivalenciaCorrelativo;
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
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "correspondencia")
    public Set<CorrespondenciaServicio> getCorrespondenciaServicios() {
        return this.correspondenciaServicios;
    }

    public void setCorrespondenciaServicios(Set<CorrespondenciaServicio> correspondenciaServicios) {
        this.correspondenciaServicios = correspondenciaServicios;
    }

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "correspondencia")
    public Set<CorrespondenciaFile> getCorrespondenciaFiles() {
        return this.correspondenciaFiles;
    }

    public void setCorrespondenciaFiles(Set<CorrespondenciaFile> correspondenciaFiles) {
        this.correspondenciaFiles = correspondenciaFiles;
    }

}
