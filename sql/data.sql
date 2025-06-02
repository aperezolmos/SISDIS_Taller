-- Crear tabla de Usuarios
CREATE TABLE IF NOT EXISTS users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(20) NOT NULL DEFAULT 'standard'
);

-- Crear tabla de Pokémons
CREATE TABLE IF NOT EXISTS pokemons (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    external_id INT,
    image_url VARCHAR(255)
);

-- Crear tabla de Pokémons favoritos
CREATE TABLE IF NOT EXISTS favorite_pokemons (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    pokemon_id BIGINT NOT NULL,
    date_added DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (pokemon_id) REFERENCES pokemons(id) ON DELETE CASCADE
);

-- Insertar datos iniciales en la tabla de Pokémons
INSERT INTO pokemons (name, external_id, image_url) VALUES ('Pikachu', 25, 'https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/25.png');
INSERT INTO pokemons (name, external_id, image_url) VALUES ('Charmander', 4, 'https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/4.png');

-- Insertar la relación en la tabla de Pokémons favoritos
SET @user_id = (SELECT id FROM users WHERE username = 'amanda');
SET @pokemon_id = (SELECT id FROM pokemons WHERE name = 'Charmander');
INSERT INTO favorite_pokemons (user_id, pokemon_id) VALUES (@user_id, @pokemon_id);