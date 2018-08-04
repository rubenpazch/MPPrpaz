package tables;

import java.net.URL;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.ResourceBundle;

import controller.BookCopyController;
import controller.MainMenuController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;
import business.Book;
import dataaccess.User;
import util.Util;

/**
 *
 * @author
 */
public class TableMappingController implements Initializable {
	static long nextId = 1;
	private Book book;
	private User user;

	private Stage primaryStage;

	// The table and columns
	@FXML
	TableView<Book> itemTbl;

	@FXML
	TableColumn itemIsbn;
	@FXML
	TableColumn itemName;
	@FXML
	TableColumn itemCheckoutDate;

	@FXML
	Button btnBack;
	// The table's data
	ObservableList<Book> data;

	public TableMappingController(Stage primaryStage, User user) {
		if (primaryStage == null) {
			Util.showAlert("Stage null", "Stage null", AlertType.ERROR);
		}
		this.user = user;
		this.primaryStage = primaryStage;
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {

		// Set up the table data
		itemIsbn.setCellValueFactory(new PropertyValueFactory<Book, String>("isbn"));
		itemName.setCellValueFactory(new PropertyValueFactory<Book, String>("title"));
		itemCheckoutDate.setCellValueFactory(new PropertyValueFactory<Book, Integer>("maxCheckoutLength"));

		data = FXCollections.observableArrayList();
		DataAccess db = new DataAccessFacade();
		HashMap<String, Book> books = db.readBooksMap();
		for (Entry<String, Book> entry : books.entrySet()) {
			data.add(entry.getValue());
		}

		itemTbl.setItems(data);

		btnBack.setOnAction((event) -> {
			MainMenuController mainMenuController = new MainMenuController(user);
			try {
				mainMenuController.start(this.primaryStage);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});

		itemTbl.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
			if (newSelection != null) {

				BookCopyController bookcontroller = new BookCopyController(newSelection, this.user);
				try {

					bookcontroller.start(this.primaryStage);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		});

	}

}
