package com.comiteetica.hibernate.model;
// Generated 11-ago-2017 18:56:32 by Hibernate Tools 4.3.1

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Correo generated by hbm2java
 */
@Entity
@Table(name = "Correo",
         schema = "dbo",
         catalog = "ComiteEtica"
)
public class Correo implements java.io.Serializable {

    private int idCorreo;
    private String apePaterno;
    private String apeMaterno;
    private String nombres;
    private String paramAreaTrabajo;
    private String correo;
    private boolean estado;
    private String usuarioIngresa;
    private Date fechaIngreso;
    private String usuarioModifica;
    private Date fechaModificacion;

    public Correo() {
    }

    public Correo(int idCorreo) {
        this.idCorreo = idCorreo;
    }

    public Correo(int idCorreo, String apePaterno, String apeMaterno, String nombres, String paramAreaTrabajo, String correo, boolean estado, String usuarioIngresa, Date fechaIngreso, String usuarioModifica, Date fechaModificacion) {
        this.idCorreo = idCorreo;
        this.apePaterno = apePaterno;
        this.apeMaterno = apeMaterno;
        this.nombres = nombres;
        this.paramAreaTrabajo = paramAreaTrabajo;
        this.correo = correo;
        this.estado = estado;
        this.usuarioIngresa = usuarioIngresa;
        this.fechaIngreso = fechaIngreso;
        this.usuarioModifica = usuarioModifica;
        this.fechaModificacion = fechaModificacion;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdCorreo", unique = true, nullable = false)
    public int getIdCorreo() {
        return this.idCorreo;
    }

    public void setIdCorreo(int idCorreo) {
        this.idCorreo = idCorreo;
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

    @Column(name = "ParamAreaTrabajo", length = 4)
    public String getParamAreaTrabajo() {
        return this.paramAreaTrabajo;
    }

    public void setParamAreaTrabajo(String paramAreaTrabajo) {
        this.paramAreaTrabajo = paramAreaTrabajo;
    }

    @Column(name = "Correo", length = 150)
    public String getCorreo() {
        return this.correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    @Column(name = "Estado")
    public boolean getEstado() {
        return this.estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
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
