# PokeManager Application v2.0

Amanda Pérez Olmos [apo1004@alu.ubu.es]

<br>

## 📋 Descripción del Proyecto
Esta práctica consiste en el diseño y desarrollo de un sistema distribuido compuesto por una interfaz web construida con **Spring Boot y Thymeleaf** y una API desarrollada en **Flask**.

El sistema permite a los usuarios **registrarse, iniciar sesión, editar su perfil, buscar Pokémon a través de la PokeAPI, añadir Pokémon a su lista de favoritos y gestionar sus favoritos**. Además, los administradores pueden **gestionar usuarios** (editar y eliminar usuarios) y acceder a una sección para **simular distintos tipos de errores** comunes en aplicaciones reales (fallos en bases de datos, errores en archivos o en llamadas a APIs externas), con el fin de analizar su manejo, propagación y presentación al usuario. El enfoque principal es la **comunicación entre el Frontend y la API Flask**, así como la gestión de usuarios y favoritos.

<br>

## 💻 Páginas del Sistema

1. **Home**:
   - Página inicial de la aplicación.
   - Permite navegar al formulario de login.

2. **Registro (Signup)**:
   - Permite crear una nueva cuenta de usuario.
   - Valida la unicidad del nombre de usuario y la coincidencia de contraseñas.

3. **Login**:  
   - Página para iniciar sesión.
   - Credenciales de prueba:  
     **Usuario:** `amanda`  
     **Contraseña:** `perez123`  

4. **Main**:
   - Página principal tras iniciar sesión.
   - Muestra el perfil del usuario y su lista de Pokémon favoritos.
   - Permite eliminar Pokémon de favoritos.
   - Muestra el "Pokémon diario" (obtenido desde la API Flask) y permite añadirlo a favoritos.
   - Acceso a la búsqueda de Pokémon y a la gestión de usuarios (si es admin).

5. **Buscar Pokémon**:
   - Permite buscar Pokémon por nombre utilizando la PokeAPI (a través de la API Flask).
   - Permite añadir Pokémon a la lista de favoritos del usuario.
   - Incluye paginación y mantiene los filtros de búsqueda.

6. **Editar Perfil**:
   - Permite editar el nombre de usuario y la contraseña.
   - Los administradores pueden cambiar el rol de cualquier usuario.
   - Si el usuario edita su propio perfil, debe volver a iniciar sesión.

7. **Gestión de Usuarios** (solo admin):
   - Permite a los administradores ver la lista de usuarios, editar cualquier usuario o eliminar usuarios (excepto a sí mismos).
   - Acceso desde el menú de navegación si el usuario tiene rol de admin.

8. **Simulate** (solo admin):
   - Permite simular diferentes tipos de errores, como:
     - Errores de base de datos (_conexión, integridad, sintaxis_).
     - Errores en llamadas a APIs de terceros (_autenticación, límite de solicitudes_).
     - Errores de archivos (_archivo no encontrado_).
   - Simular un fallo redirige a una página de error que permite volver al Home.

<br>

‼️ En todas las páginas, el usuario puede **cerrar sesión** en cualquier momento desde el menú desplegable en la esquina superior derecha, que muestra el nombre de usuario y una imagen de perfil.

<br>

## ⚙️ Ejecución del Proyecto

### Requisitos Previos
- Tener Docker y Docker Compose instalados.
- Tener un entorno de desarrollo Java configurado (JDK 17 o superior).

### Pasos para Ejecutar

1. **Iniciar los contenedores de MySQL y Flask API**:
   ```bash
   docker-compose down -v
   docker-compose build
   docker-compose up -d

2. Abrir el archivo `PokeManagerApplication.java` (carpeta _src/main/java/com/sisdis/pokemanager_) en el IDE y ejecutarlo.

3. Acceder a la aplicación:
   - Navegar a: [http://localhost:8080/home](http://localhost:8080/home).

4. Iniciar sesión:
   - **Usuario:** `amanda`
   - **Contraseña:** `perez123`
