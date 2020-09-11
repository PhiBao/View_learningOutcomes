package server;

import java.net.Socket;
import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.Vector;
import application.Subjects;

public class ThreadSocket extends Thread {

	Socket socket = null;
	private Connection con;
	private Statement stm;
	private ResultSet rs;
	private String sql;
	public Subjects data = new Subjects();

	public ThreadSocket(Socket socket) {

		System.out.println("Call to thread socket. ");
		System.out.println("Socket is connected: " + socket.isConnected());
		System.out.println("Socket address: " + socket.getInetAddress().getHostAddress());
		System.out.println("Socket port: " + socket.getPort());
		this.socket = socket;
	}

	public void run() {
		try {
			String massageClient = "";
			
			// Tao luong nhan du lieu tu client
			DataInputStream din = new DataInputStream(socket.getInputStream());
			// Tao luong gui du lieu toi client
			DataOutputStream dout = new DataOutputStream(socket.getOutputStream());

			System.out.println("Handle data. ");
			String subjects = null;
			String result = "";
			massageClient = din.readUTF();
			System.out.println("Client send:" + massageClient);

			try {
				connectData();
				sql = "SELECT * FROM HOCPHANDAHOC WHERE Mssv = '" + massageClient +"'";
		        rs = stm.executeQuery(sql);
		        
			} catch (Exception e) {
				result = "DBError";
				System.out.println("Error connect Database: " + e);
			}
			
			System.out.print(result);
			Thread.sleep(1000);
			// Gui du lieu xuong sever
			dout.writeUTF(result);
			dout.flush();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Tao ket noi den voi Database
	public void connectData() throws Exception {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String url = "jdbc:mysql://127.0.0.1:3306/KETQUAHOCTAP";
			Connection conn = DriverManager.getConnection(url, "root", "");
			Statement sttm = conn.createStatement();
		} catch (Exception e) {
			System.out.println("Error: " + e);
		}
	}
	
}
