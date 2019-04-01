/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models.auxiliares;

import antlr.StringUtils;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 *
 * @author ssrui
 */
public class BusquedaReserva {

    private Date fechaDesde;
    private Date fechaHasta;
    private LocalTime horaInicio;
    private LocalTime horaFin;
    private int idEspecialidad;
    private int idServicio;
    private int idLocal;
    private int idSucursal;
    private String estado;
    private String asistio;
    private int idEmpleado;

    public Date getFechaDesde() {
        return fechaDesde;
    }

    public void setFechaDesde(String fechaDesde) {
        try {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            Date fechaa = df.parse(fechaDesde);

            this.fechaDesde = fechaa;
        } catch (Exception e) {
        }

    }

    public Date getFechaHasta() {
        return fechaHasta;
    }

    public void setFechaHasta(String fechaHasta) {
        try {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            Date fechaa = df.parse(fechaHasta);

            this.fechaHasta = fechaa;
        } catch (Exception e) {
        }
    }

    public LocalTime getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(String horaInicio) {
        try {
            //DateFormat writeFormat = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss");
            LocalTime time3 = LocalTime.parse(horaInicio, DateTimeFormatter.ISO_TIME);
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
            LocalTime time3 = LocalTime.parse(horaFin, DateTimeFormatter.ISO_TIME);
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            //Date fechaa = df.parse(horaFin);
            this.horaFin = time3;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getIdEspecialidad() {
        return idEspecialidad;
    }

    public void setIdEspecialidad(int idEspecialidad) {
        this.idEspecialidad = idEspecialidad;
    }

    public int getIdServicio() {
        return idServicio;
    }

    public void setIdServicio(int idServicio) {
        this.idServicio = idServicio;
    }

    public int getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(int idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public int getIdLocal() {
        return idLocal;
    }

    public void setIdLocal(int idLocal) {
        this.idLocal = idLocal;
    }

    public int getIdSucursal() {
        return idSucursal;
    }

    public void setIdSucursal(int idSucursal) {
        this.idSucursal = idSucursal;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getAsistio() {
        return asistio;
    }

    public void setAsistio(String asistio) {
        if (asistio == null) {
            this.asistio = "";
        } else {
            this.asistio = asistio;
        }

    }
}