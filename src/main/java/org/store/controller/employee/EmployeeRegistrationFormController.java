package org.store.controller.employee;

import com.jfoenix.controls.JFXTextField;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import org.store.bo.BoFactory;
import org.store.bo.custom.CustomerBo;
import org.store.bo.custom.EmployeeBo;
import org.store.controller.customer.CustomerController;
import org.store.db.DBConnection;
import org.store.dto.CustomerDto;
import org.store.dto.EmployeeDto;
import org.store.dto.tm.CustomerTM;
import org.store.dto.tm.EmployeeTM;
import org.store.utill.BoType;
import org.store.utill.CrudUtill;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;
import java.util.ResourceBundle;

public class EmployeeRegistrationFormController implements Initializable {
    public AnchorPane LoadFormContent;
    public Label lblDate;
    public Label lblTime;
    public TableView tblEmployee;
    public TableColumn colId;
    public TableColumn colName;
    public TableColumn colCompany;
    public TableColumn colEmail;
    public JFXTextField txtEmployeeId;
    public JFXTextField txtCompany;
    public JFXTextField txtEmployeeName;
    public JFXTextField txtEmail;

    private EmployeeBo employeeBo = BoFactory.getInstance().getBo(BoType.EMPLOYEE);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colCompany.setCellValueFactory(new PropertyValueFactory<>("company"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        loadDateAndTime();
        loadEmployeeTable();
    }

    private void loadEmployeeTable() {
        ObservableList<EmployeeTM> employeeTableData = FXCollections.observableArrayList();
        ObservableList<EmployeeDto> allEmployees = EmployeeController.getInstance().loadEmployees();

        allEmployees.forEach(employeeDto -> {
            employeeTableData.add(
                    new EmployeeTM(
                            employeeDto.getId(),
                            employeeDto.getName(),
                            employeeDto.getCompany(),
                            employeeDto.getEmail()

                    )
            );
        });
        tblEmployee.setItems(employeeTableData);
    }

    private void loadDateAndTime() {
        Date date = new Date();
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        lblDate.setText(f.format(date));

        Timeline time = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            LocalTime cTime = LocalTime.now();
            lblTime.setText(
                    cTime.getHour() + ":" + cTime.getMinute() + ":" + cTime.getSecond()
            );
        }),
                new KeyFrame(Duration.seconds(1))
        );
        time.setCycleCount(Animation.INDEFINITE);
        time.play();
    }

    public void backBtnOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = this.getClass().getResource("/view/admin_dashboard.fxml");

        assert resource != null;

        Parent load = (Parent) FXMLLoader.load(resource);
        this.LoadFormContent.getChildren().clear();
        this.LoadFormContent.getChildren().add(load);

    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {
    }

    public void btnAddEmployeeOnAction(ActionEvent actionEvent) {

            EmployeeDto employeeDto = new EmployeeDto(
                    txtEmployeeId.getText(),
                    txtEmployeeName.getText(),
                    txtCompany.getText(),
                    txtEmail.getText()
            );

            //boolean b = EmployeeController.getInstance().addEmployee(employeeDto);
            boolean b = employeeBo.saveEmployee(employeeDto);
            if (b){
                new Alert(Alert.AlertType.CONFIRMATION,"Employee Not Added!").show();
            }else{
                new Alert(Alert.AlertType.CONFIRMATION,"Employee  Added!").show();
            }

    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {
        try {
            boolean execute = CrudUtill.execute("DELETE FROM employee WHERE EmpId='" + txtEmployeeId.getText() + "'");
            loadEmployeeTable();
            clearText();
            if(execute){
                System.out.println("Employee Deleted");
            }else {
                System.out.println("Employee Not Deleted");
            }
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void btnClearOnAction(ActionEvent actionEvent) {
        txtEmployeeId.setText(null);
        txtEmployeeName.setText(null);
        txtCompany.setText(null);
        txtEmail.setText(null);
    }

    private void clearText(){
        txtEmployeeId.setText(null);
        txtEmployeeName.setText(null);
        txtCompany.setText(null);
        txtEmail.setText(null);
    }
}
