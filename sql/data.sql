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

INSERT INTO pokemons (name, external_id, image_url) VALUES ('pikachu', 25, 'https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/25.png');
INSERT INTO pokemons (name, external_id, image_url) VALUES ('aurorus', 699, 'https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/699.png');
INSERT INTO pokemons (name, external_id, image_url) VALUES ('jirachi', 385, 'https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/385.png');
INSERT INTO pokemons (name, external_id, image_url) VALUES ('piplup', 393, 'https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/393.png');
INSERT INTO pokemons (name, external_id, image_url) VALUES ('eevee', 133, 'https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/133.png');
INSERT INTO pokemons (name, external_id, image_url) VALUES ('comfey', 764, 'https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/764.png');
INSERT INTO pokemons (name, external_id, image_url) VALUES ('sylveon', 700, 'https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/700.png');
INSERT INTO pokemons (name, external_id, image_url) VALUES ('hatterene', 858, 'https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/858.png');
INSERT INTO pokemons (name, external_id, image_url) VALUES ('mewtwo', 150, 'https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/150.png');
INSERT INTO pokemons (name, external_id, image_url) VALUES ('dewgong', 87, 'https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/87.png');
INSERT INTO pokemons (name, external_id, image_url) VALUES ('snorlax-gmax', 10206, 'https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/10206.png');
INSERT INTO pokemons (name, external_id, image_url) VALUES ('terapagos-terastal', 10276, 'https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/10276.png');
INSERT INTO pokemons (name, external_id, image_url) VALUES ('gardevoir', 282, 'https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/282.png');
