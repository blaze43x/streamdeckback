package com.streamdeck.streamdeck.model;

import java.time.LocalDateTime;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

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
@Table(name = "plugin")
public class Plugin {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Integer id;

	@Column(name = "cnombre", length = 50, nullable = false)
	private String cnombre;

	@Column(name = "cruta", columnDefinition = "TEXT", nullable = false)
	private String cruta;

	@Column(name = "cdescripcion", columnDefinition = "TEXT")
	private String cdescripcion;

	@JdbcTypeCode(SqlTypes.JSON)
	@Column(name = "cparametro", columnDefinition = "jsonb")
	private String cparametro;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "idautor", nullable = false)
	private Usuario autor;

	@Column(name = "bactivo", nullable = false)
	private Boolean bactivo;

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
	}

	@PreUpdate
	public void preUpdate() {
		this.dfecupd = LocalDateTime.now();
		if (this.bactivo == null) {
			this.bactivo = Boolean.TRUE;
		}
	}
}
