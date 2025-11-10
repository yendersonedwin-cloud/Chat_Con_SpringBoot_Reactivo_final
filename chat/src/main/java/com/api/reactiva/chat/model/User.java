package com.api.reactiva.chat.model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
@Table("chat_user")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
        @Id
        private Long id;
        private String username;
}
