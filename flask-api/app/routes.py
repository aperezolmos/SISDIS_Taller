from flask import Blueprint, jsonify, request
import requests
from app.extensions import db
from app.poke_api import PokeAPI
from app.models import User

from sqlalchemy.sql import text
from sqlalchemy import create_engine
from sqlalchemy.exc import SQLAlchemyError, OperationalError, InterfaceError, IntegrityError, ProgrammingError
from requests.exceptions import HTTPError


bp = Blueprint('routes', __name__)


# ------------------------------ DEBUG ------------------------------

@bp.route('/healthcheck', methods=['GET'])
def healthcheck():
    try:
        with db.engine.connect() as connection:
            connection.execute(text('SELECT 1'))
        return jsonify({"status": "ok", "database": "connected"}), 200
    
    except Exception as e:
        return jsonify({"status": "error", "message": str(e)}), 500

@bp.route('/validate-user', methods=['POST'])
def validate_user():
    data = request.get_json()
    username = data.get('username')
    password = data.get('password')

    user = User.query.filter_by(username=username, password=password).first()
    if user:
        return jsonify(True), 200
    else:
        return jsonify(False), 401


# ------------------------------ POKEAPI ------------------------------

@bp.route('/list-pokemon', methods=['GET'])
def list_pokemon():
    limit = int(request.args.get('limit', 20))
    offset = int(request.args.get('offset', 0))
    try:
        pokemons = PokeAPI.list_pokemon(limit=limit, offset=offset)
        # Añadir las imágenes de cada Pokémon
        for pokemon in pokemons:
            sprite_data = PokeAPI.get_pokemon_sprite(pokemon["name"])
            pokemon["sprite"] = sprite_data["sprite"]
        return jsonify(pokemons), 200
    except Exception as e:
        return jsonify({"error": str(e)}), 500




@bp.route('/search-pokemon', methods=['GET'])
def search_pokemon():
    name_query = request.args.get('name', '').lower()
    limit = int(request.args.get('limit', 20))
    offset = int(request.args.get('offset', 0))
    try:
        # Obtener todos los nombres de Pokémon (la pokeapi tiene 1300+)
        all_pokemons = PokeAPI.list_pokemon(limit=1300, offset=0)
        # Filtrar por nombre si hay búsqueda
        if name_query:
            filtered = [p for p in all_pokemons if name_query in p['name'].lower()]
        else:
            filtered = all_pokemons
        # Paginación manual
        paginated = filtered[offset:offset+limit]
        # Añadir sprite a cada uno
        for pokemon in paginated:
            sprite_data = PokeAPI.get_pokemon_sprite(pokemon["name"])
            pokemon["sprite"] = sprite_data["sprite"]

            external_id_data = PokeAPI.get_pokemon_external_id(pokemon["name"])
            pokemon["external_id"] = external_id_data["id"]
        
        return jsonify({
            "results": paginated,
            "total": len(filtered)
        }), 200
    except Exception as e:
        return jsonify({"error": str(e)}), 500

@bp.route('/pokemon-daily/<int:pokemon_id>', methods=['GET'])
def get_pokemon_daily(pokemon_id):
    try:
        # Info básica
        poke_data = PokeAPI.get_pokemon(pokemon_id)
        name = poke_data["name"]
        image = poke_data["sprites"]["other"]["official-artwork"]["front_default"] or poke_data["sprites"]["front_default"]
        external_id = poke_data["id"]
        # Flavor texts en español
        flavor_texts = PokeAPI.get_pokemon_flavor_texts(pokemon_id)
        return jsonify({
            "id": external_id,
            "name": name,
            "image": image,
            "flavor_texts": flavor_texts
        }), 200
    except Exception as e:
        return jsonify({"error": str(e)}), 404
    

# ------------------------------ BASE DE DATOS ------------------------------

@bp.route('/simulate/db-error', methods=['GET'])
def simulate_db_error():
    try:
        user = User.query.filter_by(username="nonexistent").one()
        return jsonify({"user": user.username}), 200
    except SQLAlchemyError as e:
        raise e
    
@bp.route('/simulate/db-connection-error', methods=['GET'])
def simulate_db_connection_error():
    try:
        # Conectar a un host que no existe
        fake_engine = create_engine("mysql+mysqlconnector://user:pass@nonexistent_host:3306/fake_db")
        with fake_engine.connect() as conn:
            conn.execute(text("SELECT 1"))
    except (OperationalError, InterfaceError) as e:
        raise e

@bp.route('/simulate/db-integrity-error', methods=['GET'])
def simulate_db_integrity_error():
    try:
        # Intenta insertar un registro duplicado en una tabla con clave primaria única
        with db.engine.connect() as connection:
            connection.execute(text("INSERT INTO users (id, username, password) VALUES (1, 'duplicate', 'password')"))
            connection.execute(text("INSERT INTO users (id, username, password) VALUES (1, 'duplicate', 'password')"))
    except IntegrityError as e:
        raise e
    
@bp.route('/simulate/db-syntax-error', methods=['GET'])
def simulate_db_syntax_error():
    try:
        # Ejecuta una consulta SQL mal formada
        with db.engine.connect() as connection:
            connection.execute(text('SELECT * FROM WHERE'))
    except ProgrammingError as e:
        raise e


# ------------------------------ APIS DE TERCEROS ------------------------------
@bp.route('/simulate/api-error', methods=['GET'])
def simulate_api_error():
    try:
        pokemon = PokeAPI.get_pokemon("nonexistent-pokemon")
        return jsonify(pokemon), 200
    except Exception as e:
        raise e

@bp.route('/simulate/api-auth-error', methods=['GET'])
def simulate_api_auth_error():
    try:
        # Llama a una API con credenciales incorrectas
        response = requests.get("https://httpbin.org/basic-auth/user/passwd", auth=("wrong_user", "wrong_pass"))
        response.raise_for_status()
        return jsonify(response.json())
    except HTTPError as e:
        raise e
    
@bp.route('/simulate/api-rate-limit-error', methods=['GET'])
def simulate_api_rate_limit_error():
    try:
        # Simula un error de rate limiting llamando repetidamente a una API
        for _ in range(100):
            response = requests.get("https://httpbin.org/status/429")
            response.raise_for_status()
        return jsonify({"message": "No rate limit error occurred"})
    except HTTPError as e:
        raise e


# ------------------------------ ARCHIVOS ------------------------------
@bp.route('/simulate/file-error', methods=['GET'])
def simulate_file_error():
    try:
        with open("nonexistent_file.txt", "r") as file:
            data = file.read()
        return jsonify({"data": data}), 200
    except FileNotFoundError as e:
        raise e
