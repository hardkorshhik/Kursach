package app.dao;

import javafx.beans.property.*;

public class Employee {
    private final IntegerProperty id;
    private final StringProperty fullName;
    private final StringProperty position;
    private final StringProperty department;
    private final StringProperty email;
    private final StringProperty phone;

    public Employee(int id, String fullName, String position, String department, String email, String phone) {
        this.id = new SimpleIntegerProperty(id);
        this.fullName = new SimpleStringProperty(fullName);
        this.position = new SimpleStringProperty(position);
        this.department = new SimpleStringProperty(department);
        this.email = new SimpleStringProperty(email);
        this.phone = new SimpleStringProperty(phone);
    }

    public Employee(String fullName, String position, String department, String email, String phone) {
        this.id = new SimpleIntegerProperty(0);
        this.fullName = new SimpleStringProperty(fullName);
        this.position = new SimpleStringProperty(position);
        this.department = new SimpleStringProperty(department);
        this.email = new SimpleStringProperty(email);
        this.phone = new SimpleStringProperty(phone);
    }

    public int getId() { return id.get(); }
    public IntegerProperty idProperty() { return id; }
    public void setId(int id) { this.id.set(id); }

    public String getFullName() { return fullName.get(); }
    public StringProperty fullNameProperty() { return fullName; }
    public void setFullName(String fullName) { this.fullName.set(fullName); }

    public String getPosition() { return position.get(); }
    public StringProperty positionProperty() { return position; }
    public void setPosition(String position) { this.position.set(position); }

    public String getDepartment() { return department.get(); }
    public StringProperty departmentProperty() { return department; }
    public void setDepartment(String department) { this.department.set(department); }

    public String getEmail() { return email.get(); }
    public StringProperty emailProperty() { return email; }
    public void setEmail(String email) { this.email.set(email); }

    public String getPhone() { return phone.get(); }
    public StringProperty phoneProperty() { return phone; }
    public void setPhone(String phone) { this.phone.set(phone); }
}
