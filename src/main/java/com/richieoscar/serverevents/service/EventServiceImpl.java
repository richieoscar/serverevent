package com.richieoscar.serverevents.service;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.richieoscar.serverevents.dto.DispatcRequest;
import com.richieoscar.serverevents.dto.SubscribeRequest;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Slf4j
@Service
public class EventServiceImpl implements EventService {

    private static final String EVENT_INIT = "SUBSCRIBE";
    HashMap<String, SseEmitter> clients = new HashMap<>();
    List<SseEmitter> emitters = new CopyOnWriteArrayList<>();

    @Override
    public SseEmitter subscribe(SubscribeRequest request) {
        SseEmitter sseEmitter = new SseEmitter(Long.MAX_VALUE);
        try {
            sseEmitter.send(SseEmitter.event().name(EVENT_INIT));
        } catch (IOException e) {
            e.printStackTrace();
        }
        clients.put(request.getClientId(), sseEmitter);
        sseEmitter.onCompletion(() -> clients.remove(request.getClientId()));
        return sseEmitter;
    }

    @Override
    public void dispatchEvent(DispatcRequest dispatcRequest) {
        emitters.forEach(sseEmitter -> {
            try {
                sseEmitter.send(SseEmitter.event().name("news").data(dispatcRequest));
            } catch (IOException e) {
                emitters.remove(sseEmitter);
            }
        });
    }

    @Override
    public SseEmitter generalSubscription() {
        log.info("Subscribing client");
        SseEmitter sseEmitter = new SseEmitter(Long.MAX_VALUE);
        try {
            sseEmitter.send(SseEmitter.event().name(EVENT_INIT));
        } catch (IOException e) {
            log.error("Error Occurred during subscription {}", e.getMessage());
            e.printStackTrace();
        }
        emitters.add(sseEmitter);
        sseEmitter.onCompletion(() -> emitters.remove(sseEmitter));
        sseEmitter.onError(e -> emitters.remove(sseEmitter));
        log.info("Subscription Successful {}", emitters.size());
        return sseEmitter;
    }
}
