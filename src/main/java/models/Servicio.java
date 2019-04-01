package models;
// Generated Mar 17, 2019 11:08:27 PM by Hibernate Tools 4.3.1


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.gson.annotations.Expose;
import java.util.HashSet;
import java.util.Set;

/**
 * Servicio generated by hbm2java
 */
public class Servicio  implements java.io.Serializable {

      @Expose
     private int idServicio;
     private Especialidad especialidad;
       @Expose
     private String nombre;
     private int duracionReferencia;
     private Set sucursalServicios = new HashSet(0);

    public Servicio() {
    }

	
    public Servicio(int idServicio, Especialidad especialidad, String nombre, int duracionReferencia) {
        this.idServicio = idServicio;
        this.especialidad = especialidad;
        this.nombre = nombre;
        this.duracionReferencia = duracionReferencia;
    }
    public Servicio(int idServicio, Especialidad especialidad, String nombre, int duracionReferencia, Set sucursalServicios) {
       this.idServicio = idServicio;
       this.especialidad = especialidad;
       this.nombre = nombre;
       this.duracionReferencia = duracionReferencia;
       this.sucursalServicios = sucursalServicios;
    }
   
    public int getIdServicio() {
        return this.idServicio;
    }
    
    public void setIdServicio(int idServicio) {
        this.idServicio = idServicio;
    }
    public Especialidad getEspecialidad() {
        return this.especialidad;
    }
    
    public void setEspecialidad(Especialidad especialidad) {
        this.especialidad = especialidad;
    }
    public String getNombre() {
        return this.nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public int getDuracionReferencia() {
        return this.duracionReferencia;
    }
    
    public void setDuracionReferencia(int duracionReferencia) {
        this.duracionReferencia = duracionReferencia;
    }
    public Set getSucursalServicios() {
        return this.sucursalServicios;
    }
    
    public void setSucursalServicios(Set sucursalServicios) {
        this.sucursalServicios = sucursalServicios;
    }




}