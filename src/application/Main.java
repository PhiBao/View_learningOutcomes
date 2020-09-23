package application;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class Main extends Application {

	Stage window;
	Scene scene1, scene2;

	public static void main(String[] args) {
		launch(args);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void start(Stage primaryStage) {
		window = primaryStage;
		primaryStage.setTitle("Hệ thống Kiểm tra kết quả học tập sinh viên");

		// Scene 1
		Text sceneTitle1 = new Text("Xin chào!");
		sceneTitle1.setId("welcome-text");
		TextField userName = new TextField();
		// Limit the amount of characters
		userName.lengthProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				if (newValue.intValue() > oldValue.intValue()) {
					// Check if the new character is greater than 9
					if (userName.getText().length() >= 9) {
						// if it's 10th character then just setText to previous one
						userName.setText(userName.getText().substring(0, 9));
						
					}
				}
			}
		});
		
		userName.setPromptText("Username");
		PasswordField pwBox = new PasswordField();
		pwBox.setPromptText("Passwork");

		Button btnLogin = new Button("Đăng nhập");
		HBox hbBtnLogin = new HBox(10);
		hbBtnLogin.setAlignment(Pos.BOTTOM_RIGHT);
		hbBtnLogin.getChildren().add(btnLogin);
		btnLogin.setDefaultButton(true);

		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(20, 20, 20, 20));

		grid.add(sceneTitle1, 0, 0, 2, 1);
		grid.add(new Label("MSSV:"), 0, 1);
		grid.add(userName, 1, 1);
		grid.add(new Label("Mật khẩu:"), 0, 2);
		grid.add(pwBox, 1, 2);
		grid.add(hbBtnLogin, 1, 4);

		final Text actionTarget = new Text();
		grid.add(actionTarget, 0, 6, 2, 1);
		actionTarget.setId("actionTarget");
		actionTarget.setText("Sai tài khoản hoặc mật khẩu. Vui lòng nhập lại!");
		actionTarget.setVisible(false);
		
		TableView<Subjects> table = new TableView<Subjects>();
		Label infor = new Label();

		btnLogin.setDisable(true);
		userName.textProperty().addListener((observable, oldValue, newValue) -> {
			
			btnLogin.setDisable(newValue.trim().length() != 9);
			
		});

		btnLogin.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				try {
					String resultFromServer = accept_Client(userName.getText(), pwBox.getText());
					
					if (resultFromServer.equals("Wrong")) {
						
						System.out.println(resultFromServer);
						actionTarget.setVisible(true);
						userName.setText("");
						pwBox.setText("");
						
					} else if (resultFromServer.equals("DBError")) {
						
						System.out.println(resultFromServer);
						Alert alert = new Alert(Alert.AlertType.INFORMATION);
						alert.setTitle("Xin thứ lỗi!");
						alert.setHeaderText("Thông báo:");
						alert.setContentText("Lỗi kết nối Cơ sở dữ liệu. Xin hãy thử lại sau!");
						alert.show();
						
						actionTarget.setVisible(false);
						userName.setText("");
						pwBox.setText("");
						
					} else {
						
						infor.setText(userName.getText());
						
						System.out.println(resultFromServer);
						ObservableList<Subjects> list = handleReturnData(resultFromServer);
						table.setItems(list);
						window.setScene(scene2);
						
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();		
				}
			}

			private ObservableList<Subjects> handleReturnData(String resultFromServer) {
				// TODO Auto-generated method stub
				String[] tmp = resultFromServer.split("\\#");
				Subjects buffer[] = new Subjects[tmp.length / 2];
				for (int i = 0; i < buffer.length; i++) {
					buffer[i] = new Subjects();
				}
				
				int j = 0;
				for (int i = 0; i < tmp.length; i += 2) {
					buffer[j].setSubjectName(tmp[i]);
					buffer[j].setScore(tmp[i + 1]);
					j++;
				}
				
				ObservableList<Subjects> list = FXCollections.observableArrayList(buffer);
				return list;
			}

		});

		Scene scene1 = new Scene(grid, 640, 480);
		scene1.getStylesheets().add(Main.class.getResource("application.css").toExternalForm());

		// Scene 2
		Label sceneTitle2 = new Label("KẾT QUẢ HỌC TẬP");
		HBox hbBtnTitle = new HBox(10);
		hbBtnTitle.setAlignment(Pos.CENTER);
		hbBtnTitle.getChildren().add(sceneTitle2);

		Button btnLogout = new Button("Đăng xuất");
		HBox hbBtnLogout = new HBox(10);
		hbBtnLogout.setAlignment(Pos.TOP_RIGHT);
		hbBtnLogout.getChildren().add(btnLogout);

		Label label = new Label("MSSV:");
		HBox hbox = new HBox(10);
		hbox.getChildren().addAll(label, infor);
		hbox.setAlignment(Pos.CENTER);

		TableColumn<Subjects, String> subjectNameCol = new TableColumn<Subjects, String>("Học phần");
		subjectNameCol.setPrefWidth(500);
		subjectNameCol.setCellValueFactory(new PropertyValueFactory<>("subjectName"));

		TableColumn<Subjects, String> scoreCol = new TableColumn<Subjects, String>("Điểm thang 10");
		scoreCol.setPrefWidth(300);
		scoreCol.setCellValueFactory(new PropertyValueFactory<Subjects, String>("score"));

		table.getColumns().addAll(subjectNameCol, scoreCol);

		VBox layout = new VBox();
		layout.setSpacing(10);
		layout.getChildren().addAll(hbBtnLogout, hbBtnTitle, hbox, table);

		btnLogout.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				userName.setText("");
				pwBox.setText("");
				actionTarget.setVisible(false);
				window.setScene(scene1);
			}
		});

		scene2 = new Scene(layout, 800, 720);
		scene2.getStylesheets().add(Main.class.getResource("application.css").toExternalForm());

		window.setScene(scene1);
		window.show();

	}

	private Socket connect() throws Exception {
		// Tao socket ket noi den server cho phep ket noi o cong 8039.
		Socket clientSocket = null;
		clientSocket = new Socket("localhost", 8039);
		System.out.println("Client is connected to socket server!");

		return clientSocket;
		
	}

	public String accept_Client(String userName, String passWord) throws Exception {
		// Client: tao socket ket noi den server cho phep ket noi o cong 8039
		Socket socket = connect();

		// Tao luong gui du lieu len server
		DataOutputStream outToServer = new DataOutputStream(socket.getOutputStream());
		outToServer.writeUTF((userName + passWord).trim());
		
		// Tao luon nhan vao du lieu tu server
		DataInputStream inFromServer = new DataInputStream(socket.getInputStream());
		String result = inFromServer.readUTF();
		
		System.out.println("Server send: " + result);
		socket.close();
		
		return result;
		
	}

}