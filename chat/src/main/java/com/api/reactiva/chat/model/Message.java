package com.api.reactiva.chat.model;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;  // ⬅️ AGREGAR ESTO
import org.springframework.data.relational.core.mapping.Table;
import lombok.Data;
import java.time.LocalDateTime;

@Table("message")
@Data
public class Message {

        @Id
        private Long id;
        private Long userId;
        private String content;
        private LocalDateTime timestamp = LocalDateTime.now();

        @Transient  // ⬅️ AGREGAR ESTO
        private String senderUsername;
}