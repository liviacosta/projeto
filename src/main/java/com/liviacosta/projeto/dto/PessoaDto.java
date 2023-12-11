package com.liviacosta.projeto.dto;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

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
public class PessoaDto {
	
	private Long id;
	
	@NotEmpty(message = "O nome não pode ser vazio.")
	@Size(max = 100, message = "O tamanho máximo do nome do projeto é de 100 caracteres.")
	private String nome;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date dataNascimento;
	
	@Size(min = 14, max = 14)
	private String cpf;
	
	@Builder.Default
	private Boolean isFuncionario = false;

	@Builder.Default
	private Boolean isGerente = false;
}