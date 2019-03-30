/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao.models;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import models.Especialidad;
import models.Persona;
import models.PersonaSucursalServicio;
import models.SucursalServicio;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import util.HibernateProxyTypeAdapter;

/**
 *
 * @author ssrui
 */
public class ProfesionalDAO {

    Session sesion;
    Transaction tran;

    private void iniciarOperacion() {
        sesion = NewHibernateUtil.getSessionFactory().openSession();
        tran = sesion.beginTransaction();
    }

    private void manejaExcepcion(HibernateException he) throws HibernateException {
        tran.rollback();
        throw new HibernateException("Ocurrio un error en la capa de acceso a datos", he);
    }

    /* 
     Obtiene todos los profesionales que ofrecen un servicio en particular en una sucursal dada 
     */
    public String getProfesionalesBySucursal(int id) {
        String result = "";
        try {
            iniciarOperacion();
            GsonBuilder b = new GsonBuilder();
            b.registerTypeAdapterFactory(HibernateProxyTypeAdapter.FACTORY);
            b.excludeFieldsWithoutExposeAnnotation().create();
            Gson gson = b.create();
            List<SucursalServicio> q = sesion.createQuery("select s from SucursalServicio s "
                    + "where s.sucursal.idSucursal = :id").setParameter("id", id).getResultList();

            for (SucursalServicio p : q) {

                // System.out.println("SucursalServicio: " + p.getServicio().getEspecialidad().getNombre());
                Set<PersonaSucursalServicio> ps = p.getPersonaSucursalServicios();
                
               List<Persona> personas = new ArrayList<Persona>();
                for(PersonaSucursalServicio aux : ps)
                {
                    personas.add(aux.getPersona());
                }
                result = gson.toJson(personas);
                //especialidades.add(p.getServicio().getEspecialidad());
                //lista = gson.toJson(item);
            }
            //result = gson.toJson(result);
        } catch (HibernateException e) {
            manejaExcepcion(e);
        } finally {
            sesion.close();
        }
        return result;
    }
}
