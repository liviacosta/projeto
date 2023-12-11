package com.liviacosta.projeto.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.liviacosta.projeto.dto.ProjetoDto;
import com.liviacosta.projeto.exception.BadRequestException;
import com.liviacosta.projeto.exception.NotFoundException;
import com.liviacosta.projeto.mapper.MapperHandler;
import com.liviacosta.projeto.model.Pessoa;
import com.liviacosta.projeto.model.Projeto;
import com.liviacosta.projeto.repository.ProjetoRepository;
import com.liviacosta.projeto.service.ProjetoService;

@Service
public class ProjetoServiceImpl implements ProjetoService {

    @Autowired
    private ProjetoRepository projetoRepository;
    @Autowired
    private MapperHandler mapper = new MapperHandler();

    @Override
    public List<ProjetoDto> getAll() {
        List<Projeto> projetos = projetoRepository.findAll();
        return projetos.stream().map((projeto) -> mapper.mapToProjetoDto(projeto)).collect(Collectors.toList());
    }

    @Override
    public ProjetoDto findById(Long id) {
        Projeto projeto = projetoRepository.findById(id).orElseThrow(
            () -> new BadRequestException("Projeto %d não encontrado!".formatted(id)));
        return  mapper.mapToProjetoDto(projeto);
    }

    @Override
    public ProjetoDto save(ProjetoDto projetoDto) {
        Projeto projeto = mapper.mapToProjetoEntity(projetoDto);
        Projeto savedProjeto = projetoRepository.save(projeto);
        return mapper.mapToProjetoDto(savedProjeto);
    }

    @Override
    public void remove(Long id) {
        Projeto projeto = projetoRepository.findById(id).orElseThrow(
            () -> new NotFoundException("Projeto %d não encontrado!".formatted(id)));
        if (projeto.canDelete())
            projetoRepository.delete(projeto);
    }

    @Override
    public ProjetoDto edit(ProjetoDto projetoDto) {
        Projeto projeto = projetoRepository.findById(projetoDto.getId()).orElseThrow(
            () -> new NotFoundException("Projeto %d não encontrado!".formatted(projetoDto.getId())));
        projeto = mapper.mapToProjetoEntity(projetoDto);
        Projeto savedProjeto = projetoRepository.save(projeto);
        return mapper.mapToProjetoDto(savedProjeto);
    }

    @Override
    public void saveMembro(Projeto projeto, Pessoa pessoa) {
        projeto.addMembro(pessoa);
        projetoRepository.save(projeto);
    }

    @Override
    public Projeto getById(Long id) {
         Projeto projeto = projetoRepository.findById(id).orElseThrow(
            () -> new NotFoundException("Projeto %d não encontrado!".formatted(id)));
        return projeto;
    }
}
