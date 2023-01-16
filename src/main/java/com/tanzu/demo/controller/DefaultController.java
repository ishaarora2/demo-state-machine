package com.tanzu.demo.controller;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class DefaultController {

    @GetMapping("/")
    public void defaultEndpoint(HttpServletResponse servletResponse) throws IOException {
        servletResponse.sendRedirect("/actuator/health");
    }
}
