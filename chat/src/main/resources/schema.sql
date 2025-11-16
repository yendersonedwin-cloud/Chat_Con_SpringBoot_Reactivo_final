-- Schema para aplicación de Chat Reactivo
-- Compatible con PostgreSQL y R2DBC

-- Tabla de usuarios del chat
CREATE TABLE IF NOT EXISTS chat_user (
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(100) NOT NULL UNIQUE
);

-- Tabla de mensajes
CREATE TABLE IF NOT EXISTS message (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL,
    content TEXT NOT NULL,
    timestamp TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_message_user FOREIGN KEY (user_id)
        REFERENCES chat_user(id) ON DELETE CASCADE
);

-- Índices para mejorar el rendimiento
CREATE INDEX IF NOT EXISTS idx_message_user_id ON message(user_id);
CREATE INDEX IF NOT EXISTS idx_message_timestamp ON message(timestamp);
CREATE INDEX IF NOT EXISTS idx_user_username ON chat_user(username);