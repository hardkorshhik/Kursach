package app.controller;

import app.dao.*;
import app.model.*;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import java.time.LocalDate;
import javafx.scene.control.TextField;

public class MainController {

    @FXML private TableView<Computer> computerTable;
    @FXML private TableView<Employee> employeeTable;
    @FXML private TableView<Installation> installationTable;
    @FXML private TableView<License> licenseTable;
    @FXML private TableView<Software> softwareTable;
    @FXML private TableView<SoftwareUsage> softwareUsageTable;
    @FXML private TextField softwareIdField;
    @FXML private TextField licenseKeyField;
    @FXML private TextField purchaseDateField;
    @FXML private TextField expirationDateField;
    @FXML private TextField employeeFullNameField;
    @FXML private TextField employeePositionField;
    @FXML private TextField employeeDepartmentField;
    @FXML private TextField employeeEmailField;
    @FXML private TextField employeePhoneField;
    @FXML private TextField computerInventoryNumberField;
    @FXML private TextField computerModelField;
    @FXML private TextField computerCpuField;
    @FXML private TextField computerRamField;
    @FXML private TextField computerOsField;
    @FXML private TextField installationSoftwareIdField;
    @FXML private TextField installationComputerIdField;
    @FXML private TextField installationDateField;
    @FXML private TextField softwareNameField;
    @FXML private TextField softwareVersionField;
    @FXML private TextField softwareVendorField;
    @FXML private TextField softwareLicenseTypeField;
    @FXML private TextField softwareUsageSoftwareIdField;
    @FXML private TextField softwareUsageEmployeeIdField;
    @FXML private TextField softwareUsageStartDateField;
    @FXML private TextField softwareUsageEndDateField;


    // DAO Instances
    private final ComputerDao computerDAO = new ComputerDao();
    private final EmployeeDao employeeDAO = new EmployeeDao();
    private final InstallationDao installationDAO = new InstallationDao();
    private final LicenseDao licenseDAO = new LicenseDao();
    private final SoftwareDao softwareDAO = new SoftwareDao();
    private final SoftwareUsageDao softwareUsageDAO = new SoftwareUsageDao();

    @FXML
    public void initialize() {
        setupComputerTable();
        setupEmployeeTable();
        setupInstallationTable();
        setupLicenseTable();
        setupSoftwareTable();
        setupSoftwareUsageTable();

        loadOrRefreshTables();
    }

    private void setupComputerTable() {
        computerTable.getColumns().setAll(
                createColumn("ID", "id"),
                createColumn("Inventory Number", "inventoryNumber"),
                createColumn("Model", "model"),
                createColumn("CPU", "cpu"),
                createColumn("RAM", "ram"),
                createColumn("OS", "os")
        );
    }

    private void setupEmployeeTable() {
        employeeTable.getColumns().setAll(
                createColumn("ID", "id"),
                createColumn("Full Name", "fullName"),
                createColumn("Position", "position"),
                createColumn("Department", "department"),
                createColumn("Email", "email"),
                createColumn("Phone", "phone")
        );
    }

    private void setupInstallationTable() {
        installationTable.getColumns().setAll(
                createColumn("ID", "id"),
                createColumn("Software ID", "softwareId"),
                createColumn("Computer ID", "computerId"),
                createColumn("Install Date", "installDate")
        );
    }

    private void setupLicenseTable() {
        licenseTable.getColumns().setAll(
                createColumn("ID", "id"),
                createColumn("Software ID", "softwareId"),
                createColumn("Key", "key"),
                createColumn("Purchase Date", "purchaseDate"),
                createColumn("Expiration Date", "expirationDate")
        );
    }

    private void setupSoftwareTable() {
        softwareTable.getColumns().setAll(
                createColumn("ID", "id"),
                createColumn("Name", "name"),
                createColumn("Version", "version"),
                createColumn("Vendor", "vendor"),
                createColumn("License Type", "licenseType")
        );
    }

    private void setupSoftwareUsageTable() {
        softwareUsageTable.getColumns().setAll(
                createColumn("ID", "id"),
                createColumn("Software ID", "softwareId"),
                createColumn("Employee ID", "employeeId"),
                createColumn("Start Date", "startDate"),
                createColumn("End Date", "endDate")
        );
    }

    // Универсальный метод для создания колонок
    private <T> TableColumn<T, ?> createColumn(String title, String property) {
        TableColumn<T, Object> col = new TableColumn<>(title);
        col.setCellValueFactory(new PropertyValueFactory<>(property));
        return col;
    }

