package com.loja.loja.controller;

import com.loja.loja.model.ItemPedido;
import com.loja.loja.repository.ItemPedidoRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/itens-pedido")
public class ItemPedidoController {

    private final ItemPedidoRepository repository;

    public ItemPedidoController(ItemPedidoRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<ItemPedido> listar() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ItemPedido buscarPorId(@PathVariable Long id) {
        return repository.findById(id).orElse(null);
    }

    @PostMapping
    public ItemPedido criar(@RequestBody ItemPedido itemPedido) {
        return repository.save(itemPedido);
    }

    @PutMapping("/{id}")
    public ItemPedido atualizar(@PathVariable Long id, @RequestBody ItemPedido itemPedido) {
        itemPedido.setId(id);
        return repository.save(itemPedido);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        repository.deleteById(id);
    }
}
