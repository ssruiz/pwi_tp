/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import com.google.gson.Gson;
import dao.models.CategoriaDAO;
import dao.models.EspecialidadDAO;
import dao.models.SucursalDAO;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import models.Categoria;
import models.Especialidad;
import models.Sucursal;
import org.hibernate.annotations.Proxy;

/**
 *
 * @author ssrui
 */
@Stateless
@Path("especialidad")
public class EspecialidadService {

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCategorias(@Context UriInfo info) {
        String from = info.getQueryParameters().getFirst("idSucursal");
        int id = Integer.parseInt(from);
        EspecialidadDAO daoc;
        daoc= new EspecialidadDAO();
        System.out.println("ADASDSADS");
        String categorias = daoc.getEspecialidades(id);
        return Response.ok(categorias).build();
    }
}
