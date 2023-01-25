package com.tanzu.demo.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.annotation.OnStateChanged;
import org.springframework.statemachine.annotation.OnTransition;
import org.springframework.statemachine.annotation.WithStateMachine;

@RequiredArgsConstructor
@WithStateMachine(name = "orderStateMachine")
@Slf4j
public class StateMachineWithSMAnnotation {

    private static final String ORDER_ID = "orderId";

    @OnStateChanged(source = "CREATED", target = "PROCESSED")
    public void processOrder(StateMachine<OrderState, OrderEvent> stateMachine, Message message) {

        String orderId = message.getHeaders().get(ORDER_ID).toString();

        log.debug(String.format("State: %s for order Id: %s ", stateMachine.getState().getId(), orderId));
    }

    @OnTransition(source = "PROCESSED", target = "DELIVERED")
    public void deliverOrder(StateMachine<OrderState, OrderEvent> stateMachine, Message message) {

        String orderId = message.getHeaders().get(ORDER_ID).toString();

        log.debug(String.format("State: %s for order Id: %s ", stateMachine.getState().getId(), orderId));
    }
}
