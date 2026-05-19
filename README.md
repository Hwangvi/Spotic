# 🎵 SpoticOne - Spotify Clon (Full Stack App)

[![Angular](https://img.shields.io/badge/Angular-DD0031?style=for-the-badge&logo=angular&logoColor=white)](https://angular.io/)
[![Spring Boot](https://img.shields.io/badge/Spring_Boot-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white)](https://spring.io/projects/spring-boot)

Una aplicación Full Stack robusta que replica la experiencia de usuario y el consumo de datos de la plataforma oficial de Spotify.

---

## 🛡️ Arquitectura Resiliente: Mecanismo de Tolerancia a Fallos (Modo Demo)

> **NOTA DE EVALUACIÓN PARA RECLUTADORES:** 
> Las aplicaciones en modo desarrollo de la API de Spotify restringen por política interna el acceso mediante código `403 Forbidden` a todo usuario cuyo correo electrónico no esté registrado explícitamente en el Dashboard del Desarrollador del propietario del proyecto.
> 
> **¿Cómo soluciona este proyecto dicha limitación?**
> Para evitar el colapso del sistema (Error 500) y asegurar que cualquier visitante de este portfolio pueda interactuar con la aplicación sin restricciones, el Backend en **Spring Boot** cuenta con un interceptor de excepciones centralizado (`try-catch` genérico en la capa de servicio). Si la API de Spotify rechaza las credenciales del visitante, el servidor activa automáticamente un **"Modo Demo Inteligente"**, sirviendo al Frontend estructuras de datos Mock enriquecidas con artistas, imágenes y colecciones reales precargadas.

### Traza del Error Gestionada:
```log
org.springframework.web.client.HttpClientErrorException$Forbidden: 403 Forbidden on GET request for "[https://api.spotify.com/v1/me](https://api.spotify.com/v1/me)"
