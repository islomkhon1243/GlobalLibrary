package com.libraryproject;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.*;
import java.util.Calendar;
import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.concurrent.Executor;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class SignUpController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField email;

    @FXML
    private TextField password;

    @FXML
    private TextField repeatPassword;

    @FXML
    private Button signupButton;

    @FXML
    private CheckBox termsCheck;

    @FXML
    private TextField username;

    @FXML
    private Button backButton;

    public void initialize() {

        signupButton.setOnAction(actionEvent -> {
            try {
                signUpNewUser();
            } catch (SQLException | IOException e) {
                e.printStackTrace();
            }
        });

    }

    public void signUpNewUser() throws SQLException, IOException {
        ConnectionDB connectionDB = new ConnectionDB();

        String userName = username.getText();
        String userEmail = email.getText();
        String userPassword = password.getText();

        User user = new User(userName, userEmail, userPassword);

        connectionDB.signUpUser(user);

        if (user.isResultSet() == true){
            System.out.println("Insert was successful!");
            openNewScene("signUpSuccess.fxml");
        }
    }

    public void openNewScene(String window) throws IOException {
        signupButton.getScene().getWindow().hide();

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource(window));

        fxmlLoader.load();

        Parent root = fxmlLoader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    void back(ActionEvent event) throws IOException {
        openNewScene("homepage.fxml");
    }
}
