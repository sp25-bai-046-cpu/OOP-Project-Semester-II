package oopfinalproject.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import oopfinalproject.model.FileHandlingForApp;
import oopfinalproject.model.Session;

public class MenuController {

    @FXML private Button studyMaterialBt;
    @FXML private Button practiceQuiz1Bt;
    @FXML private Button practiceQuiz2Bt;
    @FXML private Button copilotBt;
    @FXML private Button progressBt;
    @FXML private Button logoutBt;
    @FXML private Button exitBtn;

    // scene loader
    private void load(String fxml, Button source) {
            try {
                Stage stage = (Stage) source.getScene().getWindow();

                Parent root = FXMLLoader.load(
                        getClass().getResource("/fxml/" + fxml)
                );

                // KEEP SAME SIZE
                Scene scene = stage.getScene();
                scene.setRoot(root);

            } catch (Exception e) {
                e.printStackTrace();
            }

    }

    // button actions....
    @FXML
    private void StudyMaterialBt_pressed() {
        load("StudyMaterial.fxml", studyMaterialBt);
    }

    @FXML
    private void PracticeQuiz1Bt_pressed() {
        load("Quiz.fxml", practiceQuiz1Bt);
    }

    @FXML
    private void PracticeQuiz2Bt_pressed() {
        load("TrueFalse.fxml", practiceQuiz2Bt);
    }

    @FXML
    private void CopilotBt_pressed() {
        load("CoPilot.fxml", copilotBt);
    }

    @FXML
    private void ProgressBt_pressed() {
        load("progress.fxml", progressBt);
    }
    @FXML
    private void setLogoutBt_pressed() {
            try {
                Stage stage = (Stage) logoutBt.getScene().getWindow();

                // SAVE USER DATA
                FileHandlingForApp file =
                        new FileHandlingForApp("users.txt");
                file.saveUser(Session.currentUser);

                // session backup of all the users
                Session.reset();
                Parent root = FXMLLoader.load(
                        getClass().getResource("/fxml/Login.fxml")
                );

                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.setMaximized(false);// this layout
                stage.setResizable(true); //after layouts
                stage.setWidth(600);
                stage.setHeight(400);
                stage.centerOnScreen();

                stage.show();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    // exit
    @FXML
    private void setExitBt_pressed() {
        load("Exit.fxml", exitBtn);

    }
}
