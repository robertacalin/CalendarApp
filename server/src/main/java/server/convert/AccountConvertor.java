package server.convert;

import lib.dto.AccountDto;
import server.model.Account;

import java.util.Collections;

public class AccountConvertor {

    public AccountConvertor() {
    }

    public static Account convert(AccountDto accountDto) {
        var cont = new Account();

        cont.setId(accountDto.getId());
        cont.setUsername(accountDto.getUsername());
        cont.setPassword(accountDto.getPassword());

        return cont;
    }

    public static AccountDto convert(Account account) {
        AccountDto accountDto = new AccountDto(
                account.getUsername(),
                account.getPassword()
        );

        accountDto.setId(account.getId());
        accountDto.setEventsId(Collections.emptySet());

        return accountDto;
    }
}
