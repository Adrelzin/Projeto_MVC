package com.loja.loja.controller;

import com.loja.loja.model.ItemPedido;
import com.loja.loja.repository.ItemPedidoRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/Itens-Pedido")
public class ItemPedidoViewController {
    
    private final ItemPedidoRepository repository;

    public ItemPedidoViewController(ItemPedidoRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("itensPedido", repository.findAll());
        return "Itens-Pedido";
    }

    @GetMapping("/novo")
    public String formulario(Model model) {
        model.addAttribute("itemPedido", new ItemPedido());
        return "ItemPedidoForm";
    }

    @PostMapping("/salvar")
    public String salvar(@ModelAttribute ItemPedido itemPedido) {
        repository.save(itemPedido);
        return "redirect:/Itens-Pedido";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        ItemPedido itemPedido = repository.findById(id).orElse(null);
        model.addAttribute("itemPedido", itemPedido);
        return "ItemPedidoForm";
    }

    @GetMapping("/deletar/{id}")
    public String deletar(@PathVariable Long id) {
        repository.deleteById(id);
        return "redirect:/Itens-Pedido";
    }
}