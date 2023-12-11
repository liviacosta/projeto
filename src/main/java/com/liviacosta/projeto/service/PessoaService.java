package com.liviacosta.projeto.service;

import java.util.List;

import com.liviacosta.projeto.dto.PessoaDto;
import com.liviacosta.projeto.model.Pessoa;

public interface PessoaService {
    List<PessoaDto> getAll();
    List<PessoaDto> buscarTodosGerentes();
    List<PessoaDto> buscarTodosFuncionarios();
    PessoaDto findById(Long id);
    Pessoa getById(Long id);
    List<Pessoa> findAllByProjetoId(Long idProjeto);
    PessoaDto updatePessoa(PessoaDto pessoaDto, Long id);
    PessoaDto savePessoa(PessoaDto pessoaDto);
}
