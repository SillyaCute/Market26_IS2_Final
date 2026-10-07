package gui;

import java.awt.EventQueue;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import businessLogic.BLFacade;
import domain.Comentario;
import domain.Reporte;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JLabel;

public class GestionarComentariosGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	
	private JLabel lblError = null;
	
	private JScrollPane scrollComentarios = null;
	
	private JTable tablaComentarios = null;
	
	private DefaultTableModel modeloTablaComentarios;
	
	private JButton btnBuscarComentarios = null;
	
	private JTextField textNombreComentado;
	
	private String[] columnasComentarios = new String[] {
			ResourceBundle.getBundle("Etiquetas").getString("GestionarComentariosGUI.Asunto"), 
			ResourceBundle.getBundle("Etiquetas").getString("GestionarComentariosGUI.Puntuacion"),
			ResourceBundle.getBundle("Etiquetas").getString("GestionarComentariosGUI.Fecha"),
			ResourceBundle.getBundle("Etiquetas").getString("GestionarComentariosGUI.Comentado"),
			ResourceBundle.getBundle("Etiquetas").getString("GestionarComentariosGUI.Comentador"),
			
	};

	public GestionarComentariosGUI() {
		setBounds(100, 100, 920, 260);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		//Label
		lblError = new JLabel();
		lblError.setBounds(10, 10, 330, 18);
		
		//Scroll y contenidos
		scrollComentarios = new JScrollPane();
		scrollComentarios.setBounds(new Rectangle(10, 63, 880, 150));
		
		tablaComentarios = new JTable();
		tablaComentarios.setEnabled(false);
		tablaComentarios.setBounds(0, 0, 6, 6);

		scrollComentarios.setViewportView(tablaComentarios);
		modeloTablaComentarios = new DefaultTableModel(null, columnasComentarios);

		tablaComentarios.setModel(modeloTablaComentarios);
		
		modeloTablaComentarios.setDataVector(null, columnasComentarios);
		modeloTablaComentarios.setColumnCount(6);

		tablaComentarios.getColumnModel().getColumn(0).setPreferredWidth(300);
		tablaComentarios.getColumnModel().getColumn(1).setPreferredWidth(80);
		tablaComentarios.getColumnModel().getColumn(2).setPreferredWidth(100);
		tablaComentarios.getColumnModel().getColumn(3).setPreferredWidth(200);
		tablaComentarios.getColumnModel().getColumn(4).setPreferredWidth(200);
		tablaComentarios.getColumnModel().removeColumn(tablaComentarios.getColumnModel().getColumn(5));
		
		//Text field
		textNombreComentado = new JTextField();
		textNombreComentado.setBounds(488, 30, 306, 23);
		textNombreComentado.setColumns(10);
		
		//Buttons
		btnBuscarComentarios = new JButton(ResourceBundle.getBundle("Etiquetas").getString("GestionarComentariosGUI.Buscar"));
		btnBuscarComentarios.setBounds(804, 29, 86, 24);
		btnBuscarComentarios.addActionListener(new ActionListener() {
		 	public void actionPerformed(ActionEvent e) {
		 		modeloTablaComentarios.setDataVector(null, columnasComentarios);
		 		modeloTablaComentarios.setColumnCount(6);

				BLFacade facade = MainGUI.getBusinessLogic();
				
				List<Comentario> comentarios = facade.getComentarios();

				if(comentarios.isEmpty()){
					lblError.setText(ResourceBundle.getBundle("Etiquetas").getString("GestionarComentariosGUI.SinComentarios"));
				}else {
					for(Comentario comentario:comentarios){
						if((textNombreComentado.getText().length() != 0) && (textNombreComentado.getText().equals(comentario.getComentado().getEmail())) ||
								(textNombreComentado.getText().equals(comentario.getComentador().getEmail()))) {
							
							Vector<Object> row = new Vector<Object>();
							row.add(comentario.getAsunto());
							row.add(comentario.getPuntuacion());
							row.add(new SimpleDateFormat("dd-MM-yyyy").format(comentario.getFechaComentario()));
							row.add(comentario.getComentado().getEmail());
							row.add(comentario.getComentador().getEmail());
							row.add(comentario);
							modeloTablaComentarios.addRow(row);	
						}else if(textNombreComentado.getText().length() == 0) {
							Vector<Object> row = new Vector<Object>();
							row.add(comentario.getAsunto());
							row.add(comentario.getPuntuacion());
							row.add(new SimpleDateFormat("dd-MM-yyyy").format(comentario.getFechaComentario()));
							row.add(comentario.getComentado().getEmail());
							row.add(comentario.getComentador().getEmail());
							row.add(comentario);
							modeloTablaComentarios.addRow(row);	
						}
					}
				}
				
				tablaComentarios.getColumnModel().getColumn(0).setPreferredWidth(300);
				tablaComentarios.getColumnModel().getColumn(1).setPreferredWidth(80);
				tablaComentarios.getColumnModel().getColumn(2).setPreferredWidth(100);
				tablaComentarios.getColumnModel().getColumn(3).setPreferredWidth(200);
				tablaComentarios.getColumnModel().getColumn(4).setPreferredWidth(200);
				tablaComentarios.getColumnModel().removeColumn(tablaComentarios.getColumnModel().getColumn(5));
		 	}
		});
		
		//Content pane		
		getContentPane().add(lblError);
		getContentPane().add(scrollComentarios, null);
		getContentPane().add(textNombreComentado);
		getContentPane().add(btnBuscarComentarios);
		
		tablaComentarios.addMouseListener(new MouseAdapter() {
	        @Override
	        public void mousePressed(MouseEvent mouseEvent) {
	            
	            if(mouseEvent.getClickCount() == 2){
			        JTable table =(JTable) mouseEvent.getSource();
	            	Point point = mouseEvent.getPoint();
			        int row = table.rowAtPoint(point);
	            	Comentario c=(Comentario) modeloTablaComentarios.getValueAt(row, 5);
		            JFrame a = new VerComentarioGUI(c);
		            a.setVisible(true);
	            }
	        }
	 });
	}
}
