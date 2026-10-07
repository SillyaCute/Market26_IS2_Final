package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;
import javax.swing.*;
import businessLogic.BLFacade;
import domain.Usuario;

public class ReportarUsuarioGUI extends JFrame {
	private JLabel lblCorreoReportado;
	private JLabel lblRazonReporte;
	private JLabel lblMostrarError;
	private JLabel lblMostrarExito;
	
	private JTextField textFieldCorreoReportado;
	private JTextField textFieldRazonReporte;
	
	private JButton buttonRealizarReporte;
	
	public ReportarUsuarioGUI(Usuario user) {
		getContentPane().setLayout(null);
		this.setSize(new Dimension(450, 325));
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("ReportarUsuarioGUI.Titulo"));
		
		//Labels
		lblCorreoReportado = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("ReportarUsuarioGUI.CorreoReportado"));
		lblCorreoReportado.setBounds(20, 30, 140, 24);
		
		lblRazonReporte = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("ReportarUsuarioGUI.RazonReporte"));
		lblRazonReporte.setBounds(20, 64, 140, 24);
		
		lblMostrarError = new JLabel();
		lblMostrarError.setForeground(Color.RED);
		lblMostrarError.setBounds(45, 225, 337, 20);
		
		lblMostrarExito = new JLabel();
		lblMostrarExito.setForeground(Color.GREEN);
		lblMostrarExito.setBounds(45, 225, 337, 20);
		
		//Text Fields
		textFieldCorreoReportado = new JTextField();
		textFieldCorreoReportado.setBounds(170, 30, 228, 24);
		textFieldCorreoReportado.setColumns(10);
		
		textFieldRazonReporte = new JTextField();
		textFieldRazonReporte.setBounds(20, 98, 377, 110);
		textFieldRazonReporte.setColumns(10);
		
		//Buttons
		buttonRealizarReporte = new JButton(ResourceBundle.getBundle("Etiquetas").getString("ReportarUsuarioGUI.RealizarReporte"));
		buttonRealizarReporte.setBounds(113, 256, 200, 20);	
		buttonRealizarReporte.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BLFacade facade = MainGUI.getBusinessLogic();
				String error = check_fields_Errors();
				
				if(error != null) {
					lblMostrarError.setText(error);
				}else {
					lblMostrarError.setText(null);
					facade.crearReporte(textFieldCorreoReportado.getText(), textFieldRazonReporte.getText(), user.getEmail());
					lblMostrarExito.setText(ResourceBundle.getBundle("Etiquetas").getString("ReportarUsuarioGUI.Exito"));
					
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
		
		//Content Pane
		getContentPane().add(lblCorreoReportado);
		getContentPane().add(lblRazonReporte);
		getContentPane().add(lblMostrarError);
		getContentPane().add(lblMostrarExito);
		getContentPane().add(textFieldCorreoReportado);
		getContentPane().add(textFieldRazonReporte);
		getContentPane().add(buttonRealizarReporte);
	}
	
	private String check_fields_Errors() {
		if ((textFieldCorreoReportado.getText().length()==0) || (textFieldRazonReporte.getText().length()==0)) {
			return ResourceBundle.getBundle("Etiquetas").getString("Registrarse.ErrorVacio");
		} else {
			BLFacade facade = MainGUI.getBusinessLogic();
			if (!facade.usuarioExistente(textFieldCorreoReportado.getText())) {
				return ResourceBundle.getBundle("Etiquetas").getString("HacerLogin.ErrorGmail");
			}else {
				return null;
			}
		}
	}
}