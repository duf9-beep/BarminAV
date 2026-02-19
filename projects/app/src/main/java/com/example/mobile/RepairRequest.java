package com.example.mobile;

public class RepairRequest {
    private String id;
    private String equipmentId;
    private String equipmentName;
    private String reporterName;
    private String problemDescription;
    private String date;
    private String status;

    public RepairRequest(String id, String equipmentId, String equipmentName,
                         String reporterName, String problemDescription,
                         String date, String status) {
        this.id = id;
        this.equipmentId = equipmentId;
        this.equipmentName = equipmentName;
        this.reporterName = reporterName;
        this.problemDescription = problemDescription;
        this.date = date;
        this.status = status;
    }

    public String getId() { return id; }
    public String getEquipmentId() { return equipmentId; }
    public String getEquipmentName() { return equipmentName; }
    public String getReporterName() { return reporterName; }
    public String getProblemDescription() { return problemDescription; }
    public String getDate() { return date; }
    public String getStatus() { return status; }

    public void setId(String id) { this.id = id; }
    public void setEquipmentId(String equipmentId) { this.equipmentId = equipmentId; }
    public void setEquipmentName(String equipmentName) { this.equipmentName = equipmentName; }
    public void setReporterName(String reporterName) { this.reporterName = reporterName; }
    public void setProblemDescription(String problemDescription) { this.problemDescription =
            problemDescription; }
    public void setDate(String date) { this.date = date; }
    public void setStatus(String status) { this.status = status; }
}