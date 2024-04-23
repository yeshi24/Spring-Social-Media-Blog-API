package com.example.repository;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import com.example.entity.Account;
@Repository
public interface AccountRepository extends JpaRepository<Account,Integer>{

    
/*
  This method is for fetching a specific user using the using the proprty expression 
  Username
*/
 public Optional<Account> findByUsername(String username);

 /*
  This method is for fetching a specific user using the using the proprty expression 
  Username and Password
*/
 public Optional<Account> findByUsernameAndPassword(String username, String password);
}
