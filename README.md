
# ForoHub

ForoHub es un Challenge para la formación en Java Spring ofrecido por ONE + Alura, centrado en el desarrollo del backend de un foro. Este sistema permite gestionar usuarios, cursos, tópicos y respuestas, con una estructura robusta de roles y permisos para garantizar la seguridad y funcionalidad.


## Tecnologías utilizadas

El proyecto utiliza las siguientes tecnologías y herramientas:

* Java 17
* Spring Boot (Framework principal)
* Spring Security (Gestión de autenticación y roles)
* Spring Data JPA (Interacción con bases de datos)
* PostgreSQL (Base de datos predeterminada)
* Swagger y Springdocs (Documentación de API)
* Auth0JWT (Autenticación basado tokens)
* Insomnia (Para realizar y probar requests HTTP, similar a Postman)

## Requisitos previos

Antes de comenzar, asegúrese de tener instalados los siguientes programas en tu sistema:

* Java 17
* PostgreSQL (Recomendado, pero puede utilizarse otros programas como MySQL)
* IntelliJ IDEA (Para ejecutar el proyecto)
## Configuración de la base de datos

La aplicación utiliza PostgreSQL de forma predeterminada. Asegúrese de tener creada una base de datos antes de ejecutar el proyecto.

Las credenciales de acceso pueden configurarse como variables de entorno en IntelliJ IDEA, o puede añadirlas directamente a `application.properties`.

### Credenciales como variables de Entorno

`application.properties` debe hacer referencia a las variables de entorno.

```
spring.datasource.url=jdbc:postgresql://${DB_HOST}/${DB_LNAME}
spring.datasource.username=${DB_USERNAME}
spring.datasource.password=${DB_PASSWORD}
api.security.secret = ${JWT_SECRET}
```

Para especificarlas dentro de IntelliJ sigue los pasos:
1. Acceda a la ventana de `Settings` (CTRL+ALT+S)
2. En la barra de busqueda escriba `Path Variables`
3. Añada las variables especificadas en `application.properties` junto con sus valores
4. Los valores son:
  * DB_HOST: Este es el host al que accede al programa (Usualmente, `localhost:5432`).
  * DB_LNAME: Aqui va el nombre de la base de datos (Como lo tenga registrado en su equipo).
  * DB_USERNAME: Aqui va el username para acceder a su gestor de base de datos.
  * DB_PASSWORD: Aqui va la contraseña para acceder a su gestor de base de datos.
  * JWT_SECRET: 12345 (Este es necesario para la autenticación)
5. Aplique los cambios

### Credenciales dentro de properties

`application.properties` puede almacenar directamente las credenciales.

Ejemplo (Remplace con los valores de su sistema y sus credenciales de base de datos):

```
spring.datasource.url=jdbc:postgresql://localhost:5432/forohub
spring.datasource.username=admin
spring.datasource.password=password
api.security.secret = 12345
```

### Tablas

No es necesario añadir manualmente tablas o registros a la base de datos, al momento de compilar el programa, el mismo creará las tablas y los registros necesarios dentro de la base de datos.

Existen 4 tablas que podemos modificar a travez de la API:
* Usuarios
* Cursos
* Topicos
* Respuestas

Todos tienen cierta relacion entre si, se recomienda crear Usuarios y Cursos al inicio para poder crear Topicos y Respuestas sin problemas.

## Instalación

1. Haga una clonacion del proyecto en su equipo.
2. Abra el proyecto dentro de IntelliJ
3. Configure las variables de entorno mencionadas en el punto anterior.
4. Ejecute el proyecto en IntelliJ desde la clase `ForohubApplication` ubicada en `src/main/java/com.ambystudio.forohub`
5. Espere a que el programa se ejecute en su totalidad.

## Pruebas

Una vez que el programa se ha compilado correctamente, ya es posible usar la API y todas sus funciones, se recomienda probar desde la pagina de  [Swagger](http://localhost:8080/swagger-ui/index.html) (Ingrese a este enlace solo cuando el programa se encuentre en ejecución, de lo contrario no funcionará).

Para poder utilizar la mayoria de funciones, debe estar autenticado, para eso debe usar el siguiente script dentro del controlador de `autenticacion-controller`.

```
{
  "login": "admin@forohub.com",
  "password": "admin12345"
}
```

Haz una copia del token generado y pegalo dentro del boton `Authorize` (Se encuentra al inicio de la pagina). Si el perfil cuenta con el rol de `MODERADOR`, podras usar todas las funciones de la API, de lo contrario, tendras acceso limitado debido a los permisos de `USUARIO`.
