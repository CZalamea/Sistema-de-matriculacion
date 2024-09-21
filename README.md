# 🌐 WebApp API

¡Bienvenido a la API de WebApp! 🚀 Esta API te permite gestionar usuarios y autenticación de manera eficiente. 🔒

## 📋 Tabla de Contenidos

- Instalación
- Uso
- Endpoints
- Contribuir
- Licencia

## 🛠️ Instalación

Para instalar y ejecutar esta API localmente, sigue estos pasos:

1. Clona el repositorio:
   ```bash
   git clone https://github.com/CZalamea/WebApp.git

Navega al directorio del proyecto:
cd WebApp

Instala las dependencias:
npm install

Inicia el servidor:
npm start

🚀 Uso
Para usar la API, envía solicitudes HTTP a los endpoints disponibles. Aquí hay algunos ejemplos:

Obtener todos los usuarios
GET /api/users

Crear un nuevo usuario
POST /api/users
Content-Type: application/json

{
  "name": "Juan Pérez",
  "email": "juan.perez@example.com",
  "password": "supersecreto"
}

📚 Endpoints
GET /api/users - Obtiene todos los usuarios.
POST /api/users - Crea un nuevo usuario.
POST /api/auth/login - Inicia sesión con un usuario existente.
POST /api/auth/register - Registra un nuevo usuario.
🤝 Contribuir
¡Las contribuciones son bienvenidas! Para contribuir, sigue estos pasos:

Haz un fork del proyecto.
Crea una nueva rama (git checkout -b feature/nueva-funcionalidad).
Realiza tus cambios y haz commit (git commit -am 'Añadir nueva funcionalidad').
Envía un pull request.
📄 Licencia
Este proyecto está bajo la Licencia MIT. Consulta el archivo LICENSE para más detalles.

¡Gracias por usar WebApp API! Si tienes alguna pregunta, no dudes en abrir un issue. 😊
