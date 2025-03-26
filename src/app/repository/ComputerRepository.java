package app.repository;

import app.dao.Computer;
import app.util.DatabaseConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class ComputerRepository {
    public ObservableList<Computer> getAllComputers() {
        ObservableList<Computer> list = FXCollections.observableArrayList();
        String query = "SELECT * FROM computer";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Computer computer = new Computer(
                        rs.getInt("id"),
                        rs.getString("inventory_number"),
                        rs.getString("model"),
                        rs.getString("cpu"),
                        rs.getString("ram"),
                        rs.getString("os")
                );
                list.add(computer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean addComputer(Computer computer) {
        String sql = "INSERT INTO computer (inventory_number, model, cpu, ram, os) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, computer.getInventoryNumber());
            stmt.setString(2, computer.getModel());
            stmt.setString(3, computer.getCpu());
            stmt.setString(4, computer.getRam());
            stmt.setString(5, computer.getOs());
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    public boolean updateComputer(Computer computer) {
        String sql = "UPDATE computer SET inventory_number = ?, model = ?, cpu = ?, ram = ?, os = ? where id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, computer.getInventoryNumber());
            stmt.setString(2, computer.getModel());
            stmt.setString(3, computer.getCpu());
            stmt.setString(4, computer.getRam());
            stmt.setString(5, computer.getOs());
            stmt.setInt(6, computer.getId());
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    public void deleteComputer(int id) {
        String query = "DELETE FROM computer WHERE id=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
