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

public class FXMLUpdateBillController implements Initializable {
    private static final Logger logger = LoggerFactory.getLogger(FXMLUpdateBillController.class);

    @FXML private MenuBar    myMenuBar;
    @FXML private TextField   billid;
    @FXML private TextField   dentistname;
    @FXML private TextField   billdate;
    @FXML private TextField   patientname;
    @FXML private TextArea    treatments;
    @FXML private TextField   billtotal;

    private Connection           con;
    private PreparedStatement    ps;
    private ResultSet            rs;

    public FXMLUpdateBillController() {
        try {
            con = ConnectionUtil.getConnection();
        } catch (SQLException e) {
            logger.error("Error obteniendo conexión a la base de datos", e);
        }
    }

    // --- Navegación entre pantallas ---
    @FXML private void logoutButtonAction(ActionEvent e)                 throws IOException { switchScene("/javafxapplication1/view/FXMLDocument.fxml"); }
    @FXML private void updatepatientButtonAction(ActionEvent e)        throws IOException { switchScene("/javafxapplication1/view/FXMLUpdatePatient.fxml"); }
    @FXML private void addpatientButtonAction(ActionEvent e)           throws IOException { switchScene("/javafxapplication1/view/FXMLAddPatient.fxml"); }
    @FXML private void adddentistButtonAction(ActionEvent e)           throws IOException { switchScene("/javafxapplication1/view/FXMLAddDentist.fxml"); }
    @FXML private void updatedentistButtonAction(ActionEvent e)        throws IOException { switchScene("/javafxapplication1/view/FXMLUpdateDentist.fxml"); }
    @FXML private void addappointmentButtonAction(ActionEvent e)       throws IOException { switchScene("/javafxapplication1/view/FXMLAddAppointment.fxml"); }
    @FXML private void updateappointmentButtonAction(ActionEvent e)    throws IOException { switchScene("/javafxapplication1/view/FXMLUpdateAppointment.fxml"); }
    @FXML private void addtreatmentButtonAction(ActionEvent e)         throws IOException { switchScene("/javafxapplication1/view/FXMLAddTreatment.fxml"); }
    @FXML private void updatetreatmentButtonAction(ActionEvent e)      throws IOException { switchScene("/javafxapplication1/view/FXMLUpdateTreatment.fxml"); }
    @FXML private void addbillButtonAction(ActionEvent e)              throws IOException { switchScene("/javafxapplication1/view/FXMLAddBill.fxml"); }
    @FXML private void updatebillButtonAction(ActionEvent e)           throws IOException { switchScene("/javafxapplication1/view/FXMLUpdateBill.fxml"); }

    private void switchScene(String fxmlPath) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(fxmlPath));
        Stage stage = (Stage)((Node)myMenuBar).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    // --- Lógica de actualización de la factura ---
    @FXML
    private void updateBill(ActionEvent event) {
        if (billid.getText().isBlank()) {
            showAlert(Alert.AlertType.WARNING, "ID vacío", "Por favor ingrese el ID de la factura.");
            return;
        }
        try {
            int id = Integer.parseInt(billid.getText());
            String sql = ""
                + "UPDATE bill SET "
                + "  dentistname = ?,"
                + "  billdate     = ?,"
                + "  patientname  = ?,"
                + "  treatments   = ?,"
                + "  billtotal    = ?"
                + " WHERE billid = ?";
            ps = con.prepareStatement(sql);
            ps.setString(1, dentistname.getText());
            ps.setString(2, billdate.getText());
            ps.setString(3, patientname.getText());
            ps.setString(4, treatments.getText());
            ps.setString(5, billtotal.getText());
            ps.setInt   (6, id);

            int rows = ps.executeUpdate();
            if (rows > 0) {
                showAlert(Alert.AlertType.INFORMATION, "Éxito", "Factura actualizada correctamente.");
                switchScene("/javafxapplication1/view/FXMLHome.fxml");
            } else {
                showAlert(Alert.AlertType.ERROR, "No encontrada",
                          "No existe una factura con ID: " + id);
            }
        } catch (NumberFormatException nfe) {
            showAlert(Alert.AlertType.ERROR, "Formato inválido", "El ID debe ser un número.");
        } catch (SQLException | IOException ex) {
            logger.error("Error al actualizar factura", ex);
            showAlert(Alert.AlertType.ERROR, "Error",
                      "Ocurrió un problema al intentar actualizar la factura.");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // No se requiere inicialización adicional
    }

    private void showAlert(Alert.AlertType type, String title, String msg) {
        Alert a = new Alert(type);
        a.setTitle(title);
        a.setHeaderText(null);
        a.setContentText(msg);
        a.showAndWait();
    }
}
