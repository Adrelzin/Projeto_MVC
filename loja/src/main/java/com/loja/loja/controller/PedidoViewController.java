package com.loja.loja.controller;

import com.loja.loja.model.Pedido;
import com.loja.loja.repository.PedidoRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/Pedidos")
public class PedidoViewController {
    
    private final PedidoRepository repository;

    public PedidoViewController(PedidoRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("pedidos", repository.findAll());
        return "Pedidos";
    }

    @GetMapping("/novo")
    public String formulario(Model model) {
        model.addAttribute("pedido", new Pedido());
        return "PedidoForm";
    }

    @PostMapping("/salvar")
    public String salvar(@ModelAttribute Pedido pedido) {
        repository.save(pedido);
        return "redirect:/Pedidos";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        Pedido pedido = repository.findById(id).orElse(null);
        model.addAttribute("pedido", pedido);
        return "PedidoForm";
    }

    @GetMapping("/deletar/{id}")
    public String deletar(@PathVariable Long id) {
        repository.deleteById(id);
        return "redirect:/Pedidos";
    }
}