package server;

import java.net.Socket;
import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import application.Subjects;

public class ThreadSocket extends Thread {

	Socket socket = null;
	private Connection conn;
	private Statement sttm;
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
			// String subjects = null;
			String result = "";
			massageClient = din.readUTF();
			String tmpTK = massageClient.substring(0, 9);
			String tmpMK = massageClient.substring(9, massageClient.length());
			System.out.println("Client send: TK - " + tmpTK + ", MK - " + tmpMK);

			try {
				connectData();
				sql = "SELECT * FROM TKSINHVIEN WHERE TaiKhoan = '" + tmpTK + "' AND MatKhau = '" + tmpMK + "'";
				rs = sttm.executeQuery(sql);
				if (rs.getMetaData() != null) {
					System.out.println(rs.getMetaData());
					result = "Done";
				} else {
					result = "Wrong";
				}

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
			conn = DriverManager.getConnection(url, "root", "");
			sttm = conn.createStatement();
		} catch (Exception e) {
			System.out.println("Error: " + e);
		}
	}

}
