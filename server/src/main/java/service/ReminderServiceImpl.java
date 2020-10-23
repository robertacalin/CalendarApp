package service;

import lib.dto.ReminderDto;
import lib.service.ReminderService;
import server.convert.ReminderConvertor;
import server.dao.EventDao;
import server.dao.ReminderDao;
import server.dao.impl.EventDaoImpl;
import server.dao.impl.ReminderDaoImpl;

import javax.persistence.Persistence;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Collection;
import java.util.stream.Collectors;

public class ReminderServiceImpl extends UnicastRemoteObject implements ReminderService {

    private final ReminderDao reminderDao;
    private final EventDao eventDao;

    public ReminderServiceImpl() throws RemoteException {
        var emf = Persistence.createEntityManagerFactory("java2examenPU");

        reminderDao = new ReminderDaoImpl(emf.createEntityManager());
        eventDao = new EventDaoImpl(emf.createEntityManager());
    }


    @Override
    public void persist(ReminderDto reminderDto) throws RemoteException {
        var reminder = ReminderConvertor.convert(reminderDto);
        var event = eventDao.getById(reminderDto.getEventId());

        reminder.setEvent(event);
        reminderDao.persist(reminder);
    }

    @Override
    public Collection<ReminderDto> findByEventId(int eventId) throws RemoteException {
        return reminderDao.findByEventId(eventId).stream()
                .map(ReminderConvertor::convert)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(int reminderId) throws RemoteException {
        reminderDao.deleteById(reminderId);
    }

    @Override
    public Collection<ReminderDto> getNotViewedReminders(int accountId) throws RemoteException {
        return reminderDao.getNotViewedReminders(accountId).stream()
                .map(ReminderConvertor::convert)
                .collect(Collectors.toList());
    }

    @Override
    public void update(ReminderDto reminderDto) throws RemoteException {
        var reminder = ReminderConvertor.convert(reminderDto);

        reminderDao.update(reminder);
    }
}
