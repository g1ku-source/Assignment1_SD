package Repository;

import Model.AppUser.AppUser;
import Model.AppUserVacation;
import Model.CompositeKeys;
import Model.Vacation.Vacation;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import java.util.List;

public class AppUserVacationRepository {

    private final EntityManagerFactory entityManagerFactory;

    public AppUserVacationRepository() {
        this.entityManagerFactory =
                Persistence.createEntityManagerFactory("ro.tutorial.lab.SD");
    }

    public AppUserVacation findByVacationAndUser(Vacation vacation, AppUser appUser) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        try {
            return (AppUserVacation) entityManager.createQuery(
                    "SELECT av from AppUserVacation av where av.appUser = :user and av.vacation = :vacation"
            ).setParameter("user", appUser).setParameter("vacation", vacation).getSingleResult();
        } catch (NoResultException exception) {
            System.out.println("No appuser vacation");
            return null;
        }
    }

    public List<AppUserVacation> findByUser(AppUser appUser) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        try {
            return entityManager.createQuery(
                    "SELECT av from AppUserVacation av where av.appUser = :user", AppUserVacation.class
            ).setParameter("user", appUser).getResultList();
        } catch (NoResultException exception) {
            System.out.println("No appuser vacation");
            return null;
        }
    }

    public void insert(Vacation vacation, AppUser user) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        try {
            AppUserVacation appUserVacation = findByVacationAndUser(vacation, user);
            if(appUserVacation != null) {
                appUserVacation.setNumberOfUsers(appUserVacation.getNumberOfUsers() + 1);
                entityManager.merge(appUserVacation);
            }
            else
                entityManager.merge(new AppUserVacation(vacation, user));
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public List<AppUserVacation> findByVacation(Vacation vacation) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        try {
            return entityManager.createQuery(
                    "select av from AppUserVacation av where av.vacation = :vacation", AppUserVacation.class
            ).setParameter("vacation", vacation).getResultList();
        } catch (Exception e) {
            System.out.println(e.getClass());
        }
        entityManager.getTransaction().commit();
        entityManager.close();
        return null;
    }

    public void delete(Vacation vacation) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        try {
            List<AppUserVacation> appUserVacations =
                    findByVacation(vacation);

            for(AppUserVacation appUserVacation : appUserVacations) {
                entityManager.remove(
                        entityManager.contains(appUserVacation) ? appUserVacation :
                                entityManager.merge(appUserVacation));
            }
        } catch (Exception e) {
            System.out.println(e.getClass());
        }
        entityManager.getTransaction().commit();
        entityManager.close();
    }
}
