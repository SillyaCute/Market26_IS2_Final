package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import businessLogic.BLFacade;
import domain.Sale;
import domain.Seller;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;
import java.awt.event.ActionEvent;
import java.awt.Rectangle;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.Timer;
import javax.swing.JTextField;

import domain.Contraoferta;

public class AceptarContraOfertaGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JFrame thisFrame; 
	
	private JButton btnAceptarContraOferta = null;
	private JLabel lblConfirmacion = null;
	
	public AceptarContraOfertaGUI(Contraoferta c, MainGUI main) {
		setBounds(100, 100, 356, 142);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		//Label
		lblConfirmacion = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("AceptarContraOfertaGUI.Confirmacion"));
		lblConfirmacion.setBounds(25, 25, 293, 23);
		
		//Buttons
		btnAceptarContraOferta = new JButton(ResourceBundle.getBundle("Etiquetas").getString("ContraOferta.Confirmar"));
		btnAceptarContraOferta.setBounds(new Rectangle(115, 59, 107, 30));
		btnAceptarContraOferta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				BLFacade facade = MainGUI.getBusinessLogic();
				
				facade.aceptarContraoferta(c, main);
				
				new Timer(3000, new ActionListener() {
				    @Override
				    public void actionPerformed(ActionEvent e) {
				        dispose();
				    }
				}) {{
				    setRepeats(false);
				}}.start();
			};
		
		

	});
		//Content Pane
		contentPane.add(lblConfirmacion);
		contentPane.add(btnAceptarContraOferta);
		
}	
}
