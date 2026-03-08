# 🎓 Academy REST API

Sistema de gestión académica backend desarrollado con **Java** y **Spring Boot**.
Este proyecto implementa una arquitectura de base de datos **híbrida** para aprovechar lo mejor del mundo relacional (MySQL) y NoSQL (MongoDB), orquestado mediante **Docker**.

## 🚀 Descripción del Proyecto

**Academy API** es una plataforma robusta diseñada para administrar instituciones educativas. Permite la gestión de alumnos, profesores, cursos e inscripciones, manteniendo la integridad de los datos académicos en MySQL.

Simultáneamente, incorpora un módulo de **Reseñas y Encuestas** (Reviews) utilizando MongoDB, permitiendo flexibilidad y escalabilidad para el feedback de los alumnos, con reglas de negocio complejas para asegurar la unicidad por semestre académico.

## 🛠️ Stack Tecnológico y Herramientas

### Infraestructura & Desarrollo
* **Docker & Docker Compose**: Orquestación de contenedores. Permite levantar las bases de datos (MySQL y MongoDB) de forma aislada y reproducible.
* **IntelliJ IDEA**: Entorno de desarrollo (IDE).
* **Git**: Control de versiones.
* **Postman**: Pruebas de integración manuales y validación de flujos de seguridad (Bearer Token).

### Backend & Framework
* **Java 17+**: Lenguaje principal.
* **Spring Boot 3**: Framework base para microservicios y APIs REST.
* **Maven**: Gestión de dependencias.

### Persistencia Híbrida
* **MySQL (Spring Data JPA)**: Datos transaccionales (Usuarios, Cursos, Notas).
* **MongoDB (Spring Data MongoDB)**: Datos flexibles (Reseñas/Encuestas).

### Seguridad
* **Spring Security**: Autorización basada en Roles (`ADMINISTRATOR`, `TEACHER`, `STUDENT`).
* **JWT (JSON Web Tokens)**: Autenticación Stateless.
* **Token Blacklist**: Logout seguro almacenando tokens invalidados en base de datos hasta su expiración.
* **BCrypt**: Hashing de contraseñas.

---

## ✨ Funcionalidades Clave

### 1. Autenticación Robusta
* Registro, Login (JWT) y **Logout real** (lista negra de tokens).
* Protección de endpoints mediante anotaciones `@PreAuthorize`.

### 2. Módulo Académico (MySQL)
* Gestión de Cursos y Usuarios.
* **Inscripciones (Enrollments):** Vinculación segura alumno-curso.
* **Carga de Notas (Grades):**
    * Endpoint seguro `PATCH /enrollments/{id}/grade`.
    * **Regla de Negocio:** Solo el profesor asignado a ese curso específico (o un Admin) tiene permisos para calificar.

### 3. Módulo de Feedback (MongoDB)
* Sistema de reseñas de alumnos a profesores.
* **Índices Compuestos (`@CompoundIndex`):** Restricción a nivel de base de datos para garantizar que un alumno solo pueda opinar una vez por curso y por ciclo lectivo (Año + Semestre).

### 4. Calidad de Código
* **Manejo de Errores Global:** Respuestas JSON estandarizadas (RFC-like) usando `records` y `@RestControllerAdvice`.
* **DTOs & Mappers:** Separación limpia entre Entidades y objetos de transferencia.

---

## ⚙️ Instalación y Ejecución

Puedes levantar el entorno completo utilizando Docker, sin necesidad de instalar bases de datos localmente.

### Prerrequisitos
* Java 17+.
* Docker y Docker Compose.
* Maven.

### Paso a Paso

1.  **Clonar el repositorio:**
    ```bash
    git clone [https://github.com/tu-usuario/academy-api.git](https://github.com/tu-usuario/academy-api.git)
    cd academy-api
    ```

2.  **Levantar infraestructura (MySQL + MongoDB):**
    Ejecuta en la raíz del proyecto:
    ```bash
    docker-compose up -d
    ```

3.  **Ejecutar la aplicación:**
    ```bash
    mvn spring-boot:run
    ```

4.  **Acceder:**
    * API: `http://localhost:9191/api/v1/`
    * Documentación Swagger: `http://localhost:9191/api/v1/swagger-ui/index.html`

---

## 🗺️ Roadmap y Futuras Mejoras

El proyecto es funcional y sigue una arquitectura profesional, con planes de evolución continua:

* [ ] **Monitoreo (Actuator):** Panel de métricas de salud y rendimiento.
* [ ] **Migración de BD (Flyway):** Versionado de esquemas SQL para reemplazar `ddl-auto`.
* [ ] **Email Service:** Notificaciones asíncronas para recuperación de contraseñas.
* [ ] **Testing Automático:** Integración de **Testcontainers** para CI/CD.
* [ ] **Frontend:** Desarrollo de cliente web (React/Angular).

---
*Desarrollado con mucho esfuerzo por Cesar Niveyro*