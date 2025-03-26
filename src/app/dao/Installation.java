package app.dao;

import java.time.LocalDate;

public class Installation {
    private int id;
    private int softwareId;
    private int computerId;
    private LocalDate installDate;  // Исправил String -> LocalDate

    public Installation(int id, int softwareId, int computerId, LocalDate installDate) {
        this.id = id;
        this.softwareId = softwareId;
        this.computerId = computerId;
        this.installDate = installDate;
    }

    public Installation(int softwareId, int computerId, LocalDate installDate) {
        this.softwareId = softwareId;
        this.computerId = computerId;
        this.installDate = installDate;
    }

    public int getId() {
        return id;
    }

    public int getSoftwareId() {
        return softwareId;
    }

    public int getComputerId() {
        return computerId;
    }

    public LocalDate getInstallDate() {
        return installDate;
    }

    public void setSoftwareId(int softwareId) {
        this.softwareId = softwareId;
    }

    public void setComputerId(int computerId) {
        this.computerId = computerId;
    }

    public void setInstallDate(LocalDate installDate) {
        this.installDate = installDate;
    }

    @Override
    public String toString() {
        return "Installation{" +
                "id=" + id +
                ", softwareId=" + softwareId +
                ", computerId=" + computerId +
                ", installDate=" + installDate +
                '}';
    }
}
