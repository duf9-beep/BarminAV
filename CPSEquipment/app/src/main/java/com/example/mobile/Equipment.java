package com.example.mobile;

public class Equipment {
    private String id;
    private String name;
    private String model;
    private String manufacturer;
    private String inventoryNumber;
    private String operationTime;
    private String location;
    private String status;
    private String lastMaintenance;
    private String nextMaintenance;


    public Equipment(String id, String name, String model, String manufacturer,
                     String inventoryNumber, String operationTime, String location,
                     String status, String lastMaintenance, String nextMaintenance) {
        this.id = id;
        this.name = name;
        this.model = model;
        this.manufacturer = manufacturer;
        this.inventoryNumber = inventoryNumber;
        this.operationTime = operationTime;
        this.location = location;
        this.status = status;
        this.lastMaintenance = lastMaintenance;
        this.nextMaintenance = nextMaintenance;
    }


    public String getId() { return id; }
    public String getName() { return name; }
    public String getModel() { return model; }
    public String getManufacturer() { return manufacturer; }
    public String getInventoryNumber() { return inventoryNumber; }
    public String getOperationTime() { return operationTime; }
    public String getLocation() { return location; }
    public String getStatus() { return status; }
    public String getLastMaintenance() { return lastMaintenance; }
    public String getNextMaintenance() { return nextMaintenance; }


    public void setId(String id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setModel(String model) { this.model = model; }
    public void setManufacturer(String manufacturer) { this.manufacturer = manufacturer; }
    public void setInventoryNumber(String inventoryNumber) { this.inventoryNumber =
            inventoryNumber; }
    public void setOperationTime(String operationTime) { this.operationTime = operationTime; }
    public void setLocation(String location) { this.location = location; }
    public void setStatus(String status) { this.status = status; }
    public void setLastMaintenance(String lastMaintenance) { this.lastMaintenance =
            lastMaintenance; }
    public void setNextMaintenance(String nextMaintenance) { this.nextMaintenance =
            nextMaintenance; }
}