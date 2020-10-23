package lib.service;

import lib.dto.ReminderDto;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Collection;

public interface ReminderService extends Remote {

    void persist(ReminderDto reminderDto) throws RemoteException;

    Collection<ReminderDto> findByEventId(int evenimentId) throws RemoteException;

    void deleteById(int reminderId) throws RemoteException;

    Collection<ReminderDto> getNotViewedReminders(int accountId) throws RemoteException;

    void update(ReminderDto reminderDto) throws RemoteException;
}

