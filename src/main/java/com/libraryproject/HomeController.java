package com.libraryproject;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class HomeController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField login;

    @FXML
    private Button loginButton;

    @FXML
    private TextField password;

    @FXML
    private Button signupButton;

    @FXML
    private AnchorPane firstPane;

    private Connection conn = null;
    private PreparedStatement pat = null;

    @FXML
    void initialize() {
        conn = com.libraryproject.ConnectionDB.dbConn();

        loginButton.setOnAction(actionEvent1 -> {
            String userName = login.getText().trim();
            String userPassword = password.getText().trim();

            if (!userName.equals("") && !userPassword.equals("")){
                try {
                    loginUser(userName, userPassword);
                } catch (SQLException | IOException e) {
                    e.printStackTrace();
                }
            }
            else {
//                System.out.println("Login and password is empty!");
                Stage stage = (Stage) firstPane.getScene().getWindow();

                Alert.AlertType type = Alert.AlertType.ERROR;
                Alert alert = new Alert(type, "");

                alert.initModality(Modality.APPLICATION_MODAL);
                alert.initOwner(stage);

                alert.getDialogPane().setContentText("Login and password is empty!");
                alert.getDialogPane().setHeaderText("Database exception: ");
                alert.show();
            }
        });

        signupButton.setOnAction(actionEvent -> {
            try {
                openNewScene("signUp.fxml");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

    }

    @FXML
    void loginUser(String userName, String userPassword) throws SQLException, IOException {
        ConnectionDB connectionDB = new ConnectionDB();
        User user = new User();
        user.setUserName(userName);
        user.setPassword(userPassword);
        ResultSet resultSet = connectionDB.getUser(user);

        int counter = 0;

        while (resultSet.next()){
            counter++;
        }

        if (counter >= 1){
            System.out.println("Success!");
            openNewScene("userpage.fxml");
        }
        else {
            Stage stage = (Stage) firstPane.getScene().getWindow();

            Alert.AlertType type = Alert.AlertType.ERROR;
            Alert alert = new Alert(type, "");

            alert.initModality(Modality.APPLICATION_MODAL);
            alert.initOwner(stage);

            alert.getDialogPane().setContentText("Login or password is incorrect!");
            alert.getDialogPane().setHeaderText("Database exception: ");
            alert.show();
        }

        if (Objects.equals(userName, "admin") && Objects.equals(userPassword, "admin")){
            loginButton.getScene().getWindow().hide();

            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("libraryAdmin.fxml"));

            fxmlLoader.load();

            Parent root = fxmlLoader.getRoot();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        }


    }

    public void openNewScene(String window) throws IOException {
        loginButton.getScene().getWindow().hide();

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource(window));

        fxmlLoader.load();

        Parent root = fxmlLoader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    }

}
