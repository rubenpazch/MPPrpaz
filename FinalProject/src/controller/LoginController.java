package controller;

import java.util.HashMap;

//import controller.MainMenuController;
import controller.LoginController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;
import dataaccess.User;
import util.Util;


public class LoginController extends Application {

	private boolean userOrIdWrong = true;
	private Stage primaryStage;

	@Override
	public void start(Stage stage) throws Exception {

		// TODO Auto-generated method stub
		this.primaryStage = stage;
		Parent root = FXMLLoader.load(getClass().getResource("/ui/login.fxml"));

		stage.setTitle("Welcome MUM Library");
		stage.setScene(new Scene(root));

		TextField userId = (TextField) root.lookup("#txtUser");
		TextField txtPassword = (TextField) root.lookup("#txtPwd");
		Button button = (Button) root.lookup("#btLogin");

		button.setOnAction((event) -> {
			DataAccess db = new DataAccessFacade();
			HashMap<String, User> users = db.readUserMap();
			User user = users.get(userId.getText());
			if(user!=null)
			{
				if (user.authenticate(userId.getText(), txtPassword.getText()) ) {
					userOrIdWrong = false;
					MainMenuController secondWindow = new MainMenuController(user);
					try {
						secondWindow.start(this.primaryStage);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			if (userOrIdWrong) {
				Util.showAlert("User id or password Wrong ", "Error login", AlertType.ERROR);
			}
		});
		stage.show();
	}



	public static void main(String[] args) {
		Application.launch(LoginController.class, args);
	}

}
