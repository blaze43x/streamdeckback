package com.streamdeck.streamdeck.model;

import java.time.LocalDateTime;

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
@Table(name = "usuario")
public class Usuario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@Column(name = "cnombre", length = 100)
	private String cnombre;

	@Column(name = "ccorreo", length = 150)
	private String ccorreo;

	@Column(name = "dfecreg")
	private LocalDateTime dfecreg;

	@Column(name = "dfecupd")
	private LocalDateTime dfecupd;
}
