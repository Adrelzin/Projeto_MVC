package com.loja.loja.controller;

import com.loja.loja.model.Categoria;
import com.loja.loja.repository.CategoriaRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/Categorias")
public class CategoriaViewController {
    
    private final CategoriaRepository repository;

    public CategoriaViewController(CategoriaRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("categorias", repository.findAll());
        return "Categorias";
    }

    @GetMapping("/novo")
    public String formulario(Model model) {
        model.addAttribute("categoria", new Categoria());
        return "CategoriaForm";
    }

    @PostMapping("/salvar")
    public String salvar(@ModelAttribute Categoria categoria) {
        repository.save(categoria);
        return "redirect:/Categorias";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        Categoria categoria = repository.findById(id).orElse(null);
        model.addAttribute("categoria", categoria);
        return "CategoriaForm";
    }

    @GetMapping("/deletar/{id}")
    public String deletar(@PathVariable Long id) {
        repository.deleteById(id);
        return "redirect:/Categorias";
    }
}