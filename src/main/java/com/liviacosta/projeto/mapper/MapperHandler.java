package com.liviacosta.projeto.mapper;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.context.annotation.Bean;

import com.liviacosta.projeto.dto.PessoaDto;
import com.liviacosta.projeto.dto.ProjetoDto;
import com.liviacosta.projeto.model.Pessoa;
import com.liviacosta.projeto.model.Projeto;

public class MapperHandler {
    
    @Bean
    public ProjetoDto mapToProjetoDto(Projeto projeto) {
        ProjetoDto projetoDto = ProjetoDto.builder()
            .id(projeto.getId())
            .nome(projeto.getNome())
            .dataInicio(projeto.getDataInicio())
            .dataPrevisao(projeto.getDataPrevisaoFim())
            .dataFim(projeto.getDataFim())
            .descricao(projeto.getDescricao())
            .status(projeto.getStatus())
            .orcamento(BigDecimal.valueOf(projeto.getOrcamento()))
            .risco(projeto.getRisco())
            .gerente(projeto.getGerente().getId())
            .nomeGerente(projeto.getGerente().getNome())
            .membros(new HashSet<>())
            .build();
        
        Set<PessoaDto> pessoasDto = projeto.getMembros().stream().map((pessoa) -> mapToPessoaDto(pessoa)).collect(Collectors.toSet());
        projetoDto.setMembros(pessoasDto);
        return projetoDto;
    }
    
    @Bean
    public Projeto mapToProjetoEntity(ProjetoDto projetoDto) {
        Projeto projeto = Projeto.builder()
            .id(projetoDto.getId())
            .nome(projetoDto.getNome())
            .dataInicio(projetoDto.getDataInicio())
            .dataPrevisaoFim(projetoDto.getDataPrevisao())
            .dataFim(projetoDto.getDataFim())
            .descricao(projetoDto.getDescricao())
            .status(projetoDto.getStatus())
            .orcamento(projetoDto.getOrcamento().doubleValue())
            .risco(projetoDto.getRisco())
            .gerente(Pessoa.builder().id(projetoDto.getGerente()).build())
            .build();
        
        return projeto;
    }

    @Bean
    public Pessoa mapToPessoaEntity(PessoaDto pessoaDto) {
        return Pessoa.builder()
        .id(pessoaDto.getId())
        .nome(pessoaDto.getNome())
        .cpf(pessoaDto.getCpf())
        .dataNascimento(pessoaDto.getDataNascimento())
        .funcionario(pessoaDto.getIsFuncionario())
        .gerente(pessoaDto.getIsGerente())
        .build();
    }

    @Bean
    public PessoaDto mapToPessoaDto(Pessoa pessoa) {
        return PessoaDto.builder()
        .id(pessoa.getId())
        .nome(pessoa.getNome())
        .cpf(pessoa.getCpf())
        .dataNascimento(pessoa.getDataNascimento())
        .isFuncionario(pessoa.isFuncionario())
        .isGerente(pessoa.isGerente())
        .build();
    }
}
