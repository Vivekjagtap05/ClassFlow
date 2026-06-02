package model;

public class User {

    private int id;
    private String username;
    private String password;
    private String role; // ADMIN, TEACHER, STUDENT, ACCOUNTANT

    // Default constructor (REQUIRED)
    public User() {}

    // Constructor used after login (SAFE)
    public User(int id, String username, String role) {
        this.id = id;
        this.username = username;
        this.role = role;
    }

    // ================= GETTERS & SETTERS =================

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    // Password kept for DB mapping / future hashing
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }
    public void setRole(String role) {
        this.role = role;
    }
}
