package com.websocket.websocket.infrastructure.controller;

import java.io.IOException;
import java.util.logging.Level;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.logging.Logger;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import org.springframework.stereotype.Component;
@Component
@ServerEndpoint("/timer")
public class TimerEndpoint {
    private static final Logger logger = Logger.getLogger("ETFEndpoint");
    static Queue<Session> queue = new ConcurrentLinkedQueue<>();
    public static void send(String msg) {
        try {
            logger.log(Level.INFO, "Sending message to {0} sessions", queue.size());
            for (Session session : queue) {
                session.getBasicRemote().sendText(msg);
                logger.log(Level.INFO, "Sent: {0}", msg);
            }
        } catch (IOException e) {
            logger.log(Level.INFO, e.toString());
        }
    }

    @OnOpen
    public void openConnection(Session session) {
        logger.log(Level.INFO, "Connection opened. Session id: {0}", session.getId());
        queue.add(session);
        try {
            session.getBasicRemote().sendText("Connection established.");
        } catch (IOException ex) {
            Logger.getLogger(TimerEndpoint.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @OnClose
    public void onClose(Session session) {
        System.out.println("WebSocket CLOSED: " + session.getId());
        queue.remove(session);
    }

}