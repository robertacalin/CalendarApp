package server.dao.impl;

import server.dao.AccountDao;
import server.model.Account;
import server.model.Event;
import server.model.Reminder;

import javax.persistence.EntityManager;
import java.util.List;

public class AccountDaoImpl implements AccountDao {

    private final EntityManager entityManager;

    public AccountDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void persist(Account account) {
        entityManager.getTransaction().begin();

        entityManager.persist(account);

        entityManager.getTransaction().commit();
    }

    @Override
    public boolean alreadyExists(String username) {
        var query = entityManager.createQuery(
                "SELECT c FROM Account c WHERE c.username = :username", Account.class
        );

        query.setParameter("username", username);
        return !query.getResultList().isEmpty();
    }

    @Override
    public boolean validateUser(String username, String password) {
        var query = entityManager.createQuery(
                "SELECT c FROM Account c WHERE c.username = :username AND c.password = :password", Account.class
        );

        query.setParameter("username", username)
                .setParameter("password", password);
        return !query.getResultList().isEmpty();
    }

    @Override
    public void deleteById(int accountId) {
        Account account = entityManager.find(Account.class, accountId);
        List events = entityManager.createQuery("SELECT ev FROM Account a JOIN a.events ev WHERE a.id = :accountId")
                .setParameter("accountId", accountId)
                .getResultList();

        entityManager.getTransaction().begin();

        for (int i = 0; i < events.size(); i++) {
            Event tmp = (Event) events.get(i);
            List reminders = entityManager.createQuery("SELECT rem FROM Event e JOIN e.reminders rem WHERE e.id = :eventId")
                    .setParameter("eventId", tmp.getId())
                    .getResultList();

            for (int j = 0; j < reminders.size(); j++) {
                Reminder tmp2 = (Reminder) reminders.get(j);
                entityManager.remove(tmp2);
            }
            entityManager.remove(tmp);
        }

        entityManager.remove(account);
        entityManager.getTransaction().commit();
    }

    @Override
    public Account getAccountById(int accountId) {
        return entityManager.find(Account.class, accountId);
    }

    @Override
    public int getUsernameIdByUsername(String username) {
        var query = entityManager.createQuery(
                "SELECT c.id FROM Account c WHERE c.username = :username"
        );

        query.setParameter("username", username);

        return (int) query.getSingleResult();
    }

    @Override
    public Account getAccountByUsername(String username) {
        var query = entityManager.createQuery(
                "SELECT c FROM Account c WHERE c.username = :username"
        );

        query.setParameter("username", username);

        return (Account) query.getSingleResult();
    }
}