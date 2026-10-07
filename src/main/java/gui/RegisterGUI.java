package gui;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.Timer;

import businessLogic.BLFacade;
import configuration.UtilDate;
import domain.Administrador;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JRadioButton;
import java.awt.Color;

public class RegisterGUI extends JFrame {
	
	private static final long serialVersionUID = 1L;
	private JLabel lblNombre = null;
	private JLabel lblCorreo = null;
	private JLabel lblContraseña = null;
	private JLabel lblMostrarError = null;
	private JLabel lblMostrarExito = null;
	private JLabel lblSaldo = null;
	private JLabel lblContraseñaMaestra = null;
	
	private JTextField textCorreo;
	private JTextField textNombre;
	private JTextField textContraseña;
	
	private JButton btnRegistrarse = null;
	private JRadioButton rdbtnVendedor = null;
	private JRadioButton rdbtnComprador = null;
	private JRadioButton rdbtnAdministrador = null;
	
	private final ButtonGroup tipoUsuario = new ButtonGroup();
	private JTextField textContraseñaMaestra;

	private Administrador adminVacio;

	public RegisterGUI() {
		

		this.getContentPane().setLayout(null);
		this.setSize(new Dimension(480, 295));
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("CreateSaleGUI.CreateProduct"));
		
		setTitle("Register");
		getContentPane().setLayout(null);
		
		//Labels
		lblNombre = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Registrarse.Nombre"));
		lblNombre.setBounds(6, 24, 176, 20);
		
		lblCorreo = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Registrarse.Correo"));
		lblCorreo.setBounds(6, 54, 176, 20);
		
		lblContraseña = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Registrarse.Contrasena"));
		lblContraseña.setBounds(6, 84, 176, 20);
		
		lblMostrarError = new JLabel();
		lblMostrarError.setForeground(Color.red);
		lblMostrarError.setBounds(16, 145, 337, 20);
		
		lblMostrarExito = new JLabel();
		lblMostrarExito.setForeground(new Color(81, 201, 29));
		lblMostrarExito.setBounds(16, 145, 337, 20);
		
		lblContraseñaMaestra = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Registrarse.ContrasenaMaestra"));
		lblContraseñaMaestra.setBounds(6, 114, 176, 20);
		lblContraseñaMaestra.setEnabled(false);	
		lblContraseñaMaestra.setVisible(false);	
		
		//Text Fields
		textNombre = new JTextField();
		textNombre.setText((String) null);
		textNombre.setColumns(10);
		textNombre.setBounds(192, 24, 250, 20);
		
		textCorreo = new JTextField();
		textCorreo.setText((String) null);
		textCorreo.setBounds(192, 54, 250, 20);
		textCorreo.setColumns(10);
		
		textContraseña = new JTextField();
		textContraseña.setText((String) null);
		textContraseña.setColumns(10);
		textContraseña.setBounds(192, 84, 250, 20);
		
		textContraseñaMaestra = new JTextField();
		textContraseñaMaestra.setText((String) null);
		textContraseñaMaestra.setColumns(10);
		textContraseñaMaestra.setBounds(192, 114, 250, 20);
		textContraseñaMaestra.setEnabled(false);
		textContraseñaMaestra.setVisible(false);
		
