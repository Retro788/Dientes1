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
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class FXMLAddPatientController implements Initializable {

    private static final Logger logger = LoggerFactory.getLogger(FXMLAddPatientController.class);

    private String currentRole;

    @FXML private AnchorPane   rootpane;
    @FXML private MenuBar      myMenuBar;
    @FXML private TextField    patientid;
    @FXML private TextField    patientname;
    @FXML private TextField    patientage;
    @FXML private ChoiceBox<String> patientgender;
    @FXML private TextArea     patientaddress;
    @FXML private TextField    patientphone;
    @FXML private ChoiceBox<String> patientbloodgroup;
    @FXML private TextArea     patienthealthproblems;
    @FXML private Button       saveButton;

    private Connection        con;
    private PreparedStatement preparedStatement;
    private ResultSet         resultSet;

    public FXMLAddPatientController() {
        try {
            con = ConnectionUtil.getConnection();
        } catch (SQLException e) {
            logger.error("Error al conectar a la base de datos en AddPatientController", e);
        }
    }

    /**
     * Establece el rol actual y controla los permisos de guardado.
     */
    public void setCurrentRole(String role) {
        this.currentRole = role;
        logger.info("Rol establecido en AddPatientController: {}", role);
        if (!List.of("Practicante", "Operator", "Docente", "Dentist").contains(role)) {
            if (saveButton != null) {
                saveButton.setDisable(true);
                showAlert(AlertType.WARNING,
                          "Acceso restringido",
                          "No tiene permisos para añadir pacientes con el rol: " + role);
            }
        }
    }

    @FXML
    private void logoutButtonAction(ActionEvent event) throws IOException {
        Parent loginParent = FXMLLoader.load(getClass().getResource("/javafxapplication1/view/FXMLDocument.fxml"));
        Scene  loginScene  = new Scene(loginParent);
        Stage  stage       = (Stage)((Node)myMenuBar).getScene().getWindow();
        stage.setScene(loginScene);
        stage.show();
    }

    @FXML
    private void updatepatientButtonAction(ActionEvent event) throws IOException {
        Parent p = FXMLLoader.load(getClass().getResource("/javafxapplication1/view/FXMLUpdatePatient.fxml"));
        Scene  s = new Scene(p);
        Stage  st= (Stage)((Node)myMenuBar).getScene().getWindow();
        st.setScene(s);
        st.show();
    }

    @FXML
    private void addpatientButtonAction(ActionEvent event) throws IOException {
        Parent p = FXMLLoader.load(getClass().getResource("/javafxapplication1/view/FXMLAddPatient.fxml"));
        Scene  s = new Scene(p);
        Stage  st= (Stage)((Node)myMenuBar).getScene().getWindow();
        st.setScene(s);
        st.show();
    }

    @FXML
    private void adddentistButtonAction(ActionEvent event) throws IOException {
        Parent p = FXMLLoader.load(getClass().getResource("/javafxapplication1/view/FXMLAddDentist.fxml"));
        Scene  s = new Scene(p);
        Stage  st= (Stage)((Node)myMenuBar).getScene().getWindow();
        st.setScene(s);
        st.show();
    }

    @FXML
    private void updatedentistButtonAction(ActionEvent event) throws IOException {
        Parent p = FXMLLoader.load(getClass().getResource("/javafxapplication1/view/FXMLUpdateDentist.fxml"));
        Scene  s = new Scene(p);
        Stage  st= (Stage)((Node)myMenuBar).getScene().getWindow();
        st.setScene(s);
        st.show();
    }

    @FXML
    private void addappointmentButtonAction(ActionEvent event) throws IOException {
        Parent p = FXMLLoader.load(getClass().getResource("/javafxapplication1/view/FXMLAddAppointment.fxml"));
        Scene  s = new Scene(p);
        Stage  st= (Stage)((Node)myMenuBar).getScene().getWindow();
        st.setScene(s);
        st.show();
    }

    @FXML
    private void updateappointmentButtonAction(ActionEvent event) throws IOException {
        Parent p = FXMLLoader.load(getClass().getResource("/javafxapplication1/view/FXMLUpdateAppointment.fxml"));
        Scene  s = new Scene(p);
        Stage  st= (Stage)((Node)myMenuBar).getScene().getWindow();
        st.setScene(s);
        st.show();
    }

    @FXML
    private void addtreatmentButtonAction(ActionEvent event) throws IOException {
        Parent p = FXMLLoader.load(getClass().getResource("/javafxapplication1/view/FXMLAddTreatment.fxml"));
        Scene  s = new Scene(p);
        Stage  st= (Stage)((Node)myMenuBar).getScene().getWindow();
        st.setScene(s);
        st.show();
    }

    @FXML
    private void updatetreatmentButtonAction(ActionEvent event) throws IOException {
        Parent p = FXMLLoader.load(getClass().getResource("/javafxapplication1/view/FXMLUpdateTreatment.fxml"));
        Scene  s = new Scene(p);
        Stage  st= (Stage)((Node)myMenuBar).getScene().getWindow();
        st.setScene(s);
        st.show();
    }

    @FXML
    private void addbillButtonAction(ActionEvent event) throws IOException {
        Parent p = FXMLLoader.load(getClass().getResource("/javafxapplication1/view/FXMLAddBill.fxml"));
        Scene  s = new Scene(p);
        Stage  st= (Stage)((Node)myMenuBar).getScene().getWindow();
        st.setScene(s);
        st.show();
    }

    @FXML
    private void updatebillButtonAction(ActionEvent event) throws IOException {
        Parent p = FXMLLoader.load(getClass().getResource("/javafxapplication1/view/FXMLUpdateBill.fxml"));
        Scene  s = new Scene(p);
        Stage  st= (Stage)((Node)myMenuBar).getScene().getWindow();
        st.setScene(s);
        st.show();
    }

    @FXML
    private void savePatient(ActionEvent event) {
        // Validar permisos
        if (currentRole != null
         && !List.of("Practicante", "Operator", "Docente", "Dentist").contains(currentRole)) {
            showAlert(AlertType.WARNING,
                      "Acceso restringido",
                      "No tiene permisos para añadir pacientes con el rol: " + currentRole);
            return;
        }

        // Validar campos obligatorios
        if (patientid.getText().isEmpty()
         || patientname.getText().isEmpty()
         || patientage.getText().isEmpty()
         || patientphone.getText().isEmpty()) {
            showAlert(AlertType.WARNING,
                      "Datos incompletos",
                      "Por favor complete todos los campos obligatorios");
            return;
        }

        // Validar formatos
        if (!isNumeric(patientage.getText())) {
            showAlert(AlertType.WARNING,
                      "Error de formato",
                      "La edad debe ser un valor numérico");
            return;
        }
        if (!isNumeric(patientphone.getText())) {
            showAlert(AlertType.WARNING,
                      "Error de formato",
                      "El teléfono debe contener solo números");
            return;
        }

        try {
            preparedStatement = con.prepareStatement(
                "INSERT INTO patient("
              + "patientid, patientname, patientage, patientgender,"
              + "patientaddress, patientphone, patientbloodgroup, patienthealthproblems"
              + ") VALUES (?,?,?,?,?,?,?,?)"
            );
            preparedStatement.setInt   (1, Integer.parseInt(patientid.getText()));
            preparedStatement.setString(2, patientname.getText());
            preparedStatement.setInt   (3, Integer.parseInt(patientage.getText()));
            preparedStatement.setString(4, patientgender.getValue());
            preparedStatement.setString(5, patientaddress.getText());
            preparedStatement.setString(6, patientphone.getText());
            preparedStatement.setString(7, patientbloodgroup.getValue());
            preparedStatement.setString(8, patienthealthproblems.getText());
            preparedStatement.executeUpdate();

            // Verificar inserción
            preparedStatement = con.prepareStatement(
                "SELECT 1 FROM patient WHERE patientid = ?"
            );
            preparedStatement.setInt(1, Integer.parseInt(patientid.getText()));
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Parent home = FXMLLoader.load(getClass().getResource("/javafxapplication1/view/FXMLHome.fxml"));
                Scene scene = new Scene(home);
                Stage stage = (Stage)((Node)myMenuBar).getScene().getWindow();
                stage.setScene(scene);
                stage.show();
            }
        } catch (SQLException | IOException e) {
            logger.error("Error al guardar paciente", e);
            showAlert(AlertType.ERROR,
                      "Error de base de datos",
                      "No se pudo guardar el paciente: " + e.getMessage());
        }
    }

    @FXML
    private void handleClose(ActionEvent event) {
        System.exit(0);
    }

    private boolean isNumeric(String s) {
        return s != null && s.matches("\\d+");
    }

    private void showAlert(AlertType type, String title, String msg) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        patientgender.getItems().addAll("Male", "Female", "Other");
        patientbloodgroup.getItems().addAll("A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-");
        if (currentRole != null) {
            setCurrentRole(currentRole);
        }
    }
}
