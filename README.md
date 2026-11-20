# Pastelería Mil Sabores - Plataforma E-Commerce Fullstack

Aplicación web  para la gestión y venta en línea de productos de pastelería, desarrollada como proyecto integral para la asignatura **Desarrollo Fullstack II (DSY1104 - Instituto Profesional Duoc UC)**. 

El sistema integra una aplicación SPA en **React + Vite** con una API RESTful segura en **Spring Boot**, persistencia relacional en **MySQL** y autenticación basada en **JWT (JSON Web Tokens)** con control de acceso basado en roles (RBAC).

---

## Estructura del Repositorio

El repositorio está organizado con dos módulos independientes:

```text
Bakend-Bakery-Web-/
├── backend/                 # API RESTful en Java 21 + Spring Boot
│   ├── src/main/java/       # Controladores, Servicios, Repositorios, Entidades y Seguridad JWT
│   ├── src/main/resources/  # Configuración de base de datos y propiedades
│   └── pom.xml              # Dependencias Maven
└── frontend/                # Aplicación cliente en React + Vite
    ├── src/componentes/     # Componentes reutilizables (Layout, TarjetaProducto, etc.)
    ├── src/contexto/        # Gestión de estado global y persistencia de sesión (Tienda.jsx)
    ├── src/paginas/         # Vistas públicas, carrito de compras y panel administrativo
    ├── src/servicios/       # Cliente HTTP centralizado (api.js)
    └── package.json         # Dependencias y scripts de Vite
