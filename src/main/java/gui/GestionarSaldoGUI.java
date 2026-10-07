package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import businessLogic.BLFacade;
import domain.Usuario;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.Timer;
import java.awt.Color;
import javax.swing.SwingConstants;

public class GestionarSaldoGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textCantSaldo;
	private JButton btnAnadirSaldo;
	private JButton btnRetirarSaldo;
	private JLabel lblCantSaldo;

	/**
	 * Create the frame.
	 */
	public GestionarSaldoGUI(Usuario pUsuario, MainGUI main) {
		setBounds(100, 100, 450, 150);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblErrorSaldoInsuficiente = new JLabel();
		lblErrorSaldoInsuficiente.setHorizontalAlignment(SwingConstants.CENTER);
		lblErrorSaldoInsuficiente.setForeground(Color.RED);
		lblErrorSaldoInsuficiente.setBounds(10, 86, 414, 14);
		contentPane.add(lblErrorSaldoInsuficiente);
		
		JButton btnAnadirSaldo = new JButton();
		btnAnadirSaldo.setText(ResourceBundle.getBundle("Etiquetas").getString("GestionarSaldoGUI.AnadirSaldo"));
		btnAnadirSaldo.setBounds(10, 36, 206, 37);
		contentPane.add(btnAnadirSaldo);
		btnAnadirSaldo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				BLFacade facade = MainGUI.getBusinessLogic();
				
				facade.anadirSaldo(Float.parseFloat(textCantSaldo.getText()),pUsuario, main);
				
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
		
		JButton btnRetirarSaldo = new JButton();
		btnRetirarSaldo.setText(ResourceBundle.getBundle("Etiquetas").getString("GestionarSaldoGUI.RetirarSaldo"));
		btnRetirarSaldo.setBounds(218, 36, 206, 37);
		contentPane.add(btnRetirarSaldo);
		btnRetirarSaldo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (Float.parseFloat(textCantSaldo.getText())>pUsuario.getSaldo()) {
					lblErrorSaldoInsuficiente.setText(ResourceBundle.getBundle("Etiquetas").getString("GestionarSaldo.ErrorSaldoInsuficiente"));
				}else {
					BLFacade facade = MainGUI.getBusinessLogic();
					facade.retirarSaldo(Float.parseFloat(textCantSaldo.getText()),pUsuario, main);
				}
				new Timer(3000, new ActionListener() {
				    @Override
				    public void actionPerformed(ActionEvent e) {
				        dispose();
				    }
				})
				{{
				    setRepeats(false);
				}}.start();
			};
	});
		
		
		lblCantSaldo = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("GestionarSaldoGUI.CantSaldo"));
		lblCantSaldo.setBounds(94, 11, 289, 14);
		contentPane.add(lblCantSaldo);
		
		textCantSaldo = new JTextField();
		textCantSaldo.setBounds(313, 8, 86, 20);
		contentPane.add(textCantSaldo); 
		textCantSaldo.setColumns(10);
		
		
		
	}
}
