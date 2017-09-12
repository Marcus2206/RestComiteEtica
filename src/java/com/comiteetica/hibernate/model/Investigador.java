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
 * Investigador generated by hbm2java
 */
@Entity
@Table(name = "Investigador",
        schema = "dbo",
        catalog = "ComiteEtica"
)
public class Investigador implements java.io.Serializable {

    private String idInvestigador;
    private String apePaterno;
    private String apeMaterno;
    private String nombres;
    private String paramEspecialidadInvestigador;
    private String correo;
    private String usuarioIngresa;
    private Date fechaIngreso;
    private String usuarioModifica;
    private Date fechaModificacion;
    private Set<InvestigacionInvestigador> investigacionInvestigadors = new HashSet<InvestigacionInvestigador>(0);
    private Set<Registro> registros = new HashSet<Registro>(0);

    public Investigador() {
    }

    public Investigador(String idInvestigador) {
        this.idInvestigador = idInvestigador;
    }

    public Investigador(String idInvestigador, String apePaterno, String apeMaterno, String nombres, String paramEspecialidadInvestigador, String correo, String usuarioIngresa, Date fechaIngreso, String usuarioModifica, Date fechaModificacion, Set<InvestigacionInvestigador> investigacionInvestigadors) {
        this.idInvestigador = idInvestigador;
        this.apePaterno = apePaterno;
        this.apeMaterno = apeMaterno;
        this.nombres = nombres;
        this.paramEspecialidadInvestigador = paramEspecialidadInvestigador;
        this.correo = correo;
        this.usuarioIngresa = usuarioIngresa;
        this.fechaIngreso = fechaIngreso;
        this.usuarioModifica = usuarioModifica;
        this.fechaModificacion = fechaModificacion;
        this.investigacionInvestigadors = investigacionInvestigadors;
    }

    @Id

    @Column(name = "IdInvestigador", nullable = false, length = 10)
    public String getIdInvestigador() {
        return this.idInvestigador;
    }

    public void setIdInvestigador(String idInvestigador) {
        this.idInvestigador = idInvestigador;
    }

    @Column(name = "ApePaterno", length = 50)
    public String getApePaterno() {
        return this.apePaterno;
    }

    public void setApePaterno(String apePaterno) {
        this.apePaterno = apePaterno;
    }

    @Column(name = "ApeMaterno", length = 50)
    public String getApeMaterno() {
        return this.apeMaterno;
    }

    public void setApeMaterno(String apeMaterno) {
        this.apeMaterno = apeMaterno;
    }

    @Column(name = "Nombres", length = 100)
    public String getNombres() {
        return this.nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    @Column(name = "ParamEspecialidadInvestigador", length = 4)
    public String getParamEspecialidadInvestigador() {
        return this.paramEspecialidadInvestigador;
    }

    public void setParamEspecialidadInvestigador(String paramEspecialidadInvestigador) {
        this.paramEspecialidadInvestigador = paramEspecialidadInvestigador;
    }

    @Column(name = "Correo", length = 150)
    public String getCorreo() {
        return this.correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
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
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "investigador")
    public Set<InvestigacionInvestigador> getInvestigacionInvestigadors() {
        return this.investigacionInvestigadors;
    }

    public void setInvestigacionInvestigadors(Set<InvestigacionInvestigador> investigacionInvestigadors) {
        this.investigacionInvestigadors = investigacionInvestigadors;
    }

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "investigador")
    public Set<Registro> getRegistros() {
        return this.registros;
    }

    public void setRegistros(Set<Registro> registros) {
        this.registros = registros;
    }

}
