package com.liviacosta.projeto.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class HomeController {
    
    @GetMapping("/")
    public String home(Model model, HttpServletRequest httpServletRequest) {
        return "redirect:/projetos/list";
    }
    
    @GetMapping("/pessoas")
    public String viewPessoasHome(Model model, HttpServletRequest httpServletRequest) {
        return "redirect:/pessoas/list";
    }
    @GetMapping("/pessoas/")
    public String viewPessoas(Model model, HttpServletRequest httpServletRequest) {
        return "redirect:/pessoas/list";
    }

    @GetMapping("/projetos")
    public String viewProjetosHome(Model model, HttpServletRequest httpServletRequest) {
        return "redirect:/projetos/list";
    }
    @GetMapping("/projetos/")
    public String viewProjetos(Model model, HttpServletRequest httpServletRequest) {
        return "redirect:/projetos/list";
    }
}
