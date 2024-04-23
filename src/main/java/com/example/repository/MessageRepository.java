package com.example.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.entity.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message,Integer>{

/*
  This method is for retrieving all mesages for specific user account using the proprty expression 
  PostedBy
*/
public List<Message> findByPostedBy(Integer postedBy);    
}
