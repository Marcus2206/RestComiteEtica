package com.comiteetica.hibernate.model;
// Generated 17-jun-2017 14:55:19 by Hibernate Tools 4.3.1

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Sede generated by hbm2java
 */
@Entity
@Table(name = "Sede",
         schema = "dbo",
         catalog = "ComiteEtica"
)
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "idSede")
public class Sede implements java.io.Serializable {

    private String idSede;
    
    @JsonBackReference("DepartamentoSede")
    private Departamento departamento;
    
    @JsonBackReference("DistritoSede")
    private Distrito distrito;
    
    @JsonBackReference("ProvinciaSede")
    private Provincia provincia;
    
    private String nombre;
    private String direccion;
    private String usuarioIngresa;
    private Date fechaIngreso;
    private String usuarioModifica;
    private Date fechaModificacion;

    @JsonManagedReference("SedeInvestigacion")
    private Set<InvestigacionSede> investigacionSedes = new HashSet<InvestigacionSede>(0);

    public Sede() {
    }

    public Sede(String idSede) {
        this.idSede = idSede;
    }

    public Sede(String idSede, Departamento departamento, Distrito distrito, Provincia provincia, String nombre, String direccion, String usuarioIngresa, Date fechaIngreso, String usuarioModifica, Date fechaModificacion, Set<InvestigacionSede> investigacionSedes) {
        this.idSede = idSede;
        this.departamento = departamento;
        this.distrito = distrito;
        this.provincia = provincia;
        this.nombre = nombre;
        this.direccion = direccion;
        this.usuarioIngresa = usuarioIngresa;
        this.fechaIngreso = fechaIngreso;
        this.usuarioModifica = usuarioModifica;
        this.fechaModificacion = fechaModificacion;
        this.investigacionSedes = investigacionSedes;
    }

    @Id

    @Column(name = "IdSede", nullable = false, length = 10)
    public String getIdSede() {
        return this.idSede;
    }

    public void setIdSede(String idSede) {
        this.idSede = idSede;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IdDepartamento")
    public Departamento getDepartamento() {
        return this.departamento;
    }

    public void setDepartamento(Departamento departamento) {
        this.departamento = departamento;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
        @JoinColumn(name = "IdDepartamento", referencedColumnName = "IdDepartamento", insertable = false, updatable = false)
        , 
        @JoinColumn(name = "IdProvincia", referencedColumnName = "IdProvincia", insertable = false, updatable = false)
        , 
        @JoinColumn(name = "IdDistrito", referencedColumnName = "IdDistrito", insertable = false, updatable = false)})
    public Distrito getDistrito() {
        return this.distrito;
    }

    public void setDistrito(Distrito distrito) {
        this.distrito = distrito;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
        @JoinColumn(name = "IdDepartamento", referencedColumnName = "IdDepartamento", insertable = false, updatable = false)
        , 
        @JoinColumn(name = "IdProvincia", referencedColumnName = "IdProvincia", insertable = false, updatable = false)})
    public Provincia getProvincia() {
        return this.provincia;
    }

    public void setProvincia(Provincia provincia) {
        this.provincia = provincia;
    }

    @Column(name = "Nombre", length = 150)
    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Column(name = "Direccion", length = 250)
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
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "sede")
    public Set<InvestigacionSede> getInvestigacionSedes() {
        return this.investigacionSedes;
    }

    public void setInvestigacionSedes(Set<InvestigacionSede> investigacionSedes) {
        this.investigacionSedes = investigacionSedes;
    }

}
