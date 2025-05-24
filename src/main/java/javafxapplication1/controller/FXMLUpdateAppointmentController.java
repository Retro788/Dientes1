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
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class FXMLUpdateAppointmentController implements Initializable {
    @FXML private MenuBar myMenuBar;
    @FXML private TextField appointmentid;
    @FXML private TextField patientid;
    @FXML private TextField patientname;
    @FXML private TextField appointmentdate;
    @FXML private TextField appointmenttime;
    @FXML private TextField requesttreatment;
    @FXML private ChoiceBox<String> dentistname;

    private Connection con;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;

    public FXMLUpdateAppointmentController() {
        try {
            con = ConnectionUtil.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void logoutButtonAction(ActionEvent event) throws IOException {
        Parent loginParent = FXMLLoader.load(
            getClass().getResource("/javafxapplication1/view/FXMLDocument.fxml")
        );
        Scene scene = new Scene(loginParent);
        Stage stage = (Stage)((Node)myMenuBar).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void updatepatientButtonAction(ActionEvent event) throws IOException {
        Parent p = FXMLLoader.load(
            getClass().getResource("/javafxapplication1/view/FXMLUpdatePatient.fxml")
        );
        Scene scene = new Scene(p);
        Stage stage = (Stage)((Node)myMenuBar).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void addpatientButtonAction(ActionEvent event) throws IOException {
        Parent p = FXMLLoader.load(
            getClass().getResource("/javafxapplication1/view/FXMLAddPatient.fxml")
        );
        Scene scene = new Scene(p);
        Stage stage = (Stage)((Node)myMenuBar).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void adddentistButtonAction(ActionEvent event) throws IOException {
        Parent p = FXMLLoader.load(
            getClass().getResource("/javafxapplication1/view/FXMLAddDentist.fxml")
        );
        Scene scene = new Scene(p);
        Stage stage = (Stage)((Node)myMenuBar).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void updatedentistButtonAction(ActionEvent event) throws IOException {
        Parent p = FXMLLoader.load(
            getClass().getResource("/javafxapplication1/view/FXMLUpdateDentist.fxml")
        );
        Scene scene = new Scene(p);
        Stage stage = (Stage)((Node)myMenuBar).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void addappointmentButtonAction(ActionEvent event) throws IOException {
        Parent p = FXMLLoader.load(
            getClass().getResource("/javafxapplication1/view/FXMLAddAppointment.fxml")
        );
        Scene scene = new Scene(p);
        Stage stage = (Stage)((Node)myMenuBar).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void updateappointmentButtonAction(ActionEvent event) throws IOException {
        Parent p = FXMLLoader.load(
            getClass().getResource("/javafxapplication1/view/FXMLUpdateAppointment.fxml")
        );
        Scene scene = new Scene(p);
        Stage stage = (Stage)((Node)myMenuBar).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void addtreatmentButtonAction(ActionEvent event) throws IOException {
        Parent p = FXMLLoader.load(
            getClass().getResource("/javafxapplication1/view/FXMLAddTreatment.fxml")
        );
        Scene scene = new Scene(p);
        Stage stage = (Stage)((Node)myMenuBar).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void updatetreatmentButtonAction(ActionEvent event) throws IOException {
        Parent p = FXMLLoader.load(
            getClass().getResource("/javafxapplication1/view/FXMLUpdateTreatment.fxml")
        );
        Scene scene = new Scene(p);
        Stage stage = (Stage)((Node)myMenuBar).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void addbillButtonAction(ActionEvent event) throws IOException {
        Parent p = FXMLLoader.load(
            getClass().getResource("/javafxapplication1/view/FXMLAddBill.fxml")
        );
        Scene scene = new Scene(p);
        Stage stage = (Stage)((Node)myMenuBar).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void updatebillButtonAction(ActionEvent event) throws IOException {
        Parent p = FXMLLoader.load(
            getClass().getResource("/javafxapplication1/view/FXMLUpdateBill.fxml")
        );
        Scene scene = new Scene(p);
        Stage stage = (Stage)((Node)myMenuBar).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void updateAppointment(ActionEvent event) {
        String sql = ""
            + "UPDATE appointment "
            + "SET patientid = ?, patientname = ?, appointmentdate = ?, "
            + "    appointmenttime = ?, requesttreatment = ?, dentistname = ? "
            + "WHERE appointmentid = ?";
        try {
            preparedStatement = con.prepareStatement(sql);
            preparedStatement.setInt(1, Integer.parseInt(patientid.getText()));
            preparedStatement.setString(2, patientname.getText());
            preparedStatement.setString(3, appointmentdate.getText());
            preparedStatement.setString(4, appointmenttime.getText());
            preparedStatement.setString(5, requesttreatment.getText());
            preparedStatement.setString(6, dentistname.getValue());
            preparedStatement.setInt(7, Integer.parseInt(appointmentid.getText()));
            int updated = preparedStatement.executeUpdate();
            if (updated > 0) {
                preparedStatement = con.prepareStatement(
                    "SELECT * FROM appointment WHERE appointmentid = ?"
                );
                preparedStatement.setInt(1, Integer.parseInt(appointmentid.getText()));
                resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    Parent home = FXMLLoader.load(
                        getClass().getResource("/javafxapplication1/view/FXMLHome.fxml")
                    );
                    Scene scene = new Scene(home);
                    Stage stage = (Stage)((Node)myMenuBar).getScene().getWindow();
                    stage.setScene(scene);
                    stage.show();
                }
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // poblar el ChoiceBox de dentistas
        try {
            preparedStatement = con.prepareStatement("SELECT dentistname FROM dentist");
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                dentistname.getItems().add(resultSet.getString("dentistname"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
