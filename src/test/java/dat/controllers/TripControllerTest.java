package dat.controllers;

import dat.config.ApplicationConfig;
import dat.config.HibernateConfig;
import dat.config.Populator;
import io.javalin.Javalin;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;


class TripControllerTest {
    private static final EntityManagerFactory emf = HibernateConfig.getEntityManagerFactoryForTest();
    private static Javalin app;
    private static final String BASE_URL = "http://localhost:7070/api/trips";

    @BeforeAll
    static void setUpAll() {
        HibernateConfig.setIsTest(true);
        app = ApplicationConfig.startServer(7070);
    }

    @BeforeEach
    void setUp() {
        Populator.populate();
    }

    @AfterEach
    void tearDown() {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.createQuery("DELETE FROM Trip").executeUpdate();
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @AfterAll
    static void tearDownAll() {
        ApplicationConfig.stopServer(app);
    }
}
