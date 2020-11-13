package server;

import java.net.Socket;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

import dao.HtmlService;
import models.TotalScoreEntity;

public class ThreadSocket extends Thread {

	Socket socket = null;
	/*
	 * private Connection conn; private Statement sttm; private ResultSet rs;
	 * private String sql;
	 */
	private HtmlService htmlService = new HtmlService();

	public ThreadSocket(Socket socket) {
		System.out.println("Call to thread socket. ");
		System.out.println("Socket is connected: " + socket.isConnected());
		System.out.println("Socket address: " + socket.getInetAddress().getHostAddress());
		System.out.println("Socket port: " + socket.getPort());
		this.socket = socket;

	}

	@SuppressWarnings({ "rawtypes", "null" })
	public void run() {
		try {

			// ScoreResponse scoreResponse = null;
			List<TotalScoreEntity> scoreResponse = null;
			String massageClient = "";
			// Tao luong nhan du lieu tu client
			DataInputStream din = new DataInputStream(socket.getInputStream());
			massageClient = din.readUTF();

			System.out.println("Handle data. ");
			// String result = "";
			String username = massageClient.substring(0, 9);
			String password = massageClient.substring(9, massageClient.length());
			System.out.println("Client send: TK - " + username + ", MK - " + password);

			try {
				/*
				 * connectData(); sql = "SELECT * FROM TKSINHVIEN WHERE TaiKhoan = '" + tmpTK +
				 * "' AND MatKhau = '" + tmpMK + "'"; rs = sttm.executeQuery(sql); if
				 * (rs.next()) { sql = "SELECT * FROM HOCPHANDAHOC WHERE Mssv = '" + tmpTK +
				 * "'"; rs = sttm.executeQuery(sql); while (rs.next()) { for (int i = 2; i <=
				 * 11; i++) { if (rs.getString(i) != null) { result +=
				 * rs.getMetaData().getColumnName(i) + "#"; result += rs.getString(i) + "#"; } }
				 * }
				 * 
				 * } else { result = "Wrong"; }
				 */
				ArrayList datas = htmlService.getDatasAfterLogin(username, password);
				String check = (String) datas.get(0);
				if (check.equals("Wrong")) {
					scoreResponse = new ArrayList<TotalScoreEntity>();
					scoreResponse.add(new TotalScoreEntity().wrongInput());
				} else {
					String cookie = (String) datas.get(1);
					scoreResponse = htmlService.getScores(cookie);
				}

			} catch (Exception e) {
				System.out.println("Error connect: " + e);
			}

			Thread.sleep(1000);

			// Tao luong gui du lieu toi client
			ObjectOutputStream objectOutput = new ObjectOutputStream(socket.getOutputStream());
			objectOutput.writeObject(scoreResponse);
			objectOutput.flush();

		} catch (

		Exception e) {
			e.printStackTrace();
		}
	}

}
