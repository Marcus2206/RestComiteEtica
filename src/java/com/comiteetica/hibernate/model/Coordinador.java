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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Coordinador generated by hbm2java
 */
@Entity
@Table(name = "Coordinador",
         schema = "dbo",
         catalog = "ComiteEtica"
)
public class Coordinador implements java.io.Serializable {

    private String idCoordinador;
    private String apePaterno;
    private String apeMaterno;
    private String nombres;
    private String correo;
    private String usuarioIngresa;
    private Date fechaIngreso;
    private String usuarioModifica;
    private Date fechaModificacion;
    private Set<InvestigacionCoordinador> investigacionCoordinadors = new HashSet<InvestigacionCoordinador>(0);

    public Coordinador() {
    }

    public Coordinador(String idCoordinador) {
        this.idCoordinador = idCoordinador;
    }

    public Coordinador(String idCoordinador, String apePaterno, String apeMaterno, String nombres, String correo, String usuarioIngresa, Date fechaIngreso, String usuarioModifica, Date fechaModificacion, Set<InvestigacionCoordinador> investigacionCoordinadors) {
        this.idCoordinador = idCoordinador;
        this.apePaterno = apePaterno;
        this.apeMaterno = apeMaterno;
        this.nombres = nombres;
        this.correo = correo;
        this.usuarioIngresa = usuarioIngresa;
        this.fechaIngreso = fechaIngreso;
        this.usuarioModifica = usuarioModifica;
        this.fechaModificacion = fechaModificacion;
        this.investigacionCoordinadors = investigacionCoordinadors;
    }

    @Id

    @Column(name = "IdCoordinador", nullable = false, length = 10)
    public String getIdCoordinador() {
        return this.idCoordinador;
    }

    public void setIdCoordinador(String idCoordinador) {
        this.idCoordinador = idCoordinador;
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
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "coordinador")
    public Set<InvestigacionCoordinador> getInvestigacionCoordinadors() {
        return this.investigacionCoordinadors;
    }

    public void setInvestigacionCoordinadors(Set<InvestigacionCoordinador> investigacionCoordinadors) {
        this.investigacionCoordinadors = investigacionCoordinadors;
    }

}
