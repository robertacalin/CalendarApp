package client.controller;

import lib.dto.AccountDto;
import lib.service.AccountService;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

public class AccountController {
    private final AccountService accountService;

    private AccountController() {
        try {
            var registry = LocateRegistry.getRegistry("localhost", 4545);
            accountService = (AccountService) registry.lookup("accountService");
        } catch (RemoteException | NotBoundException e) {
            e.printStackTrace();

            throw new RuntimeException(e);
        }
    }

    private static final class SingletonHolder {
        public static final AccountController INSTANCE = new AccountController();
    }

    public static AccountController getInstance() {
        return AccountController.SingletonHolder.INSTANCE;
    }

    public void persist(AccountDto accountDto) {
        try {
            accountService.persist(accountDto);
        } catch (RemoteException e) {
            e.printStackTrace();

            throw new RuntimeException(e);
        }
    }

    public AccountDto getContByUsername(String username) {
        try {
            return accountService.getAccountByUsername(username);
        } catch (RemoteException e) {
            e.printStackTrace();

            throw new RuntimeException(e);
        }
    }

    public boolean alreadyExists(String username) {
        try {
            return accountService.alreadyExists(username);
        } catch (RemoteException e) {
            e.printStackTrace();

            throw new RuntimeException(e);
        }
    }

    public boolean validateUser(String username, String password) {
        try {
            return accountService.validateUser(username, password);
        } catch (RemoteException e) {
            e.printStackTrace();

            throw new RuntimeException(e);
        }
    }

    public void deleteById(int contId) {
        try {
            accountService.deleteById(contId);
        } catch (RemoteException e) {
            e.printStackTrace();

            throw new RuntimeException(e);
        }
    }

    public int getUsernameIdByUsername(String username){
        try {
            return accountService.getUsernameIdByUsername(username);
        } catch (RemoteException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
