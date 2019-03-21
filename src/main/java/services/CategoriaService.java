/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import com.google.gson.Gson;
import dao.models.CategoriaDAO;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import models.Categoria;
//
import org.hibernate.Criteria;

/**
 *
 * @author ssrui
 */
@Stateless
@Path("categoria")
public class CategoriaService {

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCategorias() {
        EntityManager em;
        CategoriaDAO daoc;
        daoc = new CategoriaDAO();
        System.out.println("ADASDSADS");
        List<Categoria> categorias = daoc.getCategorias();
        Gson gs = new Gson();
        return Response.ok().entity((categorias)).build();
    }

}
