package com.liviacosta.projeto.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import com.liviacosta.projeto.dto.ProjetoDto;
import com.liviacosta.projeto.exception.BadRequestException;
import com.liviacosta.projeto.model.Pessoa;
import com.liviacosta.projeto.model.Projeto;
import com.liviacosta.projeto.service.PessoaService;
import com.liviacosta.projeto.service.ProjetoService;

import jakarta.validation.Valid;


@Controller
@RequestMapping("projetos")
public class ProjetoController {
    
    private ProjetoService projetoService;
    private PessoaService pessoaService;

    public ProjetoController(ProjetoService projetoService, PessoaService pessoaService) {
        this.projetoService = projetoService;
        this.pessoaService = pessoaService;
    }

    @GetMapping("/list")
    public ModelAndView list() {
        List<ProjetoDto> projetos = projetoService.getAll();
        ModelAndView mv = new ModelAndView("projetos/list");
        mv.addObject("projetos", projetos);
        return mv;
    }

    @GetMapping("/create")
    public ModelAndView viewCreate() {
        ModelAndView mv = new ModelAndView("projetos/create");
        mv.addObject("projeto", new ProjetoDto());
        mv.addObject("gerentes", pessoaService.buscarTodosGerentes());
        return mv;
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public ModelAndView create(@Valid ProjetoDto projetoDto) {
        if (projetoDto.getId() != null) {
            projetoService.edit(projetoDto);
        } else {
            projetoService.save(projetoDto);
        }

        return new ModelAndView("redirect:/projetos/list");
    }

    @GetMapping("/edit/{id}")
    public ModelAndView edit(@PathVariable("id") Long id) {
        ModelAndView mv = new ModelAndView("projetos/create");
        ProjetoDto projetoDto = projetoService.findById(id);
        mv.addObject("projeto", projetoDto);
        mv.addObject("gerentes", pessoaService.buscarTodosGerentes());
        return mv;
    }
    
    @RequestMapping(path = "/salvar", method = RequestMethod.POST)
    public ModelAndView salvar(@Valid ProjetoDto projeto){
        projetoService.save(projeto);
        return new ModelAndView("redirect:/projetos/list");
    }

    @RequestMapping(path = "/excluir/{id}", method = RequestMethod.DELETE)
    public ModelAndView excluir(@PathVariable("id") Long id){
        projetoService.remove(id);
        return new ModelAndView("redirect:/projetos/list");
    }


    @GetMapping("/{id}/membros")
    public ModelAndView viewMembros(@PathVariable("id") Long id) {
        ModelAndView mv = new ModelAndView("membros/list");
        mv.addObject("projeto", projetoService.findById(id));
        mv.addObject("membros", pessoaService.findAllByProjetoId(id));
        return mv;
    }

    @GetMapping("/{id}/membros/create")
    public ModelAndView viewMembrosCreate(@PathVariable("id") Long id) {
        ModelAndView mv = new ModelAndView("membros/create");
        ProjetoDto projetoDto = projetoService.findById(id);
        mv.addObject("projeto", projetoDto);
        mv.addObject("pessoas", pessoaService.buscarTodosFuncionarios());
        return mv;
    }
    
    @RequestMapping(path = "/{id}/membros/salvar/{idPessoa}", method = RequestMethod.POST)
    public ModelAndView salvarMembro(@PathVariable("id") Long id, @PathVariable("idPessoa") Long idPessoa){
        Projeto projeto = projetoService.getById(id);
        Pessoa pessoa = pessoaService.getById(idPessoa);

        boolean existMembro = pessoaService.findAllByProjetoId(id).stream()
            .filter((membro) -> {
                return membro.getProjetos().stream()
                .filter((p) -> p.getId() == id)
                .findAny()
                .isPresent();
            })
            .findAny().isPresent();

        if (existMembro) {
            throw new BadRequestException("Membro já cadastrado no projeto");
        }

        projetoService.saveMembro(projeto, pessoa);
        return new ModelAndView("redirect:/projetos/"+id+"/membros");
    }
	
}
