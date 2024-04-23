package com.example.service;
import com.example.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Account;
import java.util.Optional;

@Service
public class AccountService {
    private AccountRepository accountRepository;
   
    @Autowired
    AccountService(AccountRepository accountRepository)
    {
        this.accountRepository=accountRepository;
    }

    /*
     This method is for cretating a new user account. It checks the validity of the user name and password based 
     on the requirement before persisting the data and take an action accordingly 
    */
    public Account addAccount(Account account)
    {
        if(account.getUsername()!= null && account.getPassword()!=null && account.getPassword().length()>=4)
            return accountRepository.save(account);
        else
            return null;
    }

    /*This method is for retrieving a specific user account and return it.*/
    public Optional<Account> getUserByUserName(String username)
    {
        return accountRepository.findByUsername(username);
    }

    /*This method is for retrieving a specific user account by its user name and password and return it.*/
    public Optional<Account> getUserByUsernameAndPassword(String username, String password)
    {
        return accountRepository.findByUsernameAndPassword(username,password);
    }
}
