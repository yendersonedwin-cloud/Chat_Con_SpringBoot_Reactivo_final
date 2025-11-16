package com.api.reactiva.chat.repository;
import com.api.reactiva.chat.model.User;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;
@Repository
public interface UserRepository extends ReactiveCrudRepository<User, Long>{
    Mono<User> findByUsername(String username);
}

