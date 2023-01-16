package com.tanzu.demo.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.messaging.support.MessageBuilder;

import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.StateMachineEventResult;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private static final String ORDER_ID = "orderId";

    @Qualifier("orderStateMachine")
    private final StateMachine<OrderState, OrderEvent> stateMachine;

    public String createOrder(){
        UUID uuid = UUID.randomUUID();
        String uuidAsString = uuid.toString();

        return "Your new order Id is: " + uuidAsString;
    }

    public String processOrder(String orderId) {
        String result = "Unexpected error";

        StateMachineEventResult<OrderState, OrderEvent> eventResult = stateMachine
                .sendEvent(Mono.just(MessageBuilder.withPayload(OrderEvent.PROCESS)
                        .setHeader(ORDER_ID, orderId)
                        .build()))
                .blockLast();

        if(eventResult.getResultType().equals(StateMachineEventResult.ResultType.ACCEPTED)){
            result = "Order processed for Id: " + orderId;
        } else if(eventResult.getResultType().equals(StateMachineEventResult.ResultType.DENIED)){
            result = "State change denied";
        }

        return result;
    }

    public String deliverOrder(String orderId) {
        String result = "Unexpected error";

        StateMachineEventResult<OrderState, OrderEvent> eventResult = stateMachine
                .sendEvent(Mono.just(MessageBuilder.withPayload(OrderEvent.DELIVER)
                        .setHeader(ORDER_ID, orderId)
                        .build()))
                .blockLast();

        if(eventResult.getResultType().equals(StateMachineEventResult.ResultType.ACCEPTED)){
            result = "Order delivered for Id: " + orderId;
        } else if(eventResult.getResultType().equals(StateMachineEventResult.ResultType.DENIED)){
            result = "State change denied";
        }

        return result;
    }
}
