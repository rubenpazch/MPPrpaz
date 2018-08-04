package controller;

import tables.TableMappingController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import dataaccess.User;

public class BookController {

	private User user;

	public BookController(User user) {
		this.user = user;
	}

	//@Override
	public void start(Stage stage) throws Exception {
		FXMLLoader root = new FXMLLoader(getClass().getResource("/ui/book-list.fxml"));

		stage.setTitle("FXML Welcome");
		TableMappingController controller = new TableMappingController(stage, this.user);
		root.setController(controller);
		stage.setScene(new Scene(root.load(), 600, 400));

		stage.show();
	}
}