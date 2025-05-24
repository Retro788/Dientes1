package javafxapplication1.controller;

import javafxapplication1.util.ConnectionUtil;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.List;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FXMLAddAppointmentController implements Initializable {
    private static final Logger logger = LoggerFactory.getLogger(FXMLAddAppointmentController.class);

    private String currentRole;

    public void setCurrentRole(String role) {
        this.currentRole = role;
        logger.info("Rol establecido en AddAppointmentController: {}", role);
        if (!List.of("Practicante", "Operator", "Docente", "Paciente").contains(role) && saveButton != null) {
            saveButton.setDisable(true);
            showAlert(Alert.AlertType.WARNING,
                      "Acceso restringido",
                      "No tiene permisos para agendar citas con el rol: " + role);
        }
    }

    @FXML private MenuBar         myMenuBar;
    @FXML private TextField       appointmentid;
    @FXML private TextField       patientid;
    @FXML private TextField       patientname;
    @FXML private TextField       appointmentdate;
    @FXML private TextField       appointmenttime;
    @FXML private TextField       requesttreatment;
    @FXML private ChoiceBox<String> dentistname;
    @FXML private Button          saveButton;

    private Connection            con;
    private PreparedStatement     ps;
    private ResultSet             rs;

    public FXMLAddAppointmentController() {
        try { con = ConnectionUtil.getConnection(); }
        catch (SQLException e) { logger.error("No se pudo obtener conexión", e); }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            ps = con.prepareStatement("SELECT dentistname FROM dentist");
            rs = ps.executeQuery();
            while (rs.next()) {
                dentistname.getItems().add(rs.getString("dentistname"));
            }
            if (currentRole != null) setCurrentRole(currentRole);
        } catch (SQLException e) {
            logger.error("Error cargando dentistas", e);
        }
    }

    @FXML
    private void saveAppointment(ActionEvent event) {
        if (currentRole != null &&
            !List.of("Practicante","Operator","Docente","Paciente").contains(currentRole))
        {
            showAlert(Alert.AlertType.WARNING,
                      "Acceso restringido",
                      "No tiene permisos para agendar citas con el rol: " + currentRole);
            return;
        }
        if (appointmentid.getText().isBlank() ||
            patientid.getText().isBlank()   ||
            patientname.getText().isBlank() ||
            appointmentdate.getText().isBlank() ||
            appointmenttime.getText().isBlank() ||
            dentistname.getValue() == null)
        {
            showAlert(Alert.AlertType.WARNING, "Datos incompletos",
                      "Complete todos los campos obligatorios");
            return;
        }
        if (!appointmentid.getText().matches("\\d+") ||
            !patientid.getText().matches("\\d+"))
        {
            showAlert(Alert.AlertType.WARNING, "Error de formato",
                      "Los IDs deben ser numéricos");
            return;
        }
        if (!appointmentdate.getText().matches("\\d{4}-\\d{2}-\\d{2}")) {
            showAlert(Alert.AlertType.WARNING, "Error de formato",
                      "La fecha debe ser YYYY-MM-DD");
            return;
        }
        if (!appointmenttime.getText().matches("\\d{2}:\\d{2}")) {
            showAlert(Alert.AlertType.WARNING, "Error de formato",
                      "La hora debe ser HH:MM");
            return;
        }

        try {
            ps = con.prepareStatement(
                "INSERT INTO appointment (appointmentid,patientid,patientname,appointmentdate,appointmenttime,requesttreatment,dentistname) VALUES (?,?,?,?,?,?,?)"
            );
            ps.setInt(1, Integer.parseInt(appointmentid.getText()));
            ps.setInt(2, Integer.parseInt(patientid.getText()));
            ps.setString(3, patientname.getText());
            ps.setString(4, appointmentdate.getText());
            ps.setString(5, appointmenttime.getText());
            ps.setString(6, requesttreatment.getText());
            ps.setString(7, dentistname.getValue());
            ps.executeUpdate();

            ps = con.prepareStatement("SELECT 1 FROM appointment WHERE appointmentid = ?");
            ps.setInt(1, Integer.parseInt(appointmentid.getText()));
            rs = ps.executeQuery();
            if (rs.next()) {
                goTo("/javafxapplication1/view/FXMLHome.fxml");
            }
        } catch (SQLException | IOException e) {
            logger.error("Error guardando cita", e);
        }
    }

    private void goTo(String fxml) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(fxml));
        Stage stage = (Stage) myMenuBar.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML private void logoutButtonAction(ActionEvent e) throws IOException { goTo("/javafxapplication1/view/FXMLDocument.fxml"); }
    @FXML private void addpatientButtonAction(ActionEvent e) throws IOException { goTo("/javafxapplication1/view/FXMLAddPatient.fxml"); }
    @FXML private void updatepatientButtonAction(ActionEvent e) throws IOException { goTo("/javafxapplication1/view/FXMLUpdatePatient.fxml"); }
    @FXML private void adddentistButtonAction(ActionEvent e) throws IOException { goTo("/javafxapplication1/view/FXMLAddDentist.fxml"); }
    @FXML private void updatedentistButtonAction(ActionEvent e) throws IOException { goTo("/javafxapplication1/view/FXMLUpdateDentist.fxml"); }
    @FXML private void addappointmentButtonAction(ActionEvent e) throws IOException { goTo("/javafxapplication1/view/FXMLAddAppointment.fxml"); }
    @FXML private void updateappointmentButtonAction(ActionEvent e) throws IOException { goTo("/javafxapplication1/view/FXMLUpdateAppointment.fxml"); }
    @FXML private void addtreatmentButtonAction(ActionEvent e) throws IOException { goTo("/javafxapplication1/view/FXMLAddTreatment.fxml"); }
    @FXML private void updatetreatmentButtonAction(ActionEvent e) throws IOException { goTo("/javafxapplication1/view/FXMLUpdateTreatment.fxml"); }
    @FXML private void addbillButtonAction(ActionEvent e) throws IOException { goTo("/javafxapplication1/view/FXMLAddBill.fxml"); }
    @FXML private void updatebillButtonAction(ActionEvent e) throws IOException { goTo("/javafxapplication1/view/FXMLUpdateBill.fxml"); }

    @FXML
    private void handleClose(ActionEvent event) {
        System.exit(0);
    }

    private void showAlert(Alert.AlertType t, String title, String msg) {
        Alert a = new Alert(t);
        a.setTitle(title);
        a.setHeaderText(null);
        a.setContentText(msg);
        a.showAndWait();
    }
}
