import requests

class PokeAPI:
    BASE_URL = "https://pokeapi.co/api/v2"

    @staticmethod
    def get_pokemon(pokemon_name):
        try:
            response = requests.get(f"{PokeAPI.BASE_URL}/pokemon/{pokemon_name}")
            response.raise_for_status()
            return response.json()
        except requests.exceptions.RequestException as e:
            raise e
        
    @staticmethod
    def list_pokemon(limit=20, offset=0):
        url = f"{PokeAPI.BASE_URL}/pokemon?limit={limit}&offset={offset}"
        response = requests.get(url)
        response.raise_for_status()
        results = response.json()["results"]
        pokemons = []
        for p in results:
            # Extraer ID de la URL
            url_parts = p["url"].rstrip('/').split('/')
            poke_id = url_parts[-1]
            sprite_url = f"https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/{poke_id}.png"
            pokemons.append({
                "name": p["name"],
                "id": int(poke_id),
                "sprite": sprite_url
            })
        return pokemons
        
    @staticmethod
    def get_pokemon_sprite(pokemon_name): # Imagen del Pokémon
        data = PokeAPI.get_pokemon(pokemon_name)
        return {
            "name": data["name"],
            "sprite": data["sprites"]["front_default"]
        }
    
    @staticmethod
    def get_pokemon_types(pokemon_name):
        data = PokeAPI.get_pokemon(pokemon_name)
        types = [t["type"]["name"] for t in data["types"]]
        return {
            "name": data["name"],
            "types": types
        }
    
    @staticmethod
    def get_pokemon_external_id(pokemon_name):
        data = PokeAPI.get_pokemon(pokemon_name)
        return {
            "name": data["name"],
            "id": data["id"]
        }
    
    @staticmethod
    def get_pokemon_flavor_texts(pokemon_id):
        url = f"{PokeAPI.BASE_URL}/pokemon-species/{pokemon_id}"
        response = requests.get(url)
        response.raise_for_status()
        data = response.json()
        # Filtrar flavor texts en español y eliminar duplicados
        texts = []
        seen = set()
        for entry in data["flavor_text_entries"]:
            if entry["language"]["name"] == "es":
                text = entry["flavor_text"].replace('\n', ' ').replace('\f', ' ').strip()
                if text not in seen:
                    texts.append(text)
                    seen.add(text)
        return texts
