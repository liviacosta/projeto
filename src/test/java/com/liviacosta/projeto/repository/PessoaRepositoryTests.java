package com.liviacosta.projeto.repository;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.liviacosta.projeto.model.Pessoa;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class PessoaRepositoryTests {
    
    @Autowired
    private PessoaRepository pessoaRepository;

    @Test
    public void deveSalvarPessoaComSucesso() {

        //Arrange
        Pessoa pessoa = Pessoa.builder()
                .nome("Maria Alice")
                .cpf("111.111.111-11")
                .gerente(true)
                .funcionario(false).build();

        //Act
        Pessoa savedPessoa = pessoaRepository.save(pessoa);

        //Assert
        Assertions.assertThat(savedPessoa).isNotNull();
        Assertions.assertThat(savedPessoa.isGerente()).isEqualTo(true);
    }


    @Test
    public void deveListarTodosOsGerentes() {
        //Arrange
        Pessoa gerente = Pessoa.builder()
                .nome("Maria Alice")
                .cpf("111.111.111-11")
                .gerente(true)
                .funcionario(false).build();
        
        Pessoa funcionario = Pessoa.builder()
                .nome("José Maria")
                .cpf("111.111.111-12")
                .gerente(false)
                .funcionario(true).build();

        pessoaRepository.save(gerente);
        pessoaRepository.save(funcionario);
        List<Pessoa> gerenteList = pessoaRepository.findByGerente(true);

        Assertions.assertThat(gerenteList).isNotNull();
        Assertions.assertThat(gerenteList.size()).isEqualTo(1);
        Assertions.assertThat(gerenteList.get(0).getNome()).isEqualTo(gerente.getNome());
    }

    @Test
    public void deveListarTodosOsFuncionarios() {
        //Arrange
        Pessoa gerente = Pessoa.builder()
                .nome("Maria Alice")
                .cpf("111.111.111-11")
                .gerente(true)
                .funcionario(false).build();
        
        Pessoa funcionario = Pessoa.builder()
                .nome("José Maria")
                .cpf("111.111.111-12")
                .gerente(false)
                .funcionario(true).build();

        pessoaRepository.save(gerente);
        pessoaRepository.save(funcionario);
        List<Pessoa> pessoas = pessoaRepository.findByFuncionario(true);

        Assertions.assertThat(pessoas).isNotNull();
        Assertions.assertThat(pessoas.size()).isEqualTo(1);
        Assertions.assertThat(pessoas.get(0).getNome()).isEqualTo(funcionario.getNome());
    }


    @Test
    public void deveEncontrarPessoaPorId() {
        //Arrange
        Pessoa pessoa = Pessoa.builder()
                .nome("Maria Alice")
                .cpf("111.111.111-11")
                .gerente(true)
                .funcionario(false).build();

        //Act
        Pessoa savedPessoa = pessoaRepository.save(pessoa);

        Pessoa pessoaById = pessoaRepository.findById(savedPessoa.getId()).get();

        Assertions.assertThat(pessoaById).isNotNull();
        Assertions.assertThat(pessoaById.getId()).isEqualTo(savedPessoa.getId());
    }

}
