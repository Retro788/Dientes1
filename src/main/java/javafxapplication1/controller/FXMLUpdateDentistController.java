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

public class FXMLUpdateDentistController implements Initializable {
    private static final Logger logger = LoggerFactory.getLogger(FXMLUpdateDentistController.class);

    @FXML private MenuBar myMenuBar;
    @FXML private TextField  dentistid;
    @FXML private TextField  dentistname;
    @FXML private TextField  dentistage;
    @FXML private ChoiceBox<String> dentistgender;
    @FXML private TextArea   dentistaddress;
    @FXML private TextField  dentistphone;
    @FXML private ChoiceBox<String> dentistbloodgroup;
    @FXML private TextField  dentistspeciality;

    private Connection        con;
    private PreparedStatement ps;
    private ResultSet         rs;

    public FXMLUpdateDentistController() {
        try {
            con = ConnectionUtil.getConnection();
        } catch (SQLException e) {
            logger.error("Error al obtener conexión a la base de datos", e);
        }
    }

    // ------------------------------
    // Navegación entre pantallas
    // ------------------------------
    @FXML private void logoutButtonAction(ActionEvent e)            throws IOException { switchScene("/javafxapplication1/view/FXMLDocument.fxml"); }
    @FXML private void updatepatientButtonAction(ActionEvent e)    throws IOException { switchScene("/javafxapplication1/view/FXMLUpdatePatient.fxml"); }
    @FXML private void addpatientButtonAction(ActionEvent e)       throws IOException { switchScene("/javafxapplication1/view/FXMLAddPatient.fxml"); }
    @FXML private void adddentistButtonAction(ActionEvent e)       throws IOException { switchScene("/javafxapplication1/view/FXMLAddDentist.fxml"); }
    @FXML private void updatedentistButtonAction(ActionEvent e)    throws IOException { switchScene("/javafxapplication1/view/FXMLUpdateDentist.fxml"); }
    @FXML private void addappointmentButtonAction(ActionEvent e)   throws IOException { switchScene("/javafxapplication1/view/FXMLAddAppointment.fxml"); }
    @FXML private void updateappointmentButtonAction(ActionEvent e)throws IOException { switchScene("/javafxapplication1/view/FXMLUpdateAppointment.fxml"); }
    @FXML private void addtreatmentButtonAction(ActionEvent e)     throws IOException { switchScene("/javafxapplication1/view/FXMLAddTreatment.fxml"); }
    @FXML private void updatetreatmentButtonAction(ActionEvent e)  throws IOException { switchScene("/javafxapplication1/view/FXMLUpdateTreatment.fxml"); }
    @FXML private void addbillButtonAction(ActionEvent e)          throws IOException { switchScene("/javafxapplication1/view/FXMLAddBill.fxml"); }
    @FXML private void updatebillButtonAction(ActionEvent e)       throws IOException { switchScene("/javafxapplication1/view/FXMLUpdateBill.fxml"); }

    private void switchScene(String fxmlPath) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(fxmlPath));
        Stage stage = (Stage)((Node)myMenuBar).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    // ----------------------------------
    // Lógica para actualizar el dentista
    // ----------------------------------
    @FXML
    private void updateDentist(ActionEvent event) {
        // Validaciones básicas
        if (dentistid.getText().isBlank() ||
            dentistname.getText().isBlank() ||
            dentistage.getText().isBlank() ||
            dentistgender.getValue() == null ||
            dentistaddress.getText().isBlank() ||
            dentistphone.getText().isBlank() ||
            dentistbloodgroup.getValue() == null ||
            dentistspeciality.getText().isBlank()) {
            showAlert(Alert.AlertType.WARNING, "Datos incompletos",
                      "Por favor complete todos los campos.");
            return;
        }
        int id;
        try {
            id = Integer.parseInt(dentistid.getText());
        } catch (NumberFormatException nfe) {
            showAlert(Alert.AlertType.ERROR, "Formato inválido",
                      "El ID debe ser un número entero.");
            return;
        }

        String sql = ""
            + "UPDATE dentist SET "
            + "  dentistname      = ?,"
            + "  dentistage       = ?,"
            + "  dentistgender    = ?,"
            + "  dentistaddress   = ?,"
            + "  dentistphone     = ?,"
            + "  dentistbloodgroup= ?,"
            + "  dentistspeciality= ?"
            + " WHERE dentistid   = ?";

        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, dentistname.getText());
            ps.setString(2, dentistage.getText());
            ps.setString(3, dentistgender.getValue());
            ps.setString(4, dentistaddress.getText());
            ps.setString(5, dentistphone.getText());
            ps.setString(6, dentistbloodgroup.getValue());
            ps.setString(7, dentistspeciality.getText());
            ps.setInt   (8, id);

            int updated = ps.executeUpdate();
            if (updated > 0) {
                showAlert(Alert.AlertType.INFORMATION, "Éxito",
                          "Dentista actualizado correctamente.");
                switchScene("/javafxapplication1/view/FXMLHome.fxml");
            } else {
                showAlert(Alert.AlertType.ERROR, "No encontrado",
                          "No existe un dentista con ID: " + id);
            }
        } catch (SQLException | IOException ex) {
            logger.error("Error al actualizar dentista", ex);
            showAlert(Alert.AlertType.ERROR, "Error",
                      "Ocurrió un problema al actualizar el dentista.");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Inicializar ChoiceBoxes
        dentistgender.getItems().addAll("Male", "Female", "Other");
        dentistbloodgroup.getItems().addAll(
            "A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-"
        );
    }

    private void showAlert(Alert.AlertType type, String title, String msg) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
