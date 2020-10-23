package server.dao;

import server.model.Account;

public interface AccountDao {

    void persist(Account account);

    boolean alreadyExists(String username);

    boolean validateUser(String username, String password);

    void deleteById(int accountId);

    Account getAccountById(int accountId);

    int getUsernameIdByUsername(String username);

    Account getAccountByUsername(String username);
}
