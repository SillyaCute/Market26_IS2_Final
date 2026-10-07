package gui;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import businessLogic.BLFacade;
import domain.Sale;

public class MostrarComentariosGUI extends JFrame {
	private static final long serialVersionUID = 1L;
	private JFrame thisFrame; 
	
	private final JLabel jLabelComentarios; 
	private JButton jButtonBuscar;
	private JScrollPane scrollPanelProducts = new JScrollPane();
	private JTable tableProducts= new JTable();
	private DefaultTableModel tableModelProducts;

	private String[] columnNamesProducts = new String[] {
			ResourceBundle.getBundle("Etiquetas").getString("MostrarComentariosGUI.Asunto"), 
			ResourceBundle.getBundle("Etiquetas").getString("MostrarComentariosGUI.Rating"),
			ResourceBundle.getBundle("Etiquetas").getString("MostrarComentariosGUI.Fecha"),
	};
	
	public MostrarComentariosGUI(Sale venta) {

	tableProducts.setEnabled(false);
	thisFrame=this;
	this.getContentPane().setLayout(null);
	this.setSize(new Dimension(580, 342));
	this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("MostrarComentariosGUI.Titulo"));
	
	jLabelComentarios = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("MostrarComentariosGUI.Comentarios"));
	jLabelComentarios.setBounds(52, 20, 216, 27);

	scrollPanelProducts.setBounds(new Rectangle(52, 57, 459, 230));

	scrollPanelProducts.setViewportView(tableProducts);
	tableModelProducts = new DefaultTableModel(null, columnNamesProducts);

	tableProducts.setModel(tableModelProducts);
	tableModelProducts.setDataVector(null, columnNamesProducts);
	tableModelProducts.setColumnCount(4); // another column added to allocate ride objects

	tableProducts.getColumnModel().getColumn(0).setPreferredWidth(200);
	tableProducts.getColumnModel().getColumn(1).setPreferredWidth(10);
	tableProducts.getColumnModel().getColumn(1).setPreferredWidth(70);
	tableProducts.getColumnModel().removeColumn(tableProducts.getColumnModel().getColumn(3));

	jButtonBuscar = new JButton(ResourceBundle.getBundle("Etiquetas").getString("MostrarComentariosGUI.Buscar"));
	jButtonBuscar.setBounds(334, 20, 177, 27);
	jButtonBuscar.addActionListener(new ActionListener() {
	 	public void actionPerformed(ActionEvent e) {
			tableModelProducts.setDataVector(null, columnNamesProducts);
			tableModelProducts.setColumnCount(4);
			tableProducts.getColumnModel().getColumn(0).setPreferredWidth(200);
			tableProducts.getColumnModel().getColumn(1).setPreferredWidth(10);
			tableProducts.getColumnModel().getColumn(1).setPreferredWidth(70);
			tableProducts.getColumnModel().removeColumn(tableProducts.getColumnModel().getColumn(3));

			BLFacade facade = MainGUI.getBusinessLogic();
			List<domain.Comentario> comentarios=facade.getComentariosVenta(venta);
			for (domain.Comentario comentario:comentarios){
				Vector<Object> row = new Vector<Object>();
				row.add(comentario.getAsunto());
				row.add(comentario.getPuntuacion());
				row.add(comentario.getFechaComentario());
				row.add(comentario);
				tableModelProducts.addRow(row);	
			}
	 	}
	});
	
	this.getContentPane().add(jButtonBuscar);
	this.getContentPane().add(jLabelComentarios);
	this.getContentPane().add(scrollPanelProducts, null);
	}
}
