package com.liviacosta.projeto.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.liviacosta.projeto.enums.RiscoProjeto;
import com.liviacosta.projeto.enums.StatusProjeto;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@Entity
@Table(name = "projeto")
public class Projeto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome", nullable = false, length = 200)
    private String nome;

    @Column(name = "data_inicio")
    private Date dataInicio;

    @Column(name = "data_previsao_fim")
    private Date dataPrevisaoFim;

    @Column(name = "data_fim")
    private Date dataFim;

    @Column(name = "descricao", length = 5000)
    private String descricao;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 45)
    private StatusProjeto status;

    @Column(name = "orcamento")
    private Double orcamento;

    @Enumerated(EnumType.STRING)
    @Column(name = "risco", length = 45)
    private RiscoProjeto risco;

    @OneToOne
    @JoinColumn(name = "idgerente", nullable = false)
    private Pessoa gerente;


    @ManyToMany(fetch = FetchType.LAZY,
    cascade = {
        CascadeType.PERSIST,
        CascadeType.MERGE
    })
    @JoinTable(name = "membros",
            joinColumns = {
                    @JoinColumn(name = "idprojeto", referencedColumnName = "id",
                            nullable = false, updatable = false)},
            inverseJoinColumns = {
                    @JoinColumn(name = "idpessoa", referencedColumnName = "id",
                            nullable = false, updatable = false)})
    private Set<Pessoa> membros = new HashSet<>();
    
    public Projeto(Long id, String nome, Date dataInicio, Date dataPrevisaoFim, Date dataFim, String descricao,
            StatusProjeto status, Double orcamento, RiscoProjeto risco, Pessoa gerente, Set<Pessoa> membros) {
        this.id = id;
        this.nome = nome;
        this.dataInicio = dataInicio;
        this.dataPrevisaoFim = dataPrevisaoFim;
        this.dataFim = dataFim;
        this.descricao = descricao;
        this.status = status;
        this.orcamento = orcamento;
        this.risco = risco;
        this.gerente = gerente;
        this.membros = membros;
    }

    public boolean canDelete() {
        final List<StatusProjeto> statusCanDelete = new ArrayList<>();
        statusCanDelete.add(StatusProjeto.INICIADO);
        statusCanDelete.add(StatusProjeto.EM_ANDAMENTO);
        statusCanDelete.add(StatusProjeto.ENCERRADO);
        return !statusCanDelete.contains(this.status);
    }

    public void addMembro(Pessoa pessoa) {
        this.membros.add(pessoa);
    }
    
    public void removeMembro(long idPessoa) {
        Pessoa membro = this.membros.stream().filter(t -> t.getId() == idPessoa).findFirst().orElse(null);
        if (membro != null) {
            this.membros.remove(membro);
            membro.getProjetos().remove(this);
        }
    }
}
