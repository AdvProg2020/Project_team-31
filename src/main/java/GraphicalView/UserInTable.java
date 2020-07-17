package GraphicalView;

public class UserInTable {
    private String username;
    private String name;
    private String lastName;

    public UserInTable(String username, String name, String lastName) {
        this.username = username;
        this.name = name;
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public String getName() {
        return name;
    }

    public String getLastName() {
        return lastName;
    }
}
