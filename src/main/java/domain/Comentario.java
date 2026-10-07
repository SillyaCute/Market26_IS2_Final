package domain;

import java.io.*;
import java.util.Date;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


@SuppressWarnings("serial")
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
public class Comentario implements Serializable {
	@XmlID
	@Id 
	@XmlJavaTypeAdapter(IntegerAdapter.class)
	@GeneratedValue
	private Integer numeroComentario;
	private String asunto;
	private String descripcion;
	private int puntuacion;
	private Date fechaComentario;
	@ManyToOne
	private Seller comentado;
	@ManyToOne
	private Comprador comentador;
	@ManyToOne
	private Sale ventaComentada;
	
	public Comentario(){
		super();
	}
		
	public Comentario(String asunto, String descripcion, int puntuacion, Date fechaComentario, Seller comentado, Comprador comentador, Sale ventaComentada) {
		super();
		
		this.asunto = asunto;
		this.descripcion = descripcion;
		this.puntuacion = puntuacion;
		this.fechaComentario=fechaComentario;

		this.comentado = comentado;
		this.comentador = comentador;
		this.ventaComentada = ventaComentada;
		
	}
	
	public int getNumeroComentario() {
		return this.numeroComentario;
	}
	
	public String getAsunto() {
		return this.asunto;
	}
	
	public void setAsunto(String asunto) {
		this.asunto = asunto;
	}
	
	public String getDescripcion() {
		return this.descripcion;
	}
	
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	public int getPuntuacion() {
		return this.puntuacion;
	}
	
	public void setPuntuacion(int puntuacion) {
		this.puntuacion = puntuacion;
	}
	
	public Date getFechaComentario() {
		return this.fechaComentario;
	}
	
	public void setFechaComentario(Date fechaComentario) {
		this.fechaComentario = fechaComentario;
	}
	
	public Seller getComentado() {
		return this.comentado;
	}
	
	public void setComentado(Seller comentado) {
		this.comentado = comentado;
	}
	
	public Comprador getComentador() {
		return this.comentador;
	}
	
	public void setcomentador(Comprador comentador) {
		this.comentador = comentador;
	}
	
	public Sale getVentaComentada() {
		return ventaComentada;
	}
	
	public void setVentaComentada(Sale ventaComentada) {
		this.ventaComentada = ventaComentada;
	}
	
	public String toString(){
		return numeroComentario+ ";"+ puntuacion +";"+comentado+";"+comentador+";"+ventaComentada;  
	}
}