    // Загрузка данных в таблицы
    @FXML
    private void loadOrRefreshTables() {
        loadOrRefreshComputers();
        loadOrRefreshEmployees();
        loadOrRefreshInstallations();
        loadOrRefreshLicenses();
        loadOrRefreshSoftwares();
        loadOrRefreshSoftwareUsages();
    }

    @FXML
    private void loadOrRefreshComputers() {
        ObservableList<Computer> computers = computerDAO.getAllComputers();
        computerTable.setItems(computers);
    }

    @FXML
    private void loadOrRefreshEmployees() {
        ObservableList<Employee> employees = employeeDAO.getAllEmployees();
        employeeTable.setItems(employees);
    }

    @FXML
    private void loadOrRefreshInstallations() {
        ObservableList<Installation> installations = installationDAO.getAllInstallations();
        installationTable.setItems(installations);
    }

    @FXML
    private void loadOrRefreshLicenses() {
        ObservableList<License> licenses = licenseDAO.getAllLicenses();
        licenseTable.setItems(licenses);
    }

    @FXML
    private void loadOrRefreshSoftwares() {
        ObservableList<Software> softwareList = softwareDAO.getAllSoftware();
        softwareTable.setItems(softwareList);
    }

    @FXML
    private void loadOrRefreshSoftwareUsages() {
        ObservableList<SoftwareUsage> usageList = softwareUsageDAO.getAllSoftwareUsage();
        softwareUsageTable.setItems(usageList);
    }

    @FXML
    private void addComputer() {
        String inventoryNumber = computerInventoryNumberField.getText();
        String model = computerModelField.getText();
        String cpu = computerCpuField.getText();
        String ram = computerRamField.getText();
        String os = computerOsField.getText();

        if (inventoryNumber.isEmpty() || model.isEmpty() || cpu.isEmpty() || ram.isEmpty() || os.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Предупреждение");
            alert.setHeaderText("Не заполнены все поля при добавлении компьютера");
            alert.showAndWait();
            return;
        }

        Computer newComputer = new Computer(inventoryNumber, model, cpu, ram, os);
        if (!computerDAO.addComputer(newComputer)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Проиозошла ошибка");
            alert.setHeaderText("Произошла ошибка при добавлении!");
            alert.showAndWait();
            return;
        }

        loadOrRefreshComputers();

        computerInventoryNumberField.clear();
        computerModelField.clear();
        computerCpuField.clear();
        computerRamField.clear();
        computerOsField.clear();
    }


