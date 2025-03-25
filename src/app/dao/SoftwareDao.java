package app.dao;

import app.model.Software;
import app.util.DatabaseConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class SoftwareDao {
    public ObservableList<Software> getAllSoftware() {
        ObservableList<Software> list = FXCollections.observableArrayList();
        String query = "SELECT * FROM software";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Software software = new Software(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("version"),
                        rs.getString("vendor"),
                        rs.getString("license_type")
                );
                list.add(software);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean addSoftware(Software software) {
        String query = "INSERT INTO software (name, version, vendor, license_type) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, software.getName());
            ps.setString(2, software.getVersion());
            ps.setString(3, software.getVendor());
            ps.setString(4, software.getLicenseType());

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public void deleteSoftware(int id) {
        String query = "DELETE FROM software WHERE id=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
