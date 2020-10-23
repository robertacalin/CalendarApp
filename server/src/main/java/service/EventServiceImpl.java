package service;

import lib.dto.EventDto;
import lib.service.EventService;
import server.convert.EventConvertor;
import server.dao.AccountDao;
import server.dao.EventDao;
import server.dao.ReminderDao;
import server.dao.impl.AccountDaoImpl;
import server.dao.impl.EventDaoImpl;
import server.dao.impl.ReminderDaoImpl;
import server.model.Event;

import javax.persistence.Persistence;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.time.LocalDate;
import java.util.Collection;
import java.util.stream.Collectors;

public class EventServiceImpl extends UnicastRemoteObject implements EventService {

    private final EventDao eventDao;
    private final ReminderDao reminderDao;
    private final AccountDao accountDao;

    public EventServiceImpl() throws RemoteException {
        var emf = Persistence.createEntityManagerFactory("java2examenPU");

        eventDao = new EventDaoImpl(emf.createEntityManager());
        reminderDao = new ReminderDaoImpl(emf.createEntityManager());
        accountDao = new AccountDaoImpl(emf.createEntityManager());
    }

    @Override
    public void persist(EventDto eventDto) throws RemoteException {
        var event = EventConvertor.convert(eventDto);

        var account = accountDao.getAccountById(eventDto.getAccountId());
        var reminders = eventDto.getRemindersId().stream()
                .map(reminderDao::getById)
                .collect(Collectors.toSet());

        event.setReminders(reminders);

        event.setAccount(account);

        eventDao.persist(event);
    }

    @Override
    public Collection<EventDto> findEventsByAccountIdAndDate(int accountId, LocalDate date) throws RemoteException {
        return eventDao.findEventsByAccountIdAndDate(accountId, date).stream()
                .map(EventConvertor::convert)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(int id) throws RemoteException {

        Event event = eventDao.getById(id);

        eventDao.deleteById(id);
    }
}
