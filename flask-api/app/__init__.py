from flask import Flask
from app.config import Config
from app.extensions import db
from app.error_handlers import register_error_handlers
from dotenv import load_dotenv

def create_app():
    load_dotenv()
    app = Flask(__name__)
    app.config.from_object(Config)

    # Inicializar extensiones
    db.init_app(app)

    # Registrar manejadores de errores
    register_error_handlers(app)

    # Registrar rutas
    with app.app_context():
        from app.routes import bp
        app.register_blueprint(bp)

    return app
