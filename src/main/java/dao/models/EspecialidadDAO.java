/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao.models;

import com.google.gson.Gson;
import java.util.List;
import java.util.Set;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Fetch;
import javax.persistence.criteria.Root;
import models.Categoria;
import models.Ciudad;
import models.Especialidad;
import models.Sucursal;
import models.SucursalServicio;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

/**
 *
 * @author ssrui
 */
public class EspecialidadDAO {

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

    public String getEspecialidades(int id) {
        String lista = null;
        try {
            iniciarOperacion();
            List<Sucursal> query = sesion.createQuery("select s from Sucursal as s "
                    + "where s.idSucursal= :id").setParameter("id", id).getResultList();
            
            Gson gson = new Gson();
           
       
            for (Sucursal item : query) {
                     lista = gson.toJson(item);
                Set<SucursalServicio> ss = item.getSucursalServicios();
                for (SucursalServicio p : ss) {
                    System.out.println("SucursalServicio: " + p.getServicio().getEspecialidad().getNombre());
                }
            }
        } catch (HibernateException e) {
            manejaExcepcion(e);
        } finally {
            sesion.close();
        }
        return lista;
    }
}
