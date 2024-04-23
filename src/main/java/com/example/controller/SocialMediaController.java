package com.example.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.service.AccountService;
import com.example.service.MessageService;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@RestController
public class SocialMediaController {

    private AccountService accountService;
    private MessageService messageService;

    @Autowired
    public SocialMediaController(AccountService accountService,MessageService messageService)
    {
        this.accountService=accountService;
        this.messageService=messageService;
    }
    /* 
      An end point and its handler method to create new user account. 
      Use POST method and url http://localhost:8080/register  to test it on client tool like postman
    */
    @PostMapping("register")
    public  ResponseEntity<Account> createAccount(@RequestBody Account account)
    {
        Optional<Account> user=accountService.getUserByUserName(account.getUsername());
        
        if(user.isEmpty())
        {
          Account addedAccount=accountService.addAccount(account);
          if(addedAccount!=null)
               return ResponseEntity.status(200).body(addedAccount);
          else
              return ResponseEntity.status(409).body(addedAccount);
        }
        else
          return ResponseEntity.status(409).body(null); 


    }
    
     /* 
     An end point and its handler method to login. 
     Use POST method and  url http://localhost:8080/login to test it on client tool like postman
    */
    @PostMapping("login")
    public  ResponseEntity<Account> authenticateaccount(@RequestBody Account account)
    {
        Optional<Account> logedAcc=accountService.getUserByUsernameAndPassword(account.getUsername(),account.getPassword());
        if(logedAcc.isPresent())
        {
            return ResponseEntity.status(200).body(logedAcc.get());
        }
        else
           return ResponseEntity.status(401).body(null);
    }
    
     /* 
     An end point and its handler method to create new message. 
     Use POST method and url http://localhost:8080/messages to test it on client tool like postman
    */
    @PostMapping("messages")
    public ResponseEntity<Message> createMessage(@RequestBody Message message)
    {
        Message mesageCreated=messageService.addMessage(message);
        if(mesageCreated!=null)
        {
            return ResponseEntity.status(200).body(mesageCreated);

        }
        else 
           return ResponseEntity.status(400).body(null);

    }
     /* 
     An end point and its handler method to retrieve all messages. 
     Use GET method and url http://localhost:8080/messages to test it on client tool like postman
    */
    @GetMapping("messages")
    public ResponseEntity<List<Message>> getAllMessages()
    {
        List<Message> messaeList= messageService.getAllMessages();
        return ResponseEntity.status(200).body(messaeList);
    }

     /* 
     An end point and its handler method to retrieve a specific message. 
     Use GET method and url http://localhost:8080/messages/{messageId} to test it on client tool 
     like postman
    */
    @GetMapping("messages/{messageId}")
    public ResponseEntity<Message> getMessageByMessageId(@PathVariable Integer messageId)
    {
        Optional<Message> message=messageService.getMessageByMessageId(messageId);
        if(message.isPresent())
              return ResponseEntity.status(200).body(message.get());
        else
             return ResponseEntity.status(200).body(null);
                 
    }

     /* 
     An end point and its handler method to delete a specific messages. 
     Use DELETE method and url http://localhost:8080/messages/{messageId} to test it on client tool 
     like postman
    */
    @DeleteMapping("messages/{messageId}")
    public ResponseEntity<Integer> deleteMessageByMessageId(@PathVariable Integer messageId)
    {
        Integer rowsAffected=messageService.deleteMessageByMessageId(messageId);
        return ResponseEntity.status(200).body(rowsAffected);
       
    }

     /* 
     An end point and its handler method to update a specific text message . 
     Use PATCH method and url http://localhost:8080/messages/{messageId} to test it on client tool like postman
    */
    @PatchMapping("messages/{messageId}")
    public ResponseEntity<Integer> updateMessageByMessageId(@RequestBody Message message, @PathVariable Integer messageId)
    {
        Integer rowsAffected=messageService.updateMessageByMessageId(message,messageId);
        if(rowsAffected!=null)
        {
           
            return ResponseEntity.status(200).body(rowsAffected);
        }
           
        else
           return ResponseEntity.status(400).body(rowsAffected);

    }

     /* 
     An end point and its handler method to retrieve all messages for specific user account. 
     Use GET method and url http://localhost:8080/accounts/{accountId}/messages to test it on 
     client tool like postman
    */
    @GetMapping("accounts/{accountId}/messages")
    public ResponseEntity<List<Message>> getMessagesByUserId(@PathVariable Integer accountId) 
    {
        List<Message> messages= messageService.getMessagesByAccountId(accountId);
        return ResponseEntity.status(200).body(messages);
    }


}
