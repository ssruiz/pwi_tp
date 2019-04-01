/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao.models;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;

import models.Reserva;
import javax.persistence.criteria.Root;
import models.HorarioExcepcion;
import models.Persona;
import models.Sucursal;

import models.SucursalServicio;
import models.auxiliares.BusquedaReserva;
import models.auxiliares.NuevaReserva;
import models.auxiliares.PutReserva;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author ssrui
 */
public class ReservaDAO {

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

    public String getHorariosDisponibles(int id, Date fecha) {
        String lista = null;
        try {
            iniciarOperacion();
            CriteriaBuilder builder = sesion.getCriteriaBuilder();

            CriteriaQuery<SucursalServicio> cqs = builder.createQuery(SucursalServicio.class);
            Root<SucursalServicio> scursa = cqs.from(SucursalServicio.class);
            cqs.select(scursa).where(builder.equal(scursa.get("idSucursalServicio"), id));
            Query<SucursalServicio> querySS = sesion.createQuery(cqs);
            SucursalServicio aux = querySS.getSingleResult();

            Calendar cal = Calendar.getInstance();
            cal.setTime(fecha);
            int day = cal.get(Calendar.DAY_OF_WEEK);

            // se revisa si hay una excepcion para esa fecha
            CriteriaQuery<HorarioExcepcion> cHoraEx = builder.createQuery(HorarioExcepcion.class);
            Root<HorarioExcepcion> rootHoraEx = cHoraEx.from(HorarioExcepcion.class);
            Predicate[] predicatesEx = new Predicate[2];
            predicatesEx[0] = builder.equal(rootHoraEx.get("sucursal").get("idSucursal"), aux.getSucursal().getIdSucursal());
            predicatesEx[1] = builder.equal(rootHoraEx.get("fecha"), cal.getTime());
            cHoraEx.select(rootHoraEx).where(predicatesEx);

            Query<HorarioExcepcion> queryHoraEx = sesion.createQuery(cHoraEx);
            HorarioExcepcion auxHEx = null;
            boolean excp = true;
            try {
                auxHEx = queryHoraEx.getSingleResult();
            } catch (Exception e) {
                excp = false;
            }

            Date horaAper = aux.getSucursal().getLunesHoraApertura();
            Date horaCierre = aux.getSucursal().getLunesHoraCierre();

            // Si hay excepcion para esa fecha se toma la hora de apertura y cierre de la tabla HoraExcepcion
            if (excp == true) {
                System.out.println("EXCEPCION");
                horaAper = auxHEx.getHoraApertura();
                horaCierre = auxHEx.getHoraCierre();
            } else {
                switch (day) {
                    case 1:
                        horaAper = aux.getSucursal().getDomingoHoraApertura();
                        horaCierre = aux.getSucursal().getDomingoHoraCierre();
                        break;
                    case 2:
                        horaAper = aux.getSucursal().getLunesHoraApertura();
                        horaCierre = aux.getSucursal().getLunesHoraCierre();
                        break;
                    case 3:
                        horaAper = aux.getSucursal().getMartesHoraApertura();
                        horaCierre = aux.getSucursal().getMartesHoraCierre();
                        break;
                    case 4:
                        horaAper = aux.getSucursal().getMiercolesHoraApertura();
                        horaCierre = aux.getSucursal().getMiercolesHoraCierre();
                        break;
                    case 5:
                        horaAper = aux.getSucursal().getJuevesHoraApertura();
                        horaCierre = aux.getSucursal().getJuevesHoraCierre();
                        break;
                    case 6:
                        horaAper = aux.getSucursal().getViernesHoraApertura();
                        horaCierre = aux.getSucursal().getViernesHoraCierre();
                        break;
                    case 7:
                        horaAper = aux.getSucursal().getSabadoHoraApertura();
                        horaCierre = aux.getSucursal().getSabadoHoraCierre();
                        break;

                }

            }

            Calendar cal1 = Calendar.getInstance();
            Calendar cal2 = Calendar.getInstance();
            Calendar cal3 = Calendar.getInstance();

            cal1.setTime(horaAper);
            cal2.setTime(horaCierre);
            cal3.setTime(horaAper);

            System.out.println("HORA INICIO>>>" + cal1.getTime());
            System.out.println("HORAI FIN>>>" + cal2.getTime());

            int duracion = aux.getServicio().getDuracionReferencia();
            int capacidad = aux.getCapacidad();
            int i = 0;

            CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
            Root<Reserva> reservaRoot = criteriaQuery.from(Reserva.class);
            Predicate[] predicates = new Predicate[5];
            predicates[0] = builder.equal(reservaRoot.get("sucursalServicio").get("idSucursalServicio"), id);
            predicates[1] = builder.equal(reservaRoot.get("fecha"), fecha);
            predicates[2] = builder.equal(reservaRoot.get("flagEstado"), "R");

            JSONArray listaDisponibles = new JSONArray();
            SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy");
            String fechaReserva = DATE_FORMAT.format(fecha);

            String hi = "";
            String mi = "";
            String mf = "";
            String hf = "";
            while (cal1.get(Calendar.HOUR_OF_DAY) < cal2.get(Calendar.HOUR_OF_DAY)) {

                predicates[3] = builder.equal(reservaRoot.get("horaInicio"), cal1.getTime());
                //cal3.setTime(cal1.getTime());
                cal1.add(Calendar.MINUTE, duracion);
                predicates[4] = builder.equal(reservaRoot.get("horaFin"), cal1.getTime());

                long count = controlarCantidadReservas(criteriaQuery, builder, reservaRoot, predicates);

                if (count < capacidad) {

                    JSONObject obj = new JSONObject();
                    if (cal3.get(Calendar.HOUR_OF_DAY) < 10) {
                        hi = "0" + cal3.get(Calendar.HOUR_OF_DAY);
                    } else {
                        hi = Integer.toString(cal3.get(Calendar.HOUR_OF_DAY));
                    }
                    if (cal3.get(Calendar.MINUTE) < 10) {
                        mi = "0" + cal3.get(Calendar.MINUTE);
                    } else {
                        mi = Integer.toString(cal3.get(Calendar.MINUTE));
                    }

                    if (cal1.get(Calendar.HOUR_OF_DAY) < 10) {
                        hf = "0" + cal1.get(Calendar.HOUR_OF_DAY);
                    } else {
                        hf = Integer.toString(cal1.get(Calendar.HOUR_OF_DAY));
                    }

                    if (cal1.get(Calendar.MINUTE) < 10) {
                        mf = "0" + cal1.get(Calendar.MINUTE);
                    } else {
                        mf = Integer.toString(cal1.get(Calendar.MINUTE));
                    }
                    obj.put("hora inicio:", hi + ":" + mi);
                    obj.put("hora fin:", hf + ":" + mf);
                    obj.put("fecha", fechaReserva);
                    listaDisponibles.add(obj);
                    //System.out.println("Si. Nro reservas: " + count);
                    i++;
                }
                cal3.add(Calendar.MINUTE, duracion);

            }
            JSONObject obj = new JSONObject();
            obj.put("Total", i);
            listaDisponibles.add(obj);
            lista = listaDisponibles.toJSONString();
            tran.commit();

        } catch (HibernateException e) {
            manejaExcepcion(e);
        } finally {
            sesion.close();
        }
        return lista;
    }

