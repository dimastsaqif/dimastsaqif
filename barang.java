package javagui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.FlowLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.Scanner;
import java.awt.event.ActionEvent;
import java.sql.*;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class FormBarang {

	private JFrame frmFormBarang;
	private JTextField txtKdBrg;
	private JTextField txtNmBrg;
	private JTextField txtStok;
	private JTextField txtStokMin;
	
	public static java.util.Scanner scanner = new Scanner(System.in);
	static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://127.0.0.1/penjualan";
    static final String USER = "root";
    static final String PASS = "";
    
    static Connection conn;
    static Statement stmt;
    static ResultSet rs;
    private JTable table;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FormBarang window = new FormBarang();
					window.frmFormBarang.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public FormBarang() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmFormBarang = new JFrame();
		frmFormBarang.addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
				showData();
			}
		});
		frmFormBarang.setTitle("Form Barang");
		frmFormBarang.setBounds(100, 100, 841, 525);
		frmFormBarang.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmFormBarang.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Form Input Barang");
		lblNewLabel.setBounds(69, 43, 155, 40);
		frmFormBarang.getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Kode Barang");
		lblNewLabel_1.setBounds(69, 94, 77, 33);
		frmFormBarang.getContentPane().add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Nama Barang");
		lblNewLabel_2.setBounds(69, 138, 77, 33);
		frmFormBarang.getContentPane().add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("Satuan");
		lblNewLabel_3.setBounds(69, 197, 64, 22);
		frmFormBarang.getContentPane().add(lblNewLabel_3);
		
		JLabel lblNewLabel_4 = new JLabel("Stok Barang");
		lblNewLabel_4.setBounds(69, 243, 77, 32);
		frmFormBarang.getContentPane().add(lblNewLabel_4);
		
		JLabel lblNewLabel_5 = new JLabel("Stok Minimal");
		lblNewLabel_5.setBounds(69, 303, 77, 22);
		frmFormBarang.getContentPane().add(lblNewLabel_5);
		
		txtKdBrg = new JTextField();
		txtKdBrg.setBounds(179, 101, 128, 20);
		frmFormBarang.getContentPane().add(txtKdBrg);
		txtKdBrg.setColumns(10);
		
		txtNmBrg = new JTextField();
		txtNmBrg.setColumns(10);
		txtNmBrg.setBounds(179, 145, 128, 20);
		frmFormBarang.getContentPane().add(txtNmBrg);
		
		final JComboBox satuan = new JComboBox();
		satuan.setModel(new DefaultComboBoxModel(new String[] {"Buah", "Lembar", "Liter", "KG", "Pasang", "box"}));
		satuan.setBounds(179, 198, 128, 22);
		frmFormBarang.getContentPane().add(satuan);
		
		txtStok = new JTextField();
		txtStok.setColumns(10);
		txtStok.setBounds(179, 250, 128, 20);
		frmFormBarang.getContentPane().add(txtStok);
		
		txtStokMin = new JTextField();
		txtStokMin.setColumns(10);
		txtStokMin.setBounds(179, 305, 128, 20);
		frmFormBarang.getContentPane().add(txtStokMin);
		
		final JButton btnSimpan = new JButton("Simpan");
		btnSimpan.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println(txtKdBrg.getText());
				System.out.println(txtNmBrg.getText());
				System.out.println(satuan.getSelectedItem());
				System.out.println(txtStok.getText());
				System.out.println(txtStokMin.getText());
				
			
			insert(txtKdBrg.getText(), txtNmBrg.getText(), (satuan.getSelectedItem().toString()), Integer.parseInt(txtStok.getText()), Integer.parseInt(txtStokMin.getText()));
			}
		});
		btnSimpan.setBounds(122, 372, 89, 23);
		frmFormBarang.getContentPane().add(btnSimpan);
		
		JButton btnReset = new JButton("Reset");
		btnReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				resetForm();
			}
		});
		btnReset.setBounds(234, 372, 89, 23);
		frmFormBarang.getContentPane().add(btnReset);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(360, 56, 423, 395);
		frmFormBarang.getContentPane().add(scrollPane);
		
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String kode = table.getValueAt(table.getSelectedRow(),0).toString();
				getData(kode);
				btnSimpan.setEnabled(false);
			}
		});
		scrollPane.setViewportView(table);
		
		JButton btnEdit = new JButton("Edit");
		btnEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				edit((satuan.getSelectedItem().toString()));
			}
		});
		btnEdit.setBounds(122, 428, 89, 23);
		frmFormBarang.getContentPane().add(btnEdit);
		
		JButton btnHapus = new JButton("Hapus");
		btnHapus.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int respon = JOptionPane.showConfirmDialog(null, "Konfirmasi Hapus!");
				if (respon == 0) {
					if (table.getSelectedRow()>=0) {
						hapusData(txtKdBrg.getText());
					}
				}else {
					JOptionPane.showMessageDialog(null, "Hapus Data dibatalkan");
				}
			}
		});
		btnHapus.setBounds(234, 428, 89, 23);
		frmFormBarang.getContentPane().add(btnHapus);
	}
	
	public void insert(String kode_brg, String nama_brg, String satuan, int stok, int stok_min ) {
		try {
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(DB_URL,USER,PASS);
			stmt = conn.createStatement();
			
			String sql = "INSERT INTO barang (kd_brg,nm_brg,satuan,stok_brg,stok_min) VALUES (?,?,?,?,?)";
			PreparedStatement ps = conn.prepareStatement(sql);
			
			ps.setString(1, kode_brg);
			ps.setString(2, nama_brg);
			ps.setString(3, satuan);
			ps.setInt(4, stok);
			ps.setInt(5, stok_min);
			
			ps.execute();
			
			stmt.close();
			conn.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		showData();
		resetForm();
	}
	
	public void showData() {
		
		DefaultTableModel model = new DefaultTableModel(); 
		model.addColumn("Kode Barang");
		model.addColumn("Nama Barang");
		model.addColumn("Satuan");
		model.addColumn("Stok Barang");
		model.addColumn("Stok Min");
		
		try {
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(DB_URL,USER,PASS);
			stmt = conn.createStatement();
			
			rs = stmt.executeQuery("SELECT * FROM barang");
			
			while(rs.next())
			{
				model.addRow(new Object[] {
					rs.getString("kd_brg"),
					rs.getString("nm_brg"),
					rs.getString("satuan"),
					rs.getString("stok_brg"),
					rs.getString("stok_min")
				});
			}
			rs.close();
			stmt.close();
			conn.close();
			
			table.setModel(model);
			table.setAutoResizeMode(0);
			table.getColumnModel().getColumn(0).setPreferredWidth(60);
			table.getColumnModel().getColumn(1).setPreferredWidth(120);
			table.getColumnModel().getColumn(2).setPreferredWidth(100);
			table.getColumnModel().getColumn(3).setPreferredWidth(80);
			table.getColumnModel().getColumn(4).setPreferredWidth(60);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void getData(String kode) {
		JComboBox satuan = new JComboBox();
		try {
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(DB_URL,USER,PASS);
			stmt = conn.createStatement();
			
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM barang WHERE kd_brg=?");
			ps.setString(1, kode);
			
			rs = ps.executeQuery();
			
			rs.next();
			
			txtKdBrg.setText(rs.getString("kd_brg"));
			txtNmBrg.setText(rs.getString("nm_brg"));
			satuan.setSelectedItem(rs.getString("satuan").toString());
			txtStok.setText(rs.getString("stok_brg"));
			txtStokMin.setText(rs.getString("stok_min"));
		
			stmt.close();
			conn.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void resetForm() {
		txtKdBrg.setText("");
		txtNmBrg.setText("");
		txtStok.setText("");
		txtStokMin.setText("");
	}
	
	public void hapusData(String kode) {
		try {
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(DB_URL,USER,PASS);
			stmt = conn.createStatement();
			
			PreparedStatement ps = conn.prepareStatement("DELETE FROM barang WHERE kd_brg=?");
			ps.setString(1, kode);
			
			ps.execute();
			
			
		
			stmt.close();
			conn.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		showData();
		resetForm();
	}
	
	public void edit(String satuan) {
//		JComboBox satuan = new JComboBox();
		String kode = txtKdBrg.getText();
		String nama = txtNmBrg.getText();
//		String asatuan = satuan.getSelectedItem().toString();
		int stokk = Integer.parseInt(txtStok.getText());
		int stokmin = Integer.parseInt(txtStokMin.getText());
		try {
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(DB_URL,USER,PASS);
			stmt = conn.createStatement();
			
			PreparedStatement ps = conn.prepareStatement("UPDATE barang SET nm_brg=?, satuan=?, stok_brg=?, stok_min=? WHERE kd_brg=?");
			ps.setString(1, nama);
			ps.setString(2, satuan);
			ps.setInt(3, stokk);
			ps.setInt(4, stokmin);
			ps.setString(5, kode);
			
			
			ps.execute();
			
			
			JOptionPane.showMessageDialog(null, "Data Berhasil diedit");
			stmt.close();
			conn.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		showData();
		resetForm();
	}
}
