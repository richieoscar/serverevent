package com.richieoscar.serverevents.service;

import com.richieoscar.serverevents.dto.DispatcRequest;
import com.richieoscar.serverevents.dto.SubscribeRequest;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

public interface EventService {

    SseEmitter subscribe(SubscribeRequest request);

    Void dispatchEvent(DispatcRequest dispatcRequest);
}
