package com.liviacosta.projeto.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.format.annotation.NumberFormat.Style;

import com.liviacosta.projeto.enums.RiscoProjeto;
import com.liviacosta.projeto.enums.StatusProjeto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProjetoDto {
	
	private Long id;

	@NotEmpty(message = "O nome não pode ser vazio.")
	@Size(max = 200, message = "O tamanho máximo do nome do projeto é de 200 caracteres.")
	private String nome;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date dataInicio;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date dataPrevisao;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date dataFim;
	
	@Size(max = 5000, message = "O tamanho máximo da descrição é de 5000 caracteres.")
	private String descricao;
	
	private StatusProjeto status;
	
	@NumberFormat(style=Style.CURRENCY, pattern = "#,###,##0.00")
	private BigDecimal orcamento;
	
	private RiscoProjeto risco;
	
	private Long gerente;

	private String nomeGerente;
	
	private Set<PessoaDto> membros;


    public boolean canDelete() {
        final List<StatusProjeto> statusCanDelete = new ArrayList<>();
        statusCanDelete.add(StatusProjeto.INICIADO);
        statusCanDelete.add(StatusProjeto.EM_ANDAMENTO);
        statusCanDelete.add(StatusProjeto.ENCERRADO);
        return !statusCanDelete.contains(this.status);
    }
	
}