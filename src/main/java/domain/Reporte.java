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
public class Reporte implements Serializable {
	@XmlID
	@Id 
	@XmlJavaTypeAdapter(IntegerAdapter.class)
	@GeneratedValue
	private Integer numeroReporte;
	private String descripcion;
	private Date fechaReporte;
	@ManyToOne
	private Usuario reportado;
	@ManyToOne
	private Usuario reportante;
	
	public Reporte(){
		super();
	}
		
	public Reporte(String descripcion, Date fechaReporte, Usuario reportado, Usuario reportante) {
		super();

		this.descripcion = descripcion;
		this.fechaReporte=fechaReporte;

		this.reportado = reportado;
		this.reportante = reportante;
		
	}
	
	public int getNumeroReporte() {
		return this.numeroReporte;
	}
	
	public String getDescripcion() {
		return this.descripcion;
	}
	
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	public Date getFechaReporte() {
		return this.fechaReporte;
	}
	
	public void setFechaReporte(Date fechaReporte) {
		this.fechaReporte = fechaReporte;
	}
	
	public Usuario getReportado() {
		return this.reportado;
	}
	
	public void setReportado(Usuario reportado) {
		this.reportado = reportado;
	}
	
	public Usuario getReportante() {
		return this.reportante;
	}
	
	public void setReportante(Usuario reportante) {
		this.reportante = reportante;
	}
	
	public String toString(){
		return numeroReporte +";"+reportado+";"+reportante;  
	}
}