/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models.auxiliares;

/**
 *
 * @author ssrui
 */
public class PutReserva {

    private int idReserva;
    private String flagEstado;
    private String observacion;
    private String flagAsistio;

    public int getIdReserva() {
        return idReserva;
    }

    public void setIdReserva(int idReserva) {
        this.idReserva = idReserva;
    }

    public String getFlagEstado() {
        return flagEstado;
    }

    public void setFlagEstado(String flagEstado) {
        this.flagEstado = flagEstado;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public String getFlagAsistio() {
        return flagAsistio;
    }

    public void setFlagAsistio(String flagAsistio) {
        this.flagAsistio = flagAsistio;
    }

    @Override
    public String toString() {
        return "Reserva [flagA=" + flagAsistio + ", flagE=" + flagEstado + ", idR=" + idReserva + " obs=" + observacion + "]";
    }
}
