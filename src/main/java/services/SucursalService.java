/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

//import dao.models.SucursalDAO;
import dao.models.SucursalDAO;
import javax.ejb.Stateless;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

/**
 *
 * @author ssrui
 */
@Stateless
@Path("sucursal")
public class SucursalService {

    /*
     Servicio GET para obtener todas las sucursales (con su ciudad, dirección y local 
     correspondiente) a partir de una categoría en particular
q @JsonIgnore
    */
  
    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSucursales(@Context UriInfo info) {
        String from = info.getQueryParameters().getFirst("idCategoria");
        SucursalDAO daos = new SucursalDAO();
        int id = Integer.parseInt(from);
        return Response.status(200)
		   .entity(daos.getSucursalesByCate(id)).build();
    }
}
