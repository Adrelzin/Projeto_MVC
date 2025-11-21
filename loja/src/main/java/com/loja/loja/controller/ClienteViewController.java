package com.loja.loja.controller;

import com.loja.loja.repository.ClienteRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/index")
public class ClienteViewController {

    private final ClienteRepository repository;

    public ClienteViewController(ClienteRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("index", repository.findAll());
        return "index"; // nome do template HTML
    }
}
