package com.tanzu.demo.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.statemachine.test.StateMachineTestPlan;
import org.springframework.statemachine.test.StateMachineTestPlanBuilder;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class OrderServiceTest {

    @Autowired
    private OrderService orderService;

    @Autowired
    private StateMachine<OrderState, OrderEvent> stateMachine;

    private static final String ORDER_ID = "1234";

    @Test
    void createOrder_shouldReturnOrderId() {
        String result = orderService.createOrder();

        assertNotNull(result);
    }

    @Test
    void processAndDeliverOrder_shouldChangeStateToProcesssedAndDelivered() throws Exception {

        StateMachineTestPlan<OrderState, OrderEvent> plan =
                StateMachineTestPlanBuilder.<OrderState, OrderEvent> builder()
                        .defaultAwaitTime(1)
                        .stateMachine(stateMachine)
                        .step()
                        .expectState(OrderState.CREATED)
                        .and()
                        .step()
                        .sendEvent(OrderEvent.PROCESS)
                        .expectState(OrderState.PROCESSED)
                        .and()
                        .step()
                        .sendEvent(OrderEvent.DELIVER)
                        .expectState(OrderState.DELIVERED)
                        .and()
                        .build();

        plan.test();
    }
}
