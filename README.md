# Foro Hub API 💻

![Captura de pantalla 2024-07-16 214034](https://github.com/user-attachments/assets/e24a5f1d-8d0e-4282-af5e-5e3a957fa62f)

API Rest para el desafío final Foro Hub de Alura.

## Descripción 📃

Esta API proporciona servicios para gestionar usuarios, cursos, respuestas y tópicos en la plataforma Foro Hub. Permite registrar usuarios, crear y gestionar cursos, responder a tópicos y más.

## Funcionalidades Principales 📑📎

- **Usuarios:**
  - Crear, actualizar y obtener detalles de usuarios.
  - Autenticación con JWT para obtener un token de acceso.
  
- **Cursos:**
  - Crear, actualizar y obtener detalles de cursos.
  - Listar cursos paginados.

- **Respuestas:**
  - Crear, actualizar y obtener detalles de respuestas a tópicos.
  - Listar respuestas paginadas.

- **Tópicos:**
  - Crear, actualizar, obtener detalles y eliminar tópicos.
  - Listar tópicos paginados.

## Tecnologías Utilizadas 📟

- Java 17
- Spring Boot
- Spring Data JPA
- Spring Security
- Spring Validation
- Spring Web
- Flyway para migraciones de base de datos
- DevTools para desarrollo
- MySQL Connector/J
- Lombok para generación automática de código
- Spring Boot Test y Spring Security Test para pruebas
- Auth0 Java JWT para manejo de tokens JWT
- Springdoc OpenAPI para documentación de API con Swagger UI

## Requisitos Previos

Antes de empezar, asegúrate de tener instalados los siguientes requisitos:

- Java JDK 17
- Maven
- MySQL (u otro sistema de gestión de bases de datos compatible)

## Configuración y Uso

1. **Clonar el repositorio:**

   ```bash
   git clone https://github.com/tu_usuario/foro-hub-api.git
   cd foro-hub-api
   ```

   
2. Configuración de la Base de Datos:

    - Crea una base de datos MySQL llamada foro_hub.
    - Ajusta las configuraciones de base de datos en application.properties según sea necesario.
      
2. Ejecuta la aplicacion en tu IDE
   
4. Documentación de la API:

    Accede a Swagger UI para explorar y probar los endpoints de la API:
  
    ```bash
    https://localhost:8080/swagger-ui.html
    ```
### Autor
Alejo Beliz

- LinkedIn: [Alejo Beliz](https://linkedin.com/in/alejo-beliz)
- GitHub: [albelizGH](https://github.com/albelizGH)

