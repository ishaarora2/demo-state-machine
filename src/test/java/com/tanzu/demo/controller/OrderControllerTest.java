package com.tanzu.demo.controller;

import com.tanzu.demo.service.OrderService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OrderController.class)
public class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderService orderService;

    private static final String ORDER_ID = "1234";

    @Test
    void createOrder_shouldReturnOrderId() throws Exception{

        Mockito.when(orderService.createOrder()).thenReturn(ORDER_ID);

        mockMvc.perform(get("/order/create"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value("1234"));
    }

    @Test
    void processOrder_shouldReturnSucess() throws Exception{

        String result = "Order processed for Id: " + ORDER_ID;

        Mockito.when(orderService.processOrder(ORDER_ID)).thenReturn(result);

        mockMvc.perform(get("/order/process/{orderId}", ORDER_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(result));
    }

    @Test
    void deliverOrder_shouldReturnSucessMessage() throws Exception{

        String result = "Order delivered for Id: " + ORDER_ID;

        Mockito.when(orderService.deliverOrder(ORDER_ID)).thenReturn(result);

        mockMvc.perform(get("/order/deliver/{orderId}", ORDER_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(result));
    }
}
