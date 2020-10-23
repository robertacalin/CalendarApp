package service;

import lib.dto.AccountDto;
import lib.service.AccountService;
import server.convert.AccountConvertor;
import server.dao.AccountDao;
import server.dao.EventDao;
import server.dao.impl.AccountDaoImpl;
import server.dao.impl.EventDaoImpl;

import javax.persistence.Persistence;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.stream.Collectors;

public class AccountServiceImpl extends UnicastRemoteObject implements AccountService {

    private final AccountDao accountDao;

    private final EventDao eventDao;

    public AccountServiceImpl() throws RemoteException {
        var emf = Persistence.createEntityManagerFactory("java2examenPU");

        accountDao = new AccountDaoImpl(emf.createEntityManager());
        eventDao = new EventDaoImpl(emf.createEntityManager());
    }


    @Override
    public void persist(AccountDto accountDto) throws RemoteException {
        var account = AccountConvertor.convert(accountDto);

        var events = accountDto.getEventsId().stream()
                .map(eventDao::getById)
                .collect(Collectors.toSet());

        accountDao.persist(account);
    }

    @Override
    public boolean alreadyExists(String username) throws RemoteException {
        return accountDao.alreadyExists(username);
    }

    @Override
    public boolean validateUser(String username, String password) throws RemoteException {
        return accountDao.validateUser(username, password);
    }

    @Override
    public void deleteById(int accountId) throws RemoteException {
        accountDao.deleteById(accountId);
    }

    @Override
    public int getUsernameIdByUsername(String username) throws RemoteException {
        return accountDao.getUsernameIdByUsername(username);
    }

    @Override
    public AccountDto getAccountByUsername(String username) throws RemoteException {
        var account = accountDao.getAccountByUsername(username);

        return AccountConvertor.convert(account);
    }


}
