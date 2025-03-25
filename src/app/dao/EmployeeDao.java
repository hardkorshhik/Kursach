package app.dao;

import app.model.Employee;
import app.util.DatabaseConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class EmployeeDao {
    public ObservableList<Employee> getAllEmployees() {
        ObservableList<Employee> list = FXCollections.observableArrayList();
        String query = "SELECT * FROM employee";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Employee employee = new Employee(
                        rs.getInt("id"),
                        rs.getString("full_name"),
                        rs.getString("position"),
                        rs.getString("department"),
                        rs.getString("email"),
                        rs.getString("phone")
                );
                list.add(employee);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean addEmployee(Employee employee) {
        String sql = "INSERT INTO employee (full_name, position, department, email, phone) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, employee.getFullName());
            pstmt.setString(2, employee.getPosition());
            pstmt.setString(3, employee.getDepartment());
            pstmt.setString(4, employee.getEmail());
            pstmt.setString(5, employee.getPhone());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }


    public void deleteEmployee(int id) {
        String query = "DELETE FROM employee WHERE id=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
