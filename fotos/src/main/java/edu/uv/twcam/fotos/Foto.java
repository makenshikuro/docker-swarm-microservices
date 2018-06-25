package edu.uv.twcam.fotos;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Foto {

	@Id
	private String id;
	private String user;
	private String titulo;
	private String descripcion;
	private String url;
	
	public Foto() {
		
	}
	
	public Foto(String id, String user, String titulo, String descripcion, String url) {
		super();
		this.id = id;
		this.user = user;
		this.titulo = titulo;
		this.descripcion = descripcion;
		this.url = url;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public String toString() {
		return "Foto [id=" + id + ", user=" + user + ", titulo=" + titulo + ", descripcion=" + descripcion + ", url="
				+ url + "]";
	}
	
	
}
