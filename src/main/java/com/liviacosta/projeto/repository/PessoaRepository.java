package com.liviacosta.projeto.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.liviacosta.projeto.model.Pessoa;

public interface PessoaRepository extends JpaRepository<Pessoa, Long>{
    
    List<Pessoa> findByGerente(Boolean isGerente);
    List<Pessoa> findByFuncionario(boolean isFuncionario);
    List<Pessoa> findPessoasByProjetosId(Long idProjeto);
}
