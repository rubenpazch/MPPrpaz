package util;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class Util {
	public static void showAlert(String message, String titleMessage, AlertType type) {
		Alert alert = new Alert(type.ERROR);

		String titleTxt = titleMessage;
		alert.setTitle(titleTxt);
		// alert.setHeaderText("Information Alert");
		alert.setContentText(message);
		alert.show();
	}

}
