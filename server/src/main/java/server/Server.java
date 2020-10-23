package server;

import service.AccountServiceImpl;
import service.EventServiceImpl;
import service.ReminderServiceImpl;


import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

public class Server {
    public static void main(String[] args) throws RemoteException {
        var registry = LocateRegistry.createRegistry(4545);

        registry.rebind("accountService", new AccountServiceImpl());
        registry.rebind("eventService", new EventServiceImpl());
        registry.rebind("reminderService", new ReminderServiceImpl());

        System.out.println("Server started!");
    }
}
