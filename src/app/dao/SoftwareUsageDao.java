package app.dao;

import app.model.SoftwareUsage;
import app.util.DatabaseConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class SoftwareUsageDao {
    public ObservableList<SoftwareUsage> getAllSoftwareUsage() {
        ObservableList<SoftwareUsage> list = FXCollections.observableArrayList();
        String query = "SELECT * FROM software_usage";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                SoftwareUsage usage = new SoftwareUsage(
                        rs.getInt("id"),
                        rs.getInt("software_id"),
                        rs.getInt("employee_id"),
                        rs.getDate("start_date").toLocalDate(),
                        rs.getDate("end_date").toLocalDate()
                );
                list.add(usage);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean addSoftwareUsage(SoftwareUsage softwareUsage) {
        String query = "INSERT INTO software_usage (software_id, employee_id, start_date, end_date) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, softwareUsage.getSoftwareId());
            ps.setInt(2, softwareUsage.getEmployeeId());
            ps.setDate(3, Date.valueOf(softwareUsage.getStartDate()));
            ps.setDate(4, Date.valueOf(softwareUsage.getEndDate()));

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean updateSoftwareUsage(SoftwareUsage softwareUsage) {
        String query = "UPDATE software_usage SET software_id = ?, employee_id = ?, start_date = ?, end_date = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, softwareUsage.getSoftwareId());
            ps.setInt(2, softwareUsage.getEmployeeId());
            ps.setDate(3, Date.valueOf(softwareUsage.getStartDate()));
            ps.setDate(4, Date.valueOf(softwareUsage.getEndDate()));
            ps.setInt(5, softwareUsage.getId());

            ps.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    public void deleteSoftwareUsage(int id) {
        String query = "DELETE FROM software_usage WHERE id=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
