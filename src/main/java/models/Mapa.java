package models;
// Generated Apr 1, 2019 9:07:39 AM by Hibernate Tools 4.3.1


import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

/**
 * Mapa generated by hbm2java
 */
public class Mapa  implements java.io.Serializable {


     private int idMapa;
     private String nombre;
     private String direccion;
     private BigDecimal latitud;
     private BigDecimal longitud;
     private Set sucursals = new HashSet(0);

    public Mapa() {
    }

	
    public Mapa(int idMapa, String nombre, String direccion) {
        this.idMapa = idMapa;
        this.nombre = nombre;
        this.direccion = direccion;
    }
    public Mapa(int idMapa, String nombre, String direccion, BigDecimal latitud, BigDecimal longitud, Set sucursals) {
       this.idMapa = idMapa;
       this.nombre = nombre;
       this.direccion = direccion;
       this.latitud = latitud;
       this.longitud = longitud;
       this.sucursals = sucursals;
    }
   
    public int getIdMapa() {
        return this.idMapa;
    }
    
    public void setIdMapa(int idMapa) {
        this.idMapa = idMapa;
    }
    public String getNombre() {
        return this.nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getDireccion() {
        return this.direccion;
    }
    
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
    public BigDecimal getLatitud() {
        return this.latitud;
    }
    
    public void setLatitud(BigDecimal latitud) {
        this.latitud = latitud;
    }
    public BigDecimal getLongitud() {
        return this.longitud;
    }
    
    public void setLongitud(BigDecimal longitud) {
        this.longitud = longitud;
    }
    public Set getSucursals() {
        return this.sucursals;
    }
    
    public void setSucursals(Set sucursals) {
        this.sucursals = sucursals;
    }




}


