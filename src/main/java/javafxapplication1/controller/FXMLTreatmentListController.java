package javafxapplication1.controller;

import javafxapplication1.util.ConnectionUtil;
import javafxapplication1.model.ModelTable3;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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

public class FXMLTreatmentListController implements Initializable {

    @FXML private MenuBar myMenuBar;
    @FXML private TableView<ModelTable3> tableview;
    @FXML private TableColumn<ModelTable3, String> col_treatmentid;
    @FXML private TableColumn<ModelTable3, String> col_treatmentname;
    @FXML private TableColumn<ModelTable3, String> col_treatmentamount;

    private final ObservableList<ModelTable3> oblist = FXCollections.observableArrayList();

    @FXML
    private void logoutButtonAction(ActionEvent event) throws IOException {
        Parent loginParent = FXMLLoader.load(
            getClass().getResource("/javafxapplication1/view/FXMLDocument.fxml")
        );
        Scene loginScene = new Scene(loginParent);
        Stage stage = (Stage) ((Node) myMenuBar).getScene().getWindow();
        stage.setScene(loginScene);
        stage.show();
    }

    // Aquí puedes agregar otros métodos de navegación (addPatient, updatePatient, etc.)

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // 1) Cargar datos
        loadTreatments();

        // 2) Configurar las columnas
        col_treatmentid.setCellValueFactory(new PropertyValueFactory<>("treatmentid"));
        col_treatmentname.setCellValueFactory(new PropertyValueFactory<>("treatmentname"));
        col_treatmentamount.setCellValueFactory(new PropertyValueFactory<>("treatmentamount"));

        // 3) Asignar datos a la tabla
        tableview.setItems(oblist);
    }

    private void loadTreatments() {
        String sql = "SELECT treatmentid, treatmentname, treatmentamount FROM treatment";
        try (Connection con = ConnectionUtil.getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                oblist.add(new ModelTable3(
                    rs.getString("treatmentid"),
                    rs.getString("treatmentname"),
                    rs.getString("treatmentamount")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
