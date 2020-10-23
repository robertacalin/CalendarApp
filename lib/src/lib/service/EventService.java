package lib.service;

import lib.dto.EventDto;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.time.LocalDate;
import java.util.Collection;

public interface EventService extends Remote {

    void persist(EventDto eventDto) throws RemoteException;

    Collection<EventDto> findEventsByAccountIdAndDate(int contId, LocalDate data) throws RemoteException;

    void deleteById(int id) throws RemoteException;
}
