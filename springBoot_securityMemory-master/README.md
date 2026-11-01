# Ejercicio de autenticación y autorización por roles con Spring Boot Security

## Cómo ejecutarlo
1. En la carpeta `database` está el archivo `script.sql`. Ábrelo en MySQL y ejecútalo.
2. En `src/main/resources/application.properties`, cambia `spring.datasource.username` y `spring.datasource.password` por el usuario y la clave de tu MySQL.
3. Ejecuta el proyecto y abre http://localhost:8080
4. Ingresa con cualquier usuario:
   - admin / admin123
   - editor / editor123
   - creator / creator123
   - user / user123