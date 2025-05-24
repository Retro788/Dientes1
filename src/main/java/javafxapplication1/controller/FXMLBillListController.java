package javafxapplication1.controller;

import javafxapplication1.util.ConnectionUtil;
import javafxapplication1.model.ModelTable4;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class FXMLBillListController implements Initializable {

    @FXML private MenuBar myMenuBar;
    @FXML private TableView<ModelTable4> tableview;
    @FXML private TableColumn<ModelTable4, String> col_billid;
    @FXML private TableColumn<ModelTable4, String> col_dentistname;
    @FXML private TableColumn<ModelTable4, String> col_billdate;
    @FXML private TableColumn<ModelTable4, String> col_patientname;
    @FXML private TableColumn<ModelTable4, String> col_treatments;
    @FXML private TableColumn<ModelTable4, String> col_billtotal;

    private final ObservableList<ModelTable4> oblist = FXCollections.observableArrayList();

    @FXML
    private void logoutButtonAction(ActionEvent event) throws IOException {
        Parent login = FXMLLoader.load(getClass().getResource("/javafxapplication1/view/FXMLDocument.fxml"));
        Scene scene = new Scene(login);
        Stage stage = (Stage)((Node)myMenuBar).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void addpatientButtonAction(ActionEvent event) throws IOException {
        Parent p = FXMLLoader.load(getClass().getResource("/javafxapplication1/view/FXMLAddPatient.fxml"));
        Scene s = new Scene(p);
        Stage st = (Stage)((Node)myMenuBar).getScene().getWindow();
        st.setScene(s);
        st.show();
    }

    @FXML
    private void updatepatientButtonAction(ActionEvent event) throws IOException {
        Parent p = FXMLLoader.load(getClass().getResource("/javafxapplication1/view/FXMLUpdatePatient.fxml"));
        Scene s = new Scene(p);
        Stage st = (Stage)((Node)myMenuBar).getScene().getWindow();
        st.setScene(s);
        st.show();
    }

    @FXML
    private void adddentistButtonAction(ActionEvent event) throws IOException {
        Parent p = FXMLLoader.load(getClass().getResource("/javafxapplication1/view/FXMLAddDentist.fxml"));
        Scene s = new Scene(p);
        Stage st = (Stage)((Node)myMenuBar).getScene().getWindow();
        st.setScene(s);
        st.show();
    }

    @FXML
    private void updatedentistButtonAction(ActionEvent event) throws IOException {
        Parent p = FXMLLoader.load(getClass().getResource("/javafxapplication1/view/FXMLUpdateDentist.fxml"));
        Scene s = new Scene(p);
        Stage st = (Stage)((Node)myMenuBar).getScene().getWindow();
        st.setScene(s);
        st.show();
    }

    @FXML
    private void addappointmentButtonAction(ActionEvent event) throws IOException {
        Parent p = FXMLLoader.load(getClass().getResource("/javafxapplication1/view/FXMLAddAppointment.fxml"));
        Scene s = new Scene(p);
        Stage st = (Stage)((Node)myMenuBar).getScene().getWindow();
        st.setScene(s);
        st.show();
    }

    @FXML
    private void updateappointmentButtonAction(ActionEvent event) throws IOException {
        Parent p = FXMLLoader.load(getClass().getResource("/javafxapplication1/view/FXMLUpdateAppointment.fxml"));
        Scene s = new Scene(p);
        Stage st = (Stage)((Node)myMenuBar).getScene().getWindow();
        st.setScene(s);
        st.show();
    }

    @FXML
    private void addtreatmentButtonAction(ActionEvent event) throws IOException {
        Parent p = FXMLLoader.load(getClass().getResource("/javafxapplication1/view/FXMLAddTreatment.fxml"));
        Scene s = new Scene(p);
        Stage st = (Stage)((Node)myMenuBar).getScene().getWindow();
        st.setScene(s);
        st.show();
    }

    @FXML
    private void updatetreatmentButtonAction(ActionEvent event) throws IOException {
        Parent p = FXMLLoader.load(getClass().getResource("/javafxapplication1/view/FXMLUpdateTreatment.fxml"));
        Scene s = new Scene(p);
        Stage st = (Stage)((Node)myMenuBar).getScene().getWindow();
        st.setScene(s);
        st.show();
    }

    @FXML
    private void addbillButtonAction(ActionEvent event) throws IOException {
        Parent p = FXMLLoader.load(getClass().getResource("/javafxapplication1/view/FXMLAddBill.fxml"));
        Scene s = new Scene(p);
        Stage st = (Stage)((Node)myMenuBar).getScene().getWindow();
        st.setScene(s);
        st.show();
    }

    @FXML
    private void updatebillButtonAction(ActionEvent event) throws IOException {
        Parent p = FXMLLoader.load(getClass().getResource("/javafxapplication1/view/FXMLUpdateBill.fxml"));
        Scene s = new Scene(p);
        Stage st = (Stage)((Node)myMenuBar).getScene().getWindow();
        st.setScene(s);
        st.show();
    }

    @FXML
    private void handleClose(ActionEvent event) {
        System.exit(0);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try (Connection con = ConnectionUtil.getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM bill")) {

            while (rs.next()) {
                oblist.add(new ModelTable4(
                    rs.getString("billid"),
                    rs.getString("dentistname"),
                    rs.getString("billdate"),
                    rs.getString("patientname"),
                    rs.getString("treatments"),
                    rs.getString("billtotal")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        col_billid    .setCellValueFactory(new PropertyValueFactory<>("billid"));
        col_dentistname.setCellValueFactory(new PropertyValueFactory<>("dentistname"));
        col_billdate  .setCellValueFactory(new PropertyValueFactory<>("billdate"));
        col_patientname.setCellValueFactory(new PropertyValueFactory<>("patientname"));
        col_treatments.setCellValueFactory(new PropertyValueFactory<>("treatments"));
        col_billtotal .setCellValueFactory(new PropertyValueFactory<>("billtotal"));

        tableview.setItems(oblist);
    }
}
