package javafxapplication1.controller;

import javafxapplication1.util.ConnectionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class FXMLUpdatePatientController implements Initializable {
    private static final Logger logger = LoggerFactory.getLogger(FXMLUpdatePatientController.class);

    @FXML private MenuBar myMenuBar;
    @FXML private TextField patientid;
    @FXML private TextField patientname;
    @FXML private TextField patientage;
    @FXML private ChoiceBox<String> patientgender;
    @FXML private TextArea patientaddress;
    @FXML private TextField patientphone;
    @FXML private ChoiceBox<String> patientbloodgroup;
    @FXML private TextArea patienthealthproblems;

    private Connection con;
    private PreparedStatement ps;
    private ResultSet rs;

    public FXMLUpdatePatientController() {
        try {
            con = ConnectionUtil.getConnection();
        } catch (SQLException e) {
            logger.error("Error al conectar con la BD", e);
        }
    }

    // ----------------------------------------
    // Métodos de navegación entre pantallas
    // ----------------------------------------
    @FXML private void logoutButtonAction(ActionEvent e)             throws IOException { switchScene("/javafxapplication1/view/FXMLDocument.fxml"); }
    @FXML private void updatepatientButtonAction(ActionEvent e)     throws IOException { switchScene("/javafxapplication1/view/FXMLUpdatePatient.fxml"); }
    @FXML private void addpatientButtonAction(ActionEvent e)        throws IOException { switchScene("/javafxapplication1/view/FXMLAddPatient.fxml"); }
    @FXML private void adddentistButtonAction(ActionEvent e)        throws IOException { switchScene("/javafxapplication1/view/FXMLAddDentist.fxml"); }
    @FXML private void updatedentistButtonAction(ActionEvent e)     throws IOException { switchScene("/javafxapplication1/view/FXMLUpdateDentist.fxml"); }
    @FXML private void addappointmentButtonAction(ActionEvent e)    throws IOException { switchScene("/javafxapplication1/view/FXMLAddAppointment.fxml"); }
    @FXML private void updateappointmentButtonAction(ActionEvent e) throws IOException { switchScene("/javafxapplication1/view/FXMLUpdateAppointment.fxml"); }
    @FXML private void addtreatmentButtonAction(ActionEvent e)      throws IOException { switchScene("/javafxapplication1/view/FXMLAddTreatment.fxml"); }
    @FXML private void updatetreatmentButtonAction(ActionEvent e)   throws IOException { switchScene("/javafxapplication1/view/FXMLUpdateTreatment.fxml"); }
    @FXML private void addbillButtonAction(ActionEvent e)           throws IOException { switchScene("/javafxapplication1/view/FXMLAddBill.fxml"); }
    @FXML private void updatebillButtonAction(ActionEvent e)        throws IOException { switchScene("/javafxapplication1/view/FXMLUpdateBill.fxml"); }

    private void switchScene(String fxml) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(fxml));
        Stage stage = (Stage)((Node)myMenuBar).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    // ----------------------------------------
    // Lógica para actualizar paciente
    // ----------------------------------------
    @FXML
    private void updatePatient(ActionEvent event) {
        // Validación de campos vacíos
        if (patientid.getText().isBlank() ||
            patientname.getText().isBlank() ||
            patientage.getText().isBlank() ||
            patientgender.getValue() == null ||
            patientaddress.getText().isBlank() ||
            patientphone.getText().isBlank() ||
            patientbloodgroup.getValue() == null ||
            patienthealthproblems.getText().isBlank())
        {
            showAlert(Alert.AlertType.WARNING, "Datos incompletos", "Por favor complete todos los campos.");
            return;
        }

        int id;
        try {
            id = Integer.parseInt(patientid.getText());
        } catch (NumberFormatException ex) {
            showAlert(Alert.AlertType.ERROR, "Formato inválido", "El ID debe ser un número entero.");
            return;
        }

        String sql = ""
            + "UPDATE patient SET "
            + "  patientname        = ?,"
            + "  patientage         = ?,"
            + "  patientgender      = ?,"
            + "  patientaddress     = ?,"
            + "  patientphone       = ?,"
            + "  patientbloodgroup  = ?,"
            + "  patienthealthproblems = ?"
            + " WHERE patientid     = ?";

        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, patientname.getText());
            ps.setString(2, patientage.getText());
            ps.setString(3, patientgender.getValue());
            ps.setString(4, patientaddress.getText());
            ps.setString(5, patientphone.getText());
            ps.setString(6, patientbloodgroup.getValue());
            ps.setString(7, patienthealthproblems.getText());
            ps.setInt   (8, id);

            int updated = ps.executeUpdate();
            if (updated > 0) {
                showAlert(Alert.AlertType.INFORMATION, "Éxito", "Paciente actualizado correctamente.");
                switchScene("/javafxapplication1/view/FXMLHome.fxml");
            } else {
                showAlert(Alert.AlertType.ERROR, "No encontrado", "No existe un paciente con ID: " + id);
            }
        } catch (SQLException | IOException e) {
            logger.error("Error al actualizar paciente", e);
            showAlert(Alert.AlertType.ERROR, "Error", "Ocurrió un problema al actualizar el paciente.");
        }
    }

    // ------------------------
    // Inicialización
    // ------------------------
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        patientgender.getItems().addAll("Male", "Female", "Other");
        patientbloodgroup.getItems().addAll(
            "A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-"
        );
    }

    // ------------------------
    // Helpers
    // ------------------------
    private void showAlert(Alert.AlertType type, String title, String msg) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
