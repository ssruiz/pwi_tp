/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models.auxiliares;

import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 *
 * @author ssrui
 */
public class NuevaReserva {

    private Date fecha;
    private LocalTime horaInicio;
    private LocalTime horaFin;
    private int idSucursalServicio;
    private int idEmpleado;
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    public Date getFecha() {
        return fecha;
    }

    public LocalTime getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(String horaInicio) {
        try {
            //DateFormat writeFormat = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss");
            LocalTime time3 = LocalTime.parse(horaInicio,DateTimeFormatter.ISO_TIME);
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            //Date fechaa = df.parse(horaFin);
            this.horaInicio = time3;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public LocalTime getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(String horaFin) {
        try {
            LocalTime time3 = LocalTime.parse(horaFin,DateTimeFormatter.ISO_TIME);
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            //Date fechaa = df.parse(horaFin);
            this.horaFin = time3;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getIdSucursalServicio() {
        return idSucursalServicio;
    }

    public void setIdSucursalServicio(int idSucursalServicio) {
        this.idSucursalServicio = idSucursalServicio;
    }

    public int getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(int idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public void setFecha(String fecha) {
        try {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            Date fechaa = df.parse(fecha);
           
            this.fecha = fechaa;
        } catch (Exception e) {
        }

    }

    @Override
    public String toString() {
        return "Rserva [fecha=" + fecha + ", hi=" + horaInicio + ", horaf=" + horaFin +  " email=" + email + ", idE=" + idEmpleado + ", idS=" + idSucursalServicio + "]";
    }
}
