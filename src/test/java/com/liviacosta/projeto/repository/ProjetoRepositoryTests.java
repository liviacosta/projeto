package com.liviacosta.projeto.repository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.liviacosta.projeto.enums.RiscoProjeto;
import com.liviacosta.projeto.enums.StatusProjeto;
import com.liviacosta.projeto.model.Pessoa;
import com.liviacosta.projeto.model.Projeto;

import jakarta.transaction.Transactional;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class ProjetoRepositoryTests {
    
    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private ProjetoRepository projetoRepository;

    private Date getNewDate(String dateString) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return dateFormat.parse(dateString);
        } catch (ParseException e) {
            // skip
            return new Date();
        }
    }
    
    @Test
    public void deveSalvarProcessoComSucesso() {

        //Arrange
        Pessoa pessoa = Pessoa.builder()
                .nome("Maria Alice")
                .cpf("111.111.111-11")
                .gerente(true)
                .funcionario(false).build();
        Pessoa savedpPessoa = pessoaRepository.save(pessoa);
        
        Projeto projeto = Projeto.builder()
                .nome("Projeto Java")
                .descricao("Descrição do Projeto Java")
                .gerente(savedpPessoa)
                .dataInicio(getNewDate("2023-01-10"))
                .dataPrevisaoFim(getNewDate("2024-01-10"))
                .dataFim(getNewDate("2024-01-10"))
                .status(StatusProjeto.EM_ANALISE)
                .risco(RiscoProjeto.ALTO_RISCO)
                .dataInicio(null).build();
        //Act
        Projeto savedProjeto = projetoRepository.save(projeto);

        //Assert
        Assertions.assertThat(savedProjeto).isNotNull();
        Assertions.assertThat(savedProjeto.getNome()).isEqualTo(projeto.getNome());
    }


    @Test
    @Transactional
    public void deveListarTodosProjetos() {
        //Arrange
        Pessoa gerente = Pessoa.builder()
                .nome("Maria Alice")
                .cpf("111.111.111-11")
                .gerente(true)
                .funcionario(false).build();
        Pessoa savedGerente = pessoaRepository.save(gerente);
        //Arrange
        Pessoa gerente2 = Pessoa.builder()
                .nome("Maria José")
                .cpf("111.111.111-12")
                .gerente(true)
                .funcionario(false).build();
        Pessoa savedGerente2 = pessoaRepository.save(gerente2);
        
        Projeto projeto = Projeto.builder()
                .nome("Projeto Java")
                .descricao("Descrição do Projeto Java")
                .gerente(savedGerente)
                .dataInicio(getNewDate("2023-01-10"))
                .dataPrevisaoFim(getNewDate("2024-01-10"))
                .dataFim(getNewDate("2024-01-10"))
                .status(StatusProjeto.EM_ANALISE)
                .risco(RiscoProjeto.ALTO_RISCO)
                .dataInicio(null).build();
        projetoRepository.save(projeto);

        Projeto projeto2 = Projeto.builder()
                .nome("Projeto Java 2ª fase")
                .descricao("Descrição do Projeto Java 2ª fase")
                .gerente(savedGerente2)
                .dataInicio(getNewDate("2023-01-10"))
                .dataPrevisaoFim(getNewDate("2024-01-10"))
                .dataFim(getNewDate("2024-01-10"))
                .status(StatusProjeto.EM_ANALISE)
                .risco(RiscoProjeto.ALTO_RISCO)
                .dataInicio(null).build();
        projetoRepository.save(projeto2);

        //Act
        List<Projeto> projetos = projetoRepository.findAll();

        Assertions.assertThat(projetos).isNotNull();
        Assertions.assertThat(projetos.size()).isEqualTo(2);
        Assertions.assertThat(projetos.get(0).getNome()).isEqualTo(projeto.getNome());
        Assertions.assertThat(projetos.get(1).getNome()).isEqualTo(projeto2.getNome());
    }


    @Test
    public void deveAlterarProjeto() {
        
        //Arrange
        Pessoa pessoa = Pessoa.builder()
                .nome("Maria Alice")
                .cpf("111.111.111-11")
                .gerente(true)
                .funcionario(false).build();
        Pessoa savedpPessoa = pessoaRepository.save(pessoa);
        
        Projeto projeto = Projeto.builder()
                .nome("Projeto Java")
                .descricao("Descrição do Projeto Java")
                .gerente(savedpPessoa)
                .dataInicio(getNewDate("2023-01-10"))
                .dataPrevisaoFim(getNewDate("2024-01-10"))
                .dataFim(getNewDate("2024-01-10"))
                .status(StatusProjeto.EM_ANALISE)
                .risco(RiscoProjeto.ALTO_RISCO)
                .dataInicio(null).build();
        Projeto savedProjeto = projetoRepository.save(projeto);
        
        savedProjeto.setNome("Projeto Editado");
        Projeto editedProjeto = projetoRepository.save(savedProjeto);

        Assertions.assertThat(editedProjeto).isNotNull();
        Assertions.assertThat(savedProjeto.getId()).isEqualTo(editedProjeto.getId());
        Assertions.assertThat(savedProjeto.getNome()).isEqualTo(editedProjeto.getNome());
    }


    @Test
    public void deveRemoverProjeto() {
        //Arrange
        Pessoa pessoa = Pessoa.builder()
                .nome("Maria Alice")
                .cpf("111.111.111-11")
                .gerente(true)
                .funcionario(false).build();
        Pessoa savedpPessoa = pessoaRepository.save(pessoa);
        
        Projeto projeto = Projeto.builder()
                .nome("Projeto Java")
                .descricao("Descrição do Projeto Java")
                .gerente(savedpPessoa)
                .dataInicio(getNewDate("2023-01-10"))
                .dataPrevisaoFim(getNewDate("2024-01-10"))
                .dataFim(getNewDate("2024-01-10"))
                .status(StatusProjeto.EM_ANALISE)
                .risco(RiscoProjeto.ALTO_RISCO)
                .dataInicio(null).build();
        Projeto savedProjeto = projetoRepository.save(projeto);
        
        projetoRepository.delete(savedProjeto);

        Optional<Projeto> projetoDeletado = projetoRepository.findById(savedProjeto.getId());

        Assertions.assertThat(projetoDeletado.isPresent()).isEqualTo(false);
    }
}
