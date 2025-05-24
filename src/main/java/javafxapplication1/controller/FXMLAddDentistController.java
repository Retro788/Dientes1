package javafxapplication1.controller;

import javafxapplication1.util.ConnectionUtil;

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
import javafx.scene.control.MenuBar;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import javafx.stage.Stage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * FXML Controller class
 *
 * @author rohit
 */
public class FXMLAddDentistController implements Initializable {

    private static final Logger logger = LoggerFactory.getLogger(FXMLAddDentistController.class);

    private String currentRole;

    @FXML private MenuBar myMenuBar;

    @FXML private TextField dentistid;
    @FXML private TextField dentistname;
    @FXML private TextField dentistage;
    @FXML private ChoiceBox<String> dentistgender;
    @FXML private TextArea dentistaddress;
    @FXML private TextField dentistphone;
    @FXML private ChoiceBox<String> dentistbloodgroup;
    @FXML private TextField dentistspeciality;
    @FXML private Button saveButton; // Botón para guardar dentista

    private Connection        con;
    private PreparedStatement preparedStatement;
    private ResultSet         resultSet;

    public FXMLAddDentistController() {
        try {
            con = ConnectionUtil.getConnection();
        } catch (SQLException e) {
            logger.error("Error al obtener conexión en AddDentistController", e);
        }
    }

