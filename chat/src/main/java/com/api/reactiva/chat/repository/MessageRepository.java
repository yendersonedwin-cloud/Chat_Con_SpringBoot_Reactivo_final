package com.api.reactiva.chat.repository;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import com.api.reactiva.chat.model.Message;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends ReactiveCrudRepository<Message, Long>{}
