package com.liviacosta.projeto.service;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import com.liviacosta.projeto.dto.ProjetoDto;
import com.liviacosta.projeto.enums.RiscoProjeto;
import com.liviacosta.projeto.enums.StatusProjeto;
import com.liviacosta.projeto.exception.NotFoundException;
import com.liviacosta.projeto.mapper.MapperHandler;
import com.liviacosta.projeto.model.Pessoa;
import com.liviacosta.projeto.model.Projeto;
import com.liviacosta.projeto.repository.PessoaRepository;
import com.liviacosta.projeto.repository.ProjetoRepository;
import com.liviacosta.projeto.service.impl.ProjetoServiceImpl;


@ExtendWith(MockitoExtension.class)
public class ProjetoServiceTests {
    @Mock
    private PessoaRepository pessoaRepository;

    @Mock
    private ProjetoRepository projetoRepository;

    @InjectMocks
    private ProjetoServiceImpl projetoService;

    @Captor
    ArgumentCaptor<Projeto> projetoCaptor;

    @Autowired
    private MapperHandler mapper = new MapperHandler();

    @Test
    public void salvarProjetoRetornarProjetoDto() {
        //Arrange
        Pessoa gerente = Pessoa.builder()
                .id(1L)
                .nome("Maria Alice")
                .cpf("111.111.111-11")
                .gerente(true)
                .funcionario(false).build();
        
        Projeto projeto = Projeto.builder()
                .id(1L)
                .nome("Projeto Java")
                .descricao("Descrição do Projeto Java")
                .gerente(gerente)
                .orcamento(Double.parseDouble("1254"))
                .dataInicio(getNewDate("2023-01-10"))
                .dataPrevisaoFim(getNewDate("2024-01-10"))
                .dataFim(getNewDate("2024-01-10"))
                .status(StatusProjeto.EM_ANALISE)
                .risco(RiscoProjeto.ALTO_RISCO)
                .dataInicio(null).build();
        projeto.setMembros(new HashSet<>());
        
        ProjetoDto projetoDto = mapper.mapToProjetoDto(projeto);
        //Act
        when(projetoRepository.save(Mockito.any(Projeto.class))).thenReturn(projeto);

        ProjetoDto savedProjetoDto = projetoService.save(projetoDto);

        Assertions.assertThat(savedProjetoDto).isNotNull();
        Assertions.assertThat(savedProjetoDto.getId()).isEqualTo(1L);
        Assertions.assertThat(savedProjetoDto.getNome()).isEqualTo(projeto.getNome());
    }

    @Test
    public void editarProjetoRetornarProjetoDto() {
        Long idProjeto = 1L;
        Pessoa gerente = Pessoa.builder()
                .id(1L)
                .nome("Maria Alice")
                .cpf("111.111.111-11")
                .gerente(true)
                .funcionario(false).build();
        
        Projeto projeto = Projeto.builder()
                .id(idProjeto)
                .nome("Projeto Java")
                .descricao("Descrição do Projeto Java")
                .gerente(gerente)
                .orcamento(Double.parseDouble("100"))
                .dataInicio(getNewDate("2023-01-10"))
                .dataPrevisaoFim(getNewDate("2024-01-10"))
                .dataFim(getNewDate("2024-01-10"))
                .status(StatusProjeto.EM_ANALISE)
                .risco(RiscoProjeto.ALTO_RISCO)
                .dataInicio(null).build();
        projeto.setMembros(new HashSet<>());

        when(projetoRepository.findById(idProjeto)).thenReturn(Optional.ofNullable(projeto));
        when(projetoRepository.save(Mockito.any(Projeto.class))).thenReturn(projeto);

        ProjetoDto projetoDto = mapper.mapToProjetoDto(projeto);
        ProjetoDto updatedProjetoDto = projetoService.edit(projetoDto);

        Assertions.assertThat(updatedProjetoDto).isNotNull();
        Assertions.assertThat(updatedProjetoDto.getId()).isEqualTo(projeto.getId());
        Assertions.assertThat(updatedProjetoDto.getNome()).isEqualTo(projeto.getNome());
    }

    @Test
    public void removerProjetoRetornaVoid() {
        Long idProjeto = 1L;
        Pessoa gerente = Pessoa.builder()
                .id(1L)
                .nome("Maria Alice")
                .cpf("111.111.111-11")
                .gerente(true)
                .funcionario(false).build();
        
        Projeto projeto = Projeto.builder()
                .id(idProjeto)
                .nome("Projeto Java")
                .descricao("Descrição do Projeto Java")
                .gerente(gerente)
                .orcamento(Double.parseDouble("100"))
                .dataInicio(getNewDate("2023-01-10"))
                .dataPrevisaoFim(getNewDate("2024-01-10"))
                .dataFim(getNewDate("2024-01-10"))
                .status(StatusProjeto.EM_ANALISE)
                .risco(RiscoProjeto.ALTO_RISCO)
                .dataInicio(null).build();
        projeto.setMembros(new HashSet<>());

        when(projetoRepository.findById(idProjeto)).thenReturn(Optional.ofNullable(projeto));
        doNothing().when(projetoRepository).delete(projeto);

        assertAll(() -> projetoService.remove(idProjeto));
    }

    @Test
    public void removerProjetoRetornaException() {
        Long idProjeto = 1L;
        when(projetoRepository.findById(idProjeto)).thenReturn(Optional.empty());

        NotFoundException notFoundException = assertThrows(NotFoundException.class, () -> {
            projetoService.remove(idProjeto);
        });

        assertThat(notFoundException.getMessage()).isEqualTo("Projeto %d não encontrado!".formatted(idProjeto));
    }

    private Date getNewDate(String dateString) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return dateFormat.parse(dateString);
        } catch (ParseException e) {
            // skip
            return new Date();
        }
    }
}
