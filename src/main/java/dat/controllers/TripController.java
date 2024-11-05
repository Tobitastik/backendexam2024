package dat.controllers;

import dat.config.HibernateConfig;
import dat.config.Populator;
import dat.daos.TripDAO;
import dat.dtos.TripDTO;
import dk.bugelhartmann.UserDTO;
import io.javalin.http.Context;
import jakarta.persistence.EntityManagerFactory;

import java.util.List;
import java.util.Set;

public class TripController {
    private final TripDAO dao;

    public TripController() {
        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory("petadoption");
        this.dao = TripDAO.getInstance(emf);
    }

    public void read(Context ctx) {
        int id = ctx.pathParamAsClass("tripid", Integer.class).get();
        TripDTO tripDTO = dao.read(id);
        ctx.status(200);
        ctx.json(tripDTO);
    }

    public void readAll(Context ctx) {
        Set<TripDTO> tripDTOS = dao.readAll();
        ctx.status(200);
        ctx.json(tripDTOS);
    }

    public void create(Context ctx) {
        TripDTO jsonRequest = ctx.bodyAsClass(TripDTO.class);
        TripDTO tripDTO = dao.create(jsonRequest);
        ctx.status(201);
        ctx.json(tripDTO);
    }

    public void update(Context ctx) {
        int id = ctx.pathParamAsClass("tripid", Integer.class).get();
        TripDTO jsonRequest = ctx.bodyAsClass(TripDTO.class);
        TripDTO tripDTO = dao.update(id, jsonRequest);
        ctx.status(200);
        ctx.json(tripDTO);
    }

    public void delete(Context ctx) {
        int id = ctx.pathParamAsClass("tripid", Integer.class).get();
        dao.delete(id);
        ctx.status(204);
    }

    public void readByCategory(Context ctx) {
        String category = ctx.pathParamAsClass("category", String.class).get();
        Set<TripDTO> tripDTOS = dao.getTripsByCategory(category);
        ctx.status(200);
        ctx.json(tripDTOS);
    }

    public void populate(Context ctx) {
        Populator.populate();
        ctx.status(200);
    }

    /*public void updateGuideForTrip(Context ctx) {
        int tripId = ctx.pathParamAsClass("tripid", Integer.class).get();
        int guideId = ctx.pathParamAsClass("guideId", Integer.class).get();
        TripDTO tripDTO = ctx.bodyAsClass(TripDTO.class).orElseThrow();
        dao.updateGuideForTrip(tripId, guideId, tripDTO);
        ctx.status(204);
    }*/
}
