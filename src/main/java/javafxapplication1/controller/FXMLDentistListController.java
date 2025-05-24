package javafxapplication1.controller;

import javafxapplication1.util.ConnectionUtil;
import javafxapplication1.model.ModelTable1;

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

public class FXMLDentistListController implements Initializable {

    @FXML private MenuBar myMenuBar;
    @FXML private TableView<ModelTable1> tableview;
    @FXML private TableColumn<ModelTable1, String> col_id;
    @FXML private TableColumn<ModelTable1, String> col_name;
    @FXML private TableColumn<ModelTable1, String> col_age;
    @FXML private TableColumn<ModelTable1, String> col_gender;
    @FXML private TableColumn<ModelTable1, String> col_address;
    @FXML private TableColumn<ModelTable1, String> col_phone;
    @FXML private TableColumn<ModelTable1, String> col_bloodgroup;
    @FXML private TableColumn<ModelTable1, String> col_speciality;

    private final ObservableList<ModelTable1> oblist = FXCollections.observableArrayList();

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
        // cargar datos de la tabla
        try (Connection con = ConnectionUtil.getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM dentist")) {

            while (rs.next()) {
                oblist.add(new ModelTable1(
                    rs.getString("dentistid"),
                    rs.getString("dentistname"),
                    rs.getString("dentistage"),
                    rs.getString("dentistgender"),
                    rs.getString("dentistaddress"),
                    rs.getString("dentistphone"),
                    rs.getString("dentistbloodgroup"),
                    rs.getString("dentistspeciality")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        // enlazar columnas con propiedades del modelo
        col_id        .setCellValueFactory(new PropertyValueFactory<>("id"));
        col_name      .setCellValueFactory(new PropertyValueFactory<>("name"));
        col_age       .setCellValueFactory(new PropertyValueFactory<>("age"));
        col_gender    .setCellValueFactory(new PropertyValueFactory<>("gender"));
        col_address   .setCellValueFactory(new PropertyValueFactory<>("address"));
        col_phone     .setCellValueFactory(new PropertyValueFactory<>("phone"));
        col_bloodgroup.setCellValueFactory(new PropertyValueFactory<>("bloodgroup"));
        col_speciality.setCellValueFactory(new PropertyValueFactory<>("speciality"));

        tableview.setItems(oblist);
    }
}
