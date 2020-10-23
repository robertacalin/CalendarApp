package lib.service;

import lib.dto.AccountDto;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface AccountService extends Remote {

    void persist(AccountDto accountDto) throws RemoteException;

    boolean alreadyExists(String username) throws RemoteException;

    boolean validateUser(String username, String password) throws RemoteException;

    void deleteById(int contId) throws RemoteException;

    int getUsernameIdByUsername(String username) throws RemoteException;

    AccountDto getAccountByUsername(String username) throws RemoteException;
}
