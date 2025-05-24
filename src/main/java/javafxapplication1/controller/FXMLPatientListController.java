package javafxapplication1.controller;

import javafxapplication1.model.ModelTable;
import javafxapplication1.util.ConnectionUtil;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

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

public class FXMLPatientListController implements Initializable {

    @FXML private MenuBar myMenuBar;
    @FXML private TableView<ModelTable> tableview;
    @FXML private TableColumn<ModelTable, String> col_id;
    @FXML private TableColumn<ModelTable, String> col_name;
    @FXML private TableColumn<ModelTable, String> col_age;
    @FXML private TableColumn<ModelTable, String> col_gender;
    @FXML private TableColumn<ModelTable, String> col_address;
    @FXML private TableColumn<ModelTable, String> col_phone;
    @FXML private TableColumn<ModelTable, String> col_bloodgroup;
    @FXML private TableColumn<ModelTable, String> col_healthproblems;

    private final ObservableList<ModelTable> oblist = FXCollections.observableArrayList();

    @FXML
    private void logoutButtonAction(ActionEvent event) throws IOException {
        Parent login = FXMLLoader.load(
            getClass().getResource("/javafxapplication1/view/FXMLDocument.fxml")
        );
        Scene scene = new Scene(login);
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void addpatientButtonAction(ActionEvent event) throws IOException {
        Parent add = FXMLLoader.load(
            getClass().getResource("/javafxapplication1/view/FXMLAddPatient.fxml")
        );
        Scene scene = new Scene(add);
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void updatepatientButtonAction(ActionEvent event) throws IOException {
        Parent upd = FXMLLoader.load(
            getClass().getResource("/javafxapplication1/view/FXMLUpdatePatient.fxml")
        );
        Scene scene = new Scene(upd);
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void adddentistButtonAction(ActionEvent event) throws IOException {
        Parent add = FXMLLoader.load(
            getClass().getResource("/javafxapplication1/view/FXMLAddDentist.fxml")
        );
        Scene scene = new Scene(add);
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void updatedentistButtonAction(ActionEvent event) throws IOException {
        Parent upd = FXMLLoader.load(
            getClass().getResource("/javafxapplication1/view/FXMLUpdateDentist.fxml")
        );
        Scene scene = new Scene(upd);
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void addappointmentButtonAction(ActionEvent event) throws IOException {
        Parent add = FXMLLoader.load(
            getClass().getResource("/javafxapplication1/view/FXMLAddAppointment.fxml")
        );
        Scene scene = new Scene(add);
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void updateappointmentButtonAction(ActionEvent event) throws IOException {
        Parent upd = FXMLLoader.load(
            getClass().getResource("/javafxapplication1/view/FXMLUpdateAppointment.fxml")
        );
        Scene scene = new Scene(upd);
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void addtreatmentButtonAction(ActionEvent event) throws IOException {
        Parent add = FXMLLoader.load(
            getClass().getResource("/javafxapplication1/view/FXMLAddTreatment.fxml")
        );
        Scene scene = new Scene(add);
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void updatetreatmentButtonAction(ActionEvent event) throws IOException {
        Parent upd = FXMLLoader.load(
            getClass().getResource("/javafxapplication1/view/FXMLUpdateTreatment.fxml")
        );
        Scene scene = new Scene(upd);
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void addbillButtonAction(ActionEvent event) throws IOException {
        Parent add = FXMLLoader.load(
            getClass().getResource("/javafxapplication1/view/FXMLAddBill.fxml")
        );
        Scene scene = new Scene(add);
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void updatebillButtonAction(ActionEvent event) throws IOException {
        Parent upd = FXMLLoader.load(
            getClass().getResource("/javafxapplication1/view/FXMLUpdateBill.fxml")
        );
        Scene scene = new Scene(upd);
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void handleClose(ActionEvent event) {
        System.exit(0);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Cargar datos desde la BD
        try (Connection con = ConnectionUtil.getConnection();
             ResultSet rs = con.createStatement()
                               .executeQuery("SELECT * FROM patient")) {

            while (rs.next()) {
                oblist.add(new ModelTable(
                    rs.getString("patientid"),
                    rs.getString("patientname"),
                    rs.getString("patientage"),
                    rs.getString("patientgender"),
                    rs.getString("patientaddress"),
                    rs.getString("patientphone"),
                    rs.getString("patientbloodgroup"),
                    rs.getString("patienthealthproblems")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Mapear columnas
        col_id            .setCellValueFactory(new PropertyValueFactory<>("id"));
        col_name          .setCellValueFactory(new PropertyValueFactory<>("name"));
        col_age           .setCellValueFactory(new PropertyValueFactory<>("age"));
        col_gender        .setCellValueFactory(new PropertyValueFactory<>("gender"));
        col_address       .setCellValueFactory(new PropertyValueFactory<>("address"));
        col_phone         .setCellValueFactory(new PropertyValueFactory<>("phone"));
        col_bloodgroup    .setCellValueFactory(new PropertyValueFactory<>("bloodgroup"));
        col_healthproblems.setCellValueFactory(new PropertyValueFactory<>("healthproblems"));

        tableview.setItems(oblist);
    }
}
