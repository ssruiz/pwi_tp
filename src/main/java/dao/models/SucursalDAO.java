/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao.models;

import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Tuple;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import models.Categoria;
import models.Local;
import org.hibernate.HibernateException;
/*import models.Categoria;
 import models.Categoria_;
 import models.Local;
 import models.Local_;
 import models.Sucursal;
 import models.Sucursal_;
 */
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.hibernate.sql.Update;

/**
 *
 * @author ssrui
 */
public class SucursalDAO {

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

    public String getSucursalesByCate(int id) {
        List lista = null;
        List<Tuple> res = null;
        try {
            iniciarOperacion();
            CriteriaBuilder builder = sesion.getCriteriaBuilder();
            CriteriaQuery<Local> query = builder.createQuery(Local.class);
            Root<Local> root = query.from(Local.class);
           // query.select(root);
            //Query<Categoria> q = sesion.createQuery(query);           
           
            Query q = sesion.createQuery(
                    "select s.nombre as Suc, l.nombre as nombre, c.nombre as Cat "
                    + "from Local l join l.categorias c on c.idCategoria = :id "
                    + "join Sucursal s on s.idSucursal = l.sucursal"
                   
            ).setParameter("id", id);
           
            res = q.list();

          

            /*
             Join<Sucursal, Local> joins = root.join("idLocal");
             Join<Sucursal, Categoria> join2 = joins.join("idCategoria");
             List<Predicate> conditions = new ArrayList();
             conditions.add(builder.equal(join2.get("idCategoria"), id));
             conditions.add(builder.isNull(joins.get("idLocal")));*/
            /* TypedQuery<Sucursal> typedQuery = session1.createQuery(criteria
             .select(root)
             .where(conditions.toArray(new Predicate[]{}))
             .distinct(true)
             );
             // criteria.multiselect( desc, loc );
             lista = typedQuery.getResultList();
             tran.commit();*/
        } catch (HibernateException e) {
            manejaExcepcion(e);
            e.printStackTrace();
        } finally {
            if (sesion.isOpen()) {
                sesion.close();
            }
        }
        Gson gson = new Gson();
        
        return gson.toJson(res);
        /*EntityManagerFactory em = Persistence.createEntityManagerFactory("tpbds");
         EntityManager man = em.createEntityManager();
         TypedQuery<Sucursal> query
         = man.createNamedQuery("Sucursal.findByIdSucursal", Sucursal.class).setParameter("idSucursal", id);
         Collection<Sucursal> results = query.getResultList();
         return results;*/
    }

}
