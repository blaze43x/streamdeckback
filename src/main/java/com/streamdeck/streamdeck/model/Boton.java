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
@Table(name = "boton")
public class Boton {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Integer id;

	@Column(name = "cnombre", length = 15, nullable = false)
	private String cnombre;

	@Column(name = "ccolor", length = 7, nullable = false)
	private String ccolor;

	@Column(name = "cicono", columnDefinition = "TEXT", nullable = false)
	private String cicono;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "idperfil", nullable = false)
	private Perfil perfil;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "idusuarioplugin")
	private UsuarioPlugin usuarioPlugin;

	@JdbcTypeCode(SqlTypes.JSON)
	@Column(name = "cparametro", columnDefinition = "jsonb")
	private String cparametro;

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
