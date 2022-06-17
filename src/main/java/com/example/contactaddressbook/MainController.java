package com.example.contactaddressbook;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;


public class MainController implements Initializable {

    @FXML
    private TableColumn<Contact, Integer> columnID;

    @FXML
    private TableColumn<Contact, String> columnFirstName;

    @FXML
    private TableColumn<Contact, String> columnLastName;

    @FXML
    private TableColumn<Contact, String> columnPhoneNumber;

    @FXML
    private TableColumn<Contact, String> columnEmailAddress;

    @FXML
    private TextField textFieldID;

    @FXML
    private TextField textFieldFirstName;

    @FXML
    private TextField textFieldLastName;

    @FXML
    private TextField textFieldPhoneNumber;

    @FXML
    private TextField textFieldEmailAddress;

    @FXML
    private TableView<Contact> contactAddress;

    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;
    private static Connection connection = null;

    //Query
    private static final String INSERT_CONTACT = "INSERT REGISTER VALUES(?, ?, ?, ?, ?)";
    private static final String UPDATE_CONTACT = "UPDATE REGISTER SET FIRST_NAME = ?, " +
            "LAST_NAME = ?," +
            "PHONE_NUMBER = ?," +
            "EMAIL = ? " +
            "WHERE ID = ?";
    private static final String DELETE_CONTACT = "DELETE FROM REGISTER WHERE ID = ?";

    @FXML
    private Button addContactButton;
    @FXML
    private Button updateContactButton;
    @FXML
    private Button deleteContactButton;

    @FXML
    void handleButtonAction(ActionEvent event) {
        if (event.getSource() == addContactButton)
            addContact();
        else if (event.getSource() == updateContactButton)
            updateContact();
        else
            deleteContactButton();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        showContact();

    }
//........................CONNECTION.....................................

    public static Connection getConnection() {

        final Logger logger = Logger.getLogger(MainController.class.getCanonicalName());
        final String DB_DRIVER = "com.mysql.cj.jdbc.Driver";
        final String DB_CONNECTION = "jdbc:mysql://localhost:3306/contact_address";
        final String DB_USER = "root";
        final String DB_PASSWORD = "#@^%B1g+-ret";

        try {
            Class.forName(DB_DRIVER);
        } catch (ClassNotFoundException exception) {
            logger.log(Level.SEVERE, exception.getMessage());
        }
        try {
            connection  = DriverManager.getConnection(DB_CONNECTION, DB_USER, DB_PASSWORD);
            return connection;
        } catch (SQLException exception) {
            logger.log(Level.SEVERE, exception.getMessage(), exception.getSQLState());
        }

        return null;
    }



    public ObservableList<Contact> getContactList() {
        ObservableList<Contact> contactList = FXCollections.observableArrayList();
        Connection connection = getConnection();
        String query = "SELECT * FROM REGISTER";
        Statement statement = null;
        try {
            if (connection != null)
                statement = connection.createStatement();
            if (statement != null)
                resultSet = statement.executeQuery(query);

            Contact contact;
            if (resultSet != null) {
                while (resultSet.next()) {
                    // Get data from the database
                    contact = new Contact(resultSet.getInt("ID"),
                            resultSet.getString("first_name"),
                            resultSet.getString("last_name"),
                            resultSet.getString("phone_number"),
                            resultSet.getString("email")
                    );
                    contactList.add(contact);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return contactList;
    }

    public void showContact() {
        ObservableList<Contact> contactList = getContactList();

        // Put data into the App table
        columnID.setCellValueFactory(new PropertyValueFactory<>("ID"));
        columnFirstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        columnLastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        columnPhoneNumber.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        columnEmailAddress.setCellValueFactory(new PropertyValueFactory<>("email"));

        contactAddress.setItems(contactList);
    }

    private void addContact() {
        try {
            preparedStatement = connection.prepareStatement(INSERT_CONTACT);
            preparedStatement.setString(1, textFieldID.getText());
            preparedStatement.setString(2, textFieldFirstName.getText());
            preparedStatement.setString(3, textFieldLastName.getText());
            preparedStatement.setString(4, textFieldPhoneNumber.getText());
            preparedStatement.setString(5, textFieldEmailAddress.getText());
            preparedStatement.executeUpdate();
            showContact();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void updateContact() {
        try {
            preparedStatement = connection.prepareStatement(UPDATE_CONTACT);
            preparedStatement.setString(1, textFieldFirstName.getText());
            preparedStatement.setString(2, textFieldLastName.getText());
            preparedStatement.setString(3, textFieldPhoneNumber.getText());
            preparedStatement.setString(4, textFieldEmailAddress.getText());
            preparedStatement.setString(5, textFieldID.getText());
            preparedStatement.executeUpdate();
            showContact();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void deleteContactButton() {
        try {
            preparedStatement = connection.prepareStatement(DELETE_CONTACT);
            preparedStatement.setString(1, textFieldID.getText());
            preparedStatement.executeUpdate();
            showContact();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    public void handleMouseAction(MouseEvent event) {
        Contact contact = contactAddress.getSelectionModel().getSelectedItem();
        textFieldID.setText(String.valueOf(contact.getID()));
        textFieldFirstName.setText(contact.getFirstName());
        textFieldLastName.setText(contact.getLastName());
        textFieldPhoneNumber.setText(contact.getPhoneNumber());
        textFieldEmailAddress.setText(contact.getEmail());
    }
}
