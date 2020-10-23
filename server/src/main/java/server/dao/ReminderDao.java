package server.dao;

import server.model.Reminder;

import java.util.Collection;

public interface ReminderDao {

    void persist(Reminder reminder);

    Reminder getById(int id);

    Collection<Reminder> findByEventId(int evenimentId);

    void deleteById(int reminderId);

    Collection<Reminder> getNotViewedReminders(int accountId);

    void update(Reminder reminder);
}
