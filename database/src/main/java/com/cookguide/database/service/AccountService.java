package com.cookguide.database.service;

import com.cookguide.database.model.Account;
import com.cookguide.database.repository.AccountRepository;

import java.util.List;

public interface AccountService {
    public abstract Account createAccount(Account account);

    public abstract Account updateAccount(Account account);
    public abstract Account getAccountById(Long id);
    public abstract List<Account> getAllAccounts();
    public abstract void deleteAccount(Long id);
    public abstract boolean isAccountExist(Long id);
}
