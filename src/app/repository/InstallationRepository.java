package app.repository;

import app.dao.Installation;
import app.util.DatabaseConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.LocalDate;

public class InstallationRepository {

    public ObservableList<Installation> getAllInstallations() {
        ObservableList<Installation> list = FXCollections.observableArrayList();
        String query = "SELECT * FROM installation";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Date sqlDate = rs.getDate("install_date");
                LocalDate installDate = (sqlDate != null) ? sqlDate.toLocalDate() : null;

                Installation installation = new Installation(
                        rs.getInt("id"),
                        rs.getInt("software_id"),
                        rs.getInt("computer_id"),
                        installDate
                );
                list.add(installation);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean addInstallation(Installation installation) {
        String query = "INSERT INTO installation (software_id, computer_id, install_date) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, installation.getSoftwareId());
            ps.setInt(2, installation.getComputerId());
            ps.setDate(3, (installation.getInstallDate() != null) ? Date.valueOf(installation.getInstallDate()) : null);
            ps.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    public boolean updateInstallation(Installation installation) {
        String query = "UPDATE installation SET software_id = ?, computer_id = ?, install_date = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, installation.getSoftwareId());
            ps.setInt(2, installation.getComputerId());
            ps.setDate(3, (installation.getInstallDate() != null) ? Date.valueOf(installation.getInstallDate()) : null);
            ps.setInt(4, installation.getId());
            ps.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    public void deleteInstallation(int id) {
        String query = "DELETE FROM installation WHERE id=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
