package com.api.reactiva.chat.repository;
import com.api.reactiva.chat.model.User;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface UserRepository extends ReactiveCrudRepository<User, Long>{
    Mono<User> findByUsername(String username);
}

