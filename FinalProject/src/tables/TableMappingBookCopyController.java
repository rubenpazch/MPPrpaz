package tables;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;
import controller.BookController;
import controller.MainMenuController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;
import business.Book;
import business.BookCopy;
import dataaccess.User;

/**
 *
 * @author ericjbruno
 */
public class TableMappingBookCopyController implements Initializable {
	static long nextId = 1;
	private User user;
	private Stage primaryStage;

	Book book;

	// The table and columns
	@FXML
	TableView<BookCopy> itemTbl;

	@FXML
	Label lblBookName;

	@FXML
	TableColumn CopyId;
	@FXML
	TableColumn isAvaible;

	@FXML
	TableColumn overdue;
	@FXML
	TableColumn possesion;

	@FXML
	Button btnnewCopy;

	@FXML
	Button btnBack;

	// The table's data
	ObservableList<BookCopy> data;

	public TableMappingBookCopyController(Book book, Stage primaryStage, User user) {
		super();
		this.book = book;
		this.user = user;
		this.primaryStage = primaryStage;
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		DataAccess db = new DataAccessFacade();
		// Set up the table data

		lblBookName.setText(String.format("%s / %s", this.book.getIsbn(), this.book.getTitle()));

		CopyId.setCellValueFactory(new PropertyValueFactory<BookCopy, String>("copyNum"));
		isAvaible.setCellValueFactory(new PropertyValueFactory<BookCopy, String>("exists"));
		overdue.setCellValueFactory(new PropertyValueFactory<BookCopy, String>("overdue"));
		possesion.setCellValueFactory(new PropertyValueFactory<BookCopy, String>("possesion"));

		// Integer>("copyNum"));

		data = FXCollections.observableArrayList();

		HashMap<String, Book> books = db.readBooksMap();

		if (book.hasCopies()) {
			for (BookCopy bookCopy : book.getCopies()) {
				data.add(bookCopy);
			}
		}

		itemTbl.setItems(data);

		itemTbl.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
			if (newSelection != null) {
				System.out.println(newSelection);

			}
		});

		btnnewCopy.setOnAction((event) -> {
			BookCopy bookcopy = this.book.addCopy();
			data.add(bookcopy);
			db.saveAbook(this.book);
			itemTbl.refresh();
		});

		btnBack.setOnAction((event) -> {
			BookController bookcontroller = new BookController(user);
			try {
				bookcontroller.start(this.primaryStage);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});

	}

}
