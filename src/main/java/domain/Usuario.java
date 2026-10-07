package domain;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlIDREF;

@XmlAccessorType(XmlAccessType.FIELD)
@Entity
@DiscriminatorColumn(name="tipo")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Usuario  implements Serializable {
	
	/**
	 * 
	 */
	protected static final long serialVersionUID = 1L;
	@XmlID
	@Id
	protected String email;
	
	protected String name; 
	protected String contraseña;
	protected String tipo;
	//Unai:
	protected float saldo;
	//
	@OneToMany
	protected List<Reporte> reportes=new ArrayList<Reporte>();
	@OneToMany
	protected List<Comentario> comentarios=new ArrayList<Comentario>();

	public Usuario() {
		super();
	}

	public Usuario(String name, String correo, String contraseña, String tipo, float saldo) {
        super();
        setName(name);
        setEmail(correo);
        setContraseña(contraseña);
        setTipo(tipo);
        setSaldo(saldo);
    }
	
	public Usuario(String name, String correo, String contraseña, String tipo) {
        super();
        setName(name);
        setEmail(correo);
        setContraseña(contraseña);
        setTipo(tipo);
        setSaldo(0);
    }
	
	public Usuario(String name, String correo, String contraseña) {
        super();
        setName(name);
        setEmail(correo);
        setContraseña(contraseña);
    }
	
	public Usuario(String correo, String tipo) {
        super();
        setEmail(correo);
        setTipo(tipo);
    }

	
	public Usuario(String correo) {
        super();
        setEmail(correo);
    }
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getContraseña() {
		return contraseña;
	}

	public void setContraseña(String contraseña) {
		this.contraseña = contraseña;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	public List<Reporte> getReportes() {
		return reportes;
	}
	
	public List<Comentario> getComentarios() {
		return comentarios;
	}
	
	//Unai:
	public void setSaldo(float saldo) {
		this.saldo = saldo;
	}
	public float getSaldo() {
		return saldo;
	}
	//
	
	public String toString(){
		return "correo: "+ this.email + " de " + this.name + " con tipo: " + tipo;
	}
	
	
}
