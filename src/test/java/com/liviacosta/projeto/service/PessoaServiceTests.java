package com.liviacosta.projeto.service;

import static org.mockito.Mockito.*;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import com.liviacosta.projeto.dto.PessoaDto;
import com.liviacosta.projeto.mapper.MapperHandler;
import com.liviacosta.projeto.model.Pessoa;
import com.liviacosta.projeto.repository.PessoaRepository;
import com.liviacosta.projeto.service.impl.PessoaServiceImpl;


@ExtendWith(MockitoExtension.class)
public class PessoaServiceTests {
    
    @Mock
    private PessoaRepository pessoaRepository;

    @InjectMocks
    private PessoaServiceImpl pessoaService;

    @Autowired
    private MapperHandler mapper = new MapperHandler();

    @Test
    public void salvarPessoaRetornarPessoaDto() {
        //Arrange
        Pessoa pessoa = Pessoa.builder()
            .id(1L)
            .nome("Maria Alice")
            .cpf("111.111.111-11")
            .gerente(true)
            .funcionario(false).build();

        PessoaDto pessoaDto = mapper.mapToPessoaDto(pessoa);

        //Act
        when(pessoaRepository.save(Mockito.any(Pessoa.class))).thenReturn(pessoa);

        PessoaDto savedPessoa = pessoaService.savePessoa(pessoaDto);

        Assertions.assertThat(savedPessoa).isNotNull();
        Assertions.assertThat(savedPessoa.getId()).isEqualTo(1L);
        Assertions.assertThat(savedPessoa.getIsGerente()).isEqualTo(true);
    }

    @Test
    public void editarPessoaRetornarPessoaDto() {
        Long idPessoa = 1L;
        Pessoa pessoa = Pessoa.builder().id(idPessoa).nome("Maria Maria").gerente(true).build();
        PessoaDto pessoaDto = PessoaDto.builder().id(idPessoa).nome("Maria Maria").isGerente(true).build();

        when(pessoaRepository.findById(idPessoa)).thenReturn(Optional.ofNullable(pessoa));
        when(pessoaRepository.save(pessoa)).thenReturn(pessoa);

        PessoaDto updateReturn = pessoaService.updatePessoa(pessoaDto, idPessoa);

        Assertions.assertThat(updateReturn).isNotNull();

    }

}