    @FXML
    private void deleteComputer() {
        Computer selected = computerTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            computerDAO.deleteComputer(selected.getId());
            loadOrRefreshComputers();
        }
    }

    @FXML
    private void addEmployee() {
        // Получаем данные из полей ввода
        String fullName = employeeFullNameField.getText();
        String position = employeePositionField.getText();
        String department = employeeDepartmentField.getText();
        String email = employeeEmailField.getText();
        String phone = employeePhoneField.getText();

        if (fullName.isEmpty() || position.isEmpty() || department.isEmpty() || email.isEmpty() || phone.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Предупреждение");
            alert.setHeaderText("Не заполнены все поля при добавлении сотрудника");
            alert.showAndWait();
            return;
        }

        Employee employee = new Employee(fullName, position, department, email, phone);
        if (!employeeDAO.addEmployee(employee)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Проиозошла ошибка");
            alert.setHeaderText("Произошла ошибка при добавлении!");
            alert.showAndWait();
            return;
        }

        // Очищаем поля ввода
        employeeFullNameField.clear();
        employeePositionField.clear();
        employeeDepartmentField.clear();
        employeeEmailField.clear();
        employeePhoneField.clear();

        // Обновляем таблицу
        loadOrRefreshEmployees();
    }


    @FXML
    private void deleteEmployee() {
        Employee selected = employeeTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            employeeDAO.deleteEmployee(selected.getId());
            loadOrRefreshEmployees();
        }
    }

    @FXML
    private void addInstallation() {
        String software = installationSoftwareIdField.getText().trim();
        String computer = installationComputerIdField.getText().trim();
        String date = installationDateField.getText().trim();

        if (software.isEmpty() || computer.isEmpty() || date.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Предупреждение");
            alert.setHeaderText("Не заполнены все поля!");
            alert.showAndWait();
            return;
        }

        Installation installation = null;
        try {
            installation = new Installation(
                    Integer.parseInt(software),
                    Integer.parseInt(computer),
                    LocalDate.parse(date)
            );
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        if (installation == null || !installationDAO.addInstallation(installation)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Проиозошла ошибка");
            alert.setHeaderText("Произошла ошибка при добавлении!");
            alert.showAndWait();
        }

        loadOrRefreshInstallations();

        // Очищаем поля
        installationSoftwareIdField.clear();
        installationComputerIdField.clear();
        installationDateField.clear();
    }


    @FXML
    private void deleteInstallation() {
        Installation selected = installationTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            installationDAO.deleteInstallation(selected.getId());
            loadOrRefreshInstallations();
        }
    }

    @FXML
    private void addLicense() {
        String softwareId = softwareIdField.getText().trim();
        String licenseKey = licenseKeyField.getText().trim();
        String purchaseDate = purchaseDateField.getText().trim();
        String expirationDate = expirationDateField.getText().trim();

        if (softwareId.isEmpty() || licenseKey.isEmpty() || purchaseDate.isEmpty() || expirationDate.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Предупреждение");
            alert.setHeaderText("Не заполнены все поля!");
            alert.showAndWait();
            return;
        }

        License license = null;
        try {
            license = new License(
                    Integer.parseInt(softwareId),
                    licenseKey,
                    LocalDate.parse(purchaseDate),
                    LocalDate.parse(expirationDate)
            );
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        if (license == null || !licenseDAO.addLicense(license)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Произошла ошибка");
            alert.setHeaderText("Слава родился");
            alert.showAndWait();
            return;
        }

        loadOrRefreshLicenses();

        softwareIdField.clear();
        licenseKeyField.clear();
        purchaseDateField.clear();
        expirationDateField.clear();
    }

    @FXML
    private void deleteLicense() {
        License selected = licenseTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            licenseDAO.deleteLicense(selected.getId());
            loadOrRefreshLicenses();
        }
    }

    @FXML
    private void addSoftware() {
        String name = softwareNameField.getText().trim(); // Получаем название программного обеспечения
        String version = softwareVersionField.getText().trim(); // Получаем версию
        String vendor = softwareVendorField.getText().trim(); // Получаем производителя
        String licenseType = softwareLicenseTypeField.getText().trim(); // Получаем тип лицензии

        if (name.isEmpty() || version.isEmpty() || vendor.isEmpty() || licenseType.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Произошла ошибка");
            alert.setHeaderText("Не все поля были заполнены");
            alert.showAndWait();
            return;
        }

        Software software = new Software(name, version, vendor, licenseType);
        if (!softwareDAO.addSoftware(software)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Произошла ошибка!");
            alert.setHeaderText("Слава родился!");
            alert.showAndWait();
            return;
        }

        loadOrRefreshSoftwares();

        softwareNameField.clear();
        softwareVersionField.clear();
        softwareVendorField.clear();
        softwareLicenseTypeField.clear();
    }


    @FXML
    private void deleteSoftware() {
        Software selected = softwareTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            softwareDAO.deleteSoftware(selected.getId());
            loadOrRefreshSoftwares();
        }
    }

    @FXML
    private void addSoftwareUsage() {
        String softwareId = softwareUsageSoftwareIdField.getText().trim();
        String employeeId = softwareUsageEmployeeIdField.getText().trim();
        String startDate = softwareUsageStartDateField.getText().trim();
        String endDate = softwareUsageEndDateField.getText().trim();

        if (softwareId.isEmpty() || employeeId.isEmpty() || startDate.isEmpty() || endDate.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Произошла ошибка");
            alert.setHeaderText("Не все поля были заполнены");
            alert.showAndWait();
            return;
        }

        SoftwareUsage softwareUsage = null;
        try {
            softwareUsage = new SoftwareUsage(
                    Integer.parseInt(softwareId),
                    Integer.parseInt(employeeId),
                    LocalDate.parse(startDate),
                    LocalDate.parse(endDate)
            );
        } catch (Exception exception) {
            System.out.println(exception.getMessage());

        }

        if (softwareUsage == null || !softwareUsageDAO.addSoftwareUsage(softwareUsage)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Произошла ошибка!");
            alert.setHeaderText("Слава родился!");
            alert.showAndWait();
            return;
        }

        loadOrRefreshSoftwareUsages();

        softwareUsageSoftwareIdField.clear();
        softwareUsageEmployeeIdField.clear();
        softwareUsageStartDateField.clear();
        softwareUsageEndDateField.clear();
    }


    @FXML
    private void deleteSoftwareUsage() {
        SoftwareUsage selected = softwareUsageTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            softwareUsageDAO.deleteSoftwareUsage(selected.getId());
            loadOrRefreshSoftwareUsages();
        }
    }

}
