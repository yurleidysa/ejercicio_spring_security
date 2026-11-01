-- Base de datos del ejercicio de Spring Security
CREATE DATABASE IF NOT EXISTS securitydb;
USE securitydb;

CREATE TABLE users (
  user_id INT NOT NULL AUTO_INCREMENT,
  username VARCHAR(45) NOT NULL,
  password VARCHAR(64) NOT NULL,
  enabled TINYINT NOT NULL DEFAULT 1,
  PRIMARY KEY (user_id)
);

CREATE TABLE roles (
  role_id INT NOT NULL AUTO_INCREMENT,
  name VARCHAR(45) NOT NULL,
  PRIMARY KEY (role_id)
);

CREATE TABLE users_roles (
  user_id INT NOT NULL,
  role_id INT NOT NULL,
  FOREIGN KEY (user_id) REFERENCES users(user_id),
  FOREIGN KEY (role_id) REFERENCES roles(role_id)
);

INSERT INTO roles (name) VALUES ('USER'), ('CREATOR'), ('EDITOR'), ('ADMIN');

-- Contraseñas encriptadas con BCrypt
-- user / user123, creator / creator123, editor / editor123, admin / admin123
INSERT INTO users (username, password, enabled) VALUES
('user',    '$2a$10$zuPiX.ARyZE78A0CRigFL.myCW.77C6Q/HklcyvPTklJZTUvKOIlO', 1),
('creator', '$2a$10$VCX/SDD/XZMQapbBsPI5YOUharYrvyjigjOZs6BEmbdSdsAaq/LLK', 1),
('editor',  '$2a$10$iwS1STYsc5jO/axCNEUxK.dOrp1G8tEX.E5L/3MUKbJW79Ul4CpN.', 1),
('admin',   '$2a$10$Nvc2HGIw2qGKIf9hnsEaH.aG9HeHaMppXPaGsYAZButLlbdpmLF9O', 1);

INSERT INTO users_roles (user_id, role_id)
SELECT u.user_id, r.role_id
FROM users u
JOIN roles r ON
  (u.username = 'user'    AND r.name = 'USER') OR
  (u.username = 'creator' AND r.name = 'CREATOR') OR
  (u.username = 'editor'  AND r.name = 'EDITOR') OR
  (u.username = 'admin'   AND r.name = 'ADMIN');