package server.dao.impl;

import server.dao.EventDao;
import server.model.Event;
import server.model.Reminder;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

public class EventDaoImpl implements EventDao {

    private final EntityManager entityManager;

    public EventDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void persist(Event event) {
        entityManager.getTransaction().begin();

        entityManager.persist(event);

        entityManager.getTransaction().commit();
    }

    @Override
    public Event getById(int id) {
        return entityManager.find(Event.class, id);
    }

    @Override
    public Collection<Event> findEventsByAccountIdAndDate(int accountId, LocalDate date) {
        var query = entityManager.createQuery(
                "SELECT e FROM Event e JOIN e.account c WHERE c.id = :accountId AND e.eventDate = :date"
        );

        query.setParameter("accountId", accountId)
                .setParameter("date", date);

        return query.getResultList();
    }

    @Override
    public void deleteById(int id) {

        var query = entityManager.createQuery("SELECT e FROM Event e WHERE e.id = :eventID")
                .setParameter("eventID", id);

        var event = query.getSingleResult();

        entityManager.getTransaction().begin();

        List reminders = entityManager.createQuery("SELECT rem FROM Event e JOIN e.reminders rem WHERE e.id = :eventID")
                .setParameter("eventID", id)
                .getResultList();
        for (int i = 0; i < reminders.size(); i++) {
            Reminder tmp = (Reminder)reminders.get(i);
            entityManager.remove(tmp);
        }

        entityManager.remove(event);
        entityManager.getTransaction().commit();
    }
}
