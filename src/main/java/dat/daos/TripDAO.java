package dat.daos;

import dat.dtos.TripDTO;
import dat.entities.Guide;
import dat.entities.Trip;
import dat.enums.TripCategories;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@NoArgsConstructor
public class TripDAO implements IDAO<TripDTO>, ITripGuideDAO {

    private static TripDAO instance;
    private static EntityManagerFactory emf;

    public static TripDAO getInstance(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new TripDAO();
        }
        return instance;
    }
/*
    @Override
    public List<TripDTO> readAll() {
        try (EntityManager em = emf.createEntityManager()) {
            TypedQuery<Trip> query = em.createQuery("SELECT t FROM Trip t", Trip.class);
            List<Trip> trips = query.getResultList();
            return trips.stream().map(TripDTO::convertToDTO).toList();
        }
    }*/

    @Override
    public Set<TripDTO> readAll() {
        try (EntityManager em = emf.createEntityManager()) {
            return em.createQuery("SELECT t FROM Trip t WHERE t.categories = :category", Trip.class)
                    .getResultStream()
                    .map(TripDTO::convertToDTO)
                    .collect(Collectors.toSet());
        }
    }

    @Override
    public TripDTO read(int id) {
        try (EntityManager em = emf.createEntityManager()) {
            Trip trip = em.find(Trip.class, id);
            return trip != null ? TripDTO.convertToDTO(trip) : null;
        }
    }


    @Override
    public TripDTO create(TripDTO tripDTO) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Trip trip = new Trip(tripDTO);
            em.persist(trip);
            em.getTransaction().commit();
            return TripDTO.convertToDTO(trip);
        }
    }

    @Override
    public TripDTO update(int id, TripDTO tripDTO) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Trip trip = em.find(Trip.class, id);
            if (trip != null) {
                trip.setStartTime(tripDTO.getStartTime());
                trip.setEndTime(tripDTO.getEndTime());
                trip.setLongitude(tripDTO.getLongitude());
                trip.setLatitude(tripDTO.getLatitude());
                trip.setName(tripDTO.getName());
                trip.setPrice(tripDTO.getPrice());
                trip.setCategories(tripDTO.getCategories());
                trip.setGuide(tripDTO.getGuide());

                em.getTransaction().commit();
                return TripDTO.convertToDTO(trip);
            }
            em.getTransaction().rollback(); // Rollback transaction if not found
            return null;
        }
    }

    @Override
    public void delete(int id) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Trip trip = em.find(Trip.class, id);
            if (trip != null) {
                em.remove(trip);
            }
            em.getTransaction().commit();
        }
    }

    @Override
    public void addGuideToTrip(int tripId, int guideId) {
        try (EntityManager em = emf.createEntityManager()) {
            Trip trip = em.find(Trip.class, tripId);
            if (trip != null) {
                trip.setGuide(em.find(Guide.class, guideId));
            }
        }
    }

    @Override
    public Set<TripDTO> getTripsByGuide(int guideId) {
        try (EntityManager em = emf.createEntityManager()) {
            Guide guide = em.find(Guide.class, guideId);
            return guide != null ? guide.getTrips().stream().map(TripDTO::convertToDTO).collect(Collectors.toSet()) : null;
        }
    }

    public Set<TripDTO> getTripsByCategory(String category) {
        try (EntityManager em = emf.createEntityManager()) {
            return em.createQuery("SELECT t FROM Trip t WHERE t.categories = :category", Trip.class)
                    .setParameter("category", TripCategories.valueOf(category))
                    .getResultStream()
                    .map(TripDTO::convertToDTO)
                    .collect(Collectors.toSet());
        }
    }

    public boolean validatePrimaryKey(Integer id) {
        try (EntityManager em = emf.createEntityManager()) {
            Trip trip = em.find(Trip.class, id);
            return trip != null;
        }
    }

    public void updateGuideForTrip(int tripId, int guideId, TripDTO tripDTO) {
        try (EntityManager em = emf.createEntityManager()) {
            Trip trip = em.find(Trip.class, tripId);
            if (trip != null) {
                Guide guide = em.find(Guide.class, guideId);
                if (guide != null) {
                    trip.setGuide(guide);
                    em.merge(trip);
                }
            }
        }
    }
}
