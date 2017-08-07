package com.comiteetica.hibernate.model;
// Generated 04-ago-2017 18:25:51 by Hibernate Tools 4.3.1

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
 * Cro generated by hbm2java
 */
@Entity
@Table(name = "Cro",
         schema = "dbo",
         catalog = "ComiteEtica"
)
public class Cro implements java.io.Serializable {

    private String idCro;
    private String nombre;
    private String usuarioIngresa;
    private Date fechaIngreso;
    private String usuarioModifica;
    private Date fechaModificacion;
    private Set<PatrocinadorCro> patrocinadorCros = new HashSet<PatrocinadorCro>(0);

    public Cro() {
    }

    public Cro(String idCro) {
        this.idCro = idCro;
    }

    public Cro(String idCro, String nombre, String usuarioIngresa, Date fechaIngreso, String usuarioModifica, Date fechaModificacion, Set<PatrocinadorCro> patrocinadorCros) {
        this.idCro = idCro;
        this.nombre = nombre;
        this.usuarioIngresa = usuarioIngresa;
        this.fechaIngreso = fechaIngreso;
        this.usuarioModifica = usuarioModifica;
        this.fechaModificacion = fechaModificacion;
        this.patrocinadorCros = patrocinadorCros;
    }

    @Id

    @Column(name = "IdCro", unique = true, nullable = false, length = 10)
    public String getIdCro() {
        return this.idCro;
    }

    public void setIdCro(String idCro) {
        this.idCro = idCro;
    }

    @Column(name = "Nombre", length = 150)
    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "cro")
    public Set<PatrocinadorCro> getPatrocinadorCros() {
        return this.patrocinadorCros;
    }

    public void setPatrocinadorCros(Set<PatrocinadorCro> patrocinadorCros) {
        this.patrocinadorCros = patrocinadorCros;
    }

}
