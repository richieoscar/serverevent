package com.richieoscar.serverevents.controller;

import com.richieoscar.serverevents.dto.DispatcRequest;
import com.richieoscar.serverevents.dto.SubscribeRequest;
import com.richieoscar.serverevents.service.EventService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@CrossOrigin
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Slf4j
public class EventController {
    private final EventService eventService;

    @PostMapping("/subscribe")
    public ResponseEntity<SseEmitter> subscribe(@RequestParam("clientId") String clientId, @RequestBody SubscribeRequest request) {
        log.info("EventController::subscribe, =>{}", request);
        request.setClientId(clientId);
        return ResponseEntity.ok(eventService.subscribe(request));
    }

    @PostMapping("/dispatch")
    public void dispatchEvents(@RequestBody DispatcRequest dispatcRequest) {
        log.info("EventController::dispatchEvent, =>{}", dispatcRequest);
        eventService.dispatchEvent(dispatcRequest);
    }

    @GetMapping(value = "/subscribe2", consumes = MediaType.ALL_VALUE)
    public ResponseEntity<SseEmitter> subscribe() {
        log.info("EventController::subscribeGeneral");
        return ResponseEntity.ok(eventService.generalSubscription());
    }
}
