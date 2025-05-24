package javafxapplication1.controller;

import javafxapplication1.util.ConnectionUtil;
import javafxapplication1.model.ModelAppointment;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
import javafx.collections.*;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class FXMLAppointmentListController implements Initializable {

    @FXML private MenuBar myMenuBar;
    @FXML private TableView<ModelAppointment> tableview;
    @FXML private TableColumn<ModelAppointment,Integer> col_appointmentid;
    @FXML private TableColumn<ModelAppointment,Integer> col_patientid;
    @FXML private TableColumn<ModelAppointment,String>  col_patientname;
    @FXML private TableColumn<ModelAppointment,String>  col_date;
    @FXML private TableColumn<ModelAppointment,String>  col_time;
    @FXML private TableColumn<ModelAppointment,String>  col_requesttreatment;
    @FXML private TableColumn<ModelAppointment,String>  col_dentistname;

    private final ObservableList<ModelAppointment> oblist = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try (Connection con = ConnectionUtil.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery("SELECT * FROM appointment"))
        {
            while (rs.next()) {
                oblist.add(new ModelAppointment(
                    rs.getInt("appointmentid"),
                    rs.getInt("patientid"),
                    rs.getString("patientname"),
                    rs.getString("appointmentdate"),
                    rs.getString("appointmenttime"),
                    rs.getString("requesttreatment"),
                    rs.getString("dentistname")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        col_appointmentid.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        col_patientid.setCellValueFactory(new PropertyValueFactory<>("patientId"));
        col_patientname.setCellValueFactory(new PropertyValueFactory<>("patientName"));
        col_date.setCellValueFactory(new PropertyValueFactory<>("appointmentDate"));
        col_time.setCellValueFactory(new PropertyValueFactory<>("appointmentTime"));
        col_requesttreatment.setCellValueFactory(new PropertyValueFactory<>("requestTreatment"));
        col_dentistname.setCellValueFactory(new PropertyValueFactory<>("dentistName"));

        tableview.setItems(oblist);
    }

    @FXML private void logoutButtonAction(ActionEvent e) throws IOException {
        goTo("/javafxapplication1/view/FXMLDocument.fxml");
    }
    @FXML private void addAppointmentButtonAction(ActionEvent e) throws IOException {
        goTo("/javafxapplication1/view/FXMLAddAppointment.fxml");
    }
    // (añade aquí updateAppointment, addPatient, etc. de manera análoga)

    @FXML private void handleClose(ActionEvent event) {
        System.exit(0);
    }

    private void goTo(String fxml) throws IOException {
        Parent p = FXMLLoader.load(getClass().getResource(fxml));
        Stage stage = (Stage) myMenuBar.getScene().getWindow();
        stage.setScene(new Scene(p));
    }
}
