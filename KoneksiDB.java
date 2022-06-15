package javadb;
import java.sql.*;
import java.util.Scanner;

public class KoneksiDB {
	public static java.util.Scanner scanner = new Scanner(System.in);
	static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://127.0.0.1/penjualan";
    static final String USER = "root";
    static final String PASS = "";
    
    static Connection conn;
    static Statement stmt;
    static ResultSet rs;
 
    
    public static void main(String[] args) {
    	viewMenampilkanDataBase();
    }
    
    public static String input(String info){
        System.out.print(info+ " : ");
        String data = scanner.nextLine();
        return data;
    }
    
    public static void insert()
	{
    	System.out.println("TAMBAH DATA");
    	System.out.print("tmbh kode_brg	: ");
		String kode_brg = scanner.nextLine();
		System.out.print("tmbh nama_brg	: ");
		String nama_brg = scanner.nextLine();
		System.out.print("tmbh satuan	: ");
		String satuan = scanner.nextLine();
		System.out.print("tmbh stok		: ");
		int stok = scanner.nextInt();
		System.out.print("tmbh stok_min	: ");
		int stok_min = scanner.nextInt();
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
	}

	public static void show()
	{
		try {
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(DB_URL,USER,PASS);
			stmt = conn.createStatement();
			
			rs = stmt.executeQuery("SELECT * FROM barang");
			int i = 1;
			while(rs.next())
			{
				System.out.println("Data ke-"+i);
				System.out.println("Kode Barang	: " + rs.getString("kd_brg"));
				System.out.println("Nama Barang	: "+rs.getString("nm_brg"));
				System.out.println("Satuan		: "+rs.getString("satuan"));
				System.out.println("Stok		: "+rs.getString("stok_brg"));
				System.out.println("Stok minimal: "+rs.getString("stok_min"));
				i++;
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public static void viewMenampilkanDataBase(){
        while(true){
            show();

            System.out.println("MENU : ");
            System.out.println("1. tambah");
            
            System.out.println("2. Keluar");

            var input = input("Pilih");
            if (input.equals("1")){
                insert();
            } else if(input.equals("2")){
                break;
            }else{
                System.out.println("Tidak Valid");
            }
        }
    }
}
