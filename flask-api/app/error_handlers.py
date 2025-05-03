from flask import jsonify
from sqlalchemy.exc import SQLAlchemyError, OperationalError, InterfaceError, IntegrityError, ProgrammingError
from requests.exceptions import HTTPError

def register_error_handlers(app):
    
    # Base de datos
    @app.errorhandler(OperationalError)
    @app.errorhandler(InterfaceError)
    def handle_operational_error(error):
        return jsonify({"error": "Database unavailable", "message": str(error)}), 503

    @app.errorhandler(IntegrityError)
    def handle_integrity_error(error):
        return jsonify({"error": "Database integrity error", "message": str(error)}), 409

    @app.errorhandler(ProgrammingError)
    def handle_programming_error(error):
        return jsonify({"error": "Database query syntax error", "message": str(error)}), 400
    
    @app.errorhandler(SQLAlchemyError)
    def handle_database_error(error):
        return jsonify({"error": "Database error", "message": str(error)}), 500


    # APIs de terceros
    @app.errorhandler(HTTPError)
    def handle_http_error(error):
        # Para el error 429 Too Many Requests, se puede devolver un 503 Service Unavailable.
        if error.response.status_code == 429:
            return jsonify({"error": "External API error", "message": str(error)}), 503
        return jsonify({"error": "External API error", "message": str(error)}), 502


    # Archivos
    @app.errorhandler(FileNotFoundError)
    def handle_file_error(error):
        return jsonify({"error": "File not found", "message": str(error)}), 404
    
    # ---
    @app.errorhandler(Exception)
    def handle_generic_error(error):
        return jsonify({"error": "Unknown error", "message": str(error)}), 500
