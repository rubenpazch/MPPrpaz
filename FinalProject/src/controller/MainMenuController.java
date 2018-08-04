package controller;

//import controller.BookController;
//import control.CheckEntriesMemberController;
//import control.CheckoutController;
//import control.LibraryMemberController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import dataaccess.Auth;
import dataaccess.User;
import util.Util;

public class MainMenuController {

	public static void main(String[] args) {
		//Application.launch(MainMenuController.class, args);
	}

	private User user;

	public MainMenuController(User user) {
		this.user = user;
	}

	public void start(Stage stage) throws Exception {

		Parent root = FXMLLoader.load(getClass().getResource("/ui/main.fxml"));
		stage.setTitle("FXML");

		Button btnCheckoutBooks = (Button) root.lookup("#btnCheckoutBooks");
		Button btnBooks = (Button) root.lookup("#btnBooks");
		Button btnLibraryMembers = (Button) root.lookup("#btnLibraryMembers");
		Button btnMembersRecords = (Button) root.lookup("#btnMembersRecords");
		Button btnSignOut = (Button) root.lookup("#btSignOut");

		stage.setScene(new Scene(root));

		btnSignOut.setOnAction((event) -> {
			LoginController login = new LoginController();
			try {
				login.start(stage);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});

		// TODO Auto-generated method stub
		btnCheckoutBooks.setOnAction((event) -> {
			if (user.getAuthorization().equals(Auth.ADMIN)) {
				Util.showAlert("Administrator can not checkout books", "Permission denied", AlertType.ERROR);
				return;
			}

			CheckoutController checkoutcontroller = new CheckoutController(this.user);

			try {
				checkoutcontroller.start(stage);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});

		btnBooks.setOnAction((event) -> {
			if (user.getAuthorization().equals(Auth.LIBRARIAN)) {
				Util.showAlert("Librarian cannot add Books", "Permission denied", AlertType.ERROR);
				return;
			}
			BookController bc = new BookController(user);
			try {
				bc.start(stage);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});

		btnLibraryMembers.setOnAction((event) -> {
			if (user.getAuthorization().equals(Auth.LIBRARIAN)) {
				Util.showAlert("Librarian cannot add Member", "Permission denied", AlertType.ERROR);
				return;
			}
			//System.out.println("btnLibraryMembers");
			try {
				LibraryMemberController librarymember = new LibraryMemberController(this.user);
				librarymember.start(stage);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		});

		btnMembersRecords.setOnAction((event) -> {

		//	CheckEntriesMemberController recordsentries = new CheckEntriesMemberController(this.user);
			try {
				//recordsentries.start(stage);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		});

		stage.show();
	}


}
