package com.tanzu.demo.config;

import com.tanzu.demo.service.OrderEvent;
import com.tanzu.demo.service.OrderState;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.StateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.listener.StateMachineListener;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;
import org.springframework.statemachine.state.State;

import java.util.Optional;

@Configuration
@EnableStateMachine
@Slf4j
public class StateMachineConfig extends StateMachineConfigurerAdapter<OrderState, OrderEvent> {

    @Override
    public void configure(StateMachineConfigurationConfigurer<OrderState, OrderEvent> config) throws Exception {
        config
                .withConfiguration()
                .machineId("orderStateMachine")
                .autoStartup(true)
                .listener(listener());
    }

    @Override
    public void configure(StateMachineStateConfigurer<OrderState, OrderEvent> states) throws Exception {
        states
                .withStates()
                .initial(OrderState.CREATED)
                .state(OrderState.PROCESSED)
                .end(OrderState.DELIVERED);
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<OrderState, OrderEvent> transitions) throws Exception {
        transitions
                .withExternal()
                .source(OrderState.CREATED)
                .target(OrderState.PROCESSED)
                .event(OrderEvent.PROCESS)
                .and().withExternal()
                .source(OrderState.PROCESSED)
                .target(OrderState.DELIVERED)
                .event(OrderEvent.DELIVER);
    }

    @Bean
    public StateMachineListener<OrderState, OrderEvent> listener() {
        return new StateMachineListenerAdapter<>() {
          @Override
          public void stateChanged(org.springframework.statemachine.state.State from, org.springframework.statemachine.state.State to) {
              log.info("OrderState changed from: {} to: {}", Optional.ofNullable(from).map(State::getId).orElse(null), to.getId());
          }
        };
    }
}
