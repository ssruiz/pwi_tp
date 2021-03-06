package models;
// Generated Apr 1, 2019 9:07:39 AM by Hibernate Tools 4.3.1

import java.util.Date;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Reserva generated by hbm2java
 */
public class Reserva implements java.io.Serializable {

    private int idReserva;
    private Persona personaByIdEmpleado;
    private Persona personaByIdCliente;
    private SucursalServicio sucursalServicio;
    private Date fecha;
    
    private Date horaInicio;
    private Date horaFin;
    private Date fechaHoraCreacion;
    private String flagEstado;
    private String flagAsistio;
    private String observacion;

    public Reserva() {
    }

    public Reserva(int idReserva, Persona personaByIdCliente, SucursalServicio sucursalServicio, Date fecha, Date horaInicio, Date horaFin, Date fechaHoraCreacion, String flagEstado) {
        this.idReserva = idReserva;
        this.personaByIdCliente = personaByIdCliente;
        this.sucursalServicio = sucursalServicio;
        this.fecha = fecha;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.fechaHoraCreacion = fechaHoraCreacion;
        this.flagEstado = flagEstado;
    }

    public Reserva(int idReserva, Persona personaByIdEmpleado, Persona personaByIdCliente, SucursalServicio sucursalServicio, Date fecha, Date horaInicio, Date horaFin, Date fechaHoraCreacion, String flagEstado, String flagAsistio, String observacion) {
        this.idReserva = idReserva;
        this.personaByIdEmpleado = personaByIdEmpleado;
        this.personaByIdCliente = personaByIdCliente;
        this.sucursalServicio = sucursalServicio;
        this.fecha = fecha;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.fechaHoraCreacion = fechaHoraCreacion;
        this.flagEstado = flagEstado;
        this.flagAsistio = flagAsistio;
        this.observacion = observacion;
    }

    public int getIdReserva() {
        return this.idReserva;
    }

    public void setIdReserva(int idReserva) {
        this.idReserva = idReserva;
    }

    public Persona getPersonaByIdEmpleado() {
        return this.personaByIdEmpleado;
    }

    public void setPersonaByIdEmpleado(Persona personaByIdEmpleado) {
        this.personaByIdEmpleado = personaByIdEmpleado;
    }

    public Persona getPersonaByIdCliente() {
        return this.personaByIdCliente;
    }

    public void setPersonaByIdCliente(Persona personaByIdCliente) {
        this.personaByIdCliente = personaByIdCliente;
    }

    public SucursalServicio getSucursalServicio() {
        return this.sucursalServicio;
    }

    public void setSucursalServicio(SucursalServicio sucursalServicio) {
        this.sucursalServicio = sucursalServicio;
    }

    public Date getFecha() {
        return this.fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Date getHoraInicio() {
        return this.horaInicio;
    }

    public void setHoraInicio(Date horaInicio) {
        this.horaInicio = horaInicio;
    }

    public Date getHoraFin() {
        return this.horaFin;
    }

    public void setHoraFin(Date horaFin) {
        this.horaFin = horaFin;
    }

    public Date getFechaHoraCreacion() {
        return this.fechaHoraCreacion;
    }

    public void setFechaHoraCreacion(Date fechaHoraCreacion) {
        this.fechaHoraCreacion = fechaHoraCreacion;
    }

    public String getFlagEstado() {
        return this.flagEstado;
    }

    public void setFlagEstado(String flagEstado) {
        this.flagEstado = flagEstado;
    }

    public String getFlagAsistio() {
        return this.flagAsistio;
    }

    public void setFlagAsistio(String flagAsistio) {
        this.flagAsistio = flagAsistio;
    }

    public String getObservacion() {
        return this.observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

}
