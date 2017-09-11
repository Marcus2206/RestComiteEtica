package com.comiteetica.hibernate.model;
// Generated 03-sep-2017 15:25:44 by Hibernate Tools 4.3.1

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
 * Patrocinador generated by hbm2java
 */
@Entity
@Table(name = "Patrocinador",
        schema = "dbo",
        catalog = "ComiteEtica"
)
public class Patrocinador implements java.io.Serializable {

    private String idPatrocinador;
    private String nombre;
    private String razonSocial;
    private String ruc;
    private String direccion;
    private String usuarioIngresa;
    private Date fechaIngreso;
    private String usuarioModifica;
    private Date fechaModificacion;
    private Set<Investigacion> investigacions = new HashSet<Investigacion>(0);
    private Set<PatrocinadorCro> patrocinadorCros = new HashSet<PatrocinadorCro>(0);

    public Patrocinador() {
    }

    public Patrocinador(String idPatrocinador) {
        this.idPatrocinador = idPatrocinador;
    }

    public Patrocinador(String idPatrocinador, String nombre, String usuarioIngresa, Date fechaIngreso, String usuarioModifica, Date fechaModificacion, Set<Investigacion> investigacions, Set<PatrocinadorCro> patrocinadorCros) {
        this.idPatrocinador = idPatrocinador;
        this.nombre = nombre;
        this.usuarioIngresa = usuarioIngresa;
        this.fechaIngreso = fechaIngreso;
        this.usuarioModifica = usuarioModifica;
        this.fechaModificacion = fechaModificacion;
        this.investigacions = investigacions;
        this.patrocinadorCros = patrocinadorCros;
    }

    @Id

    @Column(name = "IdPatrocinador", unique = true, nullable = false, length = 10)
    public String getIdPatrocinador() {
        return this.idPatrocinador;
    }

    public void setIdPatrocinador(String idPatrocinador) {
        this.idPatrocinador = idPatrocinador;
    }

    @Column(name = "Nombre", length = 150)
    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Column(name = "RazonSocial", length = 500)
    public String getRazonSocial() {
        return this.razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    @Column(name = "Ruc", length = 11)
    public String getRuc() {
        return this.ruc;
    }

    public void setRuc(String ruc) {
        this.ruc = ruc;
    }

    @Column(name = "Direccion", length = 100)
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

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "patrocinador")
    public Set<Investigacion> getInvestigacions() {
        return this.investigacions;
    }

    public void setInvestigacions(Set<Investigacion> investigacions) {
        this.investigacions = investigacions;
    }

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "patrocinador")
    public Set<PatrocinadorCro> getPatrocinadorCros() {
        return this.patrocinadorCros;
    }

    public void setPatrocinadorCros(Set<PatrocinadorCro> patrocinadorCros) {
        this.patrocinadorCros = patrocinadorCros;
    }

}
