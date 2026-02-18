package com.example.mobile;

import java.util.ArrayList;
import java.util.List;

public class DataStorage {
    private static DataStorage instance;
    private List<User> users;
    private List<Equipment> equipmentList;
    private List<RepairRequest> repairRequests;
    private User currentUser;

    private DataStorage() {
        users = new ArrayList<>();
        equipmentList = new ArrayList<>();
        repairRequests = new ArrayList<>();
        initializeData();
    }

    public static DataStorage getInstance() {
        if (instance == null) {
            instance = new DataStorage();
        }
        return instance;
    }

    private void initializeData() {
        users.add(new User("user", "123", "Иванов Иван Иванович",
                UserRole.USER, "Оператор", "Производство"));
        users.add(new User("manager", "123", "Петров Петр Петрович",
                UserRole.MANAGER, "Мастер участка", "Производство"));
        users.add(new User("admin", "123", "Сидоров Сидор Сидорович",
                UserRole.ADMIN, "Администратор", "ИТ"));

        equipmentList.add(new Equipment("1", "Станок фрезерный ЧПУ", "VF-2SS",
                "Haas Automation",
                "ФР-2023-001", "3 года 8 месяцев", "Цех №3, " +
                "участок механической обработки",
                "Исправен", "15.01.2025", "15.04.2025"));

        equipmentList.add(new Equipment("2", "Токарный станок с ЧПУ", "ST-20Y",
                "Haas Automation",
                "ТК-2023-045", "2 года 5 месяцев", "Цех №3, " +
                "участок токарной обработки",
                "В ремонте", "05.02.2025", "05.05.2025"));

        equipmentList.add(new Equipment("3", "Сварочный аппарат инверторный",
                "Invertec V350-PRO", "Lincoln Electric",
                "СВ-2024-012", "1 год 2 месяца", "Цех №2, " +
                "" +
                "сварочный участок",
                "Исправен", "20.01.2025", "20.04.2025"));
        equipmentList.add(new Equipment("4", "Компрессор винтовой", "GA 37 VSD",
                "Atlas Copco",
                "КМ-2023-089", "4 года", "Компрессорная станция",
                "В ремонте", "28.01.2025", "28.04.2025"));
    }

    public User authenticate(String login, String password) {
        for (User user : users) {
            if (user.getLogin().equals(login) && user.getPassword().equals(password)) {
                currentUser = user;
                return user;
            }
        }
        return null;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void logout() {
        currentUser = null;
    }

    public List<Equipment> getAllEquipment() {
        return equipmentList != null ? equipmentList : new ArrayList<>();
    }

    public Equipment getEquipmentById(String id) {
        if (equipmentList == null) return null;
        for (Equipment eq : equipmentList) {
            if (eq.getId().equals(id)) return eq;
        }
        return null;
    }

    public void addEquipment(Equipment equipment) {
        if (equipmentList != null) {
            equipmentList.add(equipment);
        }
    }

    public void updateEquipment(Equipment equipment) {
        if (equipmentList == null) return;
        for (int i = 0; i < equipmentList.size(); i++) {
            if (equipmentList.get(i).getId().equals(equipment.getId())) {
                equipmentList.set(i, equipment);
                break;
            }
        }
    }

    public void deleteEquipment(String id) {
        if (equipmentList == null) return;
        equipmentList.removeIf(eq -> eq.getId().equals(id));
    }

    public List<RepairRequest> getRepairRequests() {
        return repairRequests != null ? repairRequests : new ArrayList<>();
    }

    public void addRepairRequest(RepairRequest request) {
        if (repairRequests == null) {
            repairRequests = new ArrayList<>();
        }
        repairRequests.add(request);

        Equipment eq = getEquipmentById(request.getEquipmentId());
        if (eq != null) {
            eq.setStatus("В ремонте");
        }
    }

    public List<RepairRequest> getRequestsByStatus(String status) {
        List<RepairRequest> filtered = new ArrayList<>();
        if (repairRequests == null) return filtered;

        for (RepairRequest req : repairRequests) {
            if (req.getStatus().equals(status)) {
                filtered.add(req);
            }
        }
        return filtered;
    }
}