package model.shop;

public class ShopKeeper {
    private int id;
    private String username;
    private String password;
    private String name;
    private String contact;

    // Constructors
    public ShopKeeper() {}

    public ShopKeeper(int id, String username, String password, String name, String contact) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.name = name;
        this.contact = contact;
    }

    // Getters & Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getContact() { return contact; }
    public void setContact(String contact) { this.contact = contact; }

    @Override
    public String toString() {
        return name + " (" + username + ")";
    }
}
