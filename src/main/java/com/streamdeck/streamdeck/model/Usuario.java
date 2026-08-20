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
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
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
	@Column(name = "id", nullable = false)
	private Integer id;

	@Column(name = "cnombre", length = 100, nullable = false)
	private String cnombre;

	@Column(name = "ccorreo", length = 150, nullable = false)
	private String ccorreo;

	@Column(name = "cpassword", columnDefinition = "TEXT", nullable = false)
	private String cpassword;

	@Column(name = "bactivo", nullable = false)
	private Boolean bactivo;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "idrol", nullable = false)
	private Rol rol;

	@Column(name = "dfecreg")
	private LocalDateTime dfecreg;

	@Column(name = "dfecupd")
	private LocalDateTime dfecupd;

	@PrePersist
	public void prePersist() {
		this.dfecreg = LocalDateTime.now();
		this.dfecupd = LocalDateTime.now();

		if (this.bactivo == null) {
			this.bactivo = Boolean.TRUE;
		}
		
		if (this.rol == null) {
			this.rol = new Rol(Rol.USUARIO);
		}
	}

	@PreUpdate
	public void preUpdate() {
		this.dfecupd = LocalDateTime.now();

		if (this.bactivo == null) {
			this.bactivo = Boolean.TRUE;
		}

		if (this.rol == null) {
			this.rol = new Rol(Rol.USUARIO);
		}
	}
}
