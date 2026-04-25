# CRUD Desktop con flujo Git profesional

> **Tipo:** GIT_WORKFLOW · **Duración estimada:** 300 min · **Nivel:** Medio-Alto

## Objetivo

Implementar un CRUD completo de productos con JDBC y patrón DAO, entregado con flujo Git profesional: dos ramas feature, ≥ 8 commits convencionales y ≥ 2 Pull Requests.

## Contexto

El módulo de escritorio ya tiene la interfaz gráfica lista (JFrame + JTable). Tu tarea es implementar la capa de acceso a datos (`ProductRepositoryImpl`) usando JDBC puro y el patrón DAO, y orquestar todo con un workflow Git profesional.

## Prerequisitos

- Java 17+
- Maven 3.8+
- Docker (para PostgreSQL local)

## Estructura del starter

```
starter-code/
├── src/main/java/com/yura/workshop/
│   ├── App.java                         # Punto de entrada (NO modificar)
│   ├── ui/ProductFrame.java             # JFrame + JTable (NO modificar)
│   ├── db/DatabaseConnection.java       # Pool de conexiones (NO modificar)
│   ├── models/Product.java              # POJO (NO modificar)
│   └── repositories/
│       ├── ProductRepository.java       # Interfaz (NO modificar)
│       └── ProductRepositoryImpl.java   # ← TÚ implementas esto
├── src/test/java/com/yura/workshop/
│   └── repositories/
│       └── ProductRepositoryTest.java   # ← TÚ escribes los tests
├── database/schema.sql
├── compose.yml
└── pom.xml
```

## Instrucciones

### 1. Prepara la base de datos

```bash
cd starter-code
docker compose up -d
```

Verifica que la BD esté corriendo:

```bash
docker compose exec db psql -U postgres -d yura_workshop -c "\dt"
```

### 2. Levanta la aplicación

```bash
cd starter-code
mvn compile exec:java
```

Verás la ventana con la tabla vacía (sin datos aún porque el DAO no está implementado).

### 3. Primera rama: Create y Read

```bash
git checkout -b feature/create-read
```

Implementa en `ProductRepositoryImpl.java`:

- `save(Product product)` — inserta y retorna el producto con el id generado
- `findAll()` — retorna todos los productos
- `findById(int id)` — retorna el producto o `null` si no existe

Realiza **≥ 4 commits** con Conventional Commits, por ejemplo:

```
feat: implement save method with transaction control
feat: implement findAll using PreparedStatement
feat: implement findById returning null when not found
test: add integration tests for create and read operations
```

Abre un PR hacia `main` con descripción ≥ 100 caracteres.

### 4. Segunda rama: Update y Delete

```bash
git checkout main
git merge feature/create-read   # después de fusionar el primer PR
git checkout -b feature/update-delete
```

Implementa en `ProductRepositoryImpl.java`:

- `update(Product product)` — actualiza name, description, price y stock
- `delete(int id)` — elimina el producto por id

Realiza **≥ 4 commits** adicionales. Abre un segundo PR.

### 5. Escribe los tests de integración

En `ProductRepositoryTest.java` escribe tests que:

- Prueban contra la BD real (vía `compose.yml`)
- Cubren ≥ 60 % del código de `ProductRepositoryImpl`
- Verifican que `price ≤ 0` lanza excepción
- Verifican que `findById` con id inexistente retorna `null`

```bash
cd starter-code
mvn test
```

## Reglas del patrón DAO

- **Todos** los accesos a BD usan `PreparedStatement` (cero concatenación de strings)
- Las operaciones Create, Update y Delete envuelven su SQL en transacciones (`setAutoCommit(false)`)
- La UI (`ProductFrame.java`) llama únicamente a la interfaz `ProductRepository`; nunca accede a JDBC directamente

## Criterios de evaluación

| Métrica | Peso | Umbral |
|---|---|---|
| CRUD completo | 25 % | 5/5 métodos implementados |
| Calidad de commits | 20 % | ≥ 70 % siguen Conventional Commits |
| Naming de ramas | 15 % | Prefijos válidos (`feature/`, `fix/`, etc.) |
| Separación por capas | 20 % | JDBC solo en el DAO, no en UI |
| Cobertura de tests | 15 % | ≥ 60 % cobertura en `ProductRepositoryImpl` |
| Completitud del PR | 5 % | Descripción ≥ 100 caracteres |

## Recursos

- [JDBC Tutorial (Oracle)](https://docs.oracle.com/javase/tutorial/jdbc/)
- [Patrón DAO — Oracle](https://www.oracle.com/java/technologies/dataaccessobject.html)
- [Conventional Commits](https://www.conventionalcommits.org/)
- [PostgreSQL JDBC Driver](https://jdbc.postgresql.org/)
