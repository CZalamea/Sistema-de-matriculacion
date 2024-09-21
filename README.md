🌐 WebApp
Este es el repositorio para el proyecto WebApp, una aplicación sencilla para la gestión de usuarios.

📄 Descripción
WebApp es una aplicación web diseñada para gestionar usuarios, pensada como una pequeña práctica de Java y una introducción a Thymeleaf. El proyecto utiliza Java, JPA (Java Persistence API), MySQL, y Thymeleaf como motor de plantillas para la interfaz.

🛠️ Instalación
Sigue estos pasos para clonar y ejecutar este proyecto localmente:

Clona el repositorio:

bash
Copiar código
git clone https://github.com/CZalamea/WebApp.git
Navega al directorio del proyecto:

bash
Copiar código
cd WebApp
Configura el proyecto en tu IDE (IntelliJ, Eclipse, etc.), asegurándote de que tienes configurado Java 17 o superior y Maven para gestionar las dependencias.

Configura la base de datos en el archivo application.properties:

properties
Copiar código
spring.datasource.url=jdbc:mysql://localhost:3306/webapp
spring.datasource.username=tu_usuario
spring.datasource.password=tu_contraseña
spring.jpa.hibernate.ddl-auto=update
Inicia la aplicación:

bash
Copiar código
mvn spring-boot:run
La aplicación estará disponible en http://localhost:8080.

🚀 Uso
Puedes utilizar esta aplicación para explorar funcionalidades básicas de Spring Boot, Thymeleaf y la interacción con bases de datos mediante JPA. Es ideal para añadir más funcionalidades y practicar con Spring Framework.

📚 Funcionalidades Incluidas
Crear usuarios: Agrega nuevos usuarios a la base de datos.
Listar usuarios: Muestra todos los usuarios registrados.
Editar usuarios: Modifica la información de un usuario existente.
Eliminar usuarios: Elimina usuarios de la base de datos.
Web Interface
Accede a las siguientes rutas para interactuar con la aplicación:

Listar todos los usuarios: /users
Crear nuevo usuario: /users/new
Editar usuario existente: /users/edit/{id}
Eliminar usuario: /users/delete/{id}
🤝 Contribución
Si deseas contribuir a este proyecto, sigue estos pasos:

Haz un fork del repositorio.
Crea una nueva rama para tu funcionalidad:
bash
Copiar código
git checkout -b feature/nueva-funcionalidad
Realiza tus cambios y crea un commit:
bash
Copiar código
git commit -m "Añadir nueva funcionalidad"
Sube tus cambios:
bash
Copiar código
git push origin feature/nueva-funcionalidad
Abre un Pull Request en GitHub.
📬 Contacto
Si tienes alguna pregunta, sugerencia o consulta, no dudes en contactarme a través de mi perfil de GitHub o mediante correo electrónico.



