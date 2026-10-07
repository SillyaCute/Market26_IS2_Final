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
import domain.Reporte;

import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.JTextArea;
import javax.swing.JLabel;
import javax.swing.JButton;

public class VerReporteGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	
	private JLabel lblReportante;
	private JLabel lblUsuarioReportante;
	private JLabel lblReportado;
	private JLabel lblUsuarioReportado;
	private JLabel lblMostrarError;
	private JLabel lblMostrarExito;
	
	private JButton btnBanear;
	
	private JTextArea textReporte;
	
	private JScrollPane scrollReport;
	private JButton btnBorrarReporte;

	public VerReporteGUI(Reporte reporteVer) {
		setBounds(100, 100, 465, 350);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		//Labels
		lblReportante = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("VerReporteGUI.Reportante") + ":");
		lblReportante.setBounds(269, 21, 120, 20);
		
		lblUsuarioReportante = new JLabel(reporteVer.getReportante().getEmail());
		lblUsuarioReportante.setBounds(269, 53, 120, 20);
		
		lblReportado = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("VerReporteGUI.Reportado") + ":");
		lblReportado.setBounds(269, 95, 120, 20);
		
		lblUsuarioReportado = new JLabel(reporteVer.getReportado().getEmail());
		lblUsuarioReportado.setBounds(269, 125, 120, 20);
		
		lblMostrarError = new JLabel();
		lblMostrarError.setForeground(Color.red);
		lblMostrarError.setBounds(10, 221, 426, 26);
		
		lblMostrarExito = new JLabel();
		lblMostrarExito.setForeground(Color.green);
		lblMostrarExito.setBounds(10, 221, 426, 26);
		
		//Text Area con su scroll
		textReporte = new JTextArea();
		textReporte.setLineWrap(true);
		textReporte.setRows(10);
		textReporte.setEditable(false);
		scrollReport = new JScrollPane(textReporte);
		scrollReport.setBounds(10, 21, 249, 190);
		textReporte.setText(reporteVer.getDescripcion());
		
		//Button
		btnBanear = new JButton(ResourceBundle.getBundle("Etiquetas").getString("VerReporteGUI.Banear"));
		btnBanear.setBounds(269, 257, 167, 35);
		btnBanear.addActionListener(new ActionListener() {
		 	public void actionPerformed(ActionEvent e) {
		 		BLFacade facade = MainGUI.getBusinessLogic();
		 		facade.banearUsuario(reporteVer.getReportado().getEmail());
		 		facade.borrarReporte(reporteVer);
		 		lblMostrarExito.setText(ResourceBundle.getBundle("Etiquetas").getString("VerReporteGUI.ExitoUsuarioBaneado"));
		 		
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
		
		btnBorrarReporte = new JButton(ResourceBundle.getBundle("Etiquetas").getString("VerReporteGUI.BorrarReporte"));
		btnBorrarReporte.setBounds(10, 257, 167, 35);
		btnBorrarReporte.addActionListener(new ActionListener() {
		 	public void actionPerformed(ActionEvent e) {
		 		BLFacade facade = MainGUI.getBusinessLogic();
		 		facade.borrarReporte(reporteVer);
		 		lblMostrarExito.setText(ResourceBundle.getBundle("Etiquetas").getString("VerReporteGUI.ExitoBorrado"));
		 		
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
		contentPane.add(lblReportante);
		contentPane.add(lblUsuarioReportante);
		contentPane.add(lblReportado);
		contentPane.add(lblUsuarioReportado);
		contentPane.add(lblMostrarError);
		contentPane.add(lblMostrarExito);
		contentPane.add(scrollReport);
		contentPane.add(btnBanear);
		contentPane.add(btnBorrarReporte);
		
	}
}
