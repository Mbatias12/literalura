# LiterAlura — Catálogo de libros (Java + Spring Boot + PostgreSQL, consola)

Challenge ONEL / Alura Latam. App de **consola** que busca libros en la API **Gutendex** y los registra en **PostgreSQL**. Incluye menú con 5 funcionalidades básicas.

## Funcionalidades
1. **Buscar libro por título** (consulta la API y guarda en DB si no existe).
2. **Listar libros** registrados.
3. **Listar autores** registrados.
4. **Listar autores vivos en un año** dado.
5. **Listar libros por idioma** (ES/EN/FR/PT).

Reglas:
- Si no hay resultados → “Libro no encontrado”.
- No inserta duplicados (`gutendex_id` único).

## Stack
Java 17 · Spring Boot 3 · Spring Data JPA · WebClient · PostgreSQL · Maven

## Estructura
```
src/main/java/com/alura/literalura/
  App.java
  dto/  model/  repository/  service/  config/
src/main/resources/application.properties
docker-compose.yml
```

## Base de datos
Con Docker (recomendado):
```bash
docker compose up -d
```
O con Postgres local exportando variables:
```bash
# macOS/Linux
export DB_URL=jdbc:postgresql://localhost:5432/literalura
export DB_USER=alura
export DB_PASSWORD=alura
# Windows PowerShell
# $env:DB_URL="jdbc:postgresql://localhost:5432/literalura"
# $env:DB_USER="alura"
# $env:DB_PASSWORD="alura"
```

## Ejecutar
```bash
mvn spring-boot:run
```
Menú:
```
[1] Buscar libro por título (API) y guardar
[2] Listar libros registrados
[3] Listar autores registrados
[4] Listar autores vivos en un año
[5] Listar libros por idioma (ES/EN/FR/PT)
[0] Salir
```

## Capturas
Guarda tus imágenes en `docs/img/` y referéncialas aquí:
```
![Menú](docs/img/menu.png)
![Búsqueda](docs/img/ejemplo-1.png)
```