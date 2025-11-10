package com.api.reactiva.chat.service;

import com.api.reactiva.chat.model.Message;
import com.api.reactiva.chat.model.User;
import com.api.reactiva.chat.repository.MessageRepository;
import com.api.reactiva.chat.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

@Service
public class ChatService {
    @Autowired
        private final MessageRepository messageRepository;
        private final UserRepository userRepository;

        // Sinks actúa como un emisor de eventos reactivos (Multicast para varios suscriptores)
        private final Sinks.Many<Message> chatSink = Sinks.many().multicast().onBackpressureBuffer();

        public ChatService(MessageRepository messageRepository, UserRepository userRepository) {
            this.messageRepository = messageRepository;
            this.userRepository = userRepository;
        }

        // Función de utilidad para adjuntar el nombre de usuario al mensaje
        private Mono<Message> attachUsername(Message message) {
            return userRepository.findById(message.getUserId())
                    .doOnNext(user -> message.setSenderUsername(user.getUsername()))
                    .thenReturn(message);
        }

        // 1. Guarda el mensaje en DB y lo emite al Sinks
        public Mono<Message> saveAndEmitMessage(Message message) {
            return messageRepository.save(message)
                    .flatMap(this::attachUsername)
                    .doOnNext(chatSink::tryEmitNext); // Emite el mensaje a todos los suscriptores
        }

        // 2. Obtiene el historial (inicial)
        public Flux<Message> getMessageHistory() {
            return messageRepository.findAll()
                    .flatMap(this::attachUsername)
                    .sort((m1, m2) -> m1.getTimestamp().compareTo(m2.getTimestamp()));
        }

        // 3. Permite a los clientes suscribirse al flujo de nuevos mensajes (Stream)
        public Flux<Message> getMessageStream() {
            return chatSink.asFlux();
        }

        // 4. Lógica de Usuario: Encuentra o Crea
        public Mono<User> findOrCreateUser(String username) {
            User newUser = new User(null, username);

            return userRepository.findByUsername(username) // Intenta encontrar
                    .switchIfEmpty(
                            // Si no existe, guarda el nuevo usuario.
                            // R2DBC generalmente devuelve el objeto completo después de save.
                            userRepository.save(newUser)
                    );
        }
    public Flux<User> getAllUsers() {
        return userRepository.findAll();
    }
    }


