package com.liviacosta.projeto.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.liviacosta.projeto.model.Projeto;

public interface ProjetoRepository extends JpaRepository<Projeto, Long>{
    
    List<Projeto> findByNomeContaining(String query);
}