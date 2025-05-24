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
public class FXMLAddBillController implements Initializable {

    private static final Logger logger = LoggerFactory.getLogger(FXMLAddBillController.class);
    
    private String currentRole;

    @FXML private MenuBar myMenuBar;
    
    @FXML private TextField billid;
    @FXML private TextField dentistname;
    @FXML private TextField billdate;
    @FXML private TextField patientname;
    @FXML private TextArea  treatments;
    @FXML private TextField billtotal;
    @FXML private Button    saveButton; // Botón para guardar factura
    
    private Connection        con;
    private PreparedStatement preparedStatement;
    private ResultSet         resultSet;
    
    public FXMLAddBillController() {
        try {
            con = ConnectionUtil.getConnection();
        } catch (SQLException e) {
            logger.error("Error al obtener conexión en AddBillController", e);
        }
    }
    
    /**
     * Establece el rol actual y controla accesos.
     */
    public void setCurrentRole(String role) {
        this.currentRole = role;
        logger.info("Rol establecido en AddBillController: {}", role);
        // Sólo permitimos Practicante u Operator
        if (!List.of("Practicante", "Operator").contains(role)) {
            if (saveButton != null) {
                saveButton.setDisable(true);
                showAlert(AlertType.WARNING,
                          "Acceso restringido",
                          "No tiene permisos para añadir facturas con el rol: " + role);
            }
        }
    }

    @FXML
    private void logoutButtonAction(ActionEvent event) throws IOException {
        Parent loginParent = FXMLLoader.load(getClass().getResource("/javafxapplication1/view/FXMLDocument.fxml"));
        Scene  loginScene  = new Scene(loginParent);
        Stage  appStage    = (Stage)((Node)myMenuBar).getScene().getWindow();
        appStage.setScene(loginScene);
        appStage.show();
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
    private void saveBill(ActionEvent event) {
        // Validar permisos
        if (currentRole != null && !List.of("Practicante", "Operator").contains(currentRole)) {
            showAlert(AlertType.WARNING,
                      "Acceso restringido",
                      "No tiene permisos para añadir facturas con el rol: " + currentRole);
            return;
        }

        // Campos obligatorios
        if (billid.getText().isEmpty() ||
            dentistname.getText().isEmpty() ||
            billdate.getText().isEmpty() ||
            patientname.getText().isEmpty() ||
            treatments.getText().isEmpty() ||
            billtotal.getText().isEmpty())
        {
            showAlert(AlertType.WARNING,
                      "Datos incompletos",
                      "Por favor complete todos los campos obligatorios");
            return;
        }

        // Formatos
        if (!isNumeric(billid.getText())) {
            showAlert(AlertType.WARNING,
                      "Error de formato",
                      "El ID debe ser un valor numérico");
            return;
        }
        if (!isDate(billdate.getText())) {
            showAlert(AlertType.WARNING,
                      "Error de formato",
                      "La fecha debe tener el formato YYYY-MM-DD");
            return;
        }
        if (!isDecimal(billtotal.getText())) {
            showAlert(AlertType.WARNING,
                      "Error de formato",
                      "El total debe ser un valor decimal válido");
            return;
        }

        // Inserción en BD
        try {
            preparedStatement = con.prepareStatement(
                "INSERT INTO bill (billid, dentistname, billdate, patientname, treatments, billtotal) VALUES (?,?,?,?,?,?)"
            );
            preparedStatement.setInt   (1, Integer.parseInt(billid.getText()));
            preparedStatement.setString(2, dentistname.getText());
            preparedStatement.setString(3, billdate.getText());
            preparedStatement.setString(4, patientname.getText());
            preparedStatement.setString(5, treatments.getText());
            preparedStatement.setBigDecimal(6, new java.math.BigDecimal(billtotal.getText()));
            preparedStatement.executeUpdate();

            // Verificar inserción
            preparedStatement = con.prepareStatement(
                "SELECT 1 FROM bill WHERE billid = ?"
            );
            preparedStatement.setInt(1, Integer.parseInt(billid.getText()));
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                logger.info("Factura guardada con éxito, billid={}", billid.getText());
                Parent homeParent = FXMLLoader.load(getClass().getResource("/javafxapplication1/view/FXMLHome.fxml"));
                Scene  homeScene  = new Scene(homeParent);
                Stage  stage      = (Stage)((Node)myMenuBar).getScene().getWindow();
                stage.setScene(homeScene);
                stage.show();
            }

        } catch (SQLException | IOException e) {
            logger.error("Error al guardar factura", e);
            showAlert(AlertType.ERROR,
                      "Error de base de datos",
                      "No se pudo guardar la factura: " + e.getMessage());
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Aquí podrías precargar datos si fuera necesario
        if (currentRole != null) {
            setCurrentRole(currentRole);
        }
    }

    private boolean isNumeric(String s) {
        return s != null && s.matches("\\d+");
    }

    private boolean isDate(String s) {
        return s != null && s.matches("\\d{4}-\\d{2}-\\d{2}");
    }

    private boolean isDecimal(String s) {
        return s != null && s.matches("\\d+(\\.\\d+)?");
    }

    private void showAlert(AlertType type, String title, String msg) {
        Alert a = new Alert(type);
        a.setTitle(title);
        a.setHeaderText(null);
        a.setContentText(msg);
        a.showAndWait();
    }
}
