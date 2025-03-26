package app.controller;

import app.repository.*;
import app.dao.*;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.StringConverter;
import javafx.util.converter.IntegerStringConverter;

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

    private final ComputerRepository computerRepository = new ComputerRepository();
    private final EmployeeRepository employeeRepository = new EmployeeRepository();
    private final InstallationRepository installationRepository = new InstallationRepository();
    private final LicenseRepository licenseRepository = new LicenseRepository();
    private final SoftwareRepository softwareRepository = new SoftwareRepository();
    private final SoftwareUsageRepository softwareUsageRepository = new SoftwareUsageRepository();

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
                createColumnInteger("ID", "id"),
                createColumnText("Inventory Number", "inventoryNumber"),
                createColumnText("Model", "model"),
                createColumnText("CPU", "cpu"),
                createColumnText("RAM", "ram"),
                createColumnText("OS", "os")
        );
    }

    private void setupEmployeeTable() {
        employeeTable.getColumns().setAll(
                createColumnInteger("ID", "id"),
                createColumnText("Full Name", "fullName"),
                createColumnText("Position", "position"),
                createColumnText("Department", "department"),
                createColumnText("Email", "email"),
                createColumnText("Phone", "phone")
        );
    }

    private void setupInstallationTable() {
        installationTable.getColumns().setAll(
                createColumnInteger("ID", "id"),
                createColumnInteger("Software ID", "softwareId"),
                createColumnInteger("Computer ID", "computerId"),
                createColumnDate("Install Date", "installDate")
        );
    }

    private void setupLicenseTable() {
        licenseTable.getColumns().setAll(
                createColumnInteger("ID", "id"),
                createColumnInteger("Software ID", "softwareId"),
                createColumnText("Key", "key"),
                createColumnDate("Purchase Date", "purchaseDate"),
                createColumnDate("Expiration Date", "expirationDate")
        );
    }

    private void setupSoftwareTable() {
        softwareTable.getColumns().setAll(
                createColumnInteger("ID", "id"),
                createColumnText("Name", "name"),
                createColumnText("Version", "version"),
                createColumnText("Vendor", "vendor"),
                createColumnText("License Type", "licenseType")
        );
    }

    private void setupSoftwareUsageTable() {
        softwareUsageTable.getColumns().setAll(
                createColumnInteger("ID", "id"),
                createColumnInteger("Software ID", "softwareId"),
                createColumnInteger("Employee ID", "employeeId"),
                createColumnDate("Start Date", "startDate"),
                createColumnDate("End Date", "endDate")
        );
    }

    private <T> TableColumn<T, String> createColumnText(String title, String property) {
        TableColumn<T, String> col = new TableColumn<>(title);
        col.setCellFactory(TextFieldTableCell.forTableColumn());
        col.setCellValueFactory(new PropertyValueFactory<>(property));
        col.setEditable(true);
        col.setOnEditCommit(event -> {
            handlerTextColumn(event.getRowValue(), event.getNewValue(), title);
        });
        return col;
    }

    private <M> void handlerTextColumn(M model, String newValue, String title) {
        if (model instanceof Computer) {
            handlerComputer((Computer) model, newValue, title);
        } else if (model instanceof Employee) {
            handlerEmployee((Employee) model, newValue, title);
        } else if (model instanceof License) {
            handlerLicenseKey((License) model, newValue);
        } else if (model instanceof Software) {
            handlerSoftware((Software) model, newValue, title);
        }
    }

    private <T> TableColumn<T, LocalDate> createColumnDate(String title, String property) {
        TableColumn<T, LocalDate> col = new TableColumn<>(title);
        col.setCellValueFactory(new PropertyValueFactory<>(property));
        col.setCellFactory(TextFieldTableCell.forTableColumn(new StringConverter<>() {
            final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

            @Override
            public String toString(LocalDate localDate) {
                return localDate != null ? formatter.format(localDate) : "";
            }

            @Override
            public LocalDate fromString(String s) {
                return (s != null && !s.isEmpty()) ? LocalDate.parse(s, formatter) : null;
            }
        }));
        col.setEditable(true);
        col.setOnEditCommit(event -> handlerDateColumn(
                event.getRowValue(),
                event.getNewValue(),
                title
        ));
        return col;
    }

    private <M> void handlerDateColumn(M model, LocalDate newValue, String title) {
        if (model instanceof Installation) {
            handlerInstallationDate((Installation) model, newValue);
        } else if (model instanceof License) {
            handlerLicenseDate((License) model, newValue, title);
        } else if (model instanceof SoftwareUsage) {
            handlerSoftwareUsagesDate((SoftwareUsage) model, newValue, title);
        }
    }

    private <T> TableColumn<T, Integer> createColumnInteger(String title, String property) {
        TableColumn<T, Integer> col = new TableColumn<>(title);
        col.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        col.setCellValueFactory(new PropertyValueFactory<>(property));
        col.setEditable(!title.equals("ID"));
        col.setOnEditCommit(event -> handlerIntegerColumn(
                event.getRowValue(),
                event.getNewValue(),
                title
        ));
        return col;
    }

    private <M> void handlerIntegerColumn(M model, Integer newValue, String title) {
        if (model instanceof Installation) {
            handlerInstallation((Installation) model, newValue, title);
        } else if (model instanceof License) {
            handlerLicense((License) model, newValue);
        } else if (model instanceof SoftwareUsage) {
            handlerIntegerSoftwareUsages((SoftwareUsage) model, newValue, title);
        }
    }

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
        ObservableList<Computer> computers = computerRepository.getAllComputers();
        computerTable.setItems(computers);
        computerTable.setEditable(true);
    }

    private void handlerComputer(Computer computer, String newValue, String title) {
        switch (title) {
            case "Inventory Number" -> computer.setInventoryNumber(newValue);
            case "Model" -> computer.setModel(newValue);
            case "CPU" -> computer.setCpu(newValue);
            case "RAM" -> computer.setRam(newValue);
            case "OS" -> computer.setOs(newValue);
        }

        if (!computerRepository.updateComputer(computer)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка редактирования");
            alert.setHeaderText("Не удалось обновить запись в базе данных");
            alert.showAndWait();
        }
    }

    @FXML
    private void loadOrRefreshEmployees() {
        ObservableList<Employee> employees = employeeRepository.getAllEmployees();
        employeeTable.setItems(employees);
        employeeTable.setEditable(true);
    }

    private void handlerEmployee(Employee employee, String newValue, String title) {
        switch (title) {
            case "Full Name" -> employee.setFullName(newValue);
            case "Position" -> employee.setPosition(newValue);
            case "Department" -> employee.setDepartment(newValue);
            case "Email" -> employee.setEmail(newValue);
            case "Phone" -> employee.setPhone(newValue);
        }

        if (!employeeRepository.updateEmployee(employee)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка редактирования");
            alert.setHeaderText("Не удалось обновить запись в базе данных");
            alert.showAndWait();
        }
    }

    @FXML
    private void loadOrRefreshInstallations() {
        ObservableList<Installation> installations = installationRepository.getAllInstallations();
        installationTable.setItems(installations);
        installationTable.setEditable(true);
    }

    private void handlerInstallationDate(Installation installation, LocalDate newValue) {
        if (newValue == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Ошибка редактирования");
            alert.setHeaderText("Не корректна указана дата");
            alert.showAndWait();
            return;
        }

        installation.setInstallDate(newValue);

        if (!installationRepository.updateInstallation(installation)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка редактирования");
            alert.setHeaderText("Не удалось обновить запись в базе данных");
            alert.showAndWait();
        }
    }

    private void handlerInstallation(Installation installation, Integer newValue, String title) {
        switch (title) {
            case "Software ID" -> installation.setSoftwareId(newValue);
            case "Computer ID" -> installation.setComputerId(newValue);
        }

        if (!installationRepository.updateInstallation(installation)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка редактирования");
            alert.setHeaderText("Не удалось обновить запись в базе данных");
            alert.showAndWait();
        }
    }

    @FXML
    private void loadOrRefreshLicenses() {
        ObservableList<License> licenses = licenseRepository.getAllLicenses();
        licenseTable.setItems(licenses);
        licenseTable.setEditable(true);
    }

    private void handlerLicenseDate(License license, LocalDate newValue, String title) {
        switch (title) {
            case "Purchase Date" -> license.setPurchaseDate(newValue);
            case "Expiration Date" -> license.setExpirationDate(newValue);
        }

        if (!licenseRepository.updateLicense(license)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка редактирования");
            alert.setHeaderText("Не удалось обновить запись в базе данных");
            alert.showAndWait();
        }
    }

    private void handlerLicenseKey(License license, String newValue) {
        license.setKey(newValue);

        if (!licenseRepository.updateLicense(license)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка редактирования");
            alert.setHeaderText("Не удалось обновить запись в базе данных");
            alert.showAndWait();
        }
    }

    private void handlerLicense(License license, Integer newValue) {
        license.setSoftwareId(newValue);

        if (!licenseRepository.updateLicense(license)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка редактирования");
            alert.setHeaderText("Не удалось обновить запись в базе данных");
            alert.showAndWait();
        }
    }

    @FXML
    private void loadOrRefreshSoftwares() {
        ObservableList<Software> softwareList = softwareRepository.getAllSoftware();
        softwareTable.setItems(softwareList);
        softwareTable.setEditable(true);
    }

    private void handlerSoftware(Software software, String newValue, String title) {
        switch (title) {
            case "Name" -> software.setName(newValue);
            case "Version" -> software.setVersion(newValue);
            case "Vemdor" -> software.setVendor(newValue);
            case "License Type" -> software.setLicenseType(newValue);
        }

        if (!softwareRepository.updateSoftware(software)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка редактирования");
            alert.setHeaderText("Не удалось обновить запись в базе данных");
            alert.showAndWait();
        }
    }

    @FXML
    private void loadOrRefreshSoftwareUsages() {
        ObservableList<SoftwareUsage> usageList = softwareUsageRepository.getAllSoftwareUsage();
        softwareUsageTable.setItems(usageList);
        softwareUsageTable.setEditable(true);
    }

    private void handlerSoftwareUsagesDate(SoftwareUsage softwareUsage, LocalDate newValue, String title) {
        switch (title) {
            case "Start Date" -> softwareUsage.setStartDate(newValue);
            case "End Date" -> softwareUsage.setEndDate(newValue);
        }

        if (!softwareUsageRepository.updateSoftwareUsage(softwareUsage)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка редактирования");
            alert.setHeaderText("Не удалось обновить запись в базе данных");
            alert.showAndWait();
        }
    }

    private void handlerIntegerSoftwareUsages(SoftwareUsage softwareUsage, Integer newValue, String title) {
        switch (title) {
            case "Software ID" -> softwareUsage.setSoftwareId(newValue);
            case "Employee ID" -> softwareUsage.setEmployeeId(newValue);
        }

        if (!softwareUsageRepository.updateSoftwareUsage(softwareUsage)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка редактирования");
            alert.setHeaderText("Не удалось обновить запись в базе данных");
            alert.showAndWait();
        }
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
        if (!computerRepository.addComputer(newComputer)) {
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
            computerRepository.deleteComputer(selected.getId());
            loadOrRefreshComputers();
        }
    }

    @FXML
    private void addEmployee() {
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
        if (!employeeRepository.addEmployee(employee)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Проиозошла ошибка");
            alert.setHeaderText("Произошла ошибка при добавлении!");
            alert.showAndWait();
            return;
        }

        employeeFullNameField.clear();
        employeePositionField.clear();
        employeeDepartmentField.clear();
        employeeEmailField.clear();
        employeePhoneField.clear();

        loadOrRefreshEmployees();
    }


    @FXML
    private void deleteEmployee() {
        Employee selected = employeeTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            employeeRepository.deleteEmployee(selected.getId());
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

        if (installation == null || !installationRepository.addInstallation(installation)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Проиозошла ошибка");
            alert.setHeaderText("Произошла ошибка при добавлении!");
            alert.showAndWait();
        }

        loadOrRefreshInstallations();

        installationSoftwareIdField.clear();
        installationComputerIdField.clear();
        installationDateField.clear();
    }


    @FXML
    private void deleteInstallation() {
        Installation selected = installationTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            installationRepository.deleteInstallation(selected.getId());
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

        if (license == null || !licenseRepository.addLicense(license)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Произошла ошибка");
            alert.setHeaderText("Произошла ошибка при добавлении!");
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
            licenseRepository.deleteLicense(selected.getId());
            loadOrRefreshLicenses();
        }
    }

    @FXML
    private void addSoftware() {
        String name = softwareNameField.getText().trim();
        String version = softwareVersionField.getText().trim();
        String vendor = softwareVendorField.getText().trim();
        String licenseType = softwareLicenseTypeField.getText().trim();

        if (name.isEmpty() || version.isEmpty() || vendor.isEmpty() || licenseType.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Произошла ошибка");
            alert.setHeaderText("Не все поля были заполнены");
            alert.showAndWait();
            return;
        }

        Software software = new Software(name, version, vendor, licenseType);
        if (!softwareRepository.addSoftware(software)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Произошла ошибка!");
            alert.setHeaderText("Произошла ошибка при добавлении!");
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
            softwareRepository.deleteSoftware(selected.getId());
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

        if (softwareUsage == null || !softwareUsageRepository.addSoftwareUsage(softwareUsage)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Произошла ошибка!");
            alert.setHeaderText("Произошла ошибка при добавлении!");
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
            softwareUsageRepository.deleteSoftwareUsage(selected.getId());
            loadOrRefreshSoftwareUsages();
        }
    }

}
