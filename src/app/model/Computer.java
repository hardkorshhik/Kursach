package app.model;

public class Computer {
    private int id;
    private String inventoryNumber;
    private String model;
    private String cpu;
    private String ram;
    private String os;

    public Computer(int id, String inventoryNumber, String model, String cpu, String ram, String os) {
        this.id = id;
        this.inventoryNumber = inventoryNumber;
        this.model = model;
        this.cpu = cpu;
        this.ram = ram;
        this.os = os;
    }

    public Computer(String inventoryNumber, String model, String cpu, String ram, String os) {
        this.inventoryNumber = inventoryNumber;
        this.model = model;
        this.cpu = cpu;
        this.ram = ram;
        this.os = os;
    }

    public int getId() {
        return id;
    }

    public String getInventoryNumber() {
        return inventoryNumber;
    }

    public String getModel() {
        return model;
    }

    public String getCpu() {
        return cpu;
    }

    public String getRam() {
        return ram;
    }

    public String getOs() {
        return os;
    }

    @Override
    public String toString() {
        return "Computer{" +
                "id=" + id +
                ", inventoryNumber='" + inventoryNumber + '\'' +
                ", model='" + model + '\'' +
                ", cpu='" + cpu + '\'' +
                ", ram='" + ram + '\'' +
                ", os='" + os + '\'' +
                '}';
    }
}
