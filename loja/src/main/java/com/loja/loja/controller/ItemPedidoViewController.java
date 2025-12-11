package com.loja.loja.controller;

import com.loja.loja.model.ItemPedido;
import com.loja.loja.model.Pedido;
import com.loja.loja.repository.ItemPedidoRepository;
import com.loja.loja.repository.PedidoRepository;
import com.loja.loja.repository.ProdutoRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/Itens-Pedido")
public class ItemPedidoViewController {
    
    private final ItemPedidoRepository repository;
    private final PedidoRepository pedidoRepository;
    private final ProdutoRepository produtoRepository;

    public ItemPedidoViewController(ItemPedidoRepository repository, 
                                   PedidoRepository pedidoRepository,
                                   ProdutoRepository produtoRepository) {
        this.repository = repository;
        this.pedidoRepository = pedidoRepository;
        this.produtoRepository = produtoRepository;
    }

    @GetMapping
    public String listar(Model model) {
        List<Pedido> pedidosComItens = pedidoRepository.findAll().stream()
                .filter(pedido -> pedido.getItens() != null && !pedido.getItens().isEmpty())
                .collect(Collectors.toList());
        
        model.addAttribute("pedidosComItens", pedidosComItens);
        return "Itens-Pedido";
    }

    @GetMapping("/novo")
    public String formulario(Model model) {
        model.addAttribute("itemPedido", new ItemPedido());
        model.addAttribute("pedidos", pedidoRepository.findAll());
        model.addAttribute("produtos", produtoRepository.findAll());
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
        model.addAttribute("pedidos", pedidoRepository.findAll());
        model.addAttribute("produtos", produtoRepository.findAll());
        return "ItemPedidoForm";
    }

    @GetMapping("/deletar/{id}")
    public String deletar(@PathVariable Long id) {
        repository.deleteById(id);
        return "redirect:/Itens-Pedido";
    }

    @GetMapping("/pedido/{pedidoId}")
    public String listarPorPedido(@PathVariable Long pedidoId, Model model) {
        Pedido pedido = pedidoRepository.findById(pedidoId).orElse(null);
        List<Pedido> pedidosComItens = pedido != null ? List.of(pedido) : List.of();
        
        model.addAttribute("pedidosComItens", pedidosComItens);
        return "Itens-Pedido";
    }
}
