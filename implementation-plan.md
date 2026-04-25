# Implementation Plan: CRUD Desktop con flujo Git profesional

**Slug:** `cv-crud-desktop-git`
**Tipo:** GIT_WORKFLOW
**Duración estimada:** 300 min
**Competencia objetivo:** `fullstack_desktop` nivel 2 (prerequisito: 1)

---

## 1. Contexto del problema

Un CRUD de escritorio con base de datos es el tipo de aplicación más común en el sector empresarial: gestión de empleados, inventarios, clientes. Lo que distingue a un desarrollador profesional es cómo organiza el código (patrón DAO para separar persistencia de negocio) y cómo usa Git para trabajar en equipo (ramas por feature, commits convencionales, PRs con revisión).

Este taller combina ambas habilidades: implementación de un CRUD real con JDBC y patrón DAO, usando Git workflow profesional.

---

## 2. Objetivo del taller

Que el estudiante implemente un CRUD completo con JDBC y patrón DAO, usando control de transacciones, y lo entregue con un flujo Git profesional: ramas por feature, ≥ 8 commits convencionales, y al menos 2 PRs.

Relacionado con `competencyTarget.skill_id = "fullstack_desktop"` a `target_level = 2`.

---

## 3. Alcance

**El estudiante DEBE:**
- Implementar CRUD completo (Create, Read, Update, Delete) para la entidad `Producto`
- Aplicar el patrón DAO para separar la capa de acceso a datos
- Controlar transacciones en operaciones que lo requieran
- Usar ramas feature separadas: una para Create/Read y otra para Update/Delete
- ≥ 8 commits con Conventional Commits
- Abrir ≥ 2 PRs (Create/Read y Update/Delete)

**Queda FUERA del scope:**
- Implementar UI (la vista ya está provista en el starter como JTable/JFrame)
- Implementar más de una entidad
- Implementar búsqueda avanzada o filtros
- ORM (solo JDBC puro)

---

## 4. Requisitos funcionales

La entidad `Producto` tiene esta estructura en PostgreSQL:
```sql
CREATE TABLE products (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    price DECIMAL(10,2) NOT NULL CHECK(price > 0),
    stock INT NOT NULL DEFAULT 0 CHECK(stock >= 0),
    created_at TIMESTAMP DEFAULT NOW()
);
```

**Operaciones CRUD requeridas:**
1. **Create**: insertar un producto nuevo; lanzar excepción si `price ≤ 0` o `stock < 0`
2. **Read all**: retornar lista de todos los productos
3. **Read by id**: retornar un producto por id; retornar null si no existe
4. **Update**: actualizar precio, descripción y stock de un producto por id
5. **Delete**: eliminar un producto por id

**Patrón DAO:**
- Interfaz `ProductRepository` con los 5 métodos
- Implementación `ProductRepositoryImpl` con JDBC
- La UI solo llama a la interfaz, nunca accede directamente a la BD

---

## 5. Requisitos técnicos

- Todos los accesos a BD usan `PreparedStatement` (sin concatenación de strings — previene SQL injection)
- Las operaciones de escritura (Create, Update, Delete) están envueltas en transacciones (`connection.setAutoCommit(false)`)
- La separación por capas (DAO vs UI) es ≥ 80% medible por el analizador estático
- Cobertura de tests ≥ 60%
- ≥ 8 commits siguiendo Conventional Commits
- Naming de ramas correcto (prefijos `feature/`, `fix/`, etc.)

**Relacionado con `inputBlocks`:**
- `block_type: pull_request` apuntando a `main`
- `require_conventional_commits: true`
- `min_commits: 8`

---

## 6. Criterios de aceptación

| Criterio | Descripción | Umbral |
|---|---|---|
| CRUD completo | Los 5 métodos del CRUD están implementados y funcionan | 100% |
| Calidad de commits | ≥ 70% de los commits siguen Conventional Commits | 70% |
| Naming de ramas | Las ramas usan prefijos válidos | 100% |
| Separación por capas | La lógica de BD está en el DAO, no en los controllers/UI | ≥ 80% |
| Cobertura de tests | ≥ 60% de cobertura en la capa DAO | 60% |
| Completitud del PR | El PR tiene descripción con ≥ 100 caracteres | 50% |

---

## 7. Estructura esperada del proyecto

```
workshop-cv-crud-desktop-git/
├── src/
│   └── main/java/com/yura/workshop/
│       ├── App.java                    # Punto de entrada (PROVISTO)
│       ├── ui/
│       │   └── ProductFrame.java       # JFrame/JTable UI (PROVISTO — NO modificar)
│       ├── repositories/
│       │   ├── ProductRepository.java  # Interfaz (PROVISTO)
│       │   └── ProductRepositoryImpl.java  # Estudiante implementa
│       ├── models/
│       │   └── Product.java            # POJO (PROVISTO)
│       └── db/
│           └── DatabaseConnection.java # Pool de conexiones (PROVISTO)
├── src/
│   └── test/java/com/yura/workshop/
│       └── repositories/
│           └── ProductRepositoryTest.java  # Estudiante escribe tests de integración
├── database/
│   └── schema.sql                      # Schema PostgreSQL (PROVISTO)
├── compose.yml                         # PostgreSQL local (PROVISTO)
└── pom.xml
```

---

## 8. Consideraciones para multi-lenguaje

Este taller es **exclusivamente Java** (JDBC + Swing/JavaFX para la UI de escritorio). La combinación JDBC + patrón DAO + escritorio es específica del ecosistema Java.

Un taller equivalente para TypeScript sería una API REST (diferente contexto). Para Python sería Tkinter + SQLite/psycopg2.

---

## 9. Notas para generación automática

- **UI provista y no modificable:** El `ProductFrame.java` (JFrame con JTable que muestra los productos) está completamente implementado en el starter. El estudiante solo implementa el DAO que la UI llama. Esto permite al estudiante enfocarse en la capa de datos y el workflow Git.
- **Tests de integración con BD real:** Los tests de `ProductRepositoryTest.java` deben probar contra la BD real (via `compose.yml`). No mockear la BD.
- **Interfaz ProductRepository provista:** La interfaz con los 5 métodos está en el starter. El estudiante implementa la clase concreta.
- **El workflow Git importa:** El grader cuenta commits y verifica naming de ramas. El estudiante debe crear la rama `feature/create-read`, hacer ≥ 4 commits, abrir PR, fusionar, y luego crear `feature/update-delete` para la segunda parte.
- **Complejidad:** Media-Alta. Combina JDBC (sin ORM), patrón DAO, y Git workflow. 300 minutos es razonable para un estudiante que ya conoce Java básico.
