# API Inmobiliaria

API REST desarrollada con Spring Boot para la gestión de una plataforma inmobiliaria.
Permite la administración de inmuebles, usuarios, imágenes y favoritos, incorporando
autenticación y autorización basada en roles.

##Manual de uso/instalación de la API
[Descargar la documentación](DocumentaciónApiRestElPisito.pdf)


## Descripción
Este proyecto consiste en una API REST que simula el backend de una aplicación inmobiliaria.
Los usuarios pueden registrarse, iniciar sesión, consultar inmuebles y gestionar una lista
de favoritos. Los usuarios con rol administrador pueden crear, modificar y eliminar inmuebles.

La API está diseñada siguiendo buenas prácticas REST y una arquitectura por capas,
similar a la utilizada en entornos profesionales.

## Tecnologías utilizadas
- Java 17
- Spring Boot
- Spring Security (JWT)
- Spring Data JPA / Hibernate
- Base de datos: MySQL
- Angular
- Maven

## Funcionalidades principales
- Registro y autenticación de usuarios
- Autorización basada en roles (USER / ADMIN / SUPER_ADMIN)
- Gestión de inmuebles (CRUD)
- Subida y gestión de imágenes asociadas a inmuebles
- Gestión de inmuebles favoritos por usuario
- Validación de datos
- Manejo global de excepciones
- Arquitectura REST

## Arquitectura
El proyecto sigue una arquitectura por capas:
- Controllers: exposición de endpoints REST
- Services: lógica de negocio
- Repositories: acceso a datos
- DTOs: transferencia de datos entre capas

## Instalación y ejecución

### Backend
1. Clonar el repositorio
2. Configurar la base de datos en `application.yml`
3. Ejecutar la aplicación con:
   ```bash
   mvn spring-boot:run
