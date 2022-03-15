package Repository;

import Model.Vacation.Location;
import org.hibernate.engine.jdbc.spi.SqlExceptionHelper;

import javax.persistence.*;
import java.util.List;

public class LocationRepository {

    private final EntityManagerFactory entityManagerFactory;

    public LocationRepository() {
        this.entityManagerFactory = Persistence.createEntityManagerFactory("ro.tutorial.lab.SD");
    }

    public Location insertLocation(String destination, String description) {

        Location location = new Location(destination, description);
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(location);
        entityManager.getTransaction().commit();
        entityManager.close();

        return location;
    }

    public List<Location> findAll() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        try {
            return entityManager.createQuery(
                    "SELECT l from Location l", Location.class
            ).getResultList();
        } catch (NoResultException exception) {
            System.out.println("No locations found");
        }
        entityManager.getTransaction().commit();
        entityManager.close();
        return null;
    }

    public Location findLocation(String destination) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        try {
            return entityManager.createQuery(
                    "select l from Location l where l.destination = :destination", Location.class
            ).setParameter("destination", destination).getSingleResult();
        } catch (Exception e) {
            System.out.println(e.getClass());
        }
        entityManager.getTransaction().commit();
        entityManager.close();
        return null;
    }

    public void deleteLocation(Location location) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        try {
            entityManager.remove(
                    entityManager.contains(location) ? location :
                            entityManager.merge(location)
            );
        } catch (Exception exception) {
            System.out.println(exception.getClass());
        }
        entityManager.getTransaction().commit();
        entityManager.close();
    }
}
