package application;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
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
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class Main extends Application {

	Stage window;
	Scene scene1, scene2;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		window = primaryStage;
		primaryStage.setTitle("Hệ thống Kiểm tra kết quả học tập sinh viên");

		// Scene 1
		Text sceneTitle1 = new Text("Xin chào!");
		sceneTitle1.setId("welcome-text");
		TextField userName = new TextField();
		userName.setPromptText("Username");
		PasswordField pwBox = new PasswordField();
		pwBox.setPromptText("Passwork");

		Button btnLogin = new Button("Đăng nhập");
		HBox hbBtnLogin = new HBox(10);
		hbBtnLogin.setAlignment(Pos.BOTTOM_RIGHT);
		hbBtnLogin.getChildren().add(btnLogin);

		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(25, 25, 25, 25));

		grid.add(sceneTitle1, 0, 0, 2, 1);
		grid.add(new Label("MSSV:"), 0, 1);
		grid.add(userName, 1, 1);
		grid.add(new Label("Mật khẩu:"), 0, 2);
		grid.add(pwBox, 1, 2);
		grid.add(hbBtnLogin, 1, 4);

		btnLogin.setDisable(true);
		userName.textProperty().addListener((observable, oldValue, newValue) -> {
			btnLogin.setDisable(newValue.trim().isEmpty());
		});

		btnLogin.setOnAction(event -> {
			window.setScene(scene2);
		});

		/*
		 * final Text actionTarget = new Text(); grid.add(actionTarget, 1, 6);
		 * actionTarget.setId("actionTarget");
		 * 
		 * btnLogin.setOnAction(new EventHandler<ActionEvent>() {
		 * 
		 * @Override public void handle(ActionEvent e) {
		 * actionTarget.setText("Tài khoản hoặc mật khậu không hợp lệ");
		 * userName.setText(""); pwBox.setText(""); } });
		 */

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
		Text own = new Text();
		own.setText("102170138");
		own.setFont(new Font("Arial", 18));
		HBox hbox = new HBox(10);
		hbox.getChildren().addAll(label, own);
		hbox.setAlignment(Pos.CENTER);

		TableView<Subjects> table = new TableView<Subjects>();
		final ObservableList<Subjects> data = FXCollections.observableArrayList(new Subjects("Giáo dục giới tính", 10));

		TableColumn subjectCol = new TableColumn("Học phần");
		subjectCol.setPrefWidth(500);
		subjectCol.setCellValueFactory(new PropertyValueFactory<>("subjectName"));

		TableColumn scoreCol = new TableColumn("Điểm thang 10");
		scoreCol.setPrefWidth(300);
		scoreCol.setCellValueFactory(new PropertyValueFactory("score"));

		table.setItems(data);
		table.getColumns().addAll(subjectCol, scoreCol);

		VBox layout = new VBox();
		layout.setSpacing(10);
		layout.getChildren().addAll(hbBtnLogout, hbBtnTitle, hbox, table);

		btnLogout.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				userName.setText("");
				pwBox.setText("");
				window.setScene(scene1);
			}
		});

		scene2 = new Scene(layout, 800, 800);
		scene2.getStylesheets().add(Main.class.getResource("application.css").toExternalForm());

		window.setScene(scene1);
		window.show();

	}

}
