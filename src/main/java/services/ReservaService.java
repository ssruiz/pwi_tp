/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dao.models.EspecialidadDAO;
import dao.models.ReservaDAO;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import models.Reserva;
import models.auxiliares.BusquedaReserva;
import models.auxiliares.NuevaReserva;
import models.auxiliares.PutReserva;
import org.json.simple.JSONObject;

/**
 *
 * @author ssrui
 */
@Stateless
@Path("reserva")
public class ReservaService {

    @GET
    @Path("disponible")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDisponiblesFecha(@QueryParam("idSucursalServicio") String a, @QueryParam("fecha") String str_date) {
        String disponibles = "";
        try {

            int id = Integer.parseInt(a);
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            Date date = df.parse(str_date);
            ReservaDAO daoc;
            daoc = new ReservaDAO();
            //System.out.println("id " + id + " fecha " + date.toString());
            disponibles = daoc.getHorariosDisponibles(id, date);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return Response.ok(disponibles).build();
    }

    @GET
    @Path("disponible/empleado")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDisponiblesEmpleado(@QueryParam("idSucursalServicio") String a, @QueryParam("fecha") String str_date, @QueryParam("idEmpleado") String idE) {
        String disponibles = "";
        try {

            int id = Integer.parseInt(a);
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            Date fecha = df.parse(str_date);
            int idEmpleado = Integer.parseInt(idE);
            ReservaDAO daoc;
            daoc = new ReservaDAO();
            //System.out.println("id " + id + " fecha " + date.toString());
            disponibles = daoc.getHorariosDisponiblesPorEmpleado(id, fecha, idEmpleado);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return Response.ok(disponibles).build();
    }

    @POST
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response crearReserva(JSONObject reservaJson) {
        String resultado = "";
        try {

            NuevaReserva reserva;
            reserva = new NuevaReserva();
            System.out.println("FECHA>" + reservaJson.get("fecha").toString());
            reserva.setFecha(reservaJson.get("fecha").toString());
            LinkedHashMap m = (LinkedHashMap) reservaJson.get("idCliente");
            reserva.setIdEmpleado((Integer) reservaJson.get("idEmpleado"));
            reserva.setIdSucursalServicio((Integer) reservaJson.get("idSucursalServicio"));
            reserva.setHoraInicio(reservaJson.get("horaInicio").toString());
            reserva.setHoraFin(reservaJson.get("horaFin").toString());
            reserva.setEmail(m.get("email").toString());
            System.out.println(reserva.toString());
            ReservaDAO daoc;
            daoc = new ReservaDAO();
            resultado = daoc.reservar(reserva);
            /*          
             int id = Integer.parseInt(a);
             SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
             Date fecha = df.parse(str_date);
             int idEmpleado = Integer.parseInt(idE);
             ReservaDAO daoc;
             daoc = new ReservaDAO();
             //System.out.println("id " + id + " fecha " + date.toString());
             disponibles = daoc.getHorariosDisponiblesPorEmpleado(id, fecha, idEmpleado);
             */
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Response.ok(resultado).build();
    }

    @GET
    @Path("disponible/all")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllReservas(BusquedaReserva reserva ) {
        String disponibles = "";
        try {
          
            /*
             reserva.setIdSucursal((Integer) res.get("idSucursal"));
             reserva.setIdServicio((Integer) res.get("idServicio"));
             reserva.setIdLocal((Integer) res.get("idLocal"));
             reserva.setIdEspecialidad((Integer)res.get("idEspecialidad"));
             reserva.setIdEmpleado((Integer) res.get("idEmpleado"));
             reserva.setAsistio(res.get("asistio").toString());
             reserva.setEstado(res.get("estado").toString());
             reserva.setFechaDesde(res.get("fechaDesde").toString());
             reserva.setFechaHasta(res.get("fechaHasta").toString());
             reserva.setHoraInicio(res.get("horaInicio").toString());
             reserva.setHoraFin(res.get("horaFin").toString());
             System.out.println("id " + reserva.toString());
             /*int id = Integer.parseInt(a);
             SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
             Date fecha = df.parse(str_date);
             int idEmpleado = Integer.parseInt(idE);
             */
            ReservaDAO daoc;
            daoc = new ReservaDAO();
            //System.out.println("id " + id + " fecha " + date.toString());
            disponibles = daoc.getAllReserv(reserva);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return Response.ok(disponibles).build();
    }

    @PUT
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response actualizarReserva(PutReserva reserva) {
        String resultado = "";
        try {
            System.out.println("aaaaaaaa" + reserva.toString());
            ReservaDAO daoc;
            daoc = new ReservaDAO();
            resultado = daoc.actualizarReserv(reserva);
            /*
             PutReserva reserva;
             reserva = new PutReserva();
             System.out.println("FECHA>" + reservaJson.get("fecha").toString());
             reserva.setFlagAsistio(reservaJson.get("flagAsistio").toString());
             reserva.setFlagEstado(reservaJson.get("flagAsistio").toString());
             LinkedHashMap m = (LinkedHashMap) reservaJson.get("idCliente");
             reserva.setIdEmpleado((Integer) reservaJson.get("idEmpleado"));
             reserva.setIdSucursalServicio((Integer) reservaJson.get("idSucursalServicio"));
             reserva.setHoraInicio(reservaJson.get("horaInicio").toString());
             reserva.setHoraFin(reservaJson.get("horaFin").toString());
             reserva.setEmail(m.get("email").toString());
             System.out.println(reserva.toString());
             */
            //ReservaDAO daoc;
            //daoc = new ReservaDAO();
            //resultado = daoc.reservar(reserva);
            /*          
             int id = Integer.parseInt(a);
             SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
             Date fecha = df.parse(str_date);
             int idEmpleado = Integer.parseInt(idE);
             ReservaDAO daoc;
             daoc = new ReservaDAO();
             //System.out.println("id " + id + " fecha " + date.toString());
             disponibles = daoc.getHorariosDisponiblesPorEmpleado(id, fecha, idEmpleado);
             */
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Response.ok(resultado).build();
    }
}
