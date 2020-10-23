package server.dao.impl;

import server.dao.ReminderDao;
import server.model.Reminder;

import javax.persistence.EntityManager;
import java.util.Collection;

public class ReminderDaoImpl implements ReminderDao {
    private final EntityManager entityManager;

    public ReminderDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void persist(Reminder reminder) {
        entityManager.getTransaction().begin();

        entityManager.persist(reminder);

        entityManager.getTransaction().commit();
    }

    @Override
    public Reminder getById(int id) {
        return entityManager.find(Reminder.class, id);
    }

    @Override
    public Collection<Reminder> findByEventId(int eventId) {
        var query = entityManager.createQuery(
                "SELECT r FROM Reminder r JOIN r.event e WHERE e.id = :id", Reminder.class
        );

        query.setParameter("id", eventId);

        return query.getResultList();
    }

    @Override
    public void deleteById(int reminderId) {
        Reminder reminder = entityManager.find(Reminder.class, reminderId);
        entityManager.getTransaction().begin();
        entityManager.remove(reminder);
        entityManager.getTransaction().commit();
    }


    @Override
    public Collection<Reminder> getNotViewedReminders(int accountId) {
        var query =  entityManager.createQuery("SELECT r FROM Reminder r JOIN r.event e JOIN e.account a " +
                " WHERE a.id = :accountId AND r.viewed = false", Reminder.class)
                .setParameter("accountId", accountId);

        return query.getResultList();
    }

    @Override
    public void update(Reminder reminder) {
        entityManager.getTransaction().begin();

        var query = entityManager.createQuery("UPDATE Reminder r SET r.viewed = true WHERE r.id = :reminderId")
                .setParameter("reminderId", reminder.getId());

        query.executeUpdate();

        entityManager.getTransaction().commit();
    }
}
