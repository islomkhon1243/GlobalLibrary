package com.libraryproject;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class LibraryAdminController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableColumn<User, String> col_email;

    @FXML
    private TableColumn<User, String> col_password;

    @FXML
    private TableColumn<User, String> col_username;

    @FXML
    private TextField emailAdd;

    @FXML
    private TextField passwordAdd;

    @FXML
    private AnchorPane secondPane;

    @FXML
    private TableView<User> userTable;

    @FXML
    private TextField usernameAdd;

    @FXML
    private Button logOutButton;

    ObservableList<User> observableList = FXCollections.observableArrayList();

    @FXML
    void buttonAdd(ActionEvent event) {
        observableList.add(new User(usernameAdd.getText(), emailAdd.getText(), passwordAdd.getText()));
    }

    @FXML
    void initialize() throws SQLException {

        userTable.setEditable(true);

        Connection connection = ConnectionDB.dbConn();
        ResultSet resultSet = connection.createStatement().executeQuery("select * from UserData");

        while (resultSet.next()){
            observableList.add(new User(resultSet.getString("username"), resultSet.getString("email"), resultSet.getString("password")));
        }

        col_username.setCellValueFactory(new PropertyValueFactory<>("userName"));
        col_username.setCellFactory(TextFieldTableCell.forTableColumn());
        col_username.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<User, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<User, String> userStringCellEditEvent) {
                User user = userStringCellEditEvent.getRowValue();
                user.setUserName(userStringCellEditEvent.getNewValue());
            }
        });

        col_email.setCellValueFactory(new PropertyValueFactory<>("email"));
        col_email.setCellFactory(TextFieldTableCell.forTableColumn());
        col_email.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<User, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<User, String> userStringCellEditEvent) {
                User user = userStringCellEditEvent.getRowValue();
                user.setEmail(userStringCellEditEvent.getNewValue());
            }
        });

        col_password.setCellValueFactory(new PropertyValueFactory<>("password"));
        col_password.setCellFactory(TextFieldTableCell.forTableColumn());
        col_password.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<User, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<User, String> userStringCellEditEvent) {
                User user = userStringCellEditEvent.getRowValue();
                user.setPassword(userStringCellEditEvent.getNewValue());
            }
        });

        userTable.setItems(observableList);
        userTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }

    @FXML
    void logOut(ActionEvent event) throws IOException {
        logOutButton.getScene().getWindow().hide();

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("homepage.fxml"));

        fxmlLoader.load();

        Parent root = fxmlLoader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    }

}
