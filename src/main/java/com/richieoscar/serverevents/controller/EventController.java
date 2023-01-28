package com.richieoscar.serverevents.controller;

import com.richieoscar.serverevents.dto.DispatcRequest;
import com.richieoscar.serverevents.dto.SubscribeRequest;
import com.richieoscar.serverevents.service.EventService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Slf4j
public class EventController {
    private final EventService eventService;

    @PostMapping("subscribe")
    public ResponseEntity<SseEmitter> subscribe(@RequestBody SubscribeRequest request) {
        log.info("EventController::subscribe, =>{}", request);
        return ResponseEntity.ok(eventService.subscribe(request));
    }

    @PostMapping("/dispatch")
    public ResponseEntity<Void> dispatchEvents(@RequestBody DispatcRequest dispatcRequest) {
        log.info("EventController::dispatchEvent, =>{}", dispatcRequest);
        return ResponseEntity.ok(eventService.dispatchEvent(dispatcRequest));
    }
}
