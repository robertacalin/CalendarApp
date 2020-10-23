package server.dao;

import server.model.Event;

import java.time.LocalDate;
import java.util.Collection;

public interface EventDao {

    void persist(Event event);

    Event getById(int id);

    Collection<Event> findEventsByAccountIdAndDate(int contId, LocalDate dataCalendar);

    void deleteById(int id);
}

