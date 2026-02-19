package com.example.mobile;

public class User {
    private String login;
    private String password;
    private String fullName;
    private UserRole role;
    private String position;
    private String department;

    public User(String login, String password, String fullName, UserRole role, String position,
                String department) {
        this.login = login;
        this.password = password;
        this.fullName = fullName;
        this.role = role;
        this.position = position;
        this.department = department;
    }

    public String getLogin() { return login; }
    public String getPassword() { return password; }
    public String getFullName() { return fullName; }
    public UserRole getRole() { return role; }
    public String getPosition() { return position; }
    public String getDepartment() { return department; }

    public void setLogin(String login) { this.login = login; }
    public void setPassword(String password) { this.password = password; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public void setRole(UserRole role) { this.role = role; }
    public void setPosition(String position) { this.position = position; }
    public void setDepartment(String department) { this.department = department; }
}