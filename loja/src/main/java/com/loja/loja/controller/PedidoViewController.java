package com.loja.loja.controller;

import com.loja.loja.model.*;
import com.loja.loja.repository.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/Pedidos")
public class PedidoViewController {
    
    private final PedidoRepository pedidoRepository;
    private final ClienteRepository clienteRepository;
    private final ProdutoRepository produtoRepository;
    private final ItemPedidoRepository itemPedidoRepository;
    private final PagamentoRepository pagamentoRepository;

    public PedidoViewController(PedidoRepository pedidoRepository, 
                               ClienteRepository clienteRepository,
                               ProdutoRepository produtoRepository,
                               ItemPedidoRepository itemPedidoRepository,
                               PagamentoRepository pagamentoRepository) {
        this.pedidoRepository = pedidoRepository;
        this.clienteRepository = clienteRepository;
        this.produtoRepository = produtoRepository;
        this.itemPedidoRepository = itemPedidoRepository;
        this.pagamentoRepository = pagamentoRepository;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("pedidos", pedidoRepository.findAll());
        return "Pedidos";
    }

    @GetMapping("/novo")
    public String formulario(Model model) {
        Pedido pedido = new Pedido();
        pedido.setData(LocalDateTime.now());
        pedido.setStatus("PENDENTE");
        
        model.addAttribute("pedido", pedido);
        model.addAttribute("clientes", clienteRepository.findAll());
        model.addAttribute("produtos", produtoRepository.findAll());
        return "PedidoForm";
    }

    @PostMapping("/salvar")
    public String salvar(@RequestParam Long clienteId,
                        @RequestParam String status,
                        @RequestParam String tipoPagamento,
                        @RequestParam String statusPagamento,
                        @RequestParam(required = false) List<Long> produtoIds,
                        @RequestParam(required = false) List<Integer> quantidades) {
        
        Cliente cliente = clienteRepository.findById(clienteId).orElseThrow();
        
        Pedido pedido = new Pedido();
        pedido.setCliente(cliente);
        pedido.setData(LocalDateTime.now());
        pedido.setStatus(status);
        pedido.setItens(new ArrayList<>());
        
        Double valorTotal = 0.0;
        
        if (produtoIds != null && quantidades != null) {
            for (int i = 0; i < produtoIds.size(); i++) {
                Produto produto = produtoRepository.findById(produtoIds.get(i)).orElseThrow();
                Integer quantidade = quantidades.get(i);
                
                if (quantidade > 0) {
                    ItemPedido item = new ItemPedido();
                    item.setProduto(produto);
                    item.setQuantidade(quantidade);
                    item.setPrecoUnitario((double) produto.getPreco());
                    item.setPedido(pedido);
                    
                    pedido.getItens().add(item);
                    valorTotal += item.getPrecoUnitario() * item.getQuantidade();
                    
                    produto.setEstoque(produto.getEstoque() - quantidade);
                    produtoRepository.save(produto);
                }
            }
        }
        
        pedido.setValorTotal(valorTotal);
        
        Pedido pedidoSalvo = pedidoRepository.save(pedido);
        
        Pagamento pagamento = new Pagamento();
        pagamento.setTipo(tipoPagamento);
        pagamento.setStatus(statusPagamento);
        pagamento.setPedido(pedidoSalvo);
        
        pagamentoRepository.save(pagamento);
        
        pedidoSalvo.setPagamento(pagamento);
        pedidoRepository.save(pedidoSalvo);
        
        return "redirect:/Pedidos";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        Pedido pedido = pedidoRepository.findById(id).orElseThrow();
        model.addAttribute("pedido", pedido);
        model.addAttribute("clientes", clienteRepository.findAll());
        model.addAttribute("produtos", produtoRepository.findAll());
        return "PedidoForm";
    }

    @PostMapping("/atualizar/{id}")
    public String atualizar(@PathVariable Long id,
                           @RequestParam Long clienteId,
                           @RequestParam String status,
                           @RequestParam String tipoPagamento,
                           @RequestParam String statusPagamento,
                           @RequestParam(required = false) List<Long> produtoIds,
                           @RequestParam(required = false) List<Integer> quantidades) {
        
        Pedido pedido = pedidoRepository.findById(id).orElseThrow();
        
        for (ItemPedido item : pedido.getItens()) {
            Produto produto = item.getProduto();
            produto.setEstoque(produto.getEstoque() + item.getQuantidade());
            produtoRepository.save(produto);
        }
        
        itemPedidoRepository.deleteAll(pedido.getItens());
        pedido.getItens().clear();
        
        Cliente cliente = clienteRepository.findById(clienteId).orElseThrow();
        pedido.setCliente(cliente);
        pedido.setStatus(status);
        
        Double valorTotal = 0.0;
        
        if (produtoIds != null && quantidades != null) {
            for (int i = 0; i < produtoIds.size(); i++) {
                Produto produto = produtoRepository.findById(produtoIds.get(i)).orElseThrow();
                Integer quantidade = quantidades.get(i);
                
                if (quantidade > 0) {
                    ItemPedido item = new ItemPedido();
                    item.setProduto(produto);
                    item.setQuantidade(quantidade);
                    item.setPrecoUnitario((double) produto.getPreco());
                    item.setPedido(pedido);
                    
                    pedido.getItens().add(item);
                    valorTotal += item.getPrecoUnitario() * item.getQuantidade();
                    
                    produto.setEstoque(produto.getEstoque() - quantidade);
                    produtoRepository.save(produto);
                }
            }
        }
        
        pedido.setValorTotal(valorTotal);
        
        if (pedido.getPagamento() == null) {
            Pagamento pagamento = new Pagamento();
            pagamento.setTipo(tipoPagamento);
            pagamento.setStatus(statusPagamento);
            pagamento.setPedido(pedido);
            pedido.setPagamento(pagamento);
            
            pedidoRepository.save(pedido);
            pagamentoRepository.save(pagamento);
        } else {
            pedido.getPagamento().setTipo(tipoPagamento);
            pedido.getPagamento().setStatus(statusPagamento);
            pagamentoRepository.save(pedido.getPagamento());
            pedidoRepository.save(pedido);
        }
        
        return "redirect:/Pedidos";
    }

    @GetMapping("/deletar/{id}")
    public String deletar(@PathVariable Long id) {
        Pedido pedido = pedidoRepository.findById(id).orElseThrow();
        
        for (ItemPedido item : pedido.getItens()) {
            Produto produto = item.getProduto();
            produto.setEstoque(produto.getEstoque() + item.getQuantidade());
            produtoRepository.save(produto);
        }
        
        pedidoRepository.deleteById(id);
        return "redirect:/Pedidos";
    }
}
