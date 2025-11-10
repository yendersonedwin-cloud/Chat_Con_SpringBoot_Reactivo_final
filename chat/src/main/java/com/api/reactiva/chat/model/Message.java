package com.api.reactiva.chat.model;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import lombok.Data;
import java.time.LocalDateTime;

@Table("message")
@Data
public class Message {

        @Id
        private Long id;
        private Long userId; // Clave foránea al usuario
        private String content;
        private LocalDateTime timestamp = LocalDateTime.now();

        // Campo transitorio: No se mapea a DB, se usa para enviar al frontend
        private String senderUsername;
}
