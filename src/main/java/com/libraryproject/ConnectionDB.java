package com.libraryproject;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConnectionDB {

    protected static Connection dbConn() {
        Connection conn = null;
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String url = "jdbc:sqlserver://DESKTOP-KJ04LIP\\SQLEXPRESS;user=islam1243;password=islam1243;database=GlobalLibrary;encrypt=true; trustServerCertificate=true;trustStore= C:\\Program Files\\Java\\jre1.8.0_321\\lib\\security\\cacerts;trustStorePassword=changeit";
            conn = DriverManager.getConnection(url);
        }
        catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(ConnectionDB.class.getName()).log(Level.SEVERE,null,ex);

        }
        return conn;
    }

    public void signUpUser(User user) throws SQLException {
        String insert = "INSERT INTO " + Const.USER_TABLE + "(" + Const.USERS_USERNAME + "," + Const.USERS_EMAIL + "," + Const.USERS_PASSWORD + ")" + "VALUES(?, ?, ?)";

        try {
            PreparedStatement prSt = dbConn().prepareStatement(insert);
            prSt.setString(1, user.getUserName());
            prSt.setString(2, user.getEmail());
            prSt.setString(3, user.getPassword());

            int resultSet = prSt.executeUpdate();
            if (resultSet == 1){
                user.setResultSet(true);
            }
            else{
                user.setResultSet(false);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ResultSet getUser(User user){
        ResultSet resultSet = null;

        String select = "SELECT * FROM " + Const.USER_TABLE + " WHERE " + Const.USERS_USERNAME + "=? AND " + Const.USERS_PASSWORD + "=?";

        try {
            PreparedStatement prSt = dbConn().prepareStatement(select);
            prSt.setString(1, user.getUserName());
            prSt.setString(2, user.getPassword());

            resultSet = prSt.executeQuery();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return resultSet;
    }

    public ResultSet getAllUsers(User user){
        ResultSet resultSet = null;

        String select = "SELECT * FROM " + Const.USER_TABLE;

        try {
            PreparedStatement prSt = dbConn().prepareStatement(select);

            resultSet = prSt.executeQuery();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return resultSet;
    }

}
