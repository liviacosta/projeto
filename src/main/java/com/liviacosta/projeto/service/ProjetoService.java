package com.liviacosta.projeto.service;

import java.util.List;

import com.liviacosta.projeto.dto.ProjetoDto;
import com.liviacosta.projeto.model.Pessoa;
import com.liviacosta.projeto.model.Projeto;

public interface ProjetoService {
    List<ProjetoDto> getAll();
    Projeto getById(Long id);
    ProjetoDto findById(Long id);
    ProjetoDto save(ProjetoDto projetoDto);
    void remove(Long id);
    ProjetoDto edit(ProjetoDto projetoDto);
    void saveMembro(Projeto projeto, Pessoa pessoa);
}