    /* EJERCICIO 6*/
    public String getHorariosDisponiblesPorEmpleado(int id, Date fecha, int idEmpleado) {
        String lista = null;
        try {
            iniciarOperacion();
            CriteriaBuilder builder = sesion.getCriteriaBuilder();

            CriteriaQuery<SucursalServicio> cqs = builder.createQuery(SucursalServicio.class);
            Root<SucursalServicio> scursa = cqs.from(SucursalServicio.class);
            cqs.select(scursa).where(builder.equal(scursa.get("idSucursalServicio"), id));
            Query<SucursalServicio> querySS = sesion.createQuery(cqs);
            SucursalServicio aux = querySS.getSingleResult();

            Calendar cal = Calendar.getInstance();
            cal.setTime(fecha);
            int day = cal.get(Calendar.DAY_OF_WEEK);

            // se revisa si hay una excepcion para esa fecha
            CriteriaQuery<HorarioExcepcion> cHoraEx = builder.createQuery(HorarioExcepcion.class);
            Root<HorarioExcepcion> rootHoraEx = cHoraEx.from(HorarioExcepcion.class);
            Predicate[] predicatesEx = new Predicate[2];
            predicatesEx[0] = builder.equal(rootHoraEx.get("sucursal").get("idSucursal"), aux.getSucursal().getIdSucursal());
            predicatesEx[1] = builder.equal(rootHoraEx.get("fecha"), cal.getTime());
            cHoraEx.select(rootHoraEx).where(predicatesEx);

            Query<HorarioExcepcion> queryHoraEx = sesion.createQuery(cHoraEx);
            HorarioExcepcion auxHEx = null;
            boolean excp = true;
            try {
                auxHEx = queryHoraEx.getSingleResult();
            } catch (Exception e) {
                excp = false;
            }

            Date horaAper = aux.getSucursal().getLunesHoraApertura();
            Date horaCierre = aux.getSucursal().getLunesHoraCierre();
            System.out.println(">>>> " + horaAper);
            // Si hay excepcion para esa fecha se toma la hora de apertura y cierre de la tabla HoraExcepcion
            if (excp == true) {
                System.out.println("EXCEPCION");
                horaAper = auxHEx.getHoraApertura();
                horaCierre = auxHEx.getHoraCierre();
            } else {
                switch (day) {
                    case 1:
                        horaAper = aux.getSucursal().getDomingoHoraApertura();
                        horaCierre = aux.getSucursal().getDomingoHoraCierre();
                        break;
                    case 2:
                        horaAper = aux.getSucursal().getLunesHoraApertura();
                        horaCierre = aux.getSucursal().getLunesHoraCierre();
                        break;
                    case 3:
                        horaAper = aux.getSucursal().getMartesHoraApertura();
                        horaCierre = aux.getSucursal().getMartesHoraCierre();
                        break;
                    case 4:
                        horaAper = aux.getSucursal().getMiercolesHoraApertura();
                        horaCierre = aux.getSucursal().getMiercolesHoraCierre();
                        break;
                    case 5:
                        horaAper = aux.getSucursal().getJuevesHoraApertura();
                        horaCierre = aux.getSucursal().getJuevesHoraCierre();
                        break;
                    case 6:
                        horaAper = aux.getSucursal().getViernesHoraApertura();
                        horaCierre = aux.getSucursal().getViernesHoraCierre();
                        break;
                    case 7:
                        horaAper = aux.getSucursal().getSabadoHoraApertura();
                        horaCierre = aux.getSucursal().getSabadoHoraCierre();
                        break;

                }

            }

            Calendar cal1 = Calendar.getInstance();
            Calendar cal2 = Calendar.getInstance();
            Calendar cal3 = Calendar.getInstance();

            cal1.setTime(horaAper);
            cal2.setTime(horaCierre);
            cal3.setTime(horaAper);
            int duracion = aux.getServicio().getDuracionReferencia();
            int capacidad = aux.getCapacidad();
            int i = 0;

            CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
            CriteriaQuery<Long> empleadoCriteria = builder.createQuery(Long.class);
            Root<Reserva> reservaRoot = criteriaQuery.from(Reserva.class);
            Root<Reserva> reservaRootEmp = empleadoCriteria.from(Reserva.class);
            Predicate[] predicatesReserva = new Predicate[5];
            predicatesReserva[0] = builder.equal(reservaRoot.get("sucursalServicio").get("idSucursalServicio"), id);
            predicatesReserva[1] = builder.equal(reservaRoot.get("fecha"), fecha);
            predicatesReserva[2] = builder.equal(reservaRoot.get("flagEstado"), "R");

            Predicate[] predicatesEmpleado = new Predicate[5];
            predicatesEmpleado[0] = builder.equal(reservaRootEmp.get("sucursalServicio").get("idSucursalServicio"), id);
            predicatesEmpleado[1] = builder.equal(reservaRootEmp.get("personaByIdEmpleado").get("idPersona"), idEmpleado);
            predicatesEmpleado[2] = builder.equal(reservaRoot.get("fecha"), fecha);
            JSONArray listaDisponibles = new JSONArray();
            SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy");
            String fechaReserva = DATE_FORMAT.format(fecha);
            //reservaRoot.get("personaByIdEmpleado").get("idPersona")
            String hi = "";
            String mi = "";
            String mf = "";
            String hf = "";
            while (cal1.get(Calendar.HOUR_OF_DAY) < cal2.get(Calendar.HOUR_OF_DAY)) {
                // predicados para las horas actuales
                predicatesReserva[3] = builder.equal(reservaRoot.get("horaInicio"), cal1.getTime());
                predicatesEmpleado[3] = builder.equal(reservaRootEmp.get("horaInicio"), cal1.getTime());
                cal1.add(Calendar.MINUTE, duracion);
                predicatesReserva[4] = builder.equal(reservaRoot.get("horaFin"), cal1.getTime());
                predicatesEmpleado[4] = builder.equal(reservaRootEmp.get("horaFin"), cal1.getTime());

                long countCapacidad = controlarCantidadReservas(criteriaQuery, builder, reservaRoot, predicatesReserva);
                long countEmpleado = controlarEmpleado(empleadoCriteria, builder, reservaRootEmp, predicatesEmpleado);

                if (countCapacidad < capacidad && countEmpleado < 1) {
                    JSONObject obj = new JSONObject();
                    if (cal3.get(Calendar.HOUR_OF_DAY) < 10) {
                        hi = "0" + cal3.get(Calendar.HOUR_OF_DAY);
                    } else {
                        hi = Integer.toString(cal3.get(Calendar.HOUR_OF_DAY));
                    }
                    if (cal3.get(Calendar.MINUTE) < 10) {
                        mi = "0" + cal3.get(Calendar.MINUTE);
                    } else {
                        mi = Integer.toString(cal3.get(Calendar.MINUTE));
                    }

                    if (cal1.get(Calendar.HOUR_OF_DAY) < 10) {
                        hf = "0" + cal1.get(Calendar.HOUR_OF_DAY);
                    } else {
                        hf = Integer.toString(cal1.get(Calendar.HOUR_OF_DAY));
                    }

                    if (cal1.get(Calendar.MINUTE) < 10) {
                        mf = "0" + cal1.get(Calendar.MINUTE);
                    } else {
                        mf = Integer.toString(cal1.get(Calendar.MINUTE));
                    }
                    obj.put("hora inicio:", hi + ":" + mi);
                    obj.put("hora fin:", hf + ":" + mf);
                    obj.put("fecha", fechaReserva);
                    listaDisponibles.add(obj);
                    //System.out.println("Si. Nro reservas: " + count);
                    i++;
                }
                cal3.add(Calendar.MINUTE, duracion);

            }
            JSONObject obj = new JSONObject();
            obj.put("Total", i);

            listaDisponibles.add(obj);
            lista = listaDisponibles.toJSONString();
            tran.commit();

        } catch (HibernateException e) {
            manejaExcepcion(e);
        } finally {
            sesion.close();
        }
        return lista;
    }

