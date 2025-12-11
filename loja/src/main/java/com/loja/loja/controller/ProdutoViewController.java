package com.loja.loja.controller;

import com.loja.loja.model.Produto;
import com.loja.loja.repository.ProdutoRepository;
import com.loja.loja.repository.CategoriaRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/Produtos")
public class ProdutoViewController {
    
    private final ProdutoRepository repository;
    private final CategoriaRepository categoriaRepository;

    public ProdutoViewController(ProdutoRepository repository, CategoriaRepository categoriaRepository) {
        this.repository = repository;
        this.categoriaRepository = categoriaRepository;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("produtos", repository.findAll());
        return "Produtos";
    }

    @GetMapping("/novo")
    public String formulario(Model model) {
        model.addAttribute("produto", new Produto());
        model.addAttribute("categorias", categoriaRepository.findAll());
        return "ProdutoForm";
    }

    @PostMapping("/salvar")
    public String salvar(@ModelAttribute Produto produto) {
        repository.save(produto);
        return "redirect:/Produtos";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        Produto produto = repository.findById(id).orElse(null);
        model.addAttribute("produto", produto);
        model.addAttribute("categorias", categoriaRepository.findAll());
        return "ProdutoForm";
    }

    @GetMapping("/deletar/{id}")
    public String deletar(@PathVariable Long id) {
        repository.deleteById(id);
        return "redirect:/Produtos";
    }
}