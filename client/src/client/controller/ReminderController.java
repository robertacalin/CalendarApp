package client.controller;

import lib.dto.AccountDto;
import lib.dto.ReminderDto;
import lib.service.ReminderService;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.Collection;

public class ReminderController {

    private final ReminderService reminderService;

    private ReminderController() {
        try {
            var registry = LocateRegistry.getRegistry("localhost", 4545);

            reminderService = (ReminderService) registry.lookup("reminderService");
        } catch (RemoteException | NotBoundException e) {
            e.printStackTrace();

            throw new RuntimeException(e);
        }
    }

    public Collection<ReminderDto> getUnseenReminders(AccountDto accountDto) throws RemoteException {
        return reminderService.getNotViewedReminders(accountDto.getId());
    }

    private static final class SingletonHolder {
        public static final ReminderController INSTANCE = new ReminderController();
    }

    public static ReminderController getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public void persist(ReminderDto reminderDto) {
        try {
            reminderService.persist(reminderDto);
        } catch (RemoteException e) {
            e.printStackTrace();

            throw new RuntimeException(e);
        }
    }

    public void update(ReminderDto reminderDto) {
        try {
            reminderService.update(reminderDto);
        } catch (RemoteException e) {
            e.printStackTrace();

            throw new RuntimeException(e);
        }
    }

    public Collection<ReminderDto> findByEvenimentId(int evenimentId) {
        try {
            return reminderService.findByEventId(evenimentId);
        } catch (RemoteException e) {
            e.printStackTrace();

            throw new RuntimeException(e);
        }
    }

    public void deleteById(int reminderId) {
        try {
            reminderService.deleteById(reminderId);
        } catch (RemoteException e) {
            e.printStackTrace();

            throw new RuntimeException(e);
        }
    }
}
