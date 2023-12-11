package com.liviacosta.projeto.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.liviacosta.projeto.dto.PessoaDto;
import com.liviacosta.projeto.service.PessoaService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/pessoas")
public class PessoaController {
	
	@Autowired
	private PessoaService pessoaService;

    @GetMapping("/list")
    public ModelAndView list() {
        List<PessoaDto> pessoas = pessoaService.getAll();
        ModelAndView mv = new ModelAndView("pessoas/list");
        mv.addObject("pessoas", pessoas);
        return mv;
    }

    @GetMapping("/create")
    public ModelAndView getViewCreate() {
        ModelAndView mv = new ModelAndView("pessoas/create");
        mv.addObject("pessoa", new PessoaDto());
        mv.addObject("btn", "Cadastrar");
        mv.addObject("navActive", "Nova Pessoa");
        return mv;
    }

    @GetMapping("/edit/{id}")
    public ModelAndView edit(@PathVariable("id") Long id) {
        ModelAndView mv = new ModelAndView("pessoas/create");
        PessoaDto pessoaDto = pessoaService.findById(id);
        mv.addObject("pessoa", pessoaDto);
        mv.addObject("btn", "Salvar");
        mv.addObject("navActive", "Editar Pessoa");
        return mv;
    }

    @PostMapping("/create")
    public ModelAndView create(@Valid PessoaDto pessoaDto, BindingResult result) {
        if (result.hasErrors()) {
             return new ModelAndView("pessoas/create");
        }
        if (pessoaDto.getId() != null) {
            pessoaService.updatePessoa(pessoaDto, pessoaDto.getId());
        } else {
            pessoaService.savePessoa(pessoaDto);
        }
        return new ModelAndView("redirect:/pessoas/");
    }
}
