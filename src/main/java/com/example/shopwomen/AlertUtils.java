package com.example.shopwomen;

import javafx.scene.control.Alert;

public class AlertUtils {

    public static void showAlert(Alert.AlertType type, String title, String header, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public static void showErrorAlert(String title, String header, String content) {
        showAlert(Alert.AlertType.ERROR, title, header, content);
    }

    public static void showInformationAlert(String title, String header, String content) {
        showAlert(Alert.AlertType.INFORMATION, title, header, content);
    }

    public static void showWarningAlert(String title, String header, String content) {
        showAlert(Alert.AlertType.WARNING, title, header, content);
    }

}
