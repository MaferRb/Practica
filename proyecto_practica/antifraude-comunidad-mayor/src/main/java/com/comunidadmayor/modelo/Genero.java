package com.comunidadmayor.modelo;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Genero {

	@Id
	@Column(name = "id_genero")
	private Integer id;

	private String titulo;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public Genero(Integer id, String titulo) {
		super();
		this.id = id;
		this.titulo = titulo;
	}

	public Genero() {
		super();
	}

	public Genero(String titulo) {
		super();
		this.titulo = titulo;
	}

	public Genero(Integer id) {
		super();
		this.id = id;
	}

	public void setSinopsis(String sinopsis) {
		// TODO Auto-generated method stub
		
	}

	public void setFechaEstreno(LocalDate fechaEstreno) {
		// TODO Auto-generated method stub
		
	}

	public void setYoutubeAudioId(String youtubeAudioId) {
		// TODO Auto-generated method stub
		
	}

	public void setGeneros(List<Genero> generos) {
		// TODO Auto-generated method stub
		
	}

	public String getRutaPortada() {
		// TODO Auto-generated method stub
		return null;
	}

}
