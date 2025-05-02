package com.axa.api_sinistre.controller;

import com.axa.api_sinistre.model.Sinistre;
import com.axa.api_sinistre.service.SinistreService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api/sinistres")
public class SinistreController {

    private final SinistreService service;

    public SinistreController(SinistreService service) {
        this.service = service;
    }

    @GetMapping
    public List<Sinistre> getAll() {
        return service.getAll();
    }

    @PostMapping
    public Sinistre create(@RequestBody Sinistre sinistre) {
        return service.create(sinistre);
    }
     // Simple test endpoint
    @GetMapping("/test")
    public String testEndpoint() {
        return "API is working!";
    }
}
