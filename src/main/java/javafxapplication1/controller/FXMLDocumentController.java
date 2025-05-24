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

public class FXMLDocumentController implements Initializable {
    
    @FXML private Label label;
    @FXML private Label label1;
    @FXML private Label label2;
    @FXML private ToggleGroup logintype;
    @FXML private RadioButton logintypeOperator;
    @FXML private RadioButton logintypeDentist;
    @FXML private TextField username;
    @FXML private PasswordField password;
    
    // Campos internos para almacenar los valores introducidos
    private String username1;
    private String password1;
    private String type1;
    
    private Connection con;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;
    
    public FXMLDocumentController() {
        try {
            con = ConnectionUtil.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
            // Aquí puedes mostrar un alert si quieres
        }
    }
    
    @FXML
    private void loginButtonAction(ActionEvent event) throws IOException {
        // Resetear mensajes
        label .setText("");
        label1.setText("");
        label2.setText("");
        
        username1 = username.getText();
        password1 = password.getText();
        
        if (logintypeOperator.isSelected()) {
            type1 = "operator";
        } else if (logintypeDentist.isSelected()) {
            type1 = "dentist";
        } else {
            label2.setText("Seleccione un tipo de usuario");
            return;
        }
        
        if (username1.isEmpty()) {
            label.setText("Username is Empty");
            return;
        }
        if (password1.isEmpty()) {
            label1.setText("Password is Empty");
            return;
        }
        
        // Intentar login
        String sql = "SELECT * FROM login WHERE username = ? AND password = ? AND type = ?";
        try {
            preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, username1);
            preparedStatement.setString(2, password1);
            preparedStatement.setString(3, type1);
            resultSet = preparedStatement.executeQuery();
            
            if (resultSet.next()) {
                // Login correcto → cargar home
                Parent home = FXMLLoader.load(
                    getClass().getResource("/javafxapplication1/view/FXMLHome.fxml")
                );
                Scene scene = new Scene(home);
                Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.show();
            } else {
                label2.setText("Username and Password Doesn't Exist");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    @FXML
    private void registerNewAccount(ActionEvent event) throws IOException {
        Parent register = FXMLLoader.load(
            getClass().getResource("/javafxapplication1/view/FXMLRegister.fxml")
        );
        Scene scene = new Scene(register);
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void handleClose(ActionEvent event) {
        System.exit(0);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // No hay nada más que inicializar de momento
    }    
}
