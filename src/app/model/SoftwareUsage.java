package app.model;

import java.time.LocalDate;

public class SoftwareUsage {
    private int id;
    private int softwareId;
    private int employeeId;
    private LocalDate startDate;  // Было String -> стало LocalDate
    private LocalDate endDate;    // Было String -> стало LocalDate

    public SoftwareUsage(int id, int softwareId, int employeeId, LocalDate startDate, LocalDate endDate) {
        this.id = id;
        this.softwareId = softwareId;
        this.employeeId = employeeId;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public SoftwareUsage(int softwareId, int employeeId, LocalDate startDate, LocalDate endDate) {
        this.softwareId = softwareId;
        this.employeeId = employeeId;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public int getId() {
        return id;
    }

    public int getSoftwareId() {
        return softwareId;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    @Override
    public String toString() {
        return "SoftwareUsage{" +
                "id=" + id +
                ", softwareId=" + softwareId +
                ", employeeId=" + employeeId +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }

    public void setSoftwareId(int softwareId) {
        this.softwareId = softwareId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
}
