#  Pastelería Mil Sabores — Aplicación Fullstack (React + Spring Boot)

> **Descripción General (ES) :** Backend de la plataforma Pastelería Mil Sabores: migración a arquitectura Fullstack integrando React (Vite) con API REST en Java Spring Boot, MySQL y seguridad JWT con control de roles.
>
> **General Description (EN):** Part 2 of the Pastelería Mil Sabores platform: Fullstack migration integrating React (Vite) with a Java Spring Boot REST API, MySQL persistence, and role-based JWT security.

---

## Descripción del Proyecto (Español)

Segunda parte del proyecto **Pastelería Mil Sabores**. Esta etapa representa la evolución y migración de la aplicación web hacia una arquitectura cliente-servidor completa, conectando el frontend desarrollado en **React (Vite)** con una **API REST en Java Spring Boot**, persistencia relacional en **MySQL** y autenticación stateless mediante **JWT (JSON Web Tokens)** con autorización basada en roles (`Administrador` y `Cliente`).

### Enlaces de Repositorios
* **Primera Parte — Frontend Anterior (Evaluación 2 - React SPA):** `https://github.com/Kmilinho-duoc/pagina-fullstack-II`
* **Segunda Parte — Backend / Fullstack Actual (Evaluación 3 - Spring Boot + React):** `https://github.com/PaulaComputing/Bakend-Bakery-Web-`

###  Tecnologías Utilizadas
* **Backend:** Java 21 (LTS), Spring Boot 3.4.5, Spring Data JPA / Hibernate, Spring Security + JJWT (0.12.6), Springdoc OpenAPI (Swagger UI), Lombok.
* **Base de Datos:** MySQL (`pasteleria_db` gestionada vía XAMPP).
* **Frontend:** React 18+, Vite, React Router DOM, Bootstrap 5, Vitest + React Testing Library.

### Instalación y Ejecución
1. **Base de Datos:** Inicia **Apache** y **MySQL** en el Panel de Control de XAMPP (puerto `3306`).
2. **Backend (Spring Boot):** Desde la carpeta `backend`, ejecuta `./mvnw spring-boot:run`. El sistema creará la base de datos `pasteleria_db`, sus tablas y cargará automáticamente los 16 productos iniciales y usuarios de prueba.
   * **Swagger UI:** `http://localhost:8080/swagger-ui.html`
3. **Frontend (React):** Desde la carpeta del frontend, ejecuta `npm install` y luego `npm run dev` (`http://localhost:5173`).

### Credenciales de Prueba (Precargadas)
| Rol | Correo Electrónico | Contraseña | Permisos |
| :--- | :--- | :--- | :--- |
| **Administrador** | `admin@duoc.cl` | `admin123` | Acceso al Panel Admin, CRUD de Productos y Usuarios |
| **Cliente** | `cliente@gmail.com` | `cliente123` | Navegación, carrito de compras y generación de pedidos |

### Principales Endpoints de la API REST
| Método | Endpoint | Acceso | Descripción |
| :--- | :--- | :--- | :--- |
| `POST` | `/api/auth/login` | Público | Autentica credenciales y retorna el **Token JWT** y rol |
| `POST` | `/api/auth/registro` | Público | Registra un nuevo usuario con rol `Cliente` |
| `GET` | `/api/productos` | Público | Obtiene el catálogo completo de productos |
| `POST` | `/api/productos` | `ADMIN` | Crea un nuevo producto en la base de datos |
| `PUT` | `/api/productos/{id}` | `ADMIN` | Actualiza datos o stock de un producto |
| `DELETE` | `/api/productos/{id}` | `ADMIN` | Elimina un producto del catálogo |
| `GET/POST/PUT/DELETE` | `/api/usuarios/**` | `ADMIN` | Gestión CRUD completa de usuarios |
| `POST` | `/api/pedidos` | Autenticado | Procesa la compra del carrito y descuenta el stock |

---

## Project Description (English)

Second part of the **Pastelería Mil Sabores** project,. This phase evolves the previous frontend application into a complete client-server Fullstack architecture, connecting the **React (Vite)** client with a **Java Spring Boot REST API**, **MySQL** relational persistence, and stateless **JWT (JSON Web Tokens)** authentication with role-based access control (`Administrador` and `Cliente`).

### Repository Links
* **Part 1 — Previous Frontend (Evaluation 2 - React SPA):** `https://github.com/Kmilinho-duoc/pagina-fullstack-II`
* **Part 2 — Current Backend / Fullstack (Evaluation 3 - Spring Boot + React):** `https://github.com/PaulaComputing/Bakend-Bakery-Web-`

###  Tech Stack
* **Backend:** Java 21 (LTS), Spring Boot 3.4.5, Spring Data JPA / Hibernate, Spring Security + JJWT (0.12.6), Springdoc OpenAPI (Swagger UI), Lombok.
* **Database:** MySQL (`pasteleria_db` managed via XAMPP).
* **Frontend:** React 18+, Vite, React Router DOM, Bootstrap 5, Vitest + React Testing Library.

###  Setup & Running Instructions
1. **Database:** Start **Apache** and **MySQL** from the XAMPP Control Panel (port `3306`).
2. **Backend (Spring Boot):** Inside the `backend` folder, run `./mvnw spring-boot:run`. The application will automatically create the `pasteleria_db` schema, tables, and seed initial products and test accounts.
   * **Swagger UI:** `http://localhost:8080/swagger-ui.html`
3. **Frontend (React):** Inside the frontend folder, run `npm install` followed by `npm run dev` (`http://localhost:5173`).

### Test Credentials (Pre-seeded)
| Role | Email | Password | Permissions |
| :--- | :--- | :--- | :--- |
| **Administrator** | `admin@duoc.cl` | `admin123` | Admin Dashboard access, Products & Users CRUD |
| **Customer** | `cliente@gmail.com` | `cliente123` | Catalog browsing, shopping cart, and order checkout |

### Main REST API Endpoints
| Method | Endpoint | Access | Description |
| :--- | :--- | :--- | :--- |
| `POST` | `/api/auth/login` | Public | Authenticates credentials and returns a **JWT Token** & role |
| `POST` | `/api/auth/registro` | Public | Registers a new user with the `Cliente` role |
| `GET` | `/api/productos` | Public | Retrieves the full product catalog |
| `POST` | `/api/productos` | `ADMIN` | Creates a new product in the database |
| `PUT` | `/api/productos/{id}` | `ADMIN` | Updates product details or stock |
| `DELETE` | `/api/productos/{id}` | `ADMIN` | Deletes a product from the catalog |
| `GET/POST/PUT/DELETE` | `/api/usuarios/**` | `ADMIN` | Full CRUD management for users |
| `POST` | `/api/pedidos` | Authenticated | Processes cart checkout and deducts product stock |

---

## 👨‍💻 Autor / Author
* **Nombre / Name:** `Contribuidores del repositorio/ Repository contributors.`
