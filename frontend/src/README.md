# Arquitectura monolitica modular

Este frontend es una sola aplicacion Vite/React, organizada por modulos para que el codigo crezca sin mezclarse.

## Estructura

- `app/`: arranque de la aplicacion, rutas globales y providers.
- `layouts/`: estructuras visuales reutilizables de pagina.
- `modules/`: funcionalidades por dominio. Cada modulo puede tener `pages`, `components`, `hooks`, `services`, `data` y su `index.js`.
- `shared/`: piezas reutilizables entre modulos, como UI base, utilidades, configuracion, cliente HTTP y estado compartido.
- `styles/`: estilos globales y configuracion de Tailwind.
- `assets/`: imagenes, fuentes o recursos estaticos usados por la app.

## Regla practica

Si algo pertenece a una funcionalidad especifica, va en `modules/<modulo>`. Si se usa en varios modulos, sube a `shared`.
