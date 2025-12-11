package com.loja.loja.controller;

import com.loja.loja.model.Produto;
import com.loja.loja.repository.ProdutoRepository;
import com.loja.loja.repository.CategoriaRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

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
    public String listar(@RequestParam(required = false) String busca,
                        @RequestParam(required = false) Long categoriaId,
                        Model model) {
        List<Produto> produtos;
        
        if ((busca != null && !busca.trim().isEmpty()) || (categoriaId != null && categoriaId > 0)) {
            produtos = repository.findAll().stream()
                .filter(p -> {
                    boolean matchBusca = true;
                    boolean matchCategoria = true;
                    
                    if (busca != null && !busca.trim().isEmpty()) {
                        matchBusca = p.getNome().toLowerCase().contains(busca.toLowerCase());
                    }
                    
                    if (categoriaId != null && categoriaId > 0) {
                        matchCategoria = p.getCategoria() != null && 
                                       p.getCategoria().getId().equals(categoriaId);
                    }
                    
                    return matchBusca && matchCategoria;
                })
                .collect(Collectors.toList());
        } else {
            produtos = repository.findAll();
        }
        
        model.addAttribute("produtos", produtos);
        model.addAttribute("categorias", categoriaRepository.findAll());
        model.addAttribute("busca", busca);
        model.addAttribute("categoriaId", categoriaId);
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
