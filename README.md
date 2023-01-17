# Demo of Spring Statemachine
This project includes a demo of including state machine in an existing app by using **WithStateMachine** annotation.

## Overview

### What is Spring Statemachine?
According to official [Spring document](https://spring.io/projects/spring-statemachine#:~:text=Spring%20Statemachine%20is%20a%20framework,to%20ease%20complex%20state%20configuration.). it is a framework for application developers to use state machine concepts.
State machines are powerful because behaviour is always guaranteed to be consistent, 
making it relatively easy to debug.

### What is this project about?
The main objective of using a state machine is to control change of state for an entity. 
For example, in this application the entity is *Order* and an order can be in CREATED, 
PROCESSED or DELIVERED state at any given point of time. The change of state for an order is 
controlled by state machine.

According to [Spring documentation](https://docs.spring.io/spring-statemachine/docs/3.2.0/reference/#sm-context), 
it can get a little limited to do interaction with a state machine by either listening to its 
events or using [actions](https://docs.spring.io/spring-statemachine/docs/3.2.0/reference/#sm-actions)
with states and transitions. From time to time, this approach can get 
too limited and verbose to create interaction with the application with 
which a state machine works. For this specific use case, a Spring-style 
context integration is made, that can easily insert state machine functionality into beans.

One of the annotations that can be used to achieve this is **@WithStateMachine**. This annotation can be used
to associate a state machine with an existing bean.