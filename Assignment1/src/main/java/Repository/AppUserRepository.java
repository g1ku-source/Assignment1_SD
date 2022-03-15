package Repository;

import Model.AppUser.AppUser;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;

public class AppUserRepository {

    private final EntityManagerFactory entityManagerFactory;

    public AppUserRepository() {
        this.entityManagerFactory = Persistence.createEntityManagerFactory("ro.tutorial.lab.SD");
    }

    public void insertUser(String username, String password) {
        AppUser user = new AppUser(username, password);

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(user);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public AppUser findByUsername(String username) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        try {
            return entityManager.createQuery(
                    "SELECT u from AppUser u WHERE u.username = :username", AppUser.class
            ).setParameter("username", username).getSingleResult();
        } catch(NoResultException exception) {
            System.out.println("No User found.");
        }
        entityManager.getTransaction().commit();
        entityManager.close();
        return null;
    }

    public AppUser login(String username, String password) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        try {
            return entityManager.createQuery(
                    "SELECT u from AppUser u WHERE u.username = :username and u.password = :password", AppUser.class
            ).setParameter("username", username).setParameter("password", password)
                    .getSingleResult();
        } catch(NoResultException exception) {
            System.out.println("Invalid credentials.");
        }
        entityManager.getTransaction().commit();
        entityManager.close();
        return null;
    }
}
