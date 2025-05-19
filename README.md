# Sistema de Gestión de Equipos de Fútbol

Una aplicación JavaFX completa para gestionar el personal de equipos de fútbol, incluyendo jugadores y entrenadores, con diferentes roles de usuario y capacidades de gestión de datos.

## Descripción

Esta aplicación proporciona una solución completa para la gestión de equipos de fútbol, permitiendo a los administradores gestionar jugadores, entrenadores y cuentas de usuario. Cuenta con una interfaz amigable con diferentes vistas para administradores y usuarios normales, capacidades de importación/exportación de datos y funcionalidad de informes.

## Características

- **Autenticación de Usuarios**: Sistema de inicio de sesión seguro con diferentes roles de usuario (Administrador y Usuario Normal)
- **Gestión de Personal**:
  - Añadir, editar y eliminar jugadores y entrenadores
  - Seguimiento de estadísticas de jugadores (goles, partidos jugados)
  - Gestión de especializaciones de entrenadores
- **Importación/Exportación de Datos**:
  - Soporte para múltiples formatos (CSV, JSON, XML)
  - Importación/exportación de archivos Zip
- **Informes**:
  - Generación de informes HTML del personal del equipo
- **Gestión de Usuarios** (solo Administrador):
  - Crear, editar y eliminar cuentas de usuario
  - Asignar roles de usuario
- **Configuración**:
  - Configuración de la base de datos
  - Configuración de directorios de datos

## Tecnologías Utilizadas

- **Kotlin**: Lenguaje de programación principal
- **JavaFX**: Framework de interfaz de usuario
- **SQLite**: Base de datos para almacenar datos de la aplicación
- **JDBI**: SQL Object para acceso a la base de datos
- **Koin**: Inyección de dependencias
- **Serialización**: Soporte para formatos JSON, XML y CSV
- **JUnit & Mockito**: Framework de pruebas
- **Gradle**: Sistema de construcción

## Requisitos del Sistema

- Java 21 o superior
- Gradle 8.0 o superior

## Instalación

1. Clonar el repositorio:
   ```
   git clone https://github.com/yourusername/ProyectoEquipoFutbolJavaFx.git
   ```

2. Navegar al directorio del proyecto:
   ```
   cd ProyectoEquipoFutbolJavaFx
   ```

3. Construir el proyecto:
   ```
   ./gradlew build
   ```

4. Ejecutar la aplicación:
   ```
   ./gradlew run
   ```

## Uso

### Inicio de Sesión

La aplicación comienza con una pantalla de inicio de sesión. Utilice las siguientes credenciales predeterminadas:
- **Administrador**: usuario: `admin`, contraseña: `admin`
- **Usuario**: usuario: `user`, contraseña: `user`

### Vista de Administrador

La vista de administrador proporciona acceso completo a todas las funciones:
- **Pestaña de Personal**: Gestionar jugadores y entrenadores
- **Pestaña de Usuarios**: Gestionar cuentas de usuario
- **Pestaña de Configuración**: Configurar ajustes de la aplicación

### Vista de Usuario Normal

La vista de usuario normal proporciona acceso limitado:
- Ver personal del equipo
- Generar informes
- Exportar datos

## Estructura del Proyecto

- `src/main/kotlin/srangeldev/proyectoequipofutboljavafx/`: Código fuente principal
  - `controllers/`: Controladores de UI
  - `di/`: Inyección de dependencias
  - `newteam/`: Lógica de negocio principal
    - `models/`: Modelos de datos
    - `repository/`: Acceso a datos
    - `service/`: Servicios de negocio
    - `storage/`: Operaciones de almacenamiento de archivos
  - `routes/`: Gestión de navegación
  - `viewmodels/`: Modelos de vista para UI

## Colaboradores

- Ángel Sánchez Gasanz
- Jorge Morgado Jiménez
- Antoine López

## Licencia

Este proyecto está licenciado bajo la Licencia MIT - consulte el archivo LICENSE para más detalles.
