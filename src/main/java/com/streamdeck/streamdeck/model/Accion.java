package com.streamdeck.streamdeck.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "accion")
public class Accion {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idboton", nullable = false)
	private Boton boton;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idtipoaccion", nullable = false)
	private TipoAccion tipoAccion;

	@Column(name = "cclave", length = 30)
	private String cclave;

	@Column(name = "cvalor", columnDefinition = "TEXT")
	private String cvalor;
}
