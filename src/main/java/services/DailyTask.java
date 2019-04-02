/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.SimpleTrigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

/**
 *
 * @author ssrui
 */
@WebServlet("/hello")
public class DailyTask extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        try {
            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
            scheduler.start();
            JobDetail job = JobBuilder.newJob(Ejercicio10.class)
                    .withIdentity("Ejercicio10")
                    .build();
            SimpleTrigger trig = TriggerBuilder.newTrigger().withIdentity("trigger1")
                    .startNow()
                    .withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(5).repeatForever())
                    .build();

            scheduler.scheduleJob(job, trig);
        } catch (Exception e) {
        }
        resp.setContentType("text/plain");

        resp.getWriter().write("Hello World! Maven Web Project Example.");

    }

    public static void main(String[] args) {
        try {

        } catch (Exception e) {
        }

    }
}
