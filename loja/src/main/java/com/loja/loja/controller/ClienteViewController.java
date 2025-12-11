package com.loja.loja.controller;

import com.loja.loja.model.Cliente;
import com.loja.loja.repository.ClienteRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/Clientes")
public class ClienteViewController {

    private final ClienteRepository repository;

    public ClienteViewController(ClienteRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public String listar(@RequestParam(required = false) String busca, Model model) {
        List<Cliente> clientes;
        
        if (busca != null && !busca.trim().isEmpty()) {
            clientes = repository.findAll().stream()
                .filter(c -> c.getNome().toLowerCase().contains(busca.toLowerCase()) ||
                            c.getEmail().toLowerCase().contains(busca.toLowerCase()) ||
                            c.getTelefone().contains(busca))
                .collect(Collectors.toList());
        } else {
            clientes = repository.findAll();
        }
        
        model.addAttribute("clientes", clientes);
        model.addAttribute("busca", busca);
        return "Clientes";
    }

    @GetMapping("/novo")
    public String formulario(Model model) {
        model.addAttribute("cliente", new Cliente());
        return "ClienteForm";
    }

    @PostMapping("/salvar")
    public String salvar(@ModelAttribute Cliente cliente) {
        repository.save(cliente);
        return "redirect:/Clientes";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        Cliente cliente = repository.findById(id).orElse(null);
        model.addAttribute("cliente", cliente);
        return "ClienteForm";
    }

    @GetMapping("/deletar/{id}")
    public String deletar(@PathVariable Long id) {
        repository.deleteById(id);
        return "redirect:/Clientes";
    }
}
