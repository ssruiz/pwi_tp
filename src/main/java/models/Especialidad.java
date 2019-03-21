package models;
// Generated Mar 17, 2019 11:08:27 PM by Hibernate Tools 4.3.1


import java.util.HashSet;
import java.util.Set;

/**
 * Especialidad generated by hbm2java
 */
public class Especialidad  implements java.io.Serializable {


     private int idEspecialidad;
     private String nombre;
     private Set servicios = new HashSet(0);

    public Especialidad() {
    }

	
    public Especialidad(int idEspecialidad, String nombre) {
        this.idEspecialidad = idEspecialidad;
        this.nombre = nombre;
    }
    public Especialidad(int idEspecialidad, String nombre, Set servicios) {
       this.idEspecialidad = idEspecialidad;
       this.nombre = nombre;
       this.servicios = servicios;
    }
   
    public int getIdEspecialidad() {
        return this.idEspecialidad;
    }
    
    public void setIdEspecialidad(int idEspecialidad) {
        this.idEspecialidad = idEspecialidad;
    }
    public String getNombre() {
        return this.nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public Set getServicios() {
        return this.servicios;
    }
    
    public void setServicios(Set servicios) {
        this.servicios = servicios;
    }




}


