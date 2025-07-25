# Proyecto WebSocket Timer con Spring Boot y React

Este proyecto demuestra un servidor WebSocket que envía periódicamente la hora actual a los clientes conectados. Está construido con:

- Backend en **Spring Boot** usando el API estándar de WebSocket (`javax.websocket`).
- Cliente web con **ReactJS** que muestra el mensaje recibido en tiempo real.

---

## Estructura del Proyecto

- **TimerEndpoint.java**: Endpoint WebSocket que maneja conexiones, desconexiones y errores. Mantiene una cola de sesiones activas para enviar mensajes a todos.
- **TimedMessageBroker.java**: Componente Spring que periódicamente envía la hora actual a todos los clientes conectados usando el endpoint.
- **WSConfigurator.java**: Configura Spring para habilitar el soporte a endpoints WebSocket.
- **index.html + WSComponent.jsx**: Cliente React que se conecta al WebSocket y muestra los mensajes recibidos.

---

## Backend

### TimerEndpoint.java

- Mantiene una cola concurrente con todas las sesiones WebSocket activas.
- Cuando un cliente se conecta, se añade a la cola y se le envía un mensaje de bienvenida.
- Cuando un cliente se desconecta o ocurre un error, se elimina de la cola.
- Método estático `send(String msg)` para enviar mensajes a todos los clientes.


## Cómo ejecutar

Corre la aplicación Spring Boot:

```bash
./mvnw spring-boot:run
```

Abre en tu navegador:


```bash
http://localhost:8080/index.html
```
Verás la hora actual actualizándose cada 5 segundos en la página, gracias al mensaje enviado por el servidor WebSocket.


## arquitectura

![image](https://github.com/user-attachments/assets/8fd1bfef-0921-4011-9bf1-3e9a51cf81e6)

