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
public class Administrador extends Usuario {
	
	/**
	 * 
	 */
	protected static final long serialVersionUID = 1L;
	protected final String CONTRASEÑA_MAESTRA = "vaca";

	public Administrador() {
		super();
	}

	public Administrador(String name, String correo, String contraseña, String tipo) {
        super();
        setName(name);
        setEmail(correo);
        setContraseña(contraseña);
        setTipo(tipo);
    }
	
	public Administrador(String name, String correo, String contraseña) {
        super();
        setName(name);
        setEmail(correo);
        setContraseña(contraseña);
    }
	
	public Administrador(String correo, String tipo) {
        super();
        setEmail(correo);
        setTipo(tipo);
    }

	
	public Administrador(String correo) {
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
	
	public String getContraseñaMaestra() {
		return CONTRASEÑA_MAESTRA;
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
	
	public String toString(){
		return "correo: "+ this.email + " de " + this.name + " con tipo: " + tipo;
	}
	
	
}
