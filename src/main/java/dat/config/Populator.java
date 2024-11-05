package dat.config;

import dat.dtos.TripDTO;
import dat.entities.*;
import dat.enums.TripCategories;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.time.LocalDate;

public class Populator {
    public static void populate() {
        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory("tripplanning"); //Remember to change the name of the DB!!
        //SecurityDAO securityDAO = new SecurityDAO(emf);
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();

            // Check if role exists, otherwise create it
            //Role adminRole = findOrCreateRole(em, "ADMIN");

            // Check if user exists, otherwise create it
            // User user = findOrCreateUser(em, "testuser", "password123"); // Password will be hashed
            //user.addRole(adminRole); // Add role to user

            //em.persist(user);
            //System.out.println("User and role successfully added to the database.");
            System.out.println("Populating database with dummy data...");
            TripDTO tripDTO1 = new TripDTO(
                    LocalDate.of(2022, 9, 1),
                    LocalDate.of(2022, 9, 5),
                    48.8567,
                    2.3508,
                    "Trip to Paris",
                    100.0,
                    0,
                    TripCategories.CITY
            );
            System.out.println("Trying to persist trip1");
            Trip trip1 = new Trip(tripDTO1);
            System.out.println("Should be persisted");

            TripDTO tripDTO2 = new TripDTO(
                    LocalDate.of(2022, 10, 1),
                    LocalDate.of(2022, 10, 5),
                    41.9028,
                    12.4964,
                    "Trip to Rome",
                    120.0,
                    0,
                    TripCategories.CITY
            );

            Trip trip2 = new Trip(tripDTO2);

            TripDTO tripDTO3 = new TripDTO(
                    LocalDate.of(2022, 11, 1),
                    LocalDate.of(2022, 11, 5),
                    41.3879,
                    2.1699,
                    "Trip to Barcelona",
                    90.0,
                    0,
                    TripCategories.BEACH
            );

            Trip trip3 = new Trip(tripDTO3);

            TripDTO tripDTO4 = new TripDTO(
                    LocalDate.of(2022, 12, 1),
                    LocalDate.of(2022, 12, 5),
                    52.3702,
                    4.8952,
                    "Trip to Amsterdam",
                    110.0,
                    0,
                    TripCategories.SNOW
            );

            Trip trip4 = new Trip(tripDTO4);

            em.persist(trip1);
            em.persist(trip2);
            em.persist(trip3);
            em.persist(trip4);

            Guide guide1 = new Guide(
                    0,
                    "John",
                    "Snow",
                    "john.Snow@example.com",
                    123456789,
                    100
            );

            Guide guide2 = new Guide(
                    0,
                    "Johnny",
                    "Deluxe",
                    "Johnny.Deluxe@example.com",
                    987654321,
                    10
            );

            em.persist(guide1);
            em.persist(guide2);

            trip1.setGuide(guide1);
            trip2.setGuide(guide2);
            trip3.setGuide(guide1);
            trip4.setGuide(guide1);

            System.out.println("Stuff added to database successfully!");

            // Commit the transaction
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
        }
    }
/*
    private static Role findOrCreateRole(EntityManager em, String roleName) {
        try {
            return em.createQuery("SELECT r FROM Role r WHERE r.name = :name", Role.class)
                    .setParameter("name", roleName)
                    .getSingleResult();
        } catch (NoResultException e) {
            Role newRole = new Role(roleName);
            em.persist(newRole);
            return newRole;
        }
    }

    private static User findOrCreateUser(EntityManager em, String username, String password) {
        try {
            return em.createQuery("SELECT u FROM User u WHERE u.username = :username", User.class)
                    .setParameter("username", username)
                    .getSingleResult();
        } catch (NoResultException e) {
            User newUser = new User(username, password); // Ensure password is hashed as needed
            em.persist(newUser);
            return newUser;
        }
    }*/
}
