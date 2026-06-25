# Backend LQO

Backend preparado para trabajar como monolito modular con Java y Spring Boot.

## Estructura

```text
src/main/java/com/lqo/
  LqoApplication.java
  modules/
    health/
      application/
      domain/
      infrastructure/
      web/
    auth/
      application/
      domain/
      infrastructure/
      web/
    users/
      application/
      domain/
      infrastructure/
      web/
  shared/
    config/
    domain/
    error/
    persistence/
    security/
    web/
```

## Regla practica

Cada modulo contiene su propia logica de negocio, casos de uso, adaptadores de entrada y persistencia. Lo que sea transversal a varios modulos vive en `shared`.

## Capas por modulo

- `domain`: entidades, value objects, reglas de negocio y puertos del dominio.
- `application`: casos de uso, DTOs internos y orquestacion.
- `infrastructure`: repositorios, clientes externos y adaptadores tecnicos.
- `web`: controllers, request/response DTOs y mapeadores HTTP.

## Comandos

Con Maven instalado:

```bash
mvn spring-boot:run
mvn test
```

## Base de datos

El backend esta configurado para conectarse a MySQL usando la base de datos
`LQO_DATABASE`.

Valores por defecto:

```text
MYSQL_HOST=localhost
MYSQL_PORT=3306
MYSQL_DATABASE=LQO_DATABASE
MYSQL_USER=root
MYSQL_PASSWORD=
```

En PowerShell puedes configurar credenciales antes de iniciar la aplicacion:

```powershell
$env:MYSQL_USER="root"
$env:MYSQL_PASSWORD="tu_password"
mvn spring-boot:run
```

## Login

El primer caso de uso expone:

```text
POST /api/v1/auth/login
```

Body:

```json
{
  "username": "admin",
  "password": "admin123"
}
```

La respuesta incluye un token JWT tipo `Bearer` con vida limitada. Por defecto
expira en 30 minutos.

Variables relevantes:

```text
JWT_SECRET=lqo-development-secret-change-me-32-bytes
JWT_EXPIRATION_MINUTES=30
AUTH_BOOTSTRAP_ENABLED=true
AUTH_BOOTSTRAP_USERNAME=admin
AUTH_BOOTSTRAP_PASSWORD=admin123
MYSQL_SSL_MODE=PREFERRED
MYSQL_ALLOW_PUBLIC_KEY_RETRIEVAL=false
```

Para produccion cambia `JWT_SECRET`, desactiva el usuario bootstrap y usa TLS
obligatorio en MySQL cuando el servidor lo soporte.

La aplicacion expone un endpoint base:

```text
GET /api/v1/health
```
