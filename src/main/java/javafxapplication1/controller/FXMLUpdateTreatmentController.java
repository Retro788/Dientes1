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

public class FXMLUpdateTreatmentController implements Initializable {
    private static final Logger logger = LoggerFactory.getLogger(FXMLUpdateTreatmentController.class);

    @FXML private MenuBar myMenuBar;
    @FXML private TextField treatmentid;
    @FXML private TextField treatmentname;
    @FXML private TextField treatmentamount;

    private Connection con;
    private PreparedStatement ps;
    private ResultSet rs;

    public FXMLUpdateTreatmentController() {
        try {
            con = ConnectionUtil.getConnection();
        } catch (SQLException e) {
            logger.error("Error al conectar con la BD", e);
        }
    }

    // ---------------------------
    // Navegación entre pantallas
    // ---------------------------
    @FXML private void logoutButtonAction(ActionEvent e)             throws IOException { switchScene("/javafxapplication1/view/FXMLDocument.fxml"); }
    @FXML private void updatepatientButtonAction(ActionEvent e)     throws IOException { switchScene("/javafxapplication1/view/FXMLUpdatePatient.fxml"); }
    @FXML private void addpatientButtonAction(ActionEvent e)        throws IOException { switchScene("/javafxapplication1/view/FXMLAddPatient.fxml"); }
    @FXML private void adddentistButtonAction(ActionEvent e)        throws IOException { switchScene("/javafxapplication1/view/FXMLAddDentist.fxml"); }
    @FXML private void updatedentistButtonAction(ActionEvent e)     throws IOException { switchScene("/javafxapplication1/view/FXMLUpdateDentist.fxml"); }
    @FXML private void addappointmentButtonAction(ActionEvent e)    throws IOException { switchScene("/javafxapplication1/view/FXMLAddAppointment.fxml"); }
    @FXML private void updateappointmentButtonAction(ActionEvent e) throws IOException { switchScene("/javafxapplication1/view/FXMLUpdateAppointment.fxml"); }
    @FXML private void addtreatmentButtonAction(ActionEvent e)      throws IOException { switchScene("/javafxapplication1/view/FXMLAddTreatment.fxml"); }
    @FXML private void updatebillButtonAction(ActionEvent e)        throws IOException { switchScene("/javafxapplication1/view/FXMLUpdateBill.fxml"); }
    @FXML private void addbillButtonAction(ActionEvent e)           throws IOException { switchScene("/javafxapplication1/view/FXMLAddBill.fxml"); }

    private void switchScene(String fxml) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(fxml));
        Stage stage = (Stage)((Node)myMenuBar).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    // ---------------------------
    // Lógica de actualización
    // ---------------------------
    @FXML
    private void updateTreatment(ActionEvent event) {
        // Validar campos no vacíos
        if (treatmentid.getText().isBlank() ||
            treatmentname.getText().isBlank() ||
            treatmentamount.getText().isBlank())
        {
            showAlert(Alert.AlertType.WARNING, "Datos incompletos", "Complete todos los campos.");
            return;
        }

        int id;
        try {
            id = Integer.parseInt(treatmentid.getText());
        } catch (NumberFormatException ex) {
            showAlert(Alert.AlertType.ERROR, "Formato inválido", "El ID debe ser un número entero.");
            return;
        }

        // Validar que el monto sea decimal
        if (!treatmentamount.getText().matches("\\d+(\\.\\d+)?")) {
            showAlert(Alert.AlertType.ERROR, "Formato inválido", "El monto debe ser un número válido.");
            return;
        }

        String sql = ""
            + "UPDATE treatment SET "
            + "  treatmentname   = ?,"
            + "  treatmentamount = ?"
            + " WHERE treatmentid = ?";

        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, treatmentname.getText());
            ps.setString(2, treatmentamount.getText());
            ps.setInt   (3, id);

            int updated = ps.executeUpdate();
            if (updated > 0) {
                showAlert(Alert.AlertType.INFORMATION, "Éxito", "Tratamiento actualizado.");
                switchScene("/javafxapplication1/view/FXMLHome.fxml");
            } else {
                showAlert(Alert.AlertType.ERROR, "No encontrado", "No existe tratamiento con ID: " + id);
            }
        } catch (SQLException | IOException e) {
            logger.error("Error al actualizar tratamiento", e);
            showAlert(Alert.AlertType.ERROR, "Error", "No se pudo actualizar el tratamiento.");
        }
    }

    // ---------------------------
    // Inicialización
    // ---------------------------
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Nada que inicializar en este form
    }

    // ---------------------------
    // Helper para alertas
    // ---------------------------
    private void showAlert(Alert.AlertType type, String title, String msg) {
        Alert a = new Alert(type);
        a.setTitle(title);
        a.setHeaderText(null);
        a.setContentText(msg);
        a.showAndWait();
    }
}
