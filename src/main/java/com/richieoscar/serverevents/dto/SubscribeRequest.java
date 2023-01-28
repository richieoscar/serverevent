package com.richieoscar.serverevents.dto;

import lombok.Data;

@Data
public class SubscribeRequest {
    private String clientId;
    private String event;
}
