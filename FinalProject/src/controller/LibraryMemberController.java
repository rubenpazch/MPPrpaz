package controller;

import java.util.HashMap;
import java.util.Map.Entry;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;
import business.Address;
import business.LibraryMember;
import dataaccess.User;

public class LibraryMemberController  {

	private User user;
	private Stage primaryStage;
	private TextField txtFirtsName;

	private TextField txtLastName;
	private TextField txtCity;
	private TextField txtmemberId;
	private TextField txtState;
	private TextField txtStreet;
	private DataAccess db;
	private TextField txtZip;

	private TextField txtPhone;


	LibraryMemberController(User user) {
		this.user = user;
	}

	public void setUser(String txtmemberId, String txtFirtsName, String txtLastName, String txtCity, String txtState,
			String txtStreet, String txtZip, String txtPhone) {
		this.txtmemberId.setText(txtmemberId);
		this.txtFirtsName.setText(txtFirtsName);
		this.txtLastName.setText(txtLastName);

		this.txtCity.setText(txtCity);
		this.txtState.setText(txtState);
		this.txtStreet.setText(txtStreet);
		this.txtZip.setText(txtZip);
		this.txtPhone.setText(txtPhone);
	}

	//@Override
	public void start(Stage stage) throws Exception {
		this.primaryStage = stage;
		Parent root = FXMLLoader.load(getClass().getResource("/ui/addmember.fxml"));

		stage.setTitle("FXML Welcome");
		stage.setScene(new Scene(root, 600, 500));

		txtmemberId = (TextField) root.lookup("#txtmemberId");
		txtFirtsName = (TextField) root.lookup("#txtFirtsName");
		txtLastName = (TextField) root.lookup("#txtLastName");
		txtCity = (TextField) root.lookup("#txtCity");
		txtState = (TextField) root.lookup("#txtState");
		txtStreet = (TextField) root.lookup("#txtStreet");
		txtZip = (TextField) root.lookup("#txtZip");
		txtPhone = (TextField) root.lookup("#txtPhone");

		ObservableList<LibraryMember> data = FXCollections.observableArrayList();
		ListView<LibraryMember> ListUsers = (ListView<LibraryMember>) root.lookup("#ListUsers"); // new
																									// ListView<String>(data);
		//System.out.println(ListUsers);
		// listView.setPrefSize(200, 250);

		DataAccess db = new DataAccessFacade();
		HashMap<String, LibraryMember> users = db.readMemberMap();
		for (Entry<String, LibraryMember> entry : users.entrySet()) {
			data.add(entry.getValue());

		}

		ListUsers.setItems(data);
		ListUsers.getSelectionModel().selectedItemProperty().addListener(
				(ChangeListener<? super LibraryMember>) (ChangeListener<? super LibraryMember>) (ChangeListener<? super LibraryMember>) (
						ObservableValue<? extends LibraryMember> ov, LibraryMember old_val, LibraryMember new_val) -> {
					Address address = new_val.getAddress();
					this.setUser(new_val.getMemberId(), new_val.getFirstName(), new_val.getLastName(),
							address.getCity(), address.getState(), address.getStreet(), address.getZip(),
							new_val.getTelephone());

				});





		Button btnNewUser = (Button) root.lookup("#btnNewUser");
		btnNewUser.setOnAction((event) -> {
			this.setUser("NEW", "", "", "", "", "", "", "");
		});

		Button btnSave = (Button) root.lookup("#btnSave");
		btnSave.setOnAction((event) -> {

			this.db = new DataAccessFacade();

			String mId = String.valueOf(db.readMemberMap().size()+1);
			if (!this.txtmemberId.getText().equals("NEW")) {
				mId = this.txtmemberId.getText();
			}

			Address add = new Address(txtStreet.getText(), txtCity.getText(), txtState.getText(), txtZip.getText());
			LibraryMember lm = new LibraryMember(mId, txtFirtsName.getText(), txtLastName.getText(), txtCity.getText(),add);
			db.saveNewMember(lm);

			if (data.contains(lm)) {
				int index = data.indexOf(lm);
				data.set(index, lm);

			} else {
				data.add(lm);

			}

			ListUsers.setItems(data);
			ListUsers.refresh();
			this.setUser("NEW", "", "", "", "", "", "", "");

		});



		Button btnBack = (Button) root.lookup("#btBackMain");
		btnBack.setOnAction((event) -> {
			try {
				MainMenuController mainMenuController = new MainMenuController(user);
				mainMenuController.start(this.primaryStage);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});

		stage.show();
	}
}