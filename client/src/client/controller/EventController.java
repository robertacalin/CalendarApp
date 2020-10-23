package client.controller;

import lib.dto.EventDto;
import lib.service.EventService;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.time.LocalDate;
import java.util.Collection;

public class EventController {

    private final EventService eventService;

    private EventController() {
        try {
            var registry = LocateRegistry.getRegistry("localhost", 4545);

            eventService = (EventService) registry.lookup("eventService");
        } catch (RemoteException | NotBoundException e) {
            e.printStackTrace();

            throw new RuntimeException(e);
        }
    }

    private static final class SingletonHolder {
        public static final EventController INSTANCE = new EventController();
    }

    public static EventController getInstance() {
        return EventController.SingletonHolder.INSTANCE;
    }

    public void persist(EventDto eventDto) {
        try {
            eventService.persist(eventDto);
        } catch (RemoteException e) {
            e.printStackTrace();

            throw new RuntimeException(e);
        }
    }

    public Collection<EventDto> findEventsByContIdAndDate(int contId, LocalDate data) {
        try {
            return eventService.findEventsByAccountIdAndDate(contId, data);
        } catch (RemoteException e) {
            e.printStackTrace();

            throw new RuntimeException(e);
        }
    }

    public void deleteById(int id) {
        try {
            eventService.deleteById(id);
        } catch (RemoteException e) {
            e.printStackTrace();

            throw new RuntimeException(e);
        }
    }
}
