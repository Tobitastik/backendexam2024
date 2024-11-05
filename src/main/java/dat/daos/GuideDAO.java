package dat.daos;

import dat.dtos.GuideDTO;
import dat.entities.Guide;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class GuideDAO implements IDAO<GuideDTO> {

    private EntityManagerFactory emf;
  /*  @Override
    public List<GuideDTO> readAll() {
        try (EntityManager em = emf.createEntityManager()) {
            TypedQuery<Guide> query = em.createQuery("SELECT g FROM Guide g", Guide.class);
            List<Guide> guides = query.getResultList();
            return guides.stream().map(GuideDTO::convertToDTO).toList();
        }
    }*/

    @Override
    public Set<GuideDTO> readAll() {
        try (EntityManager em = emf.createEntityManager()) {
            return em.createQuery("SELECT t FROM Trip t WHERE t.categories = :category", Guide.class)
                    .getResultStream()
                    .map(GuideDTO::convertToDTO)
                    .collect(Collectors.toSet());
        }
    }

    @Override
    public GuideDTO read(int id) {
        try (EntityManager em = emf.createEntityManager()) {
            Guide guide = em.find(Guide.class, id);
            return guide != null ? GuideDTO.convertToDTO(guide) : null;
        }
    }


    @Override
    public GuideDTO create(GuideDTO guideDTO) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Guide guide = new Guide(guideDTO);
            em.persist(guide);
            em.getTransaction().commit();
            return GuideDTO.convertToDTO(guide);
        }
    }

    @Override
    public GuideDTO update(int id, GuideDTO guideDTO) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Guide guide = em.find(Guide.class, id);
            if (guide != null) {
                guide.setFirstName(guideDTO.getFirstName());
                guide.setLastName(guideDTO.getLastName());
                guide.setEmail(guideDTO.getEmail());
                guide.setPhone(guideDTO.getPhone());
                guide.setYearsOfExperience(guideDTO.getYearsOfExperience());
                em.getTransaction().commit();
                return GuideDTO.convertToDTO(guide);
            }
            em.getTransaction().rollback(); // Rollback transaction if not found
            return null;
        }
    }

    @Override
    public void delete(int id) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Guide guide = em.find(Guide.class, id);
            if (guide != null) {
                em.remove(guide);
            }
            em.getTransaction().commit();
        }
    }
}
