package GraphicalView;

public class UserInTable {
    private String username;
    private String name;
    private String lastName;
    private String status;

    public UserInTable(String username, String name, String lastName, String status) {
        this.username = username;
        this.name = name;
        this.lastName = lastName;
        this.status = status;
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


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
