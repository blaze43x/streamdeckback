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
@Table(name = "usuario_plugin")
public class UsuarioPlugin {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Integer id;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "idusuario", nullable = false)
	private Usuario usuario;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "idplugin", nullable = false)
	private Plugin plugin;

	@Column(name = "dfecreg")
	private LocalDateTime dfecreg;

	@Column(name = "dfecupd")
	private LocalDateTime dfecupd;

	@PrePersist
	public void prePersist() {
		this.dfecreg = LocalDateTime.now();
		this.dfecupd = LocalDateTime.now();
	}

	@PreUpdate
	public void preUpdate() {
		this.dfecupd = LocalDateTime.now();
	}
}
