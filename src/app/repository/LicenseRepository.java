package app.repository;

import app.dao.License;
import app.util.DatabaseConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class LicenseRepository {
    public ObservableList<License> getAllLicenses() {
        ObservableList<License> list = FXCollections.observableArrayList();
        String query = "SELECT * FROM license";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                License license = new License(
                        rs.getInt("id"),
                        rs.getInt("software_id"),
                        rs.getString("key"),
                        rs.getDate("purchase_date").toLocalDate(),
                        rs.getDate("expiration_date").toLocalDate()
                );
                list.add(license);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean addLicense(License license) {
        String query = "INSERT INTO license (software_id, key, purchase_date, expiration_date) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, license.getSoftwareId());
            ps.setString(2, license.getKey());
            ps.setDate(3, (license.getPurchaseDate() != null) ? Date.valueOf(license.getPurchaseDate()) : null);
            ps.setDate(4, (license.getExpirationDate() != null) ? Date.valueOf(license.getExpirationDate()) : null);

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean updateLicense(License license) {
        String query = "UPDATE license SET software_id = ?, key = ?, purchase_date = ?, expiration_date = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, license.getSoftwareId());
            ps.setString(2, license.getKey());
            ps.setDate(3, (license.getPurchaseDate() != null) ? Date.valueOf(license.getPurchaseDate()) : null);
            ps.setDate(4, (license.getExpirationDate() != null) ? Date.valueOf(license.getExpirationDate()) : null);
            ps.setInt(5, license.getId());

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public void deleteLicense(int id) {
        String query = "DELETE FROM license WHERE id=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
