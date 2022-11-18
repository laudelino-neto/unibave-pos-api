package br.com.unibave.unibaveposapi.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Table(name = "transacoes")
@JsonIgnoreProperties(ignoreUnknown = true, value = {"hibernateLazyInitializer", "handler"})
@Data
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Transacao {
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	private Integer id;
	
	@Column(name = "nr_cartao")
	@NotEmpty(message = "O número do cartão é obrigatório")
	@Size(max = 50, message = "O número do cartão não pode ultrapassar 50 caracteres")
	private String numeroDoCartao;
	
	@Column(name = "nome_titular")
	@NotEmpty(message = "O nome do titular do cartão é obrigatório")
	@Size(max = 150, message = "O nome do titular não pode ultrapassar 150 caracteres") 
	private String nomeDoTitular;
	
	@Column(name = "documento")
	@NotEmpty(message = "O documento é obrigatório")
	@Size(max = 20, message = "O documento não deve possuir mais de 20 caracteres")
	private String documento;
	
	@Column(name = "valor")
	//@Positive(message = "O valor da transação deve ser maior que zero")
	private Integer valor;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_bandeira")
	@NotNull(message = "A bandeira não pode ser nula")
	private Bandeira bandeira;
	
	@Column(name = "nr_telefone")
	@NotEmpty(message = "O número do telefone é obrigatório")
	@Size(min = 14, max = 14, message = "O número do telefone deve possuir 14 caracteres")
	private String numeroDoTelefone;
	
	@Column(name = "id_sms")
	private String idDoSms;

}
