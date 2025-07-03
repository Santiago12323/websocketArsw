package com.websocket.websocket.configuration;

import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class TimerHandler extends TextWebSocketHandler {

    private ScheduledExecutorService scheduler;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate(() -> {
            try {
                if (session.isOpen()) {
                    session.sendMessage(new TextMessage("Timer: " + System.currentTimeMillis()));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }, 0, 1, TimeUnit.SECONDS);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        // Cuando la conexión se cierra, detenemos el scheduler
        if (scheduler != null && !scheduler.isShutdown()) {
            scheduler.shutdown();
        }
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        // Aquí puedes manejar mensajes recibidos del cliente si quieres
        System.out.println("Mensaje recibido del cliente: " + message.getPayload());
    }
}
