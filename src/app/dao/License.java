package app.dao;

import java.time.LocalDate;

public class License {
    private int id;
    private int softwareId;
    private String key;
    private LocalDate purchaseDate;
    private LocalDate expirationDate;

    public License(int softwareId, String key, LocalDate purchaseDate, LocalDate expirationDate) {
        this.id = 0;
        this.softwareId = softwareId;
        this.key = key;
        this.purchaseDate = purchaseDate;
        this.expirationDate = expirationDate;
    }

    public License(int id, int softwareId, String key, LocalDate purchaseDate, LocalDate expirationDate) {
        this.id = id;
        this.softwareId = softwareId;
        this.key = key;
        this.purchaseDate = purchaseDate;
        this.expirationDate = expirationDate;
    }

    public void setSoftwareId(int softwareId) {
        this.softwareId = softwareId;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setPurchaseDate(LocalDate purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }

    public int getId() {
        return id;
    }

    public int getSoftwareId() {
        return softwareId;
    }

    public String getKey() {
        return key;
    }

    public LocalDate getPurchaseDate() {
        return purchaseDate;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    @Override
    public String toString() {
        return "License{" +
                "id=" + id +
                ", softwareId=" + softwareId +
                ", key='" + key + '\'' +
                ", purchaseDate=" + purchaseDate +
                ", expirationDate=" + expirationDate +
                '}';
    }
}
