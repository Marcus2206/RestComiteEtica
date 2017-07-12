package com.comiteetica.hibernate.model;
// Generated 05-jul-2017 13:55:59 by Hibernate Tools 4.3.1


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
 * Monitor generated by hbm2java
 */
@Entity
@Table(name="Monitor"
    ,schema="dbo"
    ,catalog="ComiteEtica"
)
public class Monitor  implements java.io.Serializable {


     private String idMonitor;
     private String apePaterno;
     private String apeMaterno;
     private String nombres;
     private String correo;
     private String usuarioIngresa;
     private Date fechaIngreso;
     private String usuarioModifica;
     private Date fechaModificacion;
     private Set<InvestigacionMonitor> investigacionMonitors = new HashSet<InvestigacionMonitor>(0);

    public Monitor() {
    }

	
    public Monitor(String idMonitor) {
        this.idMonitor = idMonitor;
    }
    public Monitor(String idMonitor, String apePaterno, String apeMaterno, String nombres, String correo, String usuarioIngresa, Date fechaIngreso, String usuarioModifica, Date fechaModificacion, Set<InvestigacionMonitor> investigacionMonitors) {
       this.idMonitor = idMonitor;
       this.apePaterno = apePaterno;
       this.apeMaterno = apeMaterno;
       this.nombres = nombres;
       this.correo = correo;
       this.usuarioIngresa = usuarioIngresa;
       this.fechaIngreso = fechaIngreso;
       this.usuarioModifica = usuarioModifica;
       this.fechaModificacion = fechaModificacion;
       this.investigacionMonitors = investigacionMonitors;
    }
   
     @Id 

    
    @Column(name="IdMonitor", unique=true, nullable=false, length=10)
    public String getIdMonitor() {
        return this.idMonitor;
    }
    
    public void setIdMonitor(String idMonitor) {
        this.idMonitor = idMonitor;
    }

    
    @Column(name="ApePaterno", length=50)
    public String getApePaterno() {
        return this.apePaterno;
    }
    
    public void setApePaterno(String apePaterno) {
        this.apePaterno = apePaterno;
    }

    
    @Column(name="ApeMaterno", length=50)
    public String getApeMaterno() {
        return this.apeMaterno;
    }
    
    public void setApeMaterno(String apeMaterno) {
        this.apeMaterno = apeMaterno;
    }

    
    @Column(name="Nombres", length=100)
    public String getNombres() {
        return this.nombres;
    }
    
    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    
    @Column(name="Correo", length=150)
    public String getCorreo() {
        return this.correo;
    }
    
    public void setCorreo(String correo) {
        this.correo = correo;
    }

    
    @Column(name="UsuarioIngresa", length=50)
    public String getUsuarioIngresa() {
        return this.usuarioIngresa;
    }
    
    public void setUsuarioIngresa(String usuarioIngresa) {
        this.usuarioIngresa = usuarioIngresa;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="FechaIngreso", length=23)
    public Date getFechaIngreso() {
        return this.fechaIngreso;
    }
    
    public void setFechaIngreso(Date fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    
    @Column(name="UsuarioModifica", length=50)
    public String getUsuarioModifica() {
        return this.usuarioModifica;
    }
    
    public void setUsuarioModifica(String usuarioModifica) {
        this.usuarioModifica = usuarioModifica;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="FechaModificacion", length=23)
    public Date getFechaModificacion() {
        return this.fechaModificacion;
    }
    
    public void setFechaModificacion(Date fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    @JsonIgnore
    @OneToMany(fetch=FetchType.LAZY, mappedBy="monitor")
    public Set<InvestigacionMonitor> getInvestigacionMonitors() {
        return this.investigacionMonitors;
    }
    
    @JsonIgnore
    public void setInvestigacionMonitors(Set<InvestigacionMonitor> investigacionMonitors) {
        this.investigacionMonitors = investigacionMonitors;
    }




}


