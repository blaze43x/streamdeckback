package com.streamdeck.streamdeck.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tipoaccion")
public class TipoAccion {

	public static final int CAMBIO_ESCENA = 1;
	public static final int ABRIR_APLICACION = 2;
	public static final int ABRIR_PAGINA = 3;
	public static final int REPRODUCIR_SONIDO = 4;
	public static final int COMANDO_CMD = 5;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@Column(name = "cnombre", length = 30)
	private String cnombre;
}
