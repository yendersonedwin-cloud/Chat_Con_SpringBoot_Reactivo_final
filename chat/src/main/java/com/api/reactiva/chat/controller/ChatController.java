package com.api.reactiva.chat.controller;
import com.api.reactiva.chat.model.Message;
import com.api.reactiva.chat.model.User;
import com.api.reactiva.chat.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
@RestController
@RequestMapping("/api/v1/chat")
// Habilitar CORS para permitir la conexión desde el frontend (puerto diferente)
@CrossOrigin(origins = "*")
public class ChatController {
        @Autowired
        private ChatService chatService;

    @GetMapping("/user")
    public Flux<User> getUsers() {
        return chatService.getAllUsers();
    }


        // 1. Endpoint para registrar/conectar un usuario
        @PostMapping("/user")
        public Mono<User> connectUser(@RequestBody User user) {
            return chatService.findOrCreateUser(user.getUsername());
        }


        // 2. Endpoint para enviar un mensaje
        @PostMapping("/message")
        public Mono<Message> sendMessage(@RequestBody Message message) {
            // El servicio guarda el mensaje y lo emite al stream
            return chatService.saveAndEmitMessage(message);
        }

        // 3. Endpoint para obtener el historial inicial
        @GetMapping("/history")
        public Flux<Message> getMessageHistory() {
            return chatService.getMessageHistory();
        }

        // 4. Endpoint de Streaming en Tiempo Real (SSE)
        @GetMapping(value = "/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
        public Flux<Message> streamMessages() {
            // Se suscribe al Flux generado por Sinks, enviando mensajes tan pronto como llegan
            return chatService.getMessageStream();
        }
    }