    /**
     * Establece el rol actual y controla accesos.
     */
    public void setCurrentRole(String role) {
        this.currentRole = role;
        logger.info("Rol establecido en AddDentistController: {}", role);
        if (!List.of("Practicante", "Operator", "Docente").contains(role)) {
            if (saveButton != null) {
                saveButton.setDisable(true);
                showAlert(AlertType.WARNING,
                          "Acceso restringido",
                          "No tiene permisos para añadir dentistas con el rol: " + role);
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
    private void updatePatientButtonAction(ActionEvent event) throws IOException {
        Parent p = FXMLLoader.load(getClass().getResource("/javafxapplication1/view/FXMLUpdatePatient.fxml"));
        Scene  s = new Scene(p);
        Stage  st= (Stage)((Node)myMenuBar).getScene().getWindow();
        st.setScene(s);
        st.show();
    }

    @FXML
    private void addPatientButtonAction(ActionEvent event) throws IOException {
        Parent p = FXMLLoader.load(getClass().getResource("/javafxapplication1/view/FXMLAddPatient.fxml"));
        Scene  s = new Scene(p);
        Stage  st= (Stage)((Node)myMenuBar).getScene().getWindow();
        st.setScene(s);
        st.show();
    }

    @FXML
    private void addDentistButtonAction(ActionEvent event) throws IOException {
        Parent p = FXMLLoader.load(getClass().getResource("/javafxapplication1/view/FXMLAddDentist.fxml"));
        Scene  s = new Scene(p);
        Stage  st= (Stage)((Node)myMenuBar).getScene().getWindow();
        st.setScene(s);
        st.show();
    }

    @FXML
    private void updateDentistButtonAction(ActionEvent event) throws IOException {
        Parent p = FXMLLoader.load(getClass().getResource("/javafxapplication1/view/FXMLUpdateDentist.fxml"));
        Scene  s = new Scene(p);
        Stage  st= (Stage)((Node)myMenuBar).getScene().getWindow();
        st.setScene(s);
        st.show();
    }

    @FXML
    private void addAppointmentButtonAction(ActionEvent event) throws IOException {
        Parent p = FXMLLoader.load(getClass().getResource("/javafxapplication1/view/FXMLAddAppointment.fxml"));
        Scene  s = new Scene(p);
        Stage  st= (Stage)((Node)myMenuBar).getScene().getWindow();
        st.setScene(s);
        st.show();
    }

    @FXML
    private void updateAppointmentButtonAction(ActionEvent event) throws IOException {
        Parent p = FXMLLoader.load(getClass().getResource("/javafxapplication1/view/FXMLUpdateAppointment.fxml"));
        Scene  s = new Scene(p);
        Stage  st= (Stage)((Node)myMenuBar).getScene().getWindow();
        st.setScene(s);
        st.show();
    }

    @FXML
    private void addTreatmentButtonAction(ActionEvent event) throws IOException {
        Parent p = FXMLLoader.load(getClass().getResource("/javafxapplication1/view/FXMLAddTreatment.fxml"));
        Scene  s = new Scene(p);
        Stage  st= (Stage)((Node)myMenuBar).getScene().getWindow();
        st.setScene(s);
        st.show();
    }

    @FXML
    private void updateTreatmentButtonAction(ActionEvent event) throws IOException {
        Parent p = FXMLLoader.load(getClass().getResource("/javafxapplication1/view/FXMLUpdateTreatment.fxml"));
        Scene  s = new Scene(p);
        Stage  st= (Stage)((Node)myMenuBar).getScene().getWindow();
        st.setScene(s);
        st.show();
    }

    @FXML
    private void addBillButtonAction(ActionEvent event) throws IOException {
        Parent p = FXMLLoader.load(getClass().getResource("/javafxapplication1/view/FXMLAddBill.fxml"));
        Scene  s = new Scene(p);
        Stage  st= (Stage)((Node)myMenuBar).getScene().getWindow();
        st.setScene(s);
        st.show();
    }

    @FXML
    private void updateBillButtonAction(ActionEvent event) throws IOException {
        Parent p = FXMLLoader.load(getClass().getResource("/javafxapplication1/view/FXMLUpdateBill.fxml"));
        Scene  s = new Scene(p);
        Stage  st= (Stage)((Node)myMenuBar).getScene().getWindow();
        st.setScene(s);
        st.show();
    }

    @FXML
    private void saveDentist(ActionEvent event) {
        // Validar permisos
        if (currentRole != null && !List.of("Practicante", "Operator", "Docente").contains(currentRole)) {
            showAlert(AlertType.WARNING,
                      "Acceso restringido",
                      "No tiene permisos para añadir dentistas con el rol: " + currentRole);
            return;
        }

        // Campos obligatorios
        if (dentistid.getText().isEmpty() ||
            dentistname.getText().isEmpty() ||
            dentistage.getText().isEmpty() ||
            dentistphone.getText().isEmpty())
        {
            showAlert(AlertType.WARNING,
                      "Datos incompletos",
                      "Por favor complete todos los campos obligatorios");
            return;
        }

        // Formatos
        if (!isNumeric(dentistage.getText())) {
            showAlert(AlertType.WARNING,
                      "Error de formato",
                      "La edad debe ser un valor numérico");
            return;
        }
        if (!isNumeric(dentistphone.getText())) {
            showAlert(AlertType.WARNING,
                      "Error de formato",
                      "El teléfono debe contener solo números");
            return;
        }

        // Inserción en BD
        try {
            preparedStatement = con.prepareStatement(
                "INSERT INTO dentist (dentistid, dentistname, dentistage, dentistgender, dentistaddress, dentistphone, dentistbloodgroup, dentistspeciality) "
              + "VALUES (?,?,?,?,?,?,?,?)"
            );
            preparedStatement.setInt   (1, Integer.parseInt(dentistid.getText()));
            preparedStatement.setString(2, dentistname.getText());
            preparedStatement.setInt   (3, Integer.parseInt(dentistage.getText()));
            preparedStatement.setString(4, dentistgender.getValue());
            preparedStatement.setString(5, dentistaddress.getText());
            preparedStatement.setString(6, dentistphone.getText());
            preparedStatement.setString(7, dentistbloodgroup.getValue());
            preparedStatement.setString(8, dentistspeciality.getText());
            preparedStatement.executeUpdate();

            // Verificar inserción
            preparedStatement = con.prepareStatement(
                "SELECT 1 FROM dentist WHERE dentistid = ?"
            );
            preparedStatement.setInt(1, Integer.parseInt(dentistid.getText()));
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                logger.info("Dentista guardado con éxito, dentistid={}", dentistid.getText());
                Parent homeParent = FXMLLoader.load(getClass().getResource("/javafxapplication1/view/FXMLHome.fxml"));
                Scene  homeScene  = new Scene(homeParent);
                Stage  stage      = (Stage)((Node)myMenuBar).getScene().getWindow();
                stage.setScene(homeScene);
                stage.show();
            }

        } catch (SQLException | IOException e) {
            logger.error("Error al guardar dentista", e);
            showAlert(AlertType.ERROR,
                      "Error de base de datos",
                      "No se pudo guardar el dentista: " + e.getMessage());
        }
    }

    @FXML
    private void handleClose(ActionEvent event) {
        System.exit(0);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Inicializar ChoiceBoxes
        dentistgender.getItems().addAll("Male", "Female", "Other");
        dentistbloodgroup.getItems().addAll("A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-");

        // Aplicar restricciones de rol si ya fue seteado
        if (currentRole != null) {
            setCurrentRole(currentRole);
        }
    }

    private boolean isNumeric(String s) {
        return s != null && s.matches("\\d+");
    }

    private void showAlert(AlertType type, String title, String msg) {
        Alert a = new Alert(type);
        a.setTitle(title);
        a.setHeaderText(null);
        a.setContentText(msg);
        a.showAndWait();
    }
}