    public long controlarCantidadReservas(CriteriaQuery<Long> criteriaQuery, CriteriaBuilder builder, Root<Reserva> reservaRoot, Predicate[] predicatesReserva) {
        long res = 0;
        try {
            criteriaQuery.select(builder.count(reservaRoot)).where(predicatesReserva);
            Query<Long> query = sesion.createQuery(criteriaQuery);
            res = query.getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    public long controlarEmpleado(CriteriaQuery<Long> empleadoCriteria, CriteriaBuilder builder, Root<Reserva> reservaRootEmp, Predicate[] predicatesEmpleado) {
        long res = 0;
        try {
            empleadoCriteria.select(builder.count(reservaRootEmp)).where(predicatesEmpleado);
            Query<Long> queryEmp = sesion.createQuery(empleadoCriteria);

            res = queryEmp.getSingleResult();
        } catch (Exception e) {
        }
        return res;
    }

    public String reservar(NuevaReserva nr) {
        String resultado = "";
        try {
            iniciarOperacion();
            boolean condicion1, condicion2;
            CriteriaBuilder builder = sesion.getCriteriaBuilder();

            CriteriaQuery<SucursalServicio> cqs = builder.createQuery(SucursalServicio.class);
            Root<SucursalServicio> scursa = cqs.from(SucursalServicio.class);
            cqs.select(scursa).where(builder.equal(scursa.get("idSucursalServicio"), nr.getIdSucursalServicio()));
            Query<SucursalServicio> querySS = sesion.createQuery(cqs);
            SucursalServicio aux = querySS.getSingleResult();
            Date horaAper = aux.getSucursal().getJuevesHoraApertura();
            Calendar cal1 = Calendar.getInstance();
            Calendar cal2 = Calendar.getInstance();
            Calendar cal3 = Calendar.getInstance();
            cal3.setTime(nr.getFecha());
            cal1.setTime(horaAper);
            cal2.setTime(horaAper);
            cal1.set(Calendar.HOUR_OF_DAY, nr.getHoraInicio().getHour());
            cal1.set(Calendar.MINUTE, nr.getHoraInicio().getMinute());
            cal2.set(Calendar.HOUR_OF_DAY, nr.getHoraFin().getHour());
            cal2.set(Calendar.MINUTE, nr.getHoraFin().getMinute());

            int duracion = aux.getServicio().getDuracionReferencia();
            int capacidad = aux.getCapacidad();
            int i = 0;

            CriteriaQuery<Long> criteriaReserva = builder.createQuery(Long.class);

            Root<Reserva> reservaRoot = criteriaReserva.from(Reserva.class);

            Predicate[] predicatesReserva = new Predicate[5];
            predicatesReserva[0] = builder.equal(reservaRoot.get("sucursalServicio").get("idSucursalServicio"), nr.getIdSucursalServicio());
            predicatesReserva[1] = builder.equal(reservaRoot.get("fecha"), cal3.getTime());
            predicatesReserva[2] = builder.equal(reservaRoot.get("flagEstado"), "R");
            predicatesReserva[3] = builder.equal(reservaRoot.get("horaInicio"), cal1.getTime());
            predicatesReserva[4] = builder.equal(reservaRoot.get("horaFin"), cal2.getTime());

            long countCapacidad = controlarCantidadReservas(criteriaReserva, builder, reservaRoot, predicatesReserva);

            if (countCapacidad < capacidad) {
                condicion1 = true;

            } else {
                condicion1 = false;
            }

            if (nr.getIdEmpleado() != 0) {

                CriteriaQuery<Long> empleadoCriteria = builder.createQuery(Long.class);
                Root<Reserva> reservaRootEmp = empleadoCriteria.from(Reserva.class);
                Predicate[] predicatesEmpleado = new Predicate[5];
                predicatesEmpleado[0] = builder.equal(reservaRootEmp.get("sucursalServicio").get("idSucursalServicio"), nr.getIdSucursalServicio());
                predicatesEmpleado[1] = builder.equal(reservaRootEmp.get("personaByIdEmpleado").get("idPersona"), nr.getIdEmpleado());
                predicatesEmpleado[2] = builder.equal(reservaRoot.get("fecha"), cal3.getTime());
                predicatesEmpleado[3] = builder.equal(reservaRootEmp.get("horaInicio"), cal1.getTime());
                predicatesEmpleado[4] = builder.equal(reservaRootEmp.get("horaFin"), cal2.getTime());
                long countEmpleado = controlarEmpleado(empleadoCriteria, builder, reservaRootEmp, predicatesEmpleado);
                if (countEmpleado == 1) {
                    condicion2 = false;
                    JSONObject obj = new JSONObject();
                    obj.put("Resultado", "Empleado ocupado en esa fecha y hora");
                    resultado = obj.toJSONString();
                    return resultado;
                } else {
                    condicion2 = true;
                }
            } else {
                condicion2 = true;
            }

            if (condicion1 && condicion2) {
                Reserva nuevaR;
                nuevaR = new Reserva();
                nuevaR.setFecha(cal3.getTime());
                nuevaR.setFechaHoraCreacion(Calendar.getInstance().getTime());
                nuevaR.setFlagEstado("R");
                //nuevaR.setHoraInicio(cal1.getTime());
                // nuevaR.setHoraFin(cal2.getTime());
                nuevaR.setIdReserva(400);
                Persona auxp = getCliente(nr.getEmail());
                SucursalServicio sucaux = getSucursal(nr.getIdSucursalServicio());
                nuevaR.setSucursalServicio(aux);
                nuevaR.setPersonaByIdCliente(auxp);
                sesion.save(nuevaR);
                //nuevaR.setPersonaByIdEmpleado(null);
                //sesion.persist(nuevaR);
                tran.commit();
                JSONObject obj = new JSONObject();
                obj.put("Resultado", "Rserva creada");
                resultado = obj.toJSONString();

            } else {
                JSONObject obj = new JSONObject();
                obj.put("Resultado", "Se alcanzó el límite de capacidad para ese horario");
                resultado = obj.toJSONString();
            }
            /*Predicate[] predicatesEmpleado = new Predicate[4];
             predicatesEmpleado[0] = builder.equal(reservaRootEmp.get("sucursalServicio").get("idSucursalServicio"), id);
             predicatesEmpleado[1] = builder.equal(reservaRootEmp.get("personaByIdEmpleado").get("idPersona"), idEmpleado);

             JSONArray listaDisponibles = new JSONArray();
             SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy");
             String fechaReserva = DATE_FORMAT.format(fecha);
             //reservaRoot.get("personaByIdEmpleado").get("idPersona")
             String hi = "";
             String mi = "";
             String mf = "";
             String hf = "";*/

        } catch (HibernateException e) {
            manejaExcepcion(e);
        } finally {
            sesion.close();
        }
        return resultado;
    }

    public Persona getCliente(String email) {
        CriteriaBuilder builder = sesion.getCriteriaBuilder();
        CriteriaQuery<Persona> criteriaPersona = builder.createQuery(Persona.class);
        Root<Persona> personaRoot = criteriaPersona.from(Persona.class);
        Predicate[] prediPersona = new Predicate[2];
        prediPersona[0] = builder.equal(personaRoot.get("email"), email);
        prediPersona[1] = builder.equal(personaRoot.get("flagEmpleado"), "C");
        criteriaPersona.select(personaRoot).where(prediPersona);
        Query<Persona> querySS = sesion.createQuery(criteriaPersona);
        Persona aux = null;

        try {
            aux = querySS.getSingleResult();
        } catch (Exception e) {
        }
        return aux;
    }

    public SucursalServicio getSucursal(int id) {
        CriteriaBuilder builder = sesion.getCriteriaBuilder();
        CriteriaQuery<SucursalServicio> cqs = builder.createQuery(SucursalServicio.class);
        Root<SucursalServicio> scursa = cqs.from(SucursalServicio.class);
        cqs.select(scursa).where(builder.equal(scursa.get("idSucursalServicio"), id));
        Query<SucursalServicio> querySS = sesion.createQuery(cqs);
        SucursalServicio aux = querySS.getSingleResult();
        return aux;
    }
    /*Ejercicio 8*/

    public String getAllReserv(BusquedaReserva nr) {
        String resultado = "";
        
        System.out.println("Desde" + nr.getFechaDesde());
        System.out.println("Hasta" + nr.getFechaHasta());

        try {
            iniciarOperacion();
            CriteriaBuilder builder = sesion.getCriteriaBuilder();

            CriteriaQuery<SucursalServicio> cqs = builder.createQuery(SucursalServicio.class);
            Root<SucursalServicio> scursa = cqs.from(SucursalServicio.class);
            cqs.select(scursa).where(builder.equal(scursa.get("idSucursalServicio"), 100));
            Query<SucursalServicio> querySS = sesion.createQuery(cqs);
            SucursalServicio aux = querySS.getSingleResult();
            Date horaAper = aux.getSucursal().getDomingoHoraCierre();
            Calendar cal1 = Calendar.getInstance();
            Calendar cal2 = Calendar.getInstance();
            Calendar cal3 = Calendar.getInstance();
            cal3.setTime(nr.getFechaDesde());
            cal1.setTime(horaAper);
            cal2.setTime(horaAper);

            //CriteriaBuilder builder = sesion.getCriteriaBuilder();
            CriteriaQuery<Reserva> criteriaReserva = builder.createQuery(Reserva.class);
            Root<Reserva> reservaRoot = criteriaReserva.from(Reserva.class);

            cal1.set(Calendar.HOUR_OF_DAY, nr.getHoraInicio().getHour());
            cal1.set(Calendar.MINUTE, nr.getHoraInicio().getMinute());
            cal1.set(Calendar.SECOND, nr.getHoraInicio().getSecond());
            cal2.set(Calendar.HOUR_OF_DAY, nr.getHoraFin().getHour());
            cal2.set(Calendar.MINUTE, nr.getHoraFin().getMinute());
            cal2.set(Calendar.SECOND, nr.getHoraFin().getSecond());

            Calendar cal = Calendar.getInstance();

            Date date = cal1.getTime();

            DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
            String formattedDate = dateFormat.format(date);

            Predicate[] predicates = new Predicate[5];
            predicates[0] = builder.greaterThanOrEqualTo(reservaRoot.<Date>get("horaInicio"), cal1.getTime());
            predicates[1] = builder.lessThanOrEqualTo(reservaRoot.<Date>get("horaInicio"), cal2.getTime());

            predicates[2] = builder.equal(reservaRoot.get("sucursalServicio").get("sucursal").get("idSucursal"), nr.getIdSucursal());
            predicates[3] = builder.greaterThanOrEqualTo(reservaRoot.<Date>get("fecha"), nr.getFechaDesde());
            predicates[4] = builder.lessThanOrEqualTo(reservaRoot.<Date>get("fecha"), nr.getFechaHasta());

            criteriaReserva.select(reservaRoot).where(predicates);

            Query<Reserva> queryReservas = sesion.createQuery(criteriaReserva);
            List<Reserva> reser = queryReservas.getResultList();

            System.out.println("SIZE>>" + reser.size());
            //System.out.println("SIZE>>" + cal1.getTime());

            for (Reserva item : reser) {
                System.out.println(item.getFlagEstado());
            }
            /*
             boolean condicion1, condicion2;
             CriteriaBuilder builder = sesion.getCriteriaBuilder();
             CriteriaQuery<Reserva> criteriaReserva = builder.createQuery(Reserva.class);
             Root<Reserva> reservaRoot = criteriaReserva.from(Reserva.class);
             criteriaReserva.select(reservaRoot).where(builder.equal(reservaRoot.get("idReserva"), nr.getIdReserva()));
             Query<Reserva> query = sesion.createQuery(criteriaReserva);
             Reserva r = query.getSingleResult();

             r.setObservacion(nr.getObservacion());
             r.setFlagAsistio(nr.getFlagAsistio());
             r.setFlagEstado(nr.getFlagEstado());

             sesion.update(r);
             tran.commit();
             JSONObject obj = new JSONObject();
             obj.put("Resultado", "La reserva se actualizo  correctamente");
             resultado = obj.toJSONString();
             */
        } catch (HibernateException e) {
            e.printStackTrace();
            JSONObject obj = new JSONObject();
            obj.put("Resultado", "La reserva no se actualizo  correctamente");
            resultado = obj.toJSONString();
            manejaExcepcion(e);
        } finally {
            sesion.close();
        }
        return resultado;
    }
    /*Ejercicio 9*/

    public String actualizarReserv(PutReserva nr) {
        String resultado = "";
        System.out.println("AAAAAAAAAA");
        try {
            iniciarOperacion();
            boolean condicion1, condicion2;

            CriteriaBuilder builder = sesion.getCriteriaBuilder();
            CriteriaQuery<Reserva> criteriaReserva = builder.createQuery(Reserva.class);
            Root<Reserva> reservaRoot = criteriaReserva.from(Reserva.class);
            criteriaReserva.select(reservaRoot).where(builder.equal(reservaRoot.get("idReserva"), nr.getIdReserva()));
            Query<Reserva> query = sesion.createQuery(criteriaReserva);
            Reserva r = query.getSingleResult();

            r.setObservacion(nr.getObservacion());
            r.setFlagAsistio(nr.getFlagAsistio());
            r.setFlagEstado(nr.getFlagEstado());

            sesion.update(r);
            tran.commit();
            JSONObject obj = new JSONObject();
            obj.put("Resultado", "La reserva se actualizo  correctamente");
            resultado = obj.toJSONString();

        } catch (HibernateException e) {
            e.printStackTrace();
            JSONObject obj = new JSONObject();
            obj.put("Resultado", "La reserva no se actualizo  correctamente");
            resultado = obj.toJSONString();
            manejaExcepcion(e);
        } finally {
            sesion.close();
        }
        return resultado;
    }
}
