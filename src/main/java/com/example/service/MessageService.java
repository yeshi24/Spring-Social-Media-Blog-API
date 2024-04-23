package com.example.service;

import java.util.Optional;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Message;
import com.example.repository.MessageRepository;
import com.example.repository.AccountRepository;
import com.example.entity.Account;


@Service
public class MessageService {

    private MessageRepository messageRepository;
    private AccountRepository accountRepository;

    @Autowired
    public MessageService(AccountRepository accountRepository,MessageRepository messageRepository)
    {
        this.accountRepository=accountRepository;
        this.messageRepository= messageRepository;
    }
    /*This method is for cretating new message. It checks the validity of the text message based 
     on the requirement before persisting the data and take an action accordingly 
    */
    public Message addMessage(Message message)
    {
        Optional<Account> account=accountRepository.findById(message.getPostedBy());
        if(message.getMessageText()!=null && !message.getMessageText().isEmpty() && message.getMessageText().length()<=255)
        {
            if(account.isPresent())
            {
                  return messageRepository.save(message);
            }
    
        }
        return null;
       
    }
    /*This method is for retrieving all mesages and return list of messages.*/
    public List<Message> getAllMessages()
    {
       return messageRepository.findAll();
    }
    /*This method is for retrieving a specific mesage and return it.*/
    public Optional<Message> getMessageByMessageId(Integer messageId)
    {
        return messageRepository.findById(messageId);
    }
    /*
      This method is for deleting a specific mesage and returning rows affected. 
      before trying to delete, it checks the existance of the message.
    */
    public Integer deleteMessageByMessageId(Integer messageId)
    {
        Integer rowsAffected=null;
        if(messageRepository.existsById(messageId))
        {
            messageRepository.deleteById(messageId);
            rowsAffected = 1;
        }
        return rowsAffected;
       
    }
    /*This method is for updateing specific text message. Before trying to update, it checks the 
      validity of the new text message and take an action accordingly.
    */
    public Integer updateMessageByMessageId(Message message,Integer messageId)
    {
        Integer rowsAffected=null;
        if(message.getMessageText()!=null && !message.getMessageText().isEmpty() && message.getMessageText().length()<=255)
        {
            Optional<Message> messageOpt=messageRepository.findById(messageId);
       
            if(messageOpt.isPresent())
            {
                Message messageToBeUpdated=messageOpt.get();
                messageToBeUpdated.setMessageId(messageId);
                messageToBeUpdated.setMessageText(message.getMessageText());
                messageRepository.save(messageToBeUpdated);
                rowsAffected = 1;
            }
        }
       
        return rowsAffected;
    }
    /*This method is for retrieving all mesages for specific user account and return it.*/
    public List<Message> getMessagesByAccountId(Integer accountId)
    {
        return messageRepository.findByPostedBy(accountId);
    }

}
