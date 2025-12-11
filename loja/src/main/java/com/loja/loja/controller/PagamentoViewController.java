package com.loja.loja.controller;

import com.loja.loja.model.Pagamento;
import com.loja.loja.repository.PagamentoRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/Pagamentos")
public class PagamentoViewController {
    
    private final PagamentoRepository repository;

    public PagamentoViewController(PagamentoRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("pagamentos", repository.findAll());
        return "Pagamentos";
    }

    @GetMapping("/novo")
    public String formulario(Model model) {
        model.addAttribute("pagamento", new Pagamento());
        return "PagamentoForm";
    }

    @PostMapping("/salvar")
    public String salvar(@ModelAttribute Pagamento pagamento) {
        repository.save(pagamento);
        return "redirect:/Pagamentos";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        Pagamento pagamento = repository.findById(id).orElse(null);
        model.addAttribute("pagamento", pagamento);
        return "PagamentoForm";
    }

    @GetMapping("/deletar/{id}")
    public String deletar(@PathVariable Long id) {
        repository.deleteById(id);
        return "redirect:/Pagamentos";
    }
}
