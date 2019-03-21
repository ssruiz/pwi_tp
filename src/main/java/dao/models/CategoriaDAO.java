/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao.models;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import models.Categoria;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.transaction.Transactional;
import org.hibernate.HibernateException;
import org.hibernate.query.Query;

/**
 *
 * @author ssrui
 */

public class CategoriaDAO {

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

    public List<Categoria> getCategorias(){
        List<Categoria> lista = null;
        try {
            iniciarOperacion();
            CriteriaBuilder builder = sesion.getCriteriaBuilder();
            CriteriaQuery<Categoria> query = builder.createQuery(Categoria.class);
            Root<Categoria> root = query.from(Categoria.class);
            query.select(root);
            Query<Categoria> q = sesion.createQuery(query);
            lista = q.getResultList();
        } catch (HibernateException e) {
            manejaExcepcion(e);
        }finally{
            sesion.close();
        }
           return lista; 
    }
    /*
     public List<Categoria> getCategorias() {
     Session session1 = null;
     Transaction tran = null;
     List<Categoria> lista = null;
     EntityManager em ;
     try {
     SessionFactory factory = NewHibernateUtil.getSessionFactory();
     session1 = factory.getCurrentSession();
     tran = session1.getTransaction();
     tran.begin();
     CriteriaBuilder builder = session1.getCriteriaBuilder();
     CriteriaQuery<Categoria> criteria = builder.createQuery(Categoria.class);
     Root<Categoria> root = criteria.from(Categoria.class);
     criteria.select( root );
     lista = session1.createQuery( criteria ).getResultList();
     tran.commit();
     } catch (Exception e) {
     System.out.println("AAAAAAAAAA");
     e.printStackTrace();
     } finally {
     if (session1.isOpen()) {
     session1.close();
     }
     }

     return lista;
     }*/
}
