package com.streamdeck.streamdeck.model;

import java.time.LocalDateTime;

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
@Table(name = "boton")
public class Boton {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@Column(name = "cnombre", length = 15)
	private String cnombre;

	@Column(name = "ccolor", length = 7)
	private String ccolor;

	@Column(name = "cicono", columnDefinition = "TEXT")
	private String cicono;

	@Column(name = "bactivo")
	private Boolean bactivo;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idtipoaccion")
	private TipoAccion tipoAccion;

	@Column(name = "caccion", columnDefinition = "TEXT")
	private String caccion;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idperfil", nullable = false)
	private Perfil perfil;

	@Column(name = "dfecreg")
	private LocalDateTime dfecreg;

	@Column(name = "dfecupd")
	private LocalDateTime dfecupd;
}
