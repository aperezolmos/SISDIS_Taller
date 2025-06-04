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
INSERT INTO pokemons (name, external_id, image_url) VALUES ('pikachu', 25, 'https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/25.png');
INSERT INTO pokemons (name, external_id, image_url) VALUES ('charmander', 4, 'https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/4.png');