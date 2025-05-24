package javafxapplication1.controller;

import javafxapplication1.util.ConnectionUtil;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

public class FXMLRegisterController implements Initializable {

    @FXML private Label label;
    @FXML private Label label1;
    @FXML private Label label2;
    @FXML private ToggleGroup logintype;
    @FXML private RadioButton logintypeOperator;
    @FXML private RadioButton logintypeDentist;
    @FXML private TextField username;
    @FXML private PasswordField password;
    @FXML private PasswordField confirmpassword;

    private String username1;
    private String password1;
    private String password2;
    private String type1;

    private Connection con;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;

    public FXMLRegisterController(){
        try {
            con = ConnectionUtil.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void loginPage(ActionEvent event) throws IOException {
        Parent login = FXMLLoader.load(
            getClass().getResource("/javafxapplication1/view/FXMLDocument.fxml")
        );
        Scene scene = new Scene(login);
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void registerButtonAction(ActionEvent event) throws IOException {
        // Limpiar mensajes
        label.setText("");
        label1.setText("");
        label2.setText("");

        username1 = username.getText();
        password1 = password.getText();
        password2 = confirmpassword.getText();

        if (logintypeOperator.isSelected()) {
            type1 = "operator";
        } else if (logintypeDentist.isSelected()) {
            type1 = "dentist";
        } else {
            type1 = "";
        }

        // Validaciones básicas
        if (username1.isEmpty()) {
            label.setText("Username is empty");
            return;
        }
        if (password1.isEmpty()) {
            label2.setText("Password is empty");
            return;
        }
        if (!password1.equals(password2)) {
            label1.setText("Passwords not matching");
            return;
        }
        if (type1.isEmpty()) {
            label2.setText("Select a user type");
            return;
        }

        try {
            // Verificar si el usuario ya existe
            preparedStatement = con.prepareStatement(
                "SELECT 1 FROM login WHERE username = ? AND type = ?"
            );
            preparedStatement.setString(1, username1);
            preparedStatement.setString(2, type1);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                label.setText("Username already exists");
            } else {
                // Insertar nuevo usuario
                preparedStatement = con.prepareStatement(
                    "INSERT INTO login (username, password, type) VALUES (?, ?, ?)"
                );
                preparedStatement.setString(1, username1);
                preparedStatement.setString(2, password1);
                preparedStatement.setString(3, type1);
                preparedStatement.executeUpdate();

                // Llevar a la pantalla principal
                Parent home = FXMLLoader.load(
                    getClass().getResource("/javafxapplication1/view/FXMLHome.fxml")
                );
                Scene scene = new Scene(home);
                Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.show();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleClose(ActionEvent event) {
        System.exit(0);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // No necesita inicialización adicional
    }
}
