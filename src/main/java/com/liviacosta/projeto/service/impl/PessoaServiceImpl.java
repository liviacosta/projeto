package com.liviacosta.projeto.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.liviacosta.projeto.dto.PessoaDto;
import com.liviacosta.projeto.exception.NotFoundException;
import com.liviacosta.projeto.mapper.MapperHandler;
import com.liviacosta.projeto.model.Pessoa;
import com.liviacosta.projeto.repository.PessoaRepository;
import com.liviacosta.projeto.service.PessoaService;

@Service
public class PessoaServiceImpl implements PessoaService {
    
    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private MapperHandler mapper = new MapperHandler();
	
    public List<PessoaDto> getAll() {
        List<Pessoa> pessoas = pessoaRepository.findAll();
        return pessoas.stream().map((pessoa) -> mapper.mapToPessoaDto(pessoa)).collect(Collectors.toList());

	}

    public List<PessoaDto> buscarTodosGerentes() {
        List<Pessoa> pessoas = pessoaRepository.findByGerente(true);
        return pessoas.stream().map((pessoa) -> mapper.mapToPessoaDto(pessoa)).collect(Collectors.toList());
	}

    public List<PessoaDto> buscarTodosFuncionarios() {
        List<Pessoa> pessoas = pessoaRepository.findByFuncionario(true);
        return pessoas.stream().map((pessoa) -> mapper.mapToPessoaDto(pessoa)).collect(Collectors.toList());
	}

    @Override
    public PessoaDto findById(Long id) {
        Pessoa pessoa = pessoaRepository.findById(id).orElseThrow(
            () -> new NotFoundException("Pessoa %d não encontrada!".formatted(id)));
        return mapper.mapToPessoaDto(pessoa);
    }

    @Override
    public PessoaDto updatePessoa(PessoaDto pessoaDto, Long id) {
        Pessoa pessoa = pessoaRepository.findById(id).orElseThrow(
            () -> new NotFoundException("Pessoa %d não encontrada!".formatted(id)));
        
        pessoa = mapper.mapToPessoaEntity(pessoaDto);
        Pessoa savedPessoa = pessoaRepository.save(pessoa);
        return mapper.mapToPessoaDto(savedPessoa);
    }

    @Override
    public PessoaDto savePessoa(PessoaDto pessoaDto) {
        Pessoa pessoa = mapper.mapToPessoaEntity(pessoaDto);
        Pessoa savedPessoa = pessoaRepository.save(pessoa);
        return mapper.mapToPessoaDto(savedPessoa);
    }

    @Override
    public Pessoa getById(Long id) {
        Pessoa pessoa = pessoaRepository.findById(id).orElseThrow(
            () -> new NotFoundException("Pessoa %d não encontrada!".formatted(id)));
        return pessoa;
    }


    @Override
    public List<Pessoa> findAllByProjetoId(Long idProjeto) {
        return pessoaRepository.findPessoasByProjetosId(idProjeto);
    }
}
