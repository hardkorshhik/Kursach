package app.dao;

public class Software {
    private int id;
    private String name;
    private String version;
    private String vendor;

    private String licenseType;

    public Software(int id, String name, String version, String vendor, String licenseType) {
        this.id = id;
        this.name = name;
        this.version = version;
        this.vendor = vendor;
        this.licenseType = licenseType;
    }

    public Software(String name, String version, String vendor, String licenseType) {
        this.id = 0;
        this.name = name;
        this.version = version;
        this.vendor = vendor;
        this.licenseType = licenseType;
    }

    // Геттеры
    public int getId() { return id; }
    public String getName() { return name; }
    public String getVersion() { return version; }
    public String getVendor() { return vendor; }
    public String getLicenseType() { return licenseType; }

    public void setLicenseType(String licenseType) {
        this.licenseType = licenseType;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Software{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", version='" + version + '\'' +
                ", vendor='" + vendor + '\'' +
                ", licenseType='" + licenseType + '\'' +
                '}';
    }
}
