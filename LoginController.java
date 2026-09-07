package oopfinalproject.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import oopfinalproject.model.Profile;
import oopfinalproject.model.Session;

public class LoginController {

    @FXML private TextField namefield;
    @FXML private PasswordField loginPass;
    @FXML private Button loginBtn;

    @FXML
    private void initialize() {

        // only letters n spaces
        // using textformatter for this
        namefield.setTextFormatter(new TextFormatter<>(change -> {
            if (change.getControlNewText().matches("[a-zA-Z ]*")) {
                return change; // using generic class
            }
            return null;
        }));

        // only digits
        loginPass.setTextFormatter(new TextFormatter<>(change -> {
            if (change.getControlNewText().matches("\\d*")) {
                return change;
            }
            return null;
        }));
    }

    @FXML
    private void loginBtpressed() {

        String name = namefield.getText().trim();
        String id   = loginPass.getText().trim();

        // if not present
        if (name.isEmpty() || id.isEmpty()) return; // nothing is empty check

        // SAVE SESSION
        Session.currentUser = new Profile(name, id);
        Session.progressMap.clear();

        try {
            Stage stage = (Stage) loginBtn.getScene().getWindow();
            Parent root = FXMLLoader.load(
                    getClass().getResource("/fxml/Menu.fxml")
            );
            stage.setScene(new Scene(root,1200.0,700.0));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
