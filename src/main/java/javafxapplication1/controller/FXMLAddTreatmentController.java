package javafxapplication1.controller;

import javafxapplication1.util.ConnectionUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class FXMLAddTreatmentController implements Initializable {

    private static final Logger logger = LoggerFactory.getLogger(FXMLAddTreatmentController.class);

    private String currentRole;

    @FXML private MenuBar   myMenuBar;
    @FXML private TextField treatmentid;
    @FXML private TextField treatmentname;
    @FXML private TextField treatmentamount;
    @FXML private Button    saveButton;

    private Connection        con;
    private PreparedStatement preparedStatement;
    private ResultSet         resultSet;

    public FXMLAddTreatmentController() {
        try {
            con = ConnectionUtil.getConnection();
        } catch (SQLException e) {
            logger.error("Error al conectar a la base de datos en AddTreatmentController", e);
        }
    }

    /**
     * Establece el rol actual y deshabilita el botón si no hay permisos.
     */
    public void setCurrentRole(String role) {
        this.currentRole = role;
        logger.info("Rol establecido en AddTreatmentController: {}", role);
        if (!List.of("Practicante", "Operator", "Docente").contains(role)) {
            if (saveButton != null) {
                saveButton.setDisable(true);
                showAlert(
                    AlertType.WARNING,
                    "Acceso restringido",
                    "No tiene permisos para añadir tratamientos con el rol: " + role
                );
            }
        }
    }

    @FXML
    private void logoutButtonAction(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(
            getClass().getResource("/javafxapplication1/view/FXMLDocument.fxml")
        );
        Scene scene = new Scene(root);
        Stage stage = (Stage)((Node)myMenuBar).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    // (Aquí incluye tus otros métodos de navegación, p. ej. addpatientButtonAction, etc.)

    @FXML
    private void saveTreatment(ActionEvent event) {
        // 1) permisos
        if (currentRole != null
         && !List.of("Practicante", "Operator", "Docente").contains(currentRole)) {
            showAlert(
                AlertType.WARNING,
                "Acceso restringido",
                "No tiene permisos para añadir tratamientos con el rol: " + currentRole
            );
            return;
        }

        // 2) campos obligatorios
        if (treatmentid.getText().isEmpty()
         || treatmentname.getText().isEmpty()
         || treatmentamount.getText().isEmpty()) {
            showAlert(
                AlertType.WARNING,
                "Datos incompletos",
                "Por favor complete todos los campos obligatorios"
            );
            return;
        }

        // 3) formatos
        if (!isNumeric(treatmentid.getText())) {
            showAlert(
                AlertType.WARNING,
                "Error de formato",
                "El ID debe ser un valor numérico"
            );
            return;
        }
        if (!isDecimal(treatmentamount.getText())) {
            showAlert(
                AlertType.WARNING,
                "Error de formato",
                "El monto debe ser un valor decimal válido"
            );
            return;
        }

        // 4) inserción
        try {
            preparedStatement = con.prepareStatement(
                "INSERT INTO treatment (treatmentid, treatmentname, treatmentamount) VALUES (?,?,?)"
            );
            preparedStatement.setInt   (1, Integer.parseInt(treatmentid.getText()));
            preparedStatement.setString(2, treatmentname.getText());
            preparedStatement.setString(3, treatmentamount.getText());
            preparedStatement.executeUpdate();

            // Verificar que se guardó
            preparedStatement = con.prepareStatement(
                "SELECT 1 FROM treatment WHERE treatmentid = ?"
            );
            preparedStatement.setInt(1, Integer.parseInt(treatmentid.getText()));
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Parent home = FXMLLoader.load(
                    getClass().getResource("/javafxapplication1/view/FXMLHome.fxml")
                );
                Scene homeScene = new Scene(home);
                Stage stage = (Stage)((Node)myMenuBar).getScene().getWindow();
                stage.setScene(homeScene);
                stage.show();
            }

        } catch (SQLException | IOException e) {
            logger.error("Error al guardar tratamiento", e);
            showAlert(
                AlertType.ERROR,
                "Error de base de datos",
                "No se pudo guardar el tratamiento: " + e.getMessage()
            );
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Aplicar restricciones si el rol ya estaba establecido
        if (currentRole != null) {
            setCurrentRole(currentRole);
        }
    }

    // Helpers
    private boolean isNumeric(String s) {
        return s != null && s.matches("\\d+");
    }

    private boolean isDecimal(String s) {
        return s != null && s.matches("\\d+(\\.\\d+)?");
    }

    private void showAlert(AlertType type, String title, String msg) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
