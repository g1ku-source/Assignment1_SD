package Repository;

import Model.Vacation.Location;
import Model.Vacation.Vacation;
import Model.Vacation.VacationStatus;
import org.hibernate.Session;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import java.security.InvalidParameterException;
import java.util.List;

public class VacationRepository {

    private final EntityManagerFactory entityManagerFactory;

    public VacationRepository() {
        this.entityManagerFactory = Persistence.createEntityManagerFactory("ro.tutorial.lab.SD");
    }

    public void insertVacation(Vacation vacation) {

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.merge(vacation);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public List<Vacation> findVacationsByLocation(Location location) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        try {
            return entityManager.createQuery(
                    "select v from Vacation v where v.location = :location", Vacation.class
            ).setParameter("location", location).getResultList();
        } catch (Exception e) {
            System.out.println(e.getClass());
        }
        entityManager.getTransaction().commit();
        entityManager.close();
        return null;
    }

    public void deleteVacations(List<Vacation> vacations) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        try {
            for(Vacation vacation : vacations) {

                entityManager.remove(
                        entityManager.contains(vacation) ? vacation :
                                entityManager.merge(vacation));
            }
        } catch (Exception exception) {
            System.out.println(exception.getClass());
        }
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public Vacation updateVacation(Vacation updatedVacation) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        try {
            Vacation vacation = entityManager.find(Vacation.class, updatedVacation.getId());
            if(vacation.getNumberUsers() == 0)
                throw new InvalidParameterException();

            vacation.setNumberUsers(vacation.getNumberUsers() - 1);
            if(vacation.getNumberUsers() == 0)
                vacation.setVacationStatus(VacationStatus.BOOKED);
            else
                vacation.setVacationStatus(VacationStatus.IN_PROGRESS);

            entityManager.merge(vacation);
            entityManager.getTransaction().commit();
            entityManager.close();
            return vacation;
        } catch (NoResultException exception) {
            System.out.println("dsa");
        }
        entityManager.getTransaction().commit();
        entityManager.close();
        return null;
    }
}
