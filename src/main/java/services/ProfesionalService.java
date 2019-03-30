/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import com.google.gson.Gson;
import dao.models.ProfesionalDAO;
import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

/**
 *
 * @author ssrui
 */
@Stateless
@Path("profesional")
public class ProfesionalService {

    /*
     Servicio GET para obtener todos los profesionales que ofrecen un servicio en 
     particular en una sucursal dada 
     */
    @GET
    @Path("{idSucursalServicio}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCategorias(@PathParam("idSucursalServicio") String id) {
        System.out.println("AAA>>>" + id);
        Gson gson = new Gson();
        ProfesionalDAO daop;
        daop = new ProfesionalDAO();
        int idS = Integer.parseInt(id);
        String r = daop.getProfesionalesBySucursal(idS);
        return Response.ok(r).build();
    }
}
