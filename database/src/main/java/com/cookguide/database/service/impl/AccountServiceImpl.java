package com.cookguide.database.service.impl;

import com.cookguide.database.model.Account;
import com.cookguide.database.repository.AccountRepository;
import com.cookguide.database.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public Account createAccount(Account account){
        return accountRepository.save(account);
    }
    @Override
    public  Account updateAccount(Account account){
        return accountRepository.save(account);
    }
    @Override
    public  Account getAccountById(Long id){
        return accountRepository.findById(id).get();
    }
    @Override
    public List<Account> getAllAccounts(){
        return (List<Account>) accountRepository.findAll();
    }
    @Override
    public  void deleteAccount(Long id){
        accountRepository.deleteById(id);
    }
    @Override
    public  boolean isAccountExist(Long id){
        return accountRepository.existsById(id);
    }

}