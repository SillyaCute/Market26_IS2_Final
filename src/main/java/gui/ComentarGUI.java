package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;
import javax.swing.*;
import businessLogic.BLFacade;
import domain.Comprador;
import domain.Sale;
import domain.Seller;
import domain.Usuario;

public class ComentarGUI extends JFrame {
	private JLabel lblAsunto;
	private JLabel lblComentario;
	private JLabel lblMostrarError;
	private JLabel lblMostrarExito;
	private JLabel lblPuntuacion;
	
	private JTextField textFieldAsunto;
	private JTextField textFieldComentario;
	private JTextField textFieldPuntuacion;
	
	private JButton buttonRealizarComentario;
	
	public ComentarGUI(Sale ventaComentar, Comprador comprador) {
		getContentPane().setLayout(null);
		this.setSize(new Dimension(450, 350));
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("ComentarGUI.Titulo"));
		
		//Labels
		lblAsunto = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("ComentarGUI.Asunto"));
		lblAsunto.setBounds(20, 30, 140, 24);
		
		lblComentario = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("ComentarGUI.Comentario"));
		lblComentario.setBounds(20, 99, 140, 24);
		
		lblPuntuacion = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("ComentarGUI.Puntuacion"));
		lblPuntuacion.setBounds(20, 65, 140, 24);
		
		lblMostrarError = new JLabel();
		lblMostrarError.setForeground(Color.RED);
		lblMostrarError.setBounds(45, 253, 337, 20);
		
		lblMostrarExito = new JLabel();
		lblMostrarExito.setForeground(Color.GREEN);
		lblMostrarExito.setBounds(45, 253, 337, 20);
		
		//Text Fields
		textFieldAsunto = new JTextField();
		textFieldAsunto.setBounds(170, 30, 228, 24);
		textFieldAsunto.setColumns(10);
		
		textFieldComentario = new JTextField();
		textFieldComentario.setBounds(20, 133, 377, 110);
		textFieldComentario.setColumns(10);
		
		textFieldPuntuacion = new JTextField();
		textFieldPuntuacion.setColumns(10);
		textFieldPuntuacion.setBounds(170, 65, 116, 24);
		
		buttonRealizarComentario = new JButton(ResourceBundle.getBundle("Etiquetas").getString("ComentarGUI.RealizarComentario"));
		buttonRealizarComentario.setBounds(114, 283, 200, 20);
		buttonRealizarComentario.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BLFacade facade = MainGUI.getBusinessLogic();
				String error = checkFieldsErrors();
				
				if(error != null) {
					lblMostrarError.setText(error);
				}else {
					lblMostrarError.setText(null);
					facade.crearComentario(textFieldAsunto.getText(), textFieldComentario.getText(), Integer.parseInt(textFieldPuntuacion.getText()), ventaComentar, comprador.getEmail());
					lblMostrarExito.setText(ResourceBundle.getBundle("Etiquetas").getString("ComentarGUI.Exito"));
					
					new Timer(3000, new ActionListener() {
					    @Override
					    public void actionPerformed(ActionEvent e) {
					        dispose();
					    }
					}) {{
					    setRepeats(false);
					}}.start();
				}
			}
		});
		
		getContentPane().add(lblAsunto);
		getContentPane().add(lblComentario);
		getContentPane().add(lblPuntuacion);
		getContentPane().add(lblMostrarError);
		getContentPane().add(lblMostrarExito);
		getContentPane().add(textFieldAsunto);
		getContentPane().add(textFieldComentario);
		getContentPane().add(textFieldPuntuacion);
		getContentPane().add(buttonRealizarComentario);
		
	}
	
	private String checkFieldsErrors() {
		try {
			if((textFieldAsunto.getText().length()==0) || (textFieldComentario.getText().length()==0)  || (textFieldPuntuacion.getText().length()==0))
				return ResourceBundle.getBundle("Etiquetas").getString("Registrarse.ErrorVacio");
			else {
				float price = Float.parseFloat(textFieldPuntuacion.getText());
				if (price < 0 || price > 10) {
					return ResourceBundle.getBundle("Etiquetas").getString("ComentarGUI.ErrorNumerico");
				}else {
					return null;
				}
			}
		} catch (java.lang.NumberFormatException e1) {
			return  ResourceBundle.getBundle("Etiquetas").getString("ComentarGUI.ErrorNumerico");		
		} catch (Exception e1) {
			e1.printStackTrace();
			return null;

		}
	}
}