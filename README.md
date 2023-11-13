# Ignacio María Barilatti

- *ninibarilatti@gmail.com*
- [GitHub](https://github.com/peltren)

---

## GlobalLogic-users

Microservicio que ofrece una API REST para registrar y obtener información sobre los usuarios.
Las tecnologías utilizadas son:
- Java 1.8
- Spring Boot
- Gradle
- Base de datos relacional (H2)



### Instalación

1. Clonar el repositorio

```
git clone https://github.com/peltren/globalogic-users.git
```

2. Compilar el proyecto
```
cd globalogic-users
./gradlew clean build
``` 

### Tests

Comando para ejecutar todos los tests:

```shell 
./gradlew test
```

### Uso

1) Correr el proyecto:
```shell 
./gradlew bootRun
```

2) La API provee los siguientes endpoints:

- POST /api/sign-up
  : Permite el registro de un nuevo usuario

- GET /api/login
  : Endpoint securizado que renueva el token de acceso y devuelve la información del usuario


### Documentación

Una vez que el proyecto esta corriendo en el puerto 8080, se puede acceder a la documentación en la siguiente [url](http://localhost:8080/api/swagger-ui/index.html)

```
http://localhost:8080/api/swagger-ui/index.html
``` 

### Arquitectura de software

Se desarrollo el proyecto implementando una [Arquitectura Hexagonal](https://medium.com/@edusalguero/arquitectura-hexagonal-59834bb44b7f), también llamada "de puertos y adaptadores" 

