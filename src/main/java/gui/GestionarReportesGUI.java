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
import domain.Sale;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JLabel;

public class GestionarReportesGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	
	private JLabel lblError = null;
	
	private JScrollPane scrollReportes = null;
	private JTable tablaReportes = null;
	private DefaultTableModel modeloTablaReportes;
	
	private JTextField textNombreReportado;
	
	private JButton btnBuscarReportes = null;
	
	private String[] columnasReportes = new String[] {
			ResourceBundle.getBundle("Etiquetas").getString("GestionarReportesGUI.Fecha"),
			ResourceBundle.getBundle("Etiquetas").getString("GestionarReportesGUI.Reportado"),
			
	};

	public GestionarReportesGUI() {
		setBounds(100, 100, 535, 275);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		//Label
		lblError = new JLabel();
		lblError.setBounds(10, 10, 330, 18);
		
		//Scroll y contenido
		scrollReportes = new JScrollPane();
		scrollReportes.setBounds(new Rectangle(10, 65, 500, 150));
		
		tablaReportes = new JTable();
		tablaReportes.setEnabled(false);
		tablaReportes.setBounds(0, 0, 6, 6);

		scrollReportes.setViewportView(tablaReportes);
		modeloTablaReportes = new DefaultTableModel(null, columnasReportes);

		tablaReportes.setModel(modeloTablaReportes);
		
		modeloTablaReportes.setDataVector(null, columnasReportes);
		modeloTablaReportes.setColumnCount(3);
		
		tablaReportes.getColumnModel().getColumn(0).setPreferredWidth(100);
		tablaReportes.getColumnModel().getColumn(1).setPreferredWidth(200);
		tablaReportes.getColumnModel().removeColumn(tablaReportes.getColumnModel().getColumn(2));
		
		//Text field
		textNombreReportado = new JTextField();
		textNombreReportado.setBounds(108, 32, 306, 23);
		textNombreReportado.setColumns(10);
		
		//Buttons
		btnBuscarReportes = new JButton(ResourceBundle.getBundle("Etiquetas").getString("GestionarComentariosGUI.Buscar"));
		btnBuscarReportes.setBounds(424, 31, 86, 24);
		btnBuscarReportes.addActionListener(new ActionListener() {
		 	public void actionPerformed(ActionEvent e) {
		 		modeloTablaReportes.setDataVector(null, columnasReportes);
		 		modeloTablaReportes.setColumnCount(3);

				BLFacade facade = MainGUI.getBusinessLogic();
				
				List<Reporte> reportes = facade.getReportes();

				if(reportes.isEmpty()){
					lblError.setText(ResourceBundle.getBundle("Etiquetas").getString("GestionarReportesGUI.SinReportes"));
				}else {
					for(Reporte reporte:reportes){
						if((textNombreReportado.getText().length() != 0) && 
								(textNombreReportado.getText().equals(reporte.getReportado().getEmail()))){
							
							Vector<Object> row = new Vector<Object>();
							row.add(new SimpleDateFormat("dd-MM-yyyy").format(reporte.getFechaReporte()));
							row.add(reporte.getReportado().getEmail());
							row.add(reporte);
							modeloTablaReportes.addRow(row);		
						}else if(textNombreReportado.getText().length() == 0) {
							Vector<Object> row = new Vector<Object>();
							row.add(new SimpleDateFormat("dd-MM-yyyy").format(reporte.getFechaReporte()));
							row.add(reporte.getReportado().getEmail());
							row.add(reporte);
							modeloTablaReportes.addRow(row);	
						}
					}
				}
				
				tablaReportes.getColumnModel().getColumn(0).setPreferredWidth(100);
				tablaReportes.getColumnModel().getColumn(1).setPreferredWidth(200);
				tablaReportes.getColumnModel().removeColumn(tablaReportes.getColumnModel().getColumn(2));
		 	}
		});
		
		getContentPane().add(lblError);
		getContentPane().add(scrollReportes, null);
		getContentPane().add(textNombreReportado);
		getContentPane().add(btnBuscarReportes);
		
		tablaReportes.addMouseListener(new MouseAdapter() {
	        @Override
	        public void mousePressed(MouseEvent mouseEvent) {
	            
	            if(mouseEvent.getClickCount() == 2){
			        JTable table =(JTable) mouseEvent.getSource();
	            	Point point = mouseEvent.getPoint();
			        int row = table.rowAtPoint(point);
	            	Reporte r=(Reporte) modeloTablaReportes.getValueAt(row, 2);
		            JFrame a = new VerReporteGUI(r);
		            a.setVisible(true);
	            }
	        }
	 });
	}
}
