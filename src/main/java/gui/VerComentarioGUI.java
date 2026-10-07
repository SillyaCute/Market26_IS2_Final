package gui;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import businessLogic.BLFacade;
import domain.Comentario;

import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.JTextArea;
import javax.swing.JLabel;
import javax.swing.JButton;

public class VerComentarioGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	
	private JLabel lblComentador;
	private JLabel lblUsuarioComentador;
	private JLabel lblComentado;
	private JLabel lblUsuarioComentado;
	private JLabel lblMostrarError;
	private JLabel lblMostrarExito;
	
	private JButton btnBorrarComentario;
	
	private JTextArea textComentario;
	
	private JScrollPane scrollComentario;
	private JLabel lblMostrarPuntuacion;
	private JLabel lblPuntuacion;

	public VerComentarioGUI(Comentario comentarioVer) {
		setBounds(100, 100, 465, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		//Labels
		lblComentador = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("VerComentarioGUI.Comentador") + ":");
		lblComentador.setBounds(269, 70, 120, 20);
		
		lblUsuarioComentador = new JLabel(comentarioVer.getComentador().getEmail());
		lblUsuarioComentador.setBounds(269, 102, 120, 20);
		
		lblComentado = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("VerComentarioGUI.Comentado") + ":");
		lblComentado.setBounds(269, 144, 120, 20);
		
		lblUsuarioComentado = new JLabel(comentarioVer.getComentado().getEmail());
		lblUsuarioComentado.setBounds(269, 174, 120, 20);
		
		lblMostrarError = new JLabel();
		lblMostrarError.setForeground(Color.red);
		lblMostrarError.setBounds(10, 270, 426, 26);
		
		lblMostrarExito = new JLabel();
		lblMostrarExito.setForeground(Color.green);
		lblMostrarExito.setBounds(10, 270, 426, 26);
		
		lblMostrarPuntuacion = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("VerComentarioGUI.MostrarPuntuacion"));
		lblMostrarPuntuacion.setBounds(10, 40, 120, 20);
		
		lblPuntuacion = new JLabel(""+comentarioVer.getPuntuacion());
		lblPuntuacion.setBounds(140, 40, 120, 20);
		
		//Text Area con su scroll
		textComentario = new JTextArea();
		textComentario.setEditable(false);
		scrollComentario = new JScrollPane(textComentario);
		scrollComentario.setBounds(10, 70, 249, 190);
		textComentario.setText(comentarioVer.getDescripcion());
		
		btnBorrarComentario = new JButton(ResourceBundle.getBundle("Etiquetas").getString("VerComentarioGUI.BorrarComentario"));
		btnBorrarComentario.setBounds(10, 306, 167, 35);
		btnBorrarComentario.addActionListener(new ActionListener() {
		 	public void actionPerformed(ActionEvent e) {
		 		BLFacade facade = MainGUI.getBusinessLogic();
		 		facade.borrarComentario(comentarioVer);
		 		lblMostrarExito.setText(ResourceBundle.getBundle("Etiquetas").getString("VerComentarioGUI.ExitoBorrado"));
		 		
		 		new Timer(3000, new ActionListener() {
				    @Override
				    public void actionPerformed(ActionEvent e) {
				        dispose();
				    }
				}) {{
				    setRepeats(false);
				}}.start();
		 	}
		});
		
		//Content Pane
		contentPane.add(lblMostrarPuntuacion);
		contentPane.add(lblPuntuacion);
		contentPane.add(lblComentador);
		contentPane.add(lblUsuarioComentador);
		contentPane.add(lblComentado);
		contentPane.add(lblUsuarioComentado);
		contentPane.add(lblMostrarError);
		contentPane.add(lblMostrarExito);
		contentPane.add(scrollComentario);
		contentPane.add(btnBorrarComentario);
		
	}
}