		//Radio Buttons
		rdbtnVendedor = new JRadioButton(ResourceBundle.getBundle("Etiquetas").getString("Registrarse.Vendedor"));
		rdbtnVendedor.setBounds(60, 175, 102, 20);
		rdbtnVendedor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lblContraseñaMaestra.setEnabled(false);	
				textContraseñaMaestra.setEnabled(false);
				lblContraseñaMaestra.setVisible(false);	
				textContraseñaMaestra.setVisible(false);	
			}
		});
		tipoUsuario.add(rdbtnVendedor);
		
		rdbtnComprador = new JRadioButton(ResourceBundle.getBundle("Etiquetas").getString("Registrarse.Comprador"));
		rdbtnComprador.setBounds(305, 175, 102, 20);
		rdbtnComprador.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lblContraseñaMaestra.setEnabled(false);	
				textContraseñaMaestra.setEnabled(false);
				lblContraseñaMaestra.setVisible(false);	
				textContraseñaMaestra.setVisible(false);
			}
		});
		tipoUsuario.add(rdbtnComprador);
		
		rdbtnAdministrador = new JRadioButton(ResourceBundle.getBundle("Etiquetas").getString("Registrarse.Administrador"));
		rdbtnAdministrador.setBounds(182, 175, 121, 20);
		rdbtnAdministrador.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lblContraseñaMaestra.setEnabled(true);	
				textContraseñaMaestra.setEnabled(true);
				lblContraseñaMaestra.setVisible(true);	
				textContraseñaMaestra.setVisible(true);
			}
		});
		tipoUsuario.add(rdbtnAdministrador);
		
		//Buttons
		btnRegistrarse = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Registrarse.Registrarse"));
		btnRegistrarse.setBounds(60, 202, 347, 31);
		btnRegistrarse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String tipo = null;
				String error = check_fields_Errors();
				
				if(error != null) {
					lblMostrarError.setText(error);
				}else {
					lblMostrarError.setText(null);
					BLFacade facade = MainGUI.getBusinessLogic();
					if(rdbtnVendedor.isSelected()) {
						tipo = "Vendedor";
						facade.crearUsuario(textNombre.getText(), textCorreo.getText(), textContraseña.getText(), tipo, 0);
					}else if(rdbtnComprador.isSelected()){
						tipo = "Comprador";
						facade.crearUsuario(textNombre.getText(), textCorreo.getText(), textContraseña.getText(), tipo, 0);
					}else if(rdbtnAdministrador.isSelected()){
						tipo = "Administrador";
						facade.crearUsuario(textNombre.getText(), textCorreo.getText(), textContraseña.getText(), tipo, 0);
					}
					
					lblMostrarExito.setText(ResourceBundle.getBundle("Etiquetas").getString("Registrarse.Exito"));
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
		getContentPane().add(lblNombre);
		getContentPane().add(lblCorreo);
		getContentPane().add(lblContraseña);
		getContentPane().add(lblMostrarError);
		getContentPane().add(lblMostrarExito);
		getContentPane().add(lblContraseñaMaestra);
		getContentPane().add(textNombre);
		getContentPane().add(textCorreo);
		getContentPane().add(textContraseña);
		getContentPane().add(textContraseñaMaestra);
		getContentPane().add(rdbtnVendedor);
		getContentPane().add(rdbtnComprador);
		getContentPane().add(rdbtnAdministrador);
		getContentPane().add(btnRegistrarse);
		
	}
	
	private String check_fields_Errors() {
		//Revisa espacios vacios en text fields
		if ( ((textCorreo.getText().length()==0) || (textNombre.getText().length()==0)  || (textContraseña.getText().length()==0)) || 
				(rdbtnAdministrador.isSelected() && textContraseñaMaestra.getText().length()==0)) {
			return ResourceBundle.getBundle("Etiquetas").getString("Registrarse.ErrorVacio");
		//Revisa el campo del saldo
		}else{//
			BLFacade facade = MainGUI.getBusinessLogic();
			//Revisa que el correo sea un gmail
			if (!(textCorreo.getText().endsWith("@gmail.com"))) {
				return ResourceBundle.getBundle("Etiquetas").getString("Registrarse.ErrorGmail");
			//Revisa que el correo no exista ya en la base de datos
			}else if(facade.usuarioExistente(textCorreo.getText())){
				return ResourceBundle.getBundle("Etiquetas").getString("Registrarse.ErrorDuplicado");
				//Revisa que el correo no esté baneado 
			}else if(facade.esUsuarioBaneado(textCorreo.getText())) {
				return ResourceBundle.getBundle("Etiquetas").getString("Registrarse.ErrorBaneado");
			}else {
				//Revisa que se haya seleccionado un tipo de cuenta como minimo
				if(!rdbtnVendedor.isSelected() && !rdbtnComprador.isSelected() && !rdbtnAdministrador.isSelected()) {
					return ResourceBundle.getBundle("Etiquetas").getString("Registrarse.ErrorTipo");
				}else if(rdbtnAdministrador.isSelected() && !textContraseñaMaestra.getText().equals("vaca")){
					return ResourceBundle.getBundle("Etiquetas").getString("Registrarse.ErrorMaestro");
				}else {
					return null;
				}
			}
		}
	}
	
	//private boolean noEsFloat(String posibleFloat) {
		//try {
	     //   Float.parseFloat(posibleFloat);
	    //    return false;
	    //} catch (NumberFormatException e) {
	    //    return true;
	    //}
	//}
}
