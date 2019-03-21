package models;
// Generated Mar 17, 2019 11:08:27 PM by Hibernate Tools 4.3.1


import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Proxy;

/**
 * Ciudad generated by hbm2java
 */
@Proxy(lazy = false)
public class Ciudad  implements java.io.Serializable {


     private int idCiudad;
     private String nombre;
     private Set sucursals = new HashSet(0);

    public Ciudad() {
    }

	
    public Ciudad(int idCiudad, String nombre) {
        this.idCiudad = idCiudad;
        this.nombre = nombre;
    }
    public Ciudad(int idCiudad, String nombre, Set sucursals) {
       this.idCiudad = idCiudad;
       this.nombre = nombre;
       this.sucursals = sucursals;
    }
   
    public int getIdCiudad() {
        return this.idCiudad;
    }
    
    public void setIdCiudad(int idCiudad) {
        this.idCiudad = idCiudad;
    }
    public String getNombre() {
        return this.nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public Set getSucursals() {
        return this.sucursals;
    }
    
    public void setSucursals(Set sucursals) {
        this.sucursals = sucursals;
    }




}